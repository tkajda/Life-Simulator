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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


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
    private Animal followedAnimal;
    private int fAnimalNumOfChildren ;


    //application constants
    private static int APPHEIGHT=600;
    private static int APPWIDTH=1000;
    private static int RGBSIZE=255;
    Random rng = new Random();

    CSVWriter csvFile = new CSVWriter("Map Statistics " + String.valueOf(rng.nextInt(1234567)));
    //engine
    private SimulationEngine engine;
    private Thread engineThread;
    private boolean isRunningEngine=true;

    private GridPane root;
    private boolean withDominantGenotype;

    //plot
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    final LineChart<Number,Number> lineChart =
            new LineChart<Number,Number>(xAxis,yAxis);

    XYChart.Series numOfAnimals = new XYChart.Series();
    XYChart.Series numOfGrass = new XYChart.Series();
    XYChart.Series avgEnergy = new XYChart.Series();
    XYChart.Series avgLifeLen = new XYChart.Series();
    XYChart.Series dominantGenotype = new XYChart.Series();
    XYChart.Series avgNumOfChildren = new XYChart.Series();




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

        numOfAnimals.setName("Animals");
        numOfGrass.setName("Grass");
        avgEnergy.setName("Average Energy");
        avgLifeLen.setName("Average Life Length");
        dominantGenotype.setName("Dominant Genotype * 10");
        avgNumOfChildren.setName("Average Number Of Children");

        lineChart.getData().addAll(numOfAnimals, numOfGrass, avgLifeLen, avgEnergy, dominantGenotype, avgNumOfChildren);
        lineChart.setCreateSymbols(false);

    }
    public void setButtonFunctions(Button move, Button stop, Button showDominant, Button addData) {
        move.setOnAction( event -> {
            if(!isRunningEngine) {
                isRunningEngine=true;
                onEvent();
            }
        });

        stop.setOnAction( event -> {
            if(isRunningEngine) {
                isRunningEngine=false;
                onEventStop();
            }
        });
        showDominant.setOnAction(event -> {
            if(!isRunningEngine) {
                setGrid(true);
            }
        });
        addData.setOnAction(event ->  {
            if(!isRunningEngine) {
                updateFile();
            }
        });

    }
    public void onEventStop() {
        engineThread.stop();
    }

    public void onEvent() {
        this.engineThread = new Thread(engine);
        engineThread.start();
    }


    @Override
    public void start(Stage primaryStage) {

        setGrid(false);
        root.setGridLinesVisible(true);
        HBox hbButtons = new HBox();
        Button move = new Button("Start");
        Button stop = new Button("Stop");
        Button showDominant = new Button("Show animals with dominant genotype");
        Button addData = new Button("Add Data");
        hbButtons.setSpacing(10.0);
        hbButtons.setAlignment(Pos.BOTTOM_CENTER);
        hbButtons.getChildren().addAll(move,stop, showDominant, addData);

        setButtonFunctions(move,stop,showDominant, addData);

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



    @Override
    public void stop() {
        engineThread.stop();
    }


    public void createGridForObjects(boolean withDominantGenotype) {
        int width = mapWidth;
        int height = mapHeight;

        for (int i = 1; i < height+2;i++) {
            for (int j=1; j<width+1;j++) {
                Vector2d positionAtMap = new Vector2d(j,i);

                if (field.isOccupied(positionAtMap)){
                    for(Object object: (ArrayList)field.objectAt(positionAtMap)) {

                        Animal objectAsAnimal = (Animal) object;
                        if (withDominantGenotype && objectAsAnimal.getGenes().equals(engine.findDominantGenotype())) {
                            addObject(object, j+1,height-i+1,0,0,0 ,1 );
                        }
                        else {
                            //background color depending on animal's energy
                            int red = Math.min(RGBSIZE, Math.max(0,objectAsAnimal.getEnergy()));
                            int blue = Math.min(RGBSIZE, Math.max(0,objectAsAnimal.getEnergy()));
                            int green = Math.max(0,Math.min(RGBSIZE,RGBSIZE-objectAsAnimal.getEnergy()));
                            if (withDominantGenotype) {
                                addObject(object, j+1, height-i+1,red, green,blue, 0.1);
                            }
                            else {
                                addObject(object, j+1, height-i+1,red, green,blue, 1);

                            }
                        }

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
    }


    //making new grid based on map
    public void setGrid(boolean parameter) {
        int width = mapWidth;
        int height = mapHeight;
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
        createGridForObjects(parameter);
        Label yx= new Label("y/x");
        GridPane.setHalignment(yx, HPos.CENTER);
        root.add(yx, 0,0);
    }
    public void setFollowedAnimal(Object o) {
        if(o instanceof Animal) {
            Animal oAsAnimal = (Animal) o;
            int currentNumOfChildren = oAsAnimal.getNumOfChildren();

        }

    }


    public void setAnimalButton(Button btn, Object o) {
        btn.setPrefHeight(APPHEIGHT/2/mapHeight);
        btn.setMinHeight(APPHEIGHT/2/mapHeight);
        btn.setPrefWidth(APPWIDTH/2/mapWidth);
        btn.setMinWidth(APPWIDTH/2/mapWidth);
        btn.setMaxSize(APPWIDTH/2/mapWidth, APPHEIGHT/2/mapHeight);
        btn.setOnAction(event -> {
            if(!isRunningEngine) {
                setFollowedAnimal(o);
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText(((Animal) o).getGenes().toString());
                a.setHeaderText("animal gene");
                a.show();
            }
        });

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
            setAnimalButton(btn, o);
            btn.setBackground(new Background(new BackgroundFill(Color.rgb(red, green, blue, opacity), CornerRadii.EMPTY, Insets.EMPTY)));
            x.getChildren().add(btn);
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
        dominantGenotype.getData().add(new XYChart.Data(DAY, engine.getDominantGene()*10));
        avgEnergy.getData().add(new XYChart.Data(DAY, engine.getAvarageEnergy()));
        numOfGrass.getData().add(new XYChart.Data(DAY, engine.getNumOfGrass()));
        avgNumOfChildren.getData().add(new XYChart.Data(DAY, engine.getAverageNumOfChildren()));
    }

    public void updateFile(){
        String[] dataArray = {
                String.valueOf(engine.getNumOfLivingAnimals()),
                String.valueOf( engine.getAvgLifeLen()),
                String.valueOf( engine.getAvarageEnergy()),
                String.valueOf(engine.getNumOfGrass()),
                String.valueOf( engine.getAverageNumOfChildren())};

        try {
            csvFile.addDataToFile(dataArray);
        }
        catch (IOException ex) {
            System.out.println("close the file");
        }
    }




    //app being informed about passing day
    @Override
    public void simulateDay() {

        Platform.runLater(() -> {
            //map
            root.setGridLinesVisible(false);
            root.getChildren().clear();
            setGrid(false);
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