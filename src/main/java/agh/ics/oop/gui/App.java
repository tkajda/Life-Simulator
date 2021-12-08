package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.List;



public class App extends Application {

    private final AbstractWorldMap field = new GrassField(10);
    private final GridPane root = new GridPane();


    public void init() {

        String[] args = getParameters().getRaw().toArray(new String[0]);
        List<MoveDirection> directions= OptionsParser.parse(args);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine = new SimulationEngine(directions, field, positions);
        engine.run();
    }

    @Override
    public void start(Stage primaryStage) {

        Label label = new Label("Zwierzak");

        root.setGridLinesVisible(true);

        setGrid();

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addLabel(Label label, int i, int j){
        label.setPrefHeight(60);
        label.setPrefWidth(60);

        GridPane.setRowIndex(label,i);
        GridPane.setColumnIndex(label,j);
        GridPane.setHalignment(label, HPos.CENTER);
        label.setAlignment(Pos.CENTER);

        root.getChildren().add(label);
    }


    public int getWidth() {
        return Math.abs(field.getBottomLeft().x - field.getTopRight().x);
    }
    public int getHeight() {
        return Math.abs(field.getBottomLeft().y - field.getTopRight().y);
    }


    public void setGrid() {
        int width = getWidth();
        int height = getHeight();

        Vector2d bl = field.getBottomLeft();

        int minusRow=0;
        int plusCol=0;

        for(int i =0 ; i<height+2;i++){
            for(int j =0 ; j<width+2;j++){

                if (i==0 && j>0) {
                    Label label = new Label(String.valueOf(field.getBottomLeft().x+plusCol));
                    plusCol++;
                    addLabel(label,i,j);

                }
                if (j==0 && i>0) {
                    Label label = new Label(String.valueOf(field.getTopRight().y+minusRow));
                    minusRow--;
                    addLabel(label,i,j);
                }
                else if (field.isOccupied(new Vector2d(i+bl.x,j+bl.y))) {

                    Label label = new Label(
                            field.objectAt(new Vector2d(i+bl.x,j+bl.y)).toString());
                    addLabel(label,i-bl.x,j-bl.y);
                }
                else {
                    Label label = new Label();
                    addLabel(label,i,j);
                }
            }
        }


    }

}
