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
    public void move() {
        //TODO move
    }

    @Override
    public abstract void draw();
}
