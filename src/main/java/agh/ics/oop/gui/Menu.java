package agh.ics.oop.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;


public class Menu extends Application {
    TextField jungleRatio = new TextField("0.2");
    TextField width = new TextField("15");
    TextField height = new TextField("15");
    TextField startEnergy = new TextField("10");
    TextField plantEnergy = new TextField("1");
    TextField moveEnergy = new TextField("1");
    TextField animalsAtStart = new TextField("20");



    @Override
    public void start(Stage primaryStage) throws Exception {
        Label widthText = new Label("width");
        VBox widthVBox = new VBox(widthText,width);

        Label heightText = new Label("height");
        VBox heightVBox= new VBox(heightText, height);

        Label startEnergyText = new Label("Start energy:");
        VBox startEnergyVBox= new VBox(startEnergyText, startEnergy);

        Label plantEnergyText = new Label("Plant energy:");
        VBox plantEnergyVBox= new VBox(plantEnergyText,plantEnergy);

        Label jungleRatioText = new Label("Jungle ratio:");
        VBox jungleRatioVBox= new VBox(jungleRatioText, jungleRatio);

        Label animalsAtStartText = new Label("Animals at start:");
        VBox animalsAtStartVBox = new VBox(animalsAtStartText, animalsAtStart);

        Label moveEnergyText = new Label("Move Energy:");
        VBox moveEnergyVBox = new VBox(moveEnergyText,moveEnergy);

        Button move = new Button("Start");

        try {
            move.setOnAction( event -> {
                onEvent();
            });

        }
        catch(IllegalArgumentException exception){
            System.out.println("Siema");
            System.exit(0);
        }



        VBox x = new VBox(widthVBox, heightVBox,  startEnergyVBox, plantEnergyVBox, jungleRatioVBox,
                moveEnergyVBox, animalsAtStartVBox,move);

        Scene scene = new Scene(x,600, 600);
        x.setSpacing(15);
        x.setAlignment(Pos.CENTER);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void onEvent() {


        if(jungleRatio.getText().matches("[a-zA-Z]+") ||
                width.getText().matches("[a-zA-Z]+") ||
                height.getText().matches("[a-zA-Z]+") ||
                startEnergy.getText().matches("[a-zA-Z]+") ||
                plantEnergy.getText().matches("[a-zA-Z]+")) {

            throw new IllegalArgumentException("wrong data");
        }


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



        Platform.runLater(new Runnable() {
            public void run() {
                App application = new App();
                application.setProperties(mapHeight,mapWidth,jungleRat,startE,plantE,moveE, animalsAS);
                application.init();
                application.start(new Stage());
            }
        });
    }


}
