package com.divashchenko;

import java.util.ArrayList;
import java.util.List;

public class Group extends Figure {

    protected List<Figure> shapesInGroup = new ArrayList<>();

    protected double x;
    protected double y;
    protected double diameter;

    @Override
    public void draw() {
        for (Figure figure : shapesInGroup) {
            figure.draw();
        }
    }

    @Override
    public void drawStroke() {
        for (Figure figure : shapesInGroup) {
            figure.drawStroke();
        }
    }

    @Override
    public void move(Moves moves) {
        switch (moves) {
            case UP:
                if (y > 0) {
                    for (Figure figure : shapesInGroup) {
                        figure.y -= 5;
                    }
                }
                break;
            case RIGHT:
                if (x + diameter < gc.getCanvas().getWidth()) {
                    for (Figure figure : shapesInGroup) {
                        figure.x += 5;
                    }
                }
                break;
            case DOWN:
                if (y + diameter < gc.getCanvas().getHeight()) {
                    for (Figure figure : shapesInGroup) {
                        figure.y += 5;
                    }
                }
                break;
            case LEFT:
                if (x > 0) {
                    for (Figure figure : shapesInGroup) {
                        figure.x -= 5;
                    }
                }
                break;
        }
    }

    public void addToGroup(Figure figure) {
        shapesInGroup.add(figure);
    }
}
