package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MessageView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public MessageView(Model model, ClassicMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    Label label = new Label("Game Incomplete");
    Label puzzleNum = new Label("Puzzle " + (model.getActivePuzzleIndex() + 1) + " Of " + 5);
    // style
    StackPane labelHolder1 = new StackPane(label);
    StackPane labelHolder2 = new StackPane(puzzleNum);
    VBox vbox = new VBox(labelHolder1);
    vbox.getChildren().add(labelHolder2);

    labelHolder1.getStyleClass().add("message-layout");
    labelHolder2.getStyleClass().add("message-layout");
    label.getStyleClass().add("game-incomplete-layout");
    puzzleNum.getStyleClass().add("puzzle-number");
    if (model.isSolved()) {
      // style the label here later
      label.setText("Congratulations! You Completed The Puzzle.");
      label.getStyleClass().add("game-complete-layout");
    }
    return vbox;
  }
}
