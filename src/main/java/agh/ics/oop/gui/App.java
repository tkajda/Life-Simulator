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
    public int moveDelay= 200;
    private int mapWidth;
    private int mapHeight;
    private double jungleRatio;
    private int startEnergy;
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
    Thread engineThread;
    private static int APPSIZE=600;
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
        HBox hbButtons = new HBox();
        Button move = new Button("Start");
        Button stop = new Button("Stop");
        hbButtons.setSpacing(10.0);
        hbButtons.setAlignment(Pos.BOTTOM_CENTER);
        hbButtons.getChildren().addAll(move,stop);

        move.setOnAction( event -> {
            onEvent();
        });

        stop.setOnAction( event -> {
            onEventStop();
        });

        VBox x = new VBox(root, hbButtons);
        x.setSpacing(15);
        x.setAlignment(Pos.CENTER);
        Scene scene = new Scene(x, APPSIZE, APPSIZE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onEventStop() {
        engineThread.stop();
    }

    public void onEvent() {
        this.engineThread = new Thread(engine);
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

        for(int i =1 ; i<height+1;i++){
            Label label = new Label(String.valueOf(mapHeight-1+minusRow));
            minusRow--;
            addLabel(label,i,0,RGBSIZE,RGBSIZE,RGBSIZE);
        }

        for (int j = 1; j< width+1;j++) {
            Label label = new Label(String.valueOf(plusCol));
            plusCol++;
            addLabel(label,0,j, RGBSIZE,RGBSIZE, RGBSIZE);
        }

        for (int i = 0; i < height+1;i++) {
            for (int j=0; j<width+1;j++) {
                Vector2d positionAtMap = new Vector2d(j,i);

                if (field.isOccupied(positionAtMap)){
                    for(Object object: (ArrayList)field.objectAt(positionAtMap)) {

                        Animal objectAsAnimal = (Animal) object;

                        //background color depending on animal's energy
                        int red = Math.min(RGBSIZE, Math.max(0,objectAsAnimal.getEnergy()));
                        int blue = Math.min(RGBSIZE, Math.max(0,objectAsAnimal.getEnergy()));
                        int green = Math.max(0,Math.min(RGBSIZE,RGBSIZE-objectAsAnimal.getEnergy()));

                        addObject(object, j+1, height-i+1,red, green,blue);
                    }
                }
                else if(positionAtMap.precedes(jungleTR) && positionAtMap.follows(jungleBL)) {
                    addLabel(new Label(),height-i+1, j+1, 0,126,0);
                }
                if (field.isOccupiedByGrass(new Vector2d(j,i))) {
                    addObject(field.grassAt(new Vector2d(j,i)), j+1, height-i+1, RGBSIZE,RGBSIZE, 0);
                }
            }
        }

        Label yx= new Label("y/x");
        GridPane.setHalignment(yx, HPos.CENTER);
        root.add(yx, 0,0);
    }



    //to include pictures uncomment commented lines in this method and comment 173 line
    // althogh, it is not recommended, because causes problems with threads
    public void addObject(Object o, int col, int row,int red, int green, int blue) {
//        try {
//            IMapElement y = (IMapElement) o;
//            GuiElementBox guiElementBox = new GuiElementBox(y);
//            VBox x = guiElementBox.getVBox();
            VBox x = new VBox();
            x.setBackground(new Background(new BackgroundFill(Color.rgb(red, green, blue, 1), CornerRadii.EMPTY, Insets.EMPTY)));
            GridPane.setRowIndex(x,row);
            GridPane.setColumnIndex(x,col);

            x.setAlignment(Pos.CENTER);
            root.getChildren().add(x);
//        }
//        catch (FileNotFoundException ex) {
//            System.exit(0);
//        }
    }


    public void addLabel(Label label, int i, int j, int red, int green, int blue){
        label.setPrefHeight(APPSIZE/mapHeight);
        label.setPrefWidth(APPSIZE/mapWidth);
        label.setBackground(new Background(new BackgroundFill(Color.rgb(red, green, blue, 0.7), CornerRadii.EMPTY, Insets.EMPTY)));
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