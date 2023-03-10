package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelImpl implements Model {
  private final PuzzleLibrary library;
  private final Map<Integer, Map<String, Boolean>> lampMapMap;
  private final List<ModelObserver> observers;
  private int li;
  private Map<String, Boolean> lampMap;

  // an array of size total number of corridors

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException();
    }
    this.library = library;
    li = 0;
    observers = new ArrayList<>();
    lampMapMap = new HashMap<>();
    lampMapMap.put(0, new HashMap<>());
    lampMap = lampMapMap.get(0);
  }

  @Override
  public void addLamp(int r, int c) {
    coordCheck(r, c);
    if (!isLamp(r, c)) {
      String rc = r + String.valueOf(c);
      lampMap.put(rc, Boolean.TRUE);
    }
    notifyObservers();
  }

  @Override
  public void removeLamp(int r, int c) {
    coordCheck(r, c);
    if (isLamp(r, c)) {
      String rc = r + String.valueOf(c);
      lampMap.replace(rc, Boolean.FALSE);
    }
    notifyObservers();
  }

  @Override
  public boolean isLit(int r, int c) {
    coordCheck(r, c);

    if (isLamp(r, c)) {
      return true;
    }

    if (rowHasLamp(r)) {
      if (rowHasIntruder(r)) {
        if ((c > closestRowLampLocation(r, c) && c < closestRowIntruderLocation(r, c))) {
          if (isIlluminatedFromLeft(r, c)) {
            return true;
          }
        }
        if (c < closestRowLampLocation(r, c) && c > closestRowIntruderLocation(r, c)) {
          if (isIlluminatedFromRight(r, c)) {
            return true;
          }
        }

        if (closestRowIntruderLocation(r, c) > closestRowLampLocation(r, c)) {
          if (isIlluminatedFromRight(r, c)) {
            return true;
          }
        }

        if (closestRowIntruderLocation(r, c) < closestRowLampLocation(r, c)) {
          if (isIlluminatedFromLeft(r, c)) {
            return true;
          }
        }

      } else {
        return true; // if row has no intruder
      }
    }

    if (columnHasLamp(c)) {
      if (columnHasIntruder(c)) {
        if (r > closestColumnLampLocation(r, c) && r < closestColumnIntruderLocation(r, c)) {
          if (isIlluminatedFromAbove(r, c)) {
            return true;
          }
        }
        if (r < closestColumnLampLocation(r, c) && r > closestColumnIntruderLocation(r, c)) {
          if (isIlluminatedFromBelow(r, c)) {
            return true;
          }
        }

        if (closestColumnIntruderLocation(r, c) > closestColumnLampLocation(r, c)) {
          if (isIlluminatedFromBelow(r, c)) {
            return true;
          }
        }

        if (closestColumnIntruderLocation(r, c) < closestColumnLampLocation(r, c)) {
          return isIlluminatedFromAbove(r, c);
        }

      } else {
        return true; // if column has no intruder
      }
    }

    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    coordCheck(r, c);
    String rc = r + String.valueOf(c);

    if (lampMap.containsKey(rc)) {
      return lampMap.get(rc);
    }
    return false;
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }

    if (isLamp(r, c)) {
      return isIlluminatedFromAbove(r, c)
          || isIlluminatedFromBelow(r, c)
          || isIlluminatedFromLeft(r, c)
          || isIlluminatedFromRight(r, c);
    }

    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(li);
  }

  @Override
  public int getActivePuzzleIndex() {
    return li;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= library.size()) {
      throw new IndexOutOfBoundsException();
    }

    if (!lampMapMap.containsKey(index)) {
      lampMapMap.put(index, new HashMap<>());
    }
    lampMap = lampMapMap.get(index);

    li = index;

    notifyObservers();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    lampMap.clear();

    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    for (int r = 0; r < getActivePuzzle().getHeight(); r++) {
      for (int c = 0; c < getActivePuzzle().getWidth(); c++) {

        if (getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {

          if (isLamp(r, c)) {
            if (isLampIllegal(r, c)) {
              return false;
            }
          }

          if (!isLit(r, c)) {
            return false;
          }
        }

        if (getActivePuzzle().getCellType(r, c) == CellType.CLUE) {
          if (!isClueSatisfied(r, c)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    int val =
        (isLampModded(r, c - 1) ? 1 : 0)
            + (isLampModded(r, c + 1) ? 1 : 0)
            + (isLampModded(r + 1, c) ? 1 : 0)
            + (isLampModded(r - 1, c) ? 1 : 0);
    switch (getActivePuzzle().getClue(r, c)) {
      case 0:
        return val == 0;
      case 1:
        return val == 1;
      case 2:
        return val == 2;
      case 3:
        return val == 3;
      case 4:
        return val == 4;
    }
    System.out.println("isClueSatisfied failed!");
    return false;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private void coordCheck(int r, int c) {
    if (r >= getActivePuzzle().getHeight() || c >= getActivePuzzle().getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (getActivePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
  }

  public boolean rowHasLamp(int r) {
    String rc;
    for (int c = 0; c < getActivePuzzle().getWidth(); c++) {
      rc = r + String.valueOf(c);
      if (lampMap.containsKey(rc)) {
        if (lampMap.get(rc)) {
          return lampMap.get(rc);
        }
      }
    }
    return false;
  }

  public boolean columnHasLamp(int c) {
    String rc;
    for (int r = 0; r < getActivePuzzle().getHeight(); r++) {
      rc = r + String.valueOf(c);
      if (lampMap.containsKey(rc)) {
        if (lampMap.get(rc)) {
          return lampMap.get(rc);
        }
      }
    }
    return false;
  }

  public boolean rowHasIntruder(int r) {

    for (int c = 0; c < getActivePuzzle().getWidth(); c++) {
      if (getActivePuzzle().getCellType(r, c) == CellType.WALL
          || getActivePuzzle().getCellType(r, c) == CellType.CLUE) {
        return true;
      }
    }
    return false;
  }

  public boolean columnHasIntruder(int c) {

    for (int r = 0; r < getActivePuzzle().getHeight(); r++) {
      if (getActivePuzzle().getCellType(r, c) == CellType.WALL
          || getActivePuzzle().getCellType(r, c) == CellType.CLUE) {
        return true;
      }
    }
    return false;
  }

  public int closestRowLampLocation(int r, int c) {
    int closestLampC = -1;
    int leastDistance = getActivePuzzle().getWidth() - 1;

    for (int c1 = 0; c1 < getActivePuzzle().getWidth(); c1++) {

      if (getActivePuzzle().getCellType(r, c1) == CellType.CORRIDOR) {
        if (isLamp(r, c1)) {

          if (Math.abs(c1 - c) <= leastDistance) {
            leastDistance = Math.abs(c1 - c);
            closestLampC = c1;
          }
        }
      }
    }
    return closestLampC;
  }

  public int closestColumnLampLocation(int r, int c) {
    int closestLampR = -1;
    int leastDistance = getActivePuzzle().getHeight() - 1;

    for (int r1 = 0; r1 < getActivePuzzle().getHeight(); r1++) {

      if (getActivePuzzle().getCellType(r1, c) == CellType.CORRIDOR) {
        if (isLamp(r1, c)) {

          if (Math.abs(r1 - r) <= leastDistance) {
            leastDistance = Math.abs(r1 - r);
            closestLampR = r1;
          }
        }
      }
    }
    return closestLampR;
  }

  public int closestRowIntruderLocation(int r, int c) {
    int closestIntruderC = -1;
    int leastDistance = getActivePuzzle().getWidth() - 1;

    for (int c1 = 0; c1 < getActivePuzzle().getWidth(); c1++) {

      if (getActivePuzzle().getCellType(r, c1) == CellType.CLUE
          || getActivePuzzle().getCellType(r, c1) == CellType.WALL) {
        if (Math.abs(c1 - c) <= leastDistance) {
          leastDistance = Math.abs(c1 - c);
          closestIntruderC = c1;
        }
      }
    }
    return closestIntruderC;
  }

  public int closestColumnIntruderLocation(int r, int c) {
    int closestIntruderR = -1;
    int leastDistance = getActivePuzzle().getHeight() - 1;

    for (int r1 = 0; r1 < getActivePuzzle().getHeight(); r1++) {

      if (getActivePuzzle().getCellType(r1, c) == CellType.CLUE
          || getActivePuzzle().getCellType(r1, c) == CellType.WALL) {
        if (Math.abs(r1 - r) <= leastDistance) {
          leastDistance = Math.abs(r1 - r);
          closestIntruderR = r1;
        }
      }
    }
    return closestIntruderR;
  }

  public boolean isIlluminatedFromRight(int r, int c) {
    // start from isLit() tile

    for (int i = c + 1; i < getActivePuzzle().getWidth(); i++) {

      if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        return false;
      }

      if (isLamp(r, i)) {
        return true;
      }
    }
    return false;
  }

  public boolean isIlluminatedFromAbove(int r, int c) {
    // start from isLit() tile

    for (int i = r - 1; i >= 0; i--) {

      if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        return false;
      }

      if (isLamp(i, c)) {
        return true;
      }
    }
    return false;
  }

  public boolean isIlluminatedFromBelow(int r, int c) {
    // start from isLit() tile

    for (int i = r + 1; i < getActivePuzzle().getHeight(); i++) {

      if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        return false;
      }

      if (isLamp(i, c)) {
        return true;
      }
    }
    return false;
  }

  public boolean isIlluminatedFromLeft(int r, int c) {
    // start from isLit() tile

    for (int i = c - 1; i >= 0; i--) {

      if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        return false;
      }

      if (isLamp(r, i)) {
        return true;
      }
    }
    return false;
  }

  public boolean isLampModded(int r, int c) {
    try {
      return isLamp(r, c);
    } catch (IndexOutOfBoundsException | IllegalArgumentException oops) {
      return false;
    }
  }

  public void notifyObservers() {
    for (ModelObserver o : observers) {
      o.update(this);
    }
  }
}
