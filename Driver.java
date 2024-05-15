package CSAFinalProject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Driver {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showStartMenu();
            }
        });
    }

    private static void showStartMenu() {
        JFrame frame = new JFrame("Connect 4 Start Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);

        JButton humanButton = new JButton("Play against Human");
        humanButton.setBounds(100, 50, 200, 30);
        frame.add(humanButton);

        JButton aiButton = new JButton("Play against AI");
        aiButton.setBounds(100, 100, 200, 30);
        frame.add(aiButton);

        JSlider aiDifficultySlider = new JSlider(1, 2, 1);
        aiDifficultySlider.setBounds(100, 150, 200, 50);
        aiDifficultySlider.setMajorTickSpacing(1);
        aiDifficultySlider.setPaintTicks(true);
        aiDifficultySlider.setPaintLabels(true);
        frame.add(aiDifficultySlider);

        JLabel difficultyLabel = new JLabel("AI Difficulty");
        difficultyLabel.setBounds(100, 200, 200, 30);
        frame.add(difficultyLabel);

        humanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                startGame(false, 0);
            }
        });

        aiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                startGame(true, aiDifficultySlider.getValue());
            }
        });

        frame.setVisible(true);
    }

    private static void startGame(boolean playAgainstAI, int aiDifficulty) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Game game = new Game(playAgainstAI, aiDifficulty);
                GUIBoard guiBoard = new GUIBoard(game);
                guiBoard.display();
            }
        });
    }
}
