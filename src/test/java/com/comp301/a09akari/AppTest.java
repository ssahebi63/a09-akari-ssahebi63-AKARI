package com.comp301.a09akari;

import static com.comp301.a09akari.SamplePuzzles.PUZZLE_01;
import static com.comp301.a09akari.SamplePuzzles.PUZZLE_05;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.*;

import com.comp301.a09akari.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/** Unit test for simple App. */
public class AppTest {
    public static int[][] PUZZLE_06 = {
            {6, 1, 6, 6, 6, 6, 5, 6, 6, 6},
            {6, 6, 6, 6, 6, 6, 6, 6, 6, 5},
            {6, 6, 5, 5, 6, 6, 6, 2, 6, 6},
            {2, 6, 6, 5, 6, 6, 1, 5, 6, 6},
            {6, 6, 6, 6, 6, 6,  6, 6, 6, 6},
            {6, 6, 6, 6, 6, 6, 6, 6, 6, 6},
            {6, 6, 5, 2, 6, 6, 0, 6, 6, 1},
            {6, 6, 2, 6, 6, 6, 5, 1, 6, 6},
            {2, 6, 6, 6, 6, 6, 6, 6, 6, 6},
            {6, 6, 6, 5, 6, 6, 6, 6, 5, 6},
    };
  /** Rigorous Test :-) */
  @Test
  public void PuzzleImplTest() {


    int[][] board01 = SamplePuzzles.PUZZLE_01;
    int[][] board02 = PUZZLE_05;
    Puzzle puzzle01 = new PuzzleImpl(board01);
    Puzzle puzzle02 = new PuzzleImpl(board02);
    Puzzle puzzle03 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);

    assertEquals(7, puzzle01.getHeight());
    assertEquals(7, puzzle02.getHeight());
    assertEquals(6, puzzle02.getWidth());

    System.out.println(puzzle02.getCellType(0,0));
    System.out.println(puzzle02.getClue(4,0));


  int r = 5;
  int c = 7 ;
String rc = r + String.valueOf(c);
System.out.println(rc);

}

@Test
public void modelImpl(){

  PuzzleLibrary plib = new PuzzleLibraryImpl();
  plib.addPuzzle(new PuzzleImpl(PUZZLE_01));
  ModelImpl model = new ModelImpl(plib);
  model.addLamp(0,0);
  Assert.assertTrue(model.isLamp(0,0));
 assertFalse(model.isLamp(0, 1));
 model.removeLamp(0,0);
 assertFalse(model.isLamp(0, 0));
 try{
 model.addLamp(0,4);
 fail();
 } catch(IllegalArgumentException ignored){
}


    //testing with lamps
    model.addLamp(5, 4);
 Assert.assertTrue(model.rowHasLamp(5));
    assertFalse(model.rowHasLamp(3));
    model.addLamp(5, 1);
    model.addLamp(3, 6);
    model.addLamp(3, 3);






    //test if intruder exist (and active puzzle index) also test isLit()
    plib.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    model.setActivePuzzleIndex(1);
    assertFalse(model.rowHasIntruder(3));



    model.addLamp(3,3);

    for (int c = 0; c < model.getActivePuzzle().getWidth(); c++){
        Assert.assertTrue(model.isLit(3, c));
    }


    model.setActivePuzzleIndex(0);
    //testing closest lamp/intruder locations;
    assertEquals(1, model.closestRowLampLocation(5, 2));
    assertEquals(3, model.closestRowIntruderLocation(5,4));
    assertEquals(3, model.closestRowIntruderLocation(5,0));
    assertEquals(3, model.closestRowIntruderLocation(5,6));
    assertEquals(1, model.closestRowIntruderLocation(3, 2));
    assertEquals(5, model.closestRowIntruderLocation(3, 4));

   Assert.assertTrue(model.isIlluminatedFromRight(3, 2));
    assertFalse(model.isIlluminatedFromRight(3, 0));
    assertFalse(model.isIlluminatedFromRight(3, 4));
    assertFalse(model.isIlluminatedFromRight(0,0));

    model.addLamp(6, 0);
    assertFalse(model.isIlluminatedFromLeft(6,6));
    Assert.assertTrue(model.isIlluminatedFromLeft(6,1));


    //test isLit
    plib.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    model.setActivePuzzleIndex(2);
    model.addLamp(0, 0);
    assertTrue(model.isClueSatisfied(0, 1));
    model.addLamp(0, 2);
    assertFalse(model.isClueSatisfied(0, 1));
    model.removeLamp(0, 0);
    model.removeLamp(0, 2);

    model.addLamp(4, 4);

    for( int i = 0; i < model.getActivePuzzle().getWidth(); i++){
        assertTrue(model.isLit(4, i));
        assertTrue(model.isLit(i, 4));
    }
    model.addLamp(9, 2);
    assertTrue(model.isLit(9, 4));
    model.removeLamp(4, 4);
    assertFalse(model.isLit(9, 4));
    model.addLamp(9, 7);
    assertTrue(model.isLit(9, 4));
    model.addLamp(9, 6);
    assertTrue(model.isLampIllegal(9,6));
    assertTrue(model.isLampIllegal(9,7));
    assertFalse(model.isLampIllegal(9,2));
    model.addLamp(9, 1);
    assertTrue(model.isLampIllegal(9,2));
    model.addLamp(1, 1);
    assertTrue(model.isLampIllegal(1,1));
    model.removeLamp(9,1);


//    for( int c = 0; c < model.getActivePuzzle().getWidth(); c++){
//        for( int r = 0; r < model.getActivePuzzle().getHeight(); r++){
//        if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
//          if (model.isLit(r, c)) {
//            PUZZLE_06[r][c] = 1000;
//          }
//        }
//        }
//
//    }
//    System.out.println(Arrays.deepToString(PUZZLE_06));






    plib.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));
    model.setActivePuzzleIndex(3);
    model.addLamp(0,0);
    assertTrue(model.isIlluminatedFromAbove(3,0));
    assertFalse(model.isIlluminatedFromAbove(6,0));
    model.addLamp(0, 3);
    assertFalse(model.isIlluminatedFromBelow(0, 1));
   assertEquals(4, model.closestColumnIntruderLocation(0, 0));
    assertEquals(5, model.closestColumnIntruderLocation(4, 1));
    assertEquals(1, model.closestColumnIntruderLocation(2, 1));






 }



}
