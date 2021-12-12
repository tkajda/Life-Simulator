package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;


public class App extends Application implements IPositionChangeObserver {

    private AbstractWorldMap field;
    private List<MoveDirection> directions;
    public int moveDelay= 300;


    TextField textField;
    SimulationEngine engine;
    GridPane root;


    public void init() {

        Vector2d[] positions = {new Vector2d(2, 2), new Vector2d(3, 4)};
        this.textField = new TextField();
        this.field  = new GrassField(10);
        this.root = new GridPane();
        this.engine = new SimulationEngine( field, positions);
        engine.addObserver(this);

    }

    @Override
    public void start(Stage primaryStage) {


        setGrid();
        Button move = new Button("Start");
        textField.setPrefWidth(300);

        move.setOnAction( event -> {
            onEvent();
        });

        VBox x = new VBox(root, move, textField);
        x.setSpacing(15);
        x.setAlignment(Pos.CENTER);
        Scene scene = new Scene(x, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public void onEvent() {
        String[] args = textField.getText().split(" ");
        List<MoveDirection> directions= OptionsParser.parse(args);
        engine.setMoves(directions);
        Thread engineThread = new Thread(engine);
        engineThread.start();
    }



    //setting grid
    public void setGrid() {
        int width = Math.abs(field.getBottomLeft().x - field.getTopRight().x);
        int height = Math.abs(field.getBottomLeft().y - field.getTopRight().y);
        Vector2d bl = field.getBottomLeft();
        int minusRow=0;
        int plusCol=0;

        root.setGridLinesVisible(true);


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


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Platform.runLater(() -> {
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
}