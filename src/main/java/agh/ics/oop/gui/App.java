package agh.ics.oop.gui;

import agh.ics.oop.WorldClasses.AbstractWorldMap;
import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IMapObserver;
import agh.ics.oop.WorldClasses.SimulationEngine;
import agh.ics.oop.WorldClasses.Vector2d;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class App extends Application implements IMapObserver, Runnable {

    private AbstractWorldMap field;
    public int moveDelay= 300;
    private int mapWidth=15; //placeholder
    private int mapHeight=15; //placeholder
    private double jungleRatio=0.4; //placeholder
    private int startEnergy=10; //placeholder
    private int plantEnergy=5;
    private Vector2d mapBL =  new Vector2d(0,0);
    private Vector2d mapTR = new Vector2d (mapWidth-1, mapHeight-1);
    GridPane root = new GridPane();
    SimulationEngine engine;


    public void setProperties(int MapHeight, int MapWidth, double JungleRatio, int StartEnergy, int PlantEnergy) {
        this.mapWidth = MapWidth;
        this.mapHeight= MapHeight;
        this.jungleRatio= JungleRatio;
        this.startEnergy= StartEnergy;
        this.plantEnergy= PlantEnergy;
    }


    public void init() {

        this.field  = new AbstractWorldMap(this.mapHeight,this.mapWidth,this.jungleRatio,this.startEnergy,this.plantEnergy);
        this.engine = new SimulationEngine(field, 20);
        engine.addObserver(this);
        System.out.println("aaa");

    }

    @Override
    public void start(Stage primaryStage) {

        setGrid();

        Button move = new Button("Start");

        move.setOnAction( event -> {
            onEvent();
        });

        VBox x = new VBox(root, move);
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

        for (int i = 0; i < height+2;i++) {
            for (int j=0; j<width+2;j++) {
                if (field.isOccupied(new Vector2d(j,i))){
                    for(Object object: (ArrayList)field.objectAt(new Vector2d(j,i))) {
                        addObject(object, j+1, height-i+1);
                    }
                }
                else if (field.isOccupiedByGrass(new Vector2d(j,i))) {
                    addObject(field.grassAt(new Vector2d(j,i)), j+1, height-i+1);
                }
            }
        }

        root.setGridLinesVisible(true);
        Label yx= new Label("y/x");
        GridPane.setHalignment(yx, HPos.CENTER);
        root.add(yx, 0,0);

    }

    public void addObject(Object o, int col, int row) {
        try {
            IMapElement y = (IMapElement) o;
            GuiElementBox guiElementBox = new GuiElementBox(y);
            VBox x = guiElementBox.getVBox();


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
        System.out.println(mapWidth);
        System.out.println(mapHeight);
        System.out.println(startEnergy);
        System.out.println(plantEnergy);
        System.out.println(jungleRatio);

    }
}