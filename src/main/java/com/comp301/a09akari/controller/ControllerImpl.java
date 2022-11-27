package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ControllerImpl implements ClassicMvcController {
  private final Model model;

  public ControllerImpl(Model model) {
    if (model == null) {
      throw new IllegalArgumentException("model is null inside ControllerImpl");
    }
    this.model = model;
    // Constructor code goes here
  }

  @Override
  public void clickNextPuzzle() {
    try {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
    } catch (IndexOutOfBoundsException ignored) {

    }
  }

  @Override
  public void clickPrevPuzzle() {
    try {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
    } catch (IndexOutOfBoundsException ignored) {

    }
  }

  @Override
  public void clickRandPuzzle() {
    List<Integer> randList = new ArrayList<>();

    for (int i = 0; i < model.getPuzzleLibrarySize(); i++) {
      randList.add(i);
    }
    Collections.shuffle(randList);
    model.setActivePuzzleIndex(randList.get((randList.size()) / 2));
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    // can we assume that all lamps
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    }
  }
}
