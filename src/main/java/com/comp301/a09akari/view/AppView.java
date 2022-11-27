package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class AppView implements FXComponent {

  private final Model model;
  private final ClassicMvcController controller;

  public AppView(Model model, ClassicMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();
    layout.getStyleClass().add("app-background-layout");

    // message view on top if isSolved
    MessageView messageView = new MessageView(model, controller);
    layout.getChildren().add(messageView.render());

    // puzzle itself
    PuzzleView puzzleView = new PuzzleView(model, controller);
    layout.getChildren().add(puzzleView.render());

    // next puzzle, prev puzzle, randpuzzle, reset
    ControlView controlView = new ControlView(model, controller);
    layout.getChildren().add(controlView.render());

    return layout;
  }
}
