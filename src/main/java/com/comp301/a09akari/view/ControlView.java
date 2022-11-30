package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public ControlView(Model model, ClassicMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();
    layout.getStyleClass().add("control-layout");

    // set layout style here

    Button nextButton = new Button("Next Puzzle");
    layout.getChildren().add(nextButton);

    nextButton.setOnAction(
        (ActionEvent event) -> {
          controller.clickNextPuzzle();
        });

    // prev
    Button prevButton = new Button("Previous Puzzle");
    layout.getChildren().add(prevButton);

    prevButton.setOnAction(
        ((ActionEvent event) -> {
          controller.clickPrevPuzzle();
        }));

    // rand
    Button randButton = new Button("Random Puzzle");
    layout.getChildren().add(randButton);

    randButton.setOnAction(
        (ActionEvent) -> {
          controller.clickRandPuzzle();
        });

    // reset
    Button startOver = new Button("Start Over");
    layout.getChildren().add(startOver);

    startOver.setOnAction(
        (ActionEvent) -> {
          controller.clickResetPuzzle();
        });

    // reset
    return layout;
  }
}
