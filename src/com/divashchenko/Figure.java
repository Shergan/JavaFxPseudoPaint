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

    @Override
    public void move(Moves moves) {
        switch (moves) {
            case UP:
                y += 5;
                break;
            case RIGHT:
                x += 5;
                break;
            case DOWN:
                y -= 5;
                break;
            case LEFT:
                x -= 5;
                break;
        }
    }

    @Override
    public abstract void draw();

    public abstract void drawStroke();
}
