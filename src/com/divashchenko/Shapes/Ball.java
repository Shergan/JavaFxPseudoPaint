package com.divashchenko.Shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Ball extends Figure {

    public Ball(GraphicsContext gc, double x, double y, List<Shape> shapes) {
        super(gc, x, y, shapes);
    }

    public Ball(Figure figure) {
        super(figure);
    }

    public Ball() {
    }

    @Override
    public void draw() {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, diameter, diameter);
    }

    @Override
    public void drawStroke() {
        gc.setStroke(Color.RED);
        gc.setLineWidth(2);
        gc.strokeOval(x, y, diameter, diameter);
    }

}