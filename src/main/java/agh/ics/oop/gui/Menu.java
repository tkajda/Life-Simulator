package agh.ics.oop.gui;

import agh.ics.oop.WorldClasses.BorderlessMap;
import agh.ics.oop.WorldClasses.Map;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;


public class Menu extends Application {
    TextField jungleRatio = new TextField("0.2");
    TextField width = new TextField("25");
    TextField height = new TextField("25");
    TextField startEnergy = new TextField("255");
    TextField plantEnergy = new TextField("20");
    TextField moveEnergy = new TextField("1");
    TextField animalsAtStart = new TextField("70");

    TextField[] textFieldsMap = {width,height,startEnergy,plantEnergy,jungleRatio
            ,animalsAtStart,moveEnergy};


    //bordeless map
    TextField jungleRatioBM = new TextField("0.2");
    TextField widthBM = new TextField("15");
    TextField heightBM = new TextField("15");
    TextField startEnergyBM = new TextField("100");
    TextField plantEnergyBM = new TextField("1");
    TextField moveEnergyBM = new TextField("1");
    TextField animalsAtStartBM = new TextField("20");

    TextField[] textFieldsBMap = {widthBM,heightBM,startEnergyBM,plantEnergyBM,
            jungleRatioBM,animalsAtStartBM,moveEnergyBM};

    public VBox generateVbox(TextField[] names){
        int i = 0;
        Label widthText = new Label("width");
        VBox widthVBox = new VBox(widthText,names[i]);
        i++;
        Label heightText = new Label("height");
        VBox heightVBox= new VBox(heightText, names[i]);
        i++;
        Label startEnergyText = new Label("Start energy:");
        VBox startEnergyVBox= new VBox(startEnergyText, names[i]);
        i++;
        Label plantEnergyText = new Label("Plant energy:");
        VBox plantEnergyVBox= new VBox(plantEnergyText,names[i]);
        i++;
        Label jungleRatioText = new Label("Jungle ratio:");
        VBox jungleRatioVBox= new VBox(jungleRatioText, names[i]);
        i++;
        Label animalsAtStartText = new Label("Animals at start:");
        VBox animalsAtStartVBox = new VBox(animalsAtStartText, names[i]);
        i++;
        Label moveEnergyText = new Label("Move Energy:");
        VBox moveEnergyVBox = new VBox(moveEnergyText,names[i]);

        VBox vBox = new VBox(widthVBox, heightVBox,  startEnergyVBox, plantEnergyVBox, jungleRatioVBox,
                moveEnergyVBox, animalsAtStartVBox);
        return vBox;
    }

    private void setButtonFunctions(Button start1, Button start2) {
        start2.setOnAction( event -> {
            runBorderlessMap();
        });
        try {
            start1.setOnAction( event -> {
                onEvent();
            });
        }
        catch(IllegalArgumentException exception){
            System.exit(0);
        }
    }


    @Override
    public void start(Stage primaryStage) {
        //'normal' map
        Label label1 = new Label("Mapa");
        Button start1 = new Button("Start");
        VBox vbox1 = new VBox(label1,generateVbox(textFieldsMap),start1);

        //map where borders dont exist
        Label label2 = new Label("Mapa2");
        Button start2 = new Button("Start");
        VBox vbox2 = new VBox(label2,generateVbox(textFieldsBMap),start2);

        setButtonFunctions(start1,start2);

        HBox x = new HBox(vbox1,vbox2);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        });
        Scene scene = new Scene(x,600, 600);
        x.setSpacing(100);
        x.setAlignment(Pos.CENTER);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean checkData(TextField[] names) {
        System.out.println(names[0].getText());
        return (names[0].getText().matches("[a-zA-Z]+") || names[1].getText().matches("[a-zA-Z]+")
                || names[2].getText().matches("[a-zA-Z]+") || names[3].getText().matches("[a-zA-Z]+") ||
                names[4].getText().matches("[a-zA-Z]+"));
    }


    public void onEvent() {
        if (checkData(textFieldsMap)) {
            throw new IllegalArgumentException("Invalid values");
        };

        double jungleRat = parseDouble(jungleRatio.getText());

        if(jungleRat>=1 || jungleRat<=0) {
            throw new IllegalArgumentException();
        }
        int mapWidth = parseInt(width.getText());
        int mapHeight = parseInt(height.getText());
        int startE = parseInt(startEnergy.getText());
        int plantE = parseInt(plantEnergy.getText());
        int animalsAS = parseInt(animalsAtStart.getText());
        int moveE = parseInt(moveEnergy.getText());

        Map map = new Map(mapHeight,mapWidth,jungleRat,startE,plantE, moveE);

        Platform.runLater(() -> {

                App application = new App();
                application.setProperties(map, mapHeight,mapWidth,jungleRat,startE,plantE,moveE, animalsAS);
                application.init();
                application.start(new Stage());
        });
    }



    public void runBorderlessMap() {

        if (checkData(textFieldsBMap)) {
            throw new IllegalArgumentException("Invalid values");
        };

        double jungleRat = parseDouble(jungleRatioBM.getText());

        if(jungleRat>=1 || jungleRat<=0) {
            throw new IllegalArgumentException();
        }


        int mapWidth = parseInt(widthBM.getText());
        int mapHeight = parseInt(heightBM.getText());
        int startE = parseInt(startEnergyBM.getText());
        int plantE = parseInt(plantEnergyBM.getText());
        int animalsAS = parseInt(animalsAtStartBM.getText());
        int moveE = parseInt(moveEnergyBM.getText());

        BorderlessMap map = new BorderlessMap(mapHeight,mapWidth,jungleRat,startE,plantE, moveE);

        Platform.runLater(() -> {

            App application = new App();
            application.setProperties(map,mapHeight,mapWidth,jungleRat,startE,plantE,moveE, animalsAS);
            application.init();
            application.start(new Stage());

        });
    }
}
