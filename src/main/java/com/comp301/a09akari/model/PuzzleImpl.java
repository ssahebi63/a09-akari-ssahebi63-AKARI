package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;

  public PuzzleImpl(int[][] board) {
    if (board == null) {
      throw new IllegalArgumentException();
    }
    this.board = board;
  }

  @Override
  public int getWidth() {
    int column = 0;
    try {

      while (true) {
        int boardNum = board[0][column];
        column++;
      }
    } catch (IndexOutOfBoundsException ob) {
      return column;
    }
  }

  @Override
  public int getHeight() {
    int row = 0;
    try {

      while (true) {
        int boardNum = board[row][0];
        row++;
      }
    } catch (IndexOutOfBoundsException ob) {
      return row;
    }
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r >= getHeight() || c >= getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }

    switch (board[r][c]) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
        return CellType.CLUE;
      case 5:
        return CellType.WALL;
      case 6:
        return CellType.CORRIDOR;
    }
    return null;
  }

  @Override
  public int getClue(int r, int c) {
    if (r >= getHeight() || c >= getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    return board[r][c];
  }
}
