package com.divashchenko;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Ball extends Figure {

    Ball(GraphicsContext gc, double x, double y, List<Shape> shapes) {
        super(gc, x, y, shapes);
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