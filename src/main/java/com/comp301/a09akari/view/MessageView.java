package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MessageView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public MessageView(Model model, ClassicMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {

    Label label  = new Label("Game Incomplete");
    StackPane layout = new StackPane(label);
    layout.getStyleClass().add("message-layout");
    label.getStyleClass().add("game-incomplete-layout");
    if (model.isSolved()) {
      // style the label here later
      label.setText("Congratulations! You Completed The Puzzle.");
      label.getStyleClass().add("game-complete-layout");
    }
    return layout;


  }
}
