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
    TextField jungleRatio = new TextField();
    TextField width = new TextField();
    TextField height = new TextField();
    TextField startEnergy = new TextField();
    TextField plantEnergy = new TextField();





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



        VBox x = new VBox(widthVBox, heightVBox,  startEnergyVBox, plantEnergyVBox, jungleRatioVBox, move);

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



        Platform.runLater(new Runnable() {
            public void run() {
                App application = new App();
                application.init();
                application.setProperties(mapHeight,mapWidth,jungleRat,startE,plantE);
                application.start(new Stage());
            }
        });
    }


}
