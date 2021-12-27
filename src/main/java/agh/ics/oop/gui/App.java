package agh.ics.oop.gui;

import agh.ics.oop.WorldClasses.*;
import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IMapObserver;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;


public class App extends Application implements IMapObserver {


    //map properties
    private Map field;
    public int moveDelay= 300;
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
    private int DAY = 0;


    //application constants
    private static int APPHEIGHT=600;
    private static int APPWIDTH=1000;
    private static int RGBSIZE=255;

    //engine
    private SimulationEngine engine;
    private Thread engineThread;

    private GridPane root;

    //plot
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    final LineChart<Number,Number> lineChart =
            new LineChart<Number,Number>(xAxis,yAxis);

    XYChart.Series numOfAnimals = new XYChart.Series();
    XYChart.Series numOfGrass = new XYChart.Series();
    XYChart.Series avgEnergy = new XYChart.Series();
    XYChart.Series avgLifeLen = new XYChart.Series();


    public void setProperties(Map map, int MapHeight, int MapWidth, double JungleRatio, int StartEnergy,
                              int PlantEnergy,int moveEnergy, int animalsAtStart) {

        this.field = map;
        this.mapWidth = MapWidth;
        this.mapHeight= MapHeight;
        this.jungleRatio= JungleRatio;
        this.startEnergy= StartEnergy;
        this.plantEnergy= PlantEnergy;
        this.moveEnergy = moveEnergy;
        this.animalsAtStart = animalsAtStart;
    }


    public void init() {

        root = new GridPane();
        jungleBL = field.getJungleBL();
        jungleTR = field.getJungleTR();

        engine = new SimulationEngine(this.field, this.animalsAtStart,this.mapWidth,this.mapHeight);
        engine.addObserver(this);
        engineThread = new Thread(engine);
        engineThread.start();

        lineChart.getData().addAll(numOfAnimals, numOfGrass, avgLifeLen, avgEnergy);
        lineChart.setCreateSymbols(false);

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
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                stop();
            }
        });
        Group group = new Group(lineChart);
        VBox x = new VBox(root, hbButtons);
        xAxis.setLabel("Days");
        x.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(x, group);


        Scene scene = new Scene(hBox, APPWIDTH, APPHEIGHT);
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

    @Override
    public void stop() {
        engineThread.stop();
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

        for (int i = 1; i < height+2;i++) {
            for (int j=1; j<width+1;j++) {
                Vector2d positionAtMap = new Vector2d(j,i);

                if (field.isOccupied(positionAtMap)){
                    for(Object object: (ArrayList)field.objectAt(positionAtMap)) {

                        Animal objectAsAnimal = (Animal) object;

                        //background color depending on animal's energy
                        int red = Math.min(RGBSIZE, Math.max(0,objectAsAnimal.getEnergy()));
                        int blue = Math.min(RGBSIZE, Math.max(0,objectAsAnimal.getEnergy()));
                        int green = Math.max(0,Math.min(RGBSIZE,RGBSIZE-objectAsAnimal.getEnergy()));

                        addObject(object, j+1, height-i+1,red, green,blue, 1);
                    }
                }
                else if(positionAtMap.precedes(jungleTR) && positionAtMap.follows(jungleBL)) {
                    addLabel(new Label(),height-i+1, j+1, 0,126,0);
                }
                if (field.isOccupiedByGrass(positionAtMap)) {
                    if (positionAtMap.precedes(jungleTR) && positionAtMap.follows(jungleBL)) {
                        addObject(field.grassAt(new Vector2d(j,i)), j+1, height-i+1, RGBSIZE,RGBSIZE, 0, 0.6);
                    }
                    else {
                        addObject(field.grassAt(new Vector2d(j,i)), j+1, height-i+1, RGBSIZE,RGBSIZE, 0,1);
                    }
                }
            }
        }

        Label yx= new Label("y/x");
        GridPane.setHalignment(yx, HPos.CENTER);
        root.add(yx, 0,0);
    }



    //to include pictures uncomment commented lines in this method
    // althogh, it is not recommended
    public void addObject(Object o, int col, int row,int red, int green, int blue, double opacity) {
//        try {
//            IMapElement y = (IMapElement) o;
//            GuiElementBox guiElementBox = new GuiElementBox(y);
//            VBox x = guiElementBox.getVBox();
        Pane x=new Pane();
        x.setPrefHeight(APPHEIGHT/2/mapHeight);
        x.setPrefWidth(APPWIDTH/2/mapWidth);

        if(o instanceof Animal) {
            Button btn = new Button();
            btn.setPrefHeight(APPHEIGHT/2/mapHeight+3);
            btn.setMinHeight(APPHEIGHT/2/mapHeight+3);
            btn.setPrefWidth(APPWIDTH/2/mapWidth);
            btn.setMinWidth(APPWIDTH/2/mapWidth);
            btn.setMaxSize(APPWIDTH/2/mapWidth, APPHEIGHT/2/mapHeight);
            x.getChildren().add(btn);
            btn.setOnAction(event -> {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText(((Animal) o).getGenes().toString());
                a.setHeaderText("animal gene");
                a.show();
            });
            btn.setBackground(new Background(new BackgroundFill(Color.rgb(red, green, blue, opacity), CornerRadii.EMPTY, Insets.EMPTY)));
        }

        x.setBackground(new Background(new BackgroundFill(Color.rgb(red, green, blue, opacity), CornerRadii.EMPTY, Insets.EMPTY)));
        GridPane.setRowIndex(x,row);
        GridPane.setColumnIndex(x,col);

        root.getChildren().add(x);
//        }
//        catch (FileNotFoundException ex) {
//            System.exit(0);
//        }
    }


    public void addLabel(Label label, int i, int j, int red, int green, int blue){
        label.setPrefHeight(APPHEIGHT/2/mapHeight);
        label.setPrefWidth(APPWIDTH/2/mapWidth);
        label.setBackground(new Background(new BackgroundFill(Color.rgb(red, green, blue, 0.7), CornerRadii.EMPTY, Insets.EMPTY)));
        GridPane.setRowIndex(label,i);
        GridPane.setColumnIndex(label,j);
        label.setAlignment(Pos.CENTER);
        root.getChildren().add(label);

    }

    public void updateChart() {

        numOfAnimals.getData().add(new XYChart.Data(DAY, engine.getNumOfLivingAnimals()));
        if(engine.getAvgLifeLen()>0) {
            avgLifeLen.getData().add(new XYChart.Data(DAY, engine.getAvgLifeLen()));

        }
        avgEnergy.getData().add(new XYChart.Data(DAY, engine.getAvarageEnergy()));
        numOfGrass.getData().add(new XYChart.Data(DAY, engine.getNumOfGrass()));
    }




    //app being informed about passing day
    @Override
    public void simulateDay() {

        Platform.runLater(() -> {
            //map
            root.setGridLinesVisible(false);
            root.getChildren().clear();
            setGrid();
            root.setGridLinesVisible(true);

            //plot
            DAY++;
            updateChart();
        });
        try {
            Thread.sleep(moveDelay);
        }
        catch (InterruptedException ex) {
            System.exit(0);
        }

    }


}