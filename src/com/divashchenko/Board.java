package com.divashchenko;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public enum Figures {
        BALL, SQUARE, TRIANGLE
    }

    private GraphicsContext gc;

    private List<Shape> shapes = new ArrayList<>();
    private Figure mainFigure;

    public Board(GraphicsContext gc) {
        this.gc = gc;
    }

    public void move(Moves moves) {
        if (mainFigure != null) {
            mainFigure.move(moves);
        }
    }

    public void draw() {
        clean();

        if (shapes.size() > 0) {
            if (mainFigure != null) {
                mainFigure.draw();
            }
            for (Shape shape : shapes) {
                shape.drawStroke();
            }
        }
    }

    public void addFigure(Figures figures) {
        switch (figures) {
            case BALL:
                shapes.add(new Ball(gc, 10, 10, shapes));
                mainFigure = (Figure) shapes.get(shapes.size() - 1);
                break;
            case SQUARE:
                shapes.add(new Square(gc, 10, 10, shapes));
                mainFigure = (Figure) shapes.get(shapes.size() - 1);
                break;
            case TRIANGLE:
                shapes.add(new Triangle(gc, 10, 10, shapes));
                mainFigure = (Figure) shapes.get(shapes.size() - 1);
                break;
        }
    }

    public void changeFigure() {
        if (mainFigure != null && shapes.size() > 1) {
            try {
                mainFigure = (Figure) shapes.get(shapes.indexOf(mainFigure) + 1);
            } catch (IndexOutOfBoundsException e) {
                mainFigure = (Figure) shapes.get(0);
            }
        }
    }

    public void deleteFigure() {
        if (mainFigure != null && shapes.size() > 0) {
            try {
                Figure tmp = mainFigure;
                shapes.remove(mainFigure);
                if (shapes.size() > 0) {
                    mainFigure = (Figure) shapes.get(shapes.indexOf(tmp) + 1);
                } else {
                    clean();
                }
            } catch (IndexOutOfBoundsException e) {
                mainFigure = (Figure) shapes.get(0);
            }
        }
    }

    public void cloneFigure() {
        if (mainFigure != null) {
            if (mainFigure instanceof Ball) {
                shapes.add(new Ball(mainFigure));
            } else if (mainFigure instanceof Square) {
                shapes.add(new Square(mainFigure));
            } else if (mainFigure instanceof Triangle) {
                shapes.add(new Triangle(mainFigure));
            }
            mainFigure = (Figure) shapes.get(shapes.size() - 1);
        }
    }

    public void resizePlus() {
        if (mainFigure != null) {
            mainFigure.diameter += 5;
        }
    }

    public void resizeMinus() {
        if (mainFigure != null) {
            if (mainFigure.diameter > 5) {
                mainFigure.diameter -= 5;
            }
        }
    }

    private void clean() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

}