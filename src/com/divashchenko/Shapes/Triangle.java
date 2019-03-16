package com.divashchenko.Shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Triangle extends Figure {

    Triangle(GraphicsContext gc, double x, double y, List<Shape> shapes) {
        super(gc, x, y, shapes);
    }

    public Triangle(Figure figure) {
        super(figure);
    }

    @Override
    public void draw() {
        gc.setFill(Color.BLUE);
        gc.fillPolygon(new double[]{x + diameter / 2, x + diameter, x}, new double[]{y, y + diameter, y + diameter}, 3);
    }

    @Override
    public void drawStroke() {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(2);
        gc.strokePolygon(new double[]{x + diameter / 2, x + diameter, x}, new double[]{y, y + diameter, y + diameter}, 3);
    }
}
