package com.divashchenko;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public abstract class Figure implements Shape {

    protected double diameter = 30;

    protected GraphicsContext gc;
    protected double x;
    protected double y;
    protected List<Shape> shapes;

    protected Figure(GraphicsContext gc, double x, double y, List<Shape> shapes) {
        this.gc = gc;
        this.x = x;
        this.y = y;
        this.shapes = shapes;
    }

    protected Figure(Figure figure) {
        this(figure.gc, figure.x, figure.y, figure.shapes);
        this.diameter = figure.diameter;
    }

    protected Figure() {
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
