package com.divashchenko;

import com.divashchenko.Shapes.*;
import com.divashchenko.Technical.Moves;
import com.google.gson.Gson;
import javafx.scene.canvas.GraphicsContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Board {

    public enum Figures {
        BALL, SQUARE, TRIANGLE
    }

    private transient GraphicsContext gc;

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
            } else if (mainFigure instanceof Group) {
                shapes.add(new Group(mainFigure));
            }
            mainFigure = (Figure) shapes.get(shapes.size() - 1);
        }
    }

    public void resize(boolean type) {
        if (mainFigure != null) {
            if (type) {
                mainFigure.resize(true);
            } else {
                mainFigure.resize(false);
            }
        }
    }

    public void merge(int findX, int findY) {
        for (Shape shape : shapes) {
            if (shape == mainFigure) {
                continue;
            }

            if (!(shape instanceof Group)) {
                if (checkDistance((Figure) shape, findX, findY)) {
                    addToGroup((Figure) shape);
                    break;
                }
            } else {
                for (int j = 0; j < ((Group) shape).getShapesInGroup().size(); j++) {
                    if (checkDistance(((Group) shape).getShapesInGroup().get(j), findX, findY)) {
                        addToGroup((Figure) shape);
                        break;
                    }
                }
            }
        }
    }

    public void save() {
        Gson gson = new Gson();

        try (FileWriter file = new FileWriter("save.json")) {
            for (int i = 0; i < shapes.size(); i++) {
                file.write(gson.toJson(shapes.get(i).getClass().getName()));
                String jsonShape = gson.toJson(shapes.get(i));
                file.write(jsonShape);
                file.write("\n");
            }
            String jsonMainFigure = gson.toJson(mainFigure);
            file.write("MainFigure");
            file.write(jsonMainFigure);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void load() {
        Gson gson = new Gson();

        try {
            BufferedReader br = new BufferedReader(new FileReader("save.json"));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean checkDistance(Figure figure, int findX, int findY) {
        return figure.getX() <= findX
                && figure.getX() + figure.getDiameter() >= findX
                && figure.getY() <= findY
                && figure.getY() + figure.getDiameter() >= findY;
    }

    private void addToGroup(Figure figure) {
        if ((mainFigure instanceof Group) && (figure instanceof Group)) {
            for (int i = 0; i < ((Group) figure).getShapesInGroup().size(); i++) {
                ((Group) mainFigure).addToGroup(((Group) figure).getShapesInGroup().get(i));
            }
            shapes.remove(figure);
        } else if (mainFigure instanceof Group) {
            ((Group) mainFigure).addToGroup(figure);
            shapes.remove(figure);
        } else if (figure instanceof Group) {
            ((Group) figure).addToGroup(mainFigure);
            shapes.remove(mainFigure);
            mainFigure = figure;
        } else {
            Group group = new Group(gc, 0, 0, null);

            group.addToGroup(mainFigure);
            shapes.remove(mainFigure);

            group.addToGroup(figure);
            shapes.remove(figure);

            shapes.add(group);
            mainFigure = group;
        }
    }

    private void clean() {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

}