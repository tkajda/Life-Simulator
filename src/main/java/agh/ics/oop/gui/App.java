package agh.ics.oop.gui;

import agh.ics.oop.WorldClasses.Animal;
import agh.ics.oop.WorldClasses.Map;
import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IMapObserver;
import agh.ics.oop.WorldClasses.SimulationEngine;
import agh.ics.oop.WorldClasses.Vector2d;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class App extends Application implements IMapObserver, Runnable {

    private Map field;
    public int moveDelay= 300;
    private int mapWidth; //placeholder
    private int mapHeight; //placeholder
    private double jungleRatio; //placeholder
    private int startEnergy; //placeholder
    private int plantEnergy;
    private int moveEnergy;
    private int animalsAtStart;
    private Vector2d jungleBL;
    private Vector2d jungleTR;
    private Vector2d mapBL =  new Vector2d(0,0);
    private Vector2d mapTR = new Vector2d (mapWidth-1, mapHeight-1);
    SimulationEngine engine;
    GridPane root;
    GridPane stats;


    private static int RGBSIZE=255;


    public void setProperties(int MapHeight, int MapWidth, double JungleRatio, int StartEnergy, int PlantEnergy,int moveEnergy, int animalsAtStart) {
        this.mapWidth = MapWidth;
        this.mapHeight= MapHeight;
        this.jungleRatio= JungleRatio;
        this.startEnergy= StartEnergy;
        this.plantEnergy= PlantEnergy;
        this.moveEnergy = moveEnergy;
        this.animalsAtStart = animalsAtStart;
    }


    public void init() {

        this.field  = new Map(this.mapHeight,this.mapWidth,this.jungleRatio,this.startEnergy,this.plantEnergy, this.moveEnergy);
        this.engine = new SimulationEngine(this.field, this.animalsAtStart,this.mapWidth,this.mapHeight);
        engine.addObserver(this);
        this.root = new GridPane();
        this.jungleBL = field.getJungleBL();
        this.jungleTR = field.getJungleTR();

    }

    @Override
    public void start(Stage primaryStage) {

        setGrid();
        root.setGridLinesVisible(true);
        Button move = new Button("Start");

        move.setOnAction( event -> {
            onEvent();
        });

        Label avarageLifeLen = new Label("average life length: ");
        Label aliveAnimals = new Label("number of animals alive:");
        VBox stats = new VBox(avarageLifeLen,aliveAnimals);

        VBox x = new VBox(root,stats, move);
        x.setSpacing(15);
        x.setAlignment(Pos.CENTER);
        Scene scene = new Scene(x, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void onEvent() {
        Thread engineThread = new Thread(engine);
        engineThread.start();
    }


    public void stop() {
        System.exit(0);
    }



    //making new grid based on map
    public void setGrid() {
        int width = mapWidth;
        int height = mapHeight;
        Vector2d bl = new Vector2d(0,0);
        int minusRow=0;
        int plusCol=0;

        for(int i =1 ; i<height+2;i++){
            Label label = new Label(String.valueOf(mapHeight+minusRow));
            minusRow--;
            addLabel(label,i,0);
        }

        for (int j = 1; j< width+2;j++) {
            Label label = new Label(String.valueOf(plusCol));
            plusCol++;
            addLabel(label,0,j);
        }

//        for (int i = 0; i < height+2;i++) {
//            for (int j=0; j<width+2;j++) {
//                Vector2d positionAtMap = new Vector2d(j,i);
//
//                if (field.isOccupied(positionAtMap)){
//                    for(Object object: (ArrayList)field.objectAt(positionAtMap)) {
//
//                        Animal objectAsAnimal = (Animal) object;
//                        int color1 = Math.min(RGBSIZE, objectAsAnimal.getEnergy());
//                        int color2 = Math.max(0,RGBSIZE-objectAsAnimal.getEnergy());
//                        addObject(object, j+1, height-i+1,color1, color2);
//                    }
//                }
//                else if (field.isOccupiedByGrass(new Vector2d(j,i))) {
//                    addObject(field.grassAt(new Vector2d(j,i)), j+1, height-i+1, RGBSIZE, 50);
//                }
//            }
//        }

        Label yx= new Label("y/x");
        GridPane.setHalignment(yx, HPos.CENTER);
        root.add(yx, 0,0);
    }




    public void addObject(Object o, int col, int row,int color1, int color2) {
        try {
            IMapElement y = (IMapElement) o;
            GuiElementBox guiElementBox = new GuiElementBox(y);
            VBox x = guiElementBox.getVBox();
            x.setBackground(new Background(new BackgroundFill(Color.rgb(0, color1, color2, 0.7), CornerRadii.EMPTY, Insets.EMPTY)));
            GridPane.setRowIndex(x,row);
            GridPane.setColumnIndex(x,col);

            x.setAlignment(Pos.CENTER);
            root.getChildren().add(x);
        }
        catch (FileNotFoundException ex) {
            System.exit(0);
        }
    }


    public void addLabel(Label label, int i, int j){
        label.setPrefHeight(60);
        label.setPrefWidth(60);

        GridPane.setRowIndex(label,i);
        GridPane.setColumnIndex(label,j);
        label.setAlignment(Pos.CENTER);
        root.getChildren().add(label);

    }


    //app being informed about passing day
    @Override
    public void simulateDay() {

        Platform.runLater(() -> {
            root.setGridLinesVisible(false);
            root.getChildren().clear();
            setGrid();
            root.setGridLinesVisible(true);
        });
        try {
            Thread.sleep(moveDelay);
        }
        catch (InterruptedException ex) {
            System.exit(0);
        }

    }

    @Override
    public void run() {

    }
}