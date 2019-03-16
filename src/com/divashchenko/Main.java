package com.divashchenko;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 500;
    private static final int FPS = 60;

    private boolean closed;
    private Board board;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SimpleGame");
        Canvas canvas = new Canvas();
        canvas.setWidth(BOARD_WIDTH);
        canvas.setHeight(BOARD_HEIGHT);
        BorderPane group = new BorderPane(canvas);
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);
        primaryStage.show();
        Logger.log("Game started");

        GraphicsContext gc = canvas.getGraphicsContext2D();
        board = new Board(gc);
        board.draw();

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DIGIT1:
                    board.addFigure(Board.Figures.BALL);
                    Logger.log("Add BALL");
                    break;
                case DIGIT2:
                    board.addFigure(Board.Figures.SQUARE);
                    Logger.log("Add SQUARE");
                    break;
                case DIGIT3:
                    board.addFigure(Board.Figures.TRIANGLE);
                    Logger.log("Add TRIANGLE");
                    break;
                case ALT:
                    board.changeFigure();
                    Logger.log("Figure changed");
                    break;
                case DELETE:
                    board.deleteFigure();
                    Logger.log("Figure deleted");
                    break;
                case UP:
                    board.move(Moves.UP);
                    break;
                case RIGHT:
                    board.move(Moves.RIGHT);
                    break;
                case DOWN:
                    board.move(Moves.DOWN);
                    break;
                case LEFT:
                    board.move(Moves.LEFT);
                    break;
                case C:
                    board.cloneFigure();
                    Logger.log("Figure copy");
                    break;
                case PAGE_UP:
                    board.resizePlus();
                    Logger.log("resize +");
                    break;
                case PAGE_DOWN:
                    board.resizeMinus();
                    Logger.log("resize -");
                    break;
            }
        });

        scene.setOnMousePressed(event -> {
            if (event.isControlDown()) {
                board.merge((int) event.getSceneX(), (int) event.getSceneY());
            }
        });

        new Thread(this::runMainGameLoopInThread).start();
    }

    @Override
    public void stop() {
        closed = true;
    }


    private void runMainGameLoopInThread() {
        while (!closed) {
            // run in UI thread
            Platform.runLater(this::drawFrame);
            try {
                int pauseBetweenFramesMillis = 1000 / FPS;
                Thread.sleep(pauseBetweenFramesMillis);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private void drawFrame() {
        board.draw();
        //board.move();
    }

}
