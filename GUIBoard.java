package CSAFinalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIBoard implements ActionListener {
    private JFrame frame;
    private JLabel[][] displayBoard = new JLabel[6][7];
    private JButton[] buttons = new JButton[7];
    private Game game;

    public GUIBoard(Game game) {
        this.game = game;
        createDisplay();
        game.setOnAIMoveComplete(this::enableButtons);
        game.setOnGameEnd(this::displayWinner);
    }

    public void display() {
        frame.setVisible(true);
    }

    private void createDisplay() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new GridLayout(7, 7));

        for (int i = 0; i < 7; i++) {
            buttons[i] = new JButton("Drop");
            buttons[i].addActionListener(this);
            frame.add(buttons[i]);
        }

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                displayBoard[row][col] = new JLabel();
                displayBoard[row][col].setOpaque(true);
                displayBoard[row][col].setBackground(Color.WHITE);
                frame.add(displayBoard[row][col]);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]) {
                game.playerMove(i);
                updateBoard();
                disableButtons();
            }
        }
    }

    private void updateBoard() {
        Board board = game.getBoard();
        Player p1 = game.getP1();
        Player p2 = game.getP2();
        int[][] boardArray = board.getBoard();

        for (int row = 0; row < boardArray.length; row++) {
            for (int col = 0; col < boardArray[row].length; col++) {
                int point = boardArray[row][col];
                if (point == p1.getID()) {
                    displayBoard[row][col].setBackground(p1.getColor());
                } else if (point == p2.getID()) {
                    displayBoard[row][col].setBackground(p2.getColor());
                } else {
                    displayBoard[row][col].setBackground(Color.WHITE);
                }
            }
        }
    }

    private void disableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    private void enableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(true);
        }
    }

    private void displayWinner() {
        disableButtons();
        int winner = game.getBoard().winCheckAll();
        String message = ""; // Initialize message with an empty string
        if (winner == game.getP1().getID()) {
            message = "Player 1 (Red) wins!";
        } else if (winner == game.getP2().getID()) {
            message = "Player 2 (Yellow) wins!";
        }
        JOptionPane.showMessageDialog(frame, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }
}
