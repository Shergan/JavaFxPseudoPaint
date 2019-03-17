package com.divashchenko.Shapes;

import com.divashchenko.Technical.Moves;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public abstract class Figure implements Shape {

    protected double diameter = 30;

    protected GraphicsContext gc;
    protected double x;
    protected double y;
    protected List<Shape> shapes;

    public Figure(GraphicsContext gc, double x, double y, List<Shape> shapes) {
        this.gc = gc;
        this.x = x;
        this.y = y;
        this.shapes = shapes;
    }

    public Figure(Figure figure) {
        this(figure.gc, figure.x, figure.y, figure.shapes);
        this.diameter = figure.diameter;
    }

    public Figure() {
    }

    @Override
    public void move(Moves moves) {
        switch (moves) {
            case UP:
                if (y > 0) {
                    y -= 5;
                }
                break;
            case RIGHT:
                if (x + diameter < gc.getCanvas().getWidth()) {
                    x += 5;
                }
                break;
            case DOWN:
                if (y + diameter < gc.getCanvas().getHeight()) {
                    y += 5;
                }
                break;
            case LEFT:
                if (x > 0) {
                    x -= 5;
                }
                break;
        }
    }

    public void resize(boolean type) {
        if (type) {
            diameter += 5;
        } else {
            if (diameter > 5) {
                diameter -= 5;
            }
        }
    }

    public double getDiameter() {
        return diameter;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public abstract void draw();

    public abstract void drawStroke();
}
