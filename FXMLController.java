/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectfour;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author mhaqu
 */
public class FXMLController implements Initializable {

    private int numTurns = 0;
    private final int NUM_POSSIBLE_MOVES = 42;
    private PlayerEnum turn = PlayerEnum.Player1;
    
    //pane's containing 6 circles that comprise the board
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;
    @FXML
    private Pane pane4;
    @FXML
    private Pane pane5;
    @FXML
    private Pane pane6;
    @FXML
    private Pane pane7;
    
    //label indicating which player's turn it is, or 
    //the winner of the game
    @FXML
    private Label turnLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    //every pane has the same hoverOut event
    @FXML
    private void hoverOutPane(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        pane.setOpacity(1);
    }

    //every pane has the same hoverIn event
    @FXML
    private void hoverInPane(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        pane.setOpacity(0.5);
    }

    //every pane has the same click event
    @FXML
    private void paneClicked(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        ObservableList<Node> children = pane.getChildren();
        for (Node node : children) {
            Circle circle = (Circle) node;
            if (circle.fillProperty().getValue().equals(Color.WHITE)) {
                numTurns++;
                if (turn.equals(PlayerEnum.Player1)) {
                    circle.setFill(Color.RED);
                    if (areFourConnected(Color.RED)) {
                        declareWinner(PlayerEnum.Player1);
                        return;
                    }
                    //check if game board is full with no winner
                    if (numTurns == NUM_POSSIBLE_MOVES) {
                        declareWinner(null);
                        return;
                    }
                    turn = PlayerEnum.Player2;
                } else {
                    circle.setFill(Color.BLACK);
                    if (areFourConnected(Color.BLACK)) {
                        declareWinner(PlayerEnum.Player2);
                        return;
                    }
                    //check if game board is full with no winner
                    if (numTurns == NUM_POSSIBLE_MOVES) {
                        declareWinner(null);
                        return;
                    }
                    turn = PlayerEnum.Player1;
                }
                turnLabel.setText(turn.toString() + "'s Turn!");
                break;
            }
        }
    }

    private boolean areFourConnected(Color color) {
        Pane[] board = {pane1, pane2, pane3, pane4, pane5, pane6, pane7};

        int height = 6;
        int width = 7;
        // Check vertical conections
        for (int i = 0; i < width - 3; i++) {
            for (int j = 0; j < height; j++) {
                if (((Circle) board[i].getChildren().get(j)).fillProperty().getValue().equals(color)
                        && ((Circle) board[i + 1].getChildren().get(j)).fillProperty().getValue().equals(color)
                        && ((Circle) board[i + 2].getChildren().get(j)).fillProperty().getValue().equals(color)
                        && ((Circle) board[i + 3].getChildren().get(j)).fillProperty().getValue().equals(color)) {
                    return true;
                }
            }
        }
        // Check horizontal connections
        for (int j = 0; j < height - 3; j++) {
            for (int i = 0; i < width; i++) {
                if (((Circle) board[i].getChildren().get(j)).fillProperty().getValue().equals(color)
                        && ((Circle) board[i].getChildren().get(j + 1)).fillProperty().getValue().equals(color)
                        && ((Circle) board[i].getChildren().get(j + 2)).fillProperty().getValue().equals(color)
                        && ((Circle) board[i].getChildren().get(j + 3)).fillProperty().getValue().equals(color)) {
                    return true;
                }
            }
        }
        // Check ascending diagonal connections
        for (int i = 3; i < width; i++) {
            for (int j = 0; j < height - 3; j++) {
                if (((Circle) board[i].getChildren().get(j)).fillProperty().getValue().equals(color)
                        && ((Circle) board[i - 1].getChildren().get(j + 1)).fillProperty().getValue().equals(color)
                        && ((Circle) board[i - 2].getChildren().get(j + 2)).fillProperty().getValue().equals(color)
                        && ((Circle) board[i - 3].getChildren().get(j + 3)).fillProperty().getValue().equals(color)) {
                    return true;
                }
            }
        }
        // Check descending diagonal connections
        for (int i = 3; i < width; i++) {
            for (int j = 3; j < height; j++) {
                if (((Circle) board[i].getChildren().get(j)).fillProperty().getValue().equals(color)
                        && ((Circle) board[i - 1].getChildren().get(j - 1)).fillProperty().getValue().equals(color)
                        && ((Circle) board[i - 2].getChildren().get(j - 2)).fillProperty().getValue().equals(color)
                        && ((Circle) board[i - 3].getChildren().get(j - 3)).fillProperty().getValue().equals(color)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void declareWinner(PlayerEnum player) {
        Pane[] board = {pane1, pane2, pane3, pane4, pane5, pane6, pane7};
        //prevent player from interacting with board after a winner
        //has been determined
        for (Pane pane : board) {
            pane.setDisable(true);
        }
        
        //null value for the 'player' parameter indicates game board is full
        //and no winner has been determined. aka a draw
        if (player == null) {
        turnLabel.setText("Game is a draw!");
        turnLabel.setTextFill(Color.RED);
        } else {
            turnLabel.setText(player.toString() + " Wins!");
            turnLabel.setTextFill(Color.GREEN);
        }
    }

    private void resetBoard() {
        Pane[] board = {pane1, pane2, pane3, pane4, pane5, pane6, pane7};
        for (Pane pane : board) {
            pane.setDisable(false);
            for (Node node : pane.getChildren()) {
                Circle circle = (Circle) node;
                if (!circle.fillProperty().equals(Color.WHITE)) {
                    circle.setFill(Color.WHITE);
                    continue;
                }
                break;
            }
        }
        numTurns = 0;
        turn = PlayerEnum.Player1;
        turnLabel.setText("Player1's Turn!");
        turnLabel.setTextFill(Color.BLACK);
    }

    @FXML
    private void newGame(ActionEvent event) {
        resetBoard();
    }
}
