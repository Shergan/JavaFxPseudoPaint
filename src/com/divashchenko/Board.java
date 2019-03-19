package com.divashchenko;

import com.divashchenko.Save.Save;
import com.divashchenko.Shapes.*;
import com.divashchenko.Technical.Moves;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            if (shapes.size() > shapes.indexOf(mainFigure) + 1) {
                mainFigure = (Figure) shapes.get(shapes.indexOf(mainFigure) + 1);
            } else {
                mainFigure = (Figure) shapes.get(0);
            }
        }
    }

    public void deleteFigure() {
        if (mainFigure != null && shapes.size() > 0) {
            shapes.remove(mainFigure);
            if (shapes.size() > 0) {
                mainFigure = (Figure) shapes.get(0);
            } else {
                clean();
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
        Save save = new Save(shapes, shapes.indexOf(mainFigure));

        JSONSerializer serializer = new JSONSerializer().prettyPrint(true);
        String js = serializer.deepSerialize(save);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("save.txt"))) {
            bw.write(js);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            String fileString = new String(Files.readAllBytes(Paths.get("save.txt")), StandardCharsets.UTF_8);

            JSONDeserializer<Save> deserializer = new JSONDeserializer<>();
            Save save2 = deserializer.deserialize(fileString);
            List<Shape> shapesTmp = save2.getShapeList();

            for (Shape shape : shapesTmp) {
                if (shape instanceof Group) {
                    ((Group) shape).setGc(gc);
                    for (int j = 0; j < ((Group) shape).getShapesInGroup().size(); j++) {
                        Figure tmpFigure = ((Group) shape).getShapesInGroup().get(j);
                        tmpFigure.setGc(gc);
                    }

                    continue;
                }
                ((Figure) shape).setGc(gc);
            }

            shapes = shapesTmp;
            mainFigure = (Figure) shapes.get(save2.getMainFigureIndex());

            //TODO fix loader
        } catch (IOException e) {
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