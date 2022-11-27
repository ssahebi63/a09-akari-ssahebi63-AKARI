package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PuzzleView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public PuzzleView(Model model, ClassicMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane puzzleGrid = new GridPane();

    puzzleGrid.getStyleClass().add("puzzle-layout");

    mapGrid(puzzleGrid);
    // after I map it out, set the style (add border space and color?)
    return puzzleGrid;
  }

  public void mapGrid(GridPane puzzleGrid) {

    for (int c = 0; c < model.getActivePuzzle().getWidth(); c++) {
      for (int r = 0; r < model.getActivePuzzle().getHeight(); r++) {
        StackPane layout = new StackPane();

        // default cell
        Rectangle rect = new Rectangle();
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.WHITE);
        rect.setWidth(40);
        rect.setHeight(40);
        layout.getChildren().add(rect);
        puzzleGrid.add(layout, c, r);

        if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {

          if (model.isLit(r, c)) {
            rect.setFill(Color.LIGHTYELLOW);
            if (model.isLamp(r, c)) {
              if (!model.isLampIllegal(r, c)) {
                layout.getChildren().add(lightBulb());
              } else {
                layout.getChildren().add(poop());
              }
            }
          }

          int finalR = r;
          int finalC = c;

          layout.setOnMouseClicked(
              (MouseEvent mouseEvent) -> {
                controller.clickCell(finalR, finalC);
              });

        } else if (model.getActivePuzzle().getCellType(r, c) == CellType.CLUE) {
          rect.setFill(Color.BLACK);
          Label label = new Label();
          label.getStyleClass().add("clue-layout");
          layout.getChildren().add(label);
          switch (model.getActivePuzzle().getClue(r, c)) {
            case 0:
              label.setText("0");
              break;
            case 1:
              label.setText("1");
              break;
            case 2:
              label.setText("2");
              break;
            case 3:
              label.setText("3");
              break;
            case 4:
              label.setText("4");
              break;
          }

        } else if (model.getActivePuzzle().getCellType(r, c) == CellType.WALL) {
          rect.setFill(Color.BLACK);
        }
      }
    }
  }

  private ImageView lightBulb() {
    FileInputStream input = null;
    try {
      input =
          new FileInputStream(
              "/Users/samansahebi/IdeaProjects/a09-akari-ssahebi63/src/main/resources/light-bulb.png");
    } catch (FileNotFoundException e) {
      System.out.println("Light Bulb File Not Found!");
    }
    Image bulb = new Image(input);
    ImageView imgPane = new ImageView();
    imgPane.setFitWidth(40);
    imgPane.setPreserveRatio(true);
    imgPane.setImage(bulb);
    return imgPane;
  }

  private ImageView poop() {
    FileInputStream input = null;
    try {
      input =
          new FileInputStream(
              "/Users/samansahebi/IdeaProjects/a09-akari-ssahebi63/src/main/resources/poop.png");
    } catch (FileNotFoundException e) {
      System.out.println("Light Bulb File Not Found!");
    }
    Image bulb = new Image(input);
    ImageView imgPane = new ImageView();
    imgPane.setFitWidth(40);
    imgPane.setPreserveRatio(true);
    imgPane.setImage(bulb);
    return imgPane;
  }
}
