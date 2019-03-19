package com.divashchenko.Shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Square extends Figure {

    public Square(GraphicsContext gc, double x, double y, List<Shape> shapes) {
        super(gc, x, y, shapes);
    }

    public Square(Figure figure) {
        super(figure);
    }

    public Square() {
    }

    @Override
    public void draw() {
        gc.setFill(Color.GREEN);
        gc.fillRect(x, y, diameter, diameter);
    }

    @Override
    public void drawStroke() {
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.strokeRect(x, y, diameter, diameter);
    }

}