package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PuzzleView implements FXComponent {
    private Model model;
    private ClassicMvcController controller;

    public PuzzleView(Model model, ClassicMvcController controller){
        this.model = model;
        this.controller = controller;
    }
    @Override
    public Parent render(){
        GridPane puzzleGrid = new GridPane();

        mapGrid(puzzleGrid);
        //after I map it out, set the style (add border space and color?)
        return puzzleGrid;

    }

    public void mapGrid(GridPane puzzleGrid){

        for(int c = 0; c < model.getActivePuzzle().getWidth(); c++){
            for(int r = 0; r < model.getActivePuzzle().getHeight(); r++){
                StackPane layout = new StackPane();

                //default cell
                Rectangle rect = new Rectangle();
                rect.setStroke(Color.BLACK);
                rect.setFill(Color.WHITE);
                rect.setWidth(40);
                rect.setHeight(40);
                layout.getChildren().add(rect);
                puzzleGrid.add(layout, c, r);

                if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR){

                    if (model.isLit(r, c)) {
                        rect.setFill(Color.LIGHTYELLOW);
                        if (model.isLamp(r, c)){
                            if (!model.isLampIllegal(r, c)){
                                layout.getChildren().add(lightBulb());
                            } else{
                                layout.getChildren().add(poop());
                            }


                        }
                    }




                    int finalR = r;
                    int finalC = c;

                    layout.setOnMouseClicked((MouseEvent mouseEvent) -> {
                            controller.clickCell(finalR, finalC);

                    });


                } else if(model.getActivePuzzle().getCellType(r, c) == CellType.CLUE){



                    switch(model.getActivePuzzle().getClue(r, c)){
                        case 0:
                            Label label = new Label("0");
                            //set label fonts and etc
                            layout.getChildren().add(label);
                            break;
                        case 1:
                            Label label1 = new Label("1");
                            layout.getChildren().add(label1);
                            break;
                        case 2:
                            Label label2 = new Label("2");
                            layout.getChildren().add(label2);
                            break;
                        case 3:
                            Label label3 = new Label("3");
                            layout.getChildren().add(label3);
                            break;
                        case 4:
                            Label label4 = new Label("4");
                            layout.getChildren().add(label4);
                            break;
                    }




                } else if(model.getActivePuzzle().getCellType(r, c) == CellType.WALL){
                    rect.setFill(Color.BLACK);

                }
            }
        }


    }

    private ImageView lightBulb() {
        FileInputStream input = null;
        try {
            input = new FileInputStream("/Users/samansahebi/IdeaProjects/a09-akari-ssahebi63/src/main/resources/light-bulb.png");
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
            input = new FileInputStream("/Users/samansahebi/IdeaProjects/a09-akari-ssahebi63/src/main/resources/poop.png");
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
