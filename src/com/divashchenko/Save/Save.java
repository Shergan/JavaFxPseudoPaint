package com.divashchenko.Save;

import com.divashchenko.Shapes.Shape;

import java.util.ArrayList;
import java.util.List;

public class Save {

    private List<Shape> shapeList = new ArrayList<>();
    private int mainFigureIndex;

    public Save() {
    }

    public Save(List<Shape> shapeList, int mainFigureIndex) {
        this.shapeList = shapeList;
        this.mainFigureIndex = mainFigureIndex;
    }

    public List<Shape> getShapeList() {
        return shapeList;
    }

    public void setShapeList(List<Shape> shapeList) {
        this.shapeList = shapeList;
    }

    public int getMainFigureIndex() {
        return mainFigureIndex;
    }

    public void setMainFigureIndex(int mainFigureIndex) {
        this.mainFigureIndex = mainFigureIndex;
    }
}
