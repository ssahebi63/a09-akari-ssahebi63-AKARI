package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelImpl;
import com.comp301.a09akari.model.Puzzle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.util.concurrent.ExecutionException;

public class ControllerImpl implements ClassicMvcController{
    private Model model;
    public ControllerImpl(Model model) {
        if (model == null){
            throw new IllegalArgumentException("model is null inside ControllerImpl");
        }
        this.model =  model;
        // Constructor code goes here
    }
    @Override
    public void clickNextPuzzle() {
        try{
            model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
        } catch (IndexOutOfBoundsException ignored){

        }


    }

    @Override
    public void clickPrevPuzzle() {
        try{
            model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
        } catch (IndexOutOfBoundsException ignored){

        }

    }

    @Override
    public void clickRandPuzzle() {
        List<Integer> randList = new ArrayList<>();

        for (int i = 0; i < model.getPuzzleLibrarySize(); i++){
            randList.add(i);
        }
        Collections.shuffle(randList);
    model.setActivePuzzleIndex(randList.get((randList.size())/2));

    }

    @Override
    public void clickResetPuzzle() {
        model.resetPuzzle();

    }

    @Override
    public void clickCell(int r, int c){
        // can we assume that all lamps
        if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR){
            if (model.isLamp(r,c)){
                model.removeLamp(r, c);
            } else{
                model.addLamp(r, c);
            }


        }

    }

}
