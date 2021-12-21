package agh.ics.oop.gui;

import agh.ics.oop.IMapElement;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox extends App{
    public VBox vBox;

    public GuiElementBox(IMapElement object) throws FileNotFoundException {

        Image image = new Image(new FileInputStream(object.imageAddress()));

        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        this.vBox = new VBox(imageView);

    }

    public VBox getVBox() {
        return vBox;
    }


}
