package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.List;


public class App extends Application {

    String[] args = getParameters().getRaw().toArray(new String[0]);
    List<MoveDirection> directions= OptionsParser.parse(args);
    IWorldMap field = new GrassField(10);


    public void init() {
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine = new SimulationEngine(directions, field, positions);
        engine.run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Label label = new Label("Zwierzak");

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);

//        int height = field.getBoundedMapTR().y - field.getBoundedMapBL().y;
//        int width = field.getBoundedMapTR().x - field.getBoundedMapBL().x;
//
//        grid.getRowConstraints().add(new RowConstraints(height));
//        grid.getColumnConstraints().add(new ColumnConstraints(width));
        GridPane.setHalignment(label, HPos.CENTER);


        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
