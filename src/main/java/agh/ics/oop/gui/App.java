package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.List;



public class App extends Application {

    private final AbstractWorldMap field = new GrassField(10);
    private final GridPane root = new GridPane();


    //application initiation
    public void init() {

        String[] args = getParameters().getRaw().toArray(new String[0]);
        List<MoveDirection> directions= OptionsParser.parse(args);
        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        IEngine engine = new SimulationEngine(directions, field, positions);
        engine.run();
    }

    @Override
    public void start(Stage primaryStage) {

        root.setGridLinesVisible(true);
        setGrid();

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    //setting grid
    public void setGrid() {
        int width = Math.abs(field.getBottomLeft().x - field.getTopRight().x);
        int height = Math.abs(field.getBottomLeft().y - field.getTopRight().y);

        Vector2d bl = field.getBottomLeft();

        int minusRow=0;
        int plusCol=0;

        for(int i =1 ; i<height+2;i++){
            Label label = new Label(String.valueOf(field.getTopRight().y+minusRow));
            minusRow--;
            addLabel(label,i,0);
        }

        for (int j = 1; j< width+2;j++) {
            Label label = new Label(String.valueOf(field.getBottomLeft().x+plusCol));
            plusCol++;
            addLabel(label,0,j);
        }

        for (int i = 0; i < height+2;i++) {
            for (int j=0; j<width+2;j++) {
                if (field.isOccupied(new Vector2d(bl.x+j,bl.y+i))){
                    addObject(field.objectAt(new Vector2d(bl.x+j,bl.y+i)), j+1, height-i+1);
                }
            }
        }

    }


    public void addObject(Object o, int col, int row) {
        try {
            GuiElementBox guiElementBox = new GuiElementBox((IMapElement) o);
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






}