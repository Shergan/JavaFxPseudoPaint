package com.divashchenko.Shapes;

import com.divashchenko.Technical.Moves;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Group extends Figure {

    protected List<Figure> shapesInGroup = new ArrayList<>();

    protected double maxX;
    protected double maxY;
    protected double minX;
    protected double minY;
    protected double diameterX;
    protected double diameterY;
    protected GraphicsContext gc;

    public Group(GraphicsContext gc, double x, double y, List<Shape> shapes) {
        super(gc, x, y, shapes);
        this.gc = gc;
        minY = gc.getCanvas().getHeight();
        minX = gc.getCanvas().getWidth();
    }

    public Group(Figure figure) {
        this(figure.gc, 0, 0, figure.shapes);
        for (int i = 0; i < ((Group) figure).shapesInGroup.size(); i++) {
            Figure tmpFigure = ((Group) figure).shapesInGroup.get(i);

            if (tmpFigure instanceof Ball) {
                addToGroup(new Ball(tmpFigure));
            } else if (tmpFigure instanceof Square) {
                addToGroup(new Square(tmpFigure));
            } else if (tmpFigure instanceof Triangle) {
                addToGroup(new Triangle(tmpFigure));
            } else if (tmpFigure instanceof Group) {
                addToGroup(new Group(tmpFigure));
            }
        }
    }

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
                if (minY > 0) {
                    for (Figure figure : shapesInGroup) {
                        figure.y -= 5;
                    }
                    minY -= 5;
                    maxY -= 5;
                }
                break;
            case RIGHT:
                if (maxX + diameterX < gc.getCanvas().getWidth()) {
                    for (Figure figure : shapesInGroup) {
                        figure.x += 5;
                    }
                    minX += 5;
                    maxX += 5;
                }
                break;
            case DOWN:
                if (maxY + diameterY < gc.getCanvas().getHeight()) {
                    for (Figure figure : shapesInGroup) {
                        figure.y += 5;
                    }
                    minY += 5;
                    maxY += 5;
                }
                break;
            case LEFT:
                if (minX > 0) {
                    for (Figure figure : shapesInGroup) {
                        figure.x -= 5;
                    }
                    minX -= 5;
                    maxX -= 5;
                }
                break;
        }
    }

    @Override
    public void resize(boolean type) {
        for (Figure figure : shapesInGroup) {
            if (!type && figure.diameter > 5) {
                figure.diameter -= 5;
            } else if (type) {
                figure.diameter += 5;
            }
        }
    }


    public List<Figure> getShapesInGroup() {
        return shapesInGroup;
    }

    public void addToGroup(Figure figure) {
        shapesInGroup.add(figure);
        changeParameters();
    }

    private void changeParameters() {
        for (Figure figure : shapesInGroup) {
            if (maxX < figure.x) {
                maxX = figure.x;
                diameterX = figure.diameter;
            }
            if (maxY < figure.y) {
                maxY = figure.y;
                diameterY = figure.diameter;
            }
            if (minX > figure.x) {
                minX = figure.x;
            }
            if (minY > figure.y) {
                minY = figure.y;
            }
        }
    }
}
