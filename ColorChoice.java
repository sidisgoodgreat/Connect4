package CSAFinalProject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorChoice {
    private JFrame frame;
    private JSlider p1RSlider, p1GSlider, p1BSlider;
    private JSlider p2RSlider, p2GSlider, p2BSlider;
    private JLabel p1Color, p2Color;
    private JButton startGameButton;
    private Color p1SelectedColor = Color.RED;
    private Color p2SelectedColor = Color.YELLOW;

    public ColorChoice() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Select Player Colors");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(null);

        JLabel p1Label = new JLabel("Player 1 Color:");
        p1Label.setBounds(50, 20, 150, 30);
        frame.add(p1Label);

        p1RSlider = new JSlider(0, 255, 255);
        p1RSlider.setBounds(50, 50, 150, 30);
        p1RSlider.setBackground(Color.RED);
        p1RSlider.addChangeListener(new SliderChangeListener());
        frame.add(p1RSlider);

        p1GSlider = new JSlider(0, 255, 0);
        p1GSlider.setBounds(50, 80, 150, 30);
        p1GSlider.setBackground(Color.GREEN);
        p1GSlider.addChangeListener(new SliderChangeListener());
        frame.add(p1GSlider);

        p1BSlider = new JSlider(0, 255, 0);
        p1BSlider.setBounds(50, 110, 150, 30);
        p1BSlider.setBackground(Color.BLUE);
        p1BSlider.addChangeListener(new SliderChangeListener());
        frame.add(p1BSlider);

        p1Color = new JLabel();
        p1Color.setBounds(210, 50, 50, 50);
        p1Color.setOpaque(true);
        p1Color.setBackground(p1SelectedColor);
        frame.add(p1Color);

        JLabel p2Label = new JLabel("Player 2 Color:");
        p2Label.setBounds(50, 150, 150, 30);
        frame.add(p2Label);

        p2RSlider = new JSlider(0, 255, 255);
        p2RSlider.setBounds(50, 180, 150, 30);
        p2RSlider.setBackground(Color.RED);
        p2RSlider.addChangeListener(new SliderChangeListener());
        frame.add(p2RSlider);

        p2GSlider = new JSlider(0, 255, 255);
        p2GSlider.setBounds(50, 210, 150, 30);
        p2GSlider.setBackground(Color.GREEN);
        p2GSlider.addChangeListener(new SliderChangeListener());
        frame.add(p2GSlider);

        p2BSlider = new JSlider(0, 255, 0);
        p2BSlider.setBounds(50, 240, 150, 30);
        p2BSlider.setBackground(Color.BLUE);
        p2BSlider.addChangeListener(new SliderChangeListener());
        frame.add(p2BSlider);

        p2Color = new JLabel();
        p2Color.setBounds(210, 180, 50, 50);
        p2Color.setOpaque(true);
        p2Color.setBackground(p2SelectedColor);
        frame.add(p2Color);

        startGameButton = new JButton("Start Game");
        startGameButton.setBounds(150, 300, 150, 30);
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                startGame();
            }
        });
        frame.add(startGameButton);

        frame.setVisible(true);
    }

    private void updateColorLabels() {
        p1SelectedColor = new Color(p1RSlider.getValue(), p1GSlider.getValue(), p1BSlider.getValue());
        p1Color.setBackground(p1SelectedColor);

        p2SelectedColor = new Color(p2RSlider.getValue(), p2GSlider.getValue(), p2BSlider.getValue());
        p2Color.setBackground(p2SelectedColor);
    }

    private class SliderChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            updateColorLabels();
        }
    }

    private void startGame() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Player p1 = new Player(p1SelectedColor, 1);
                Player p2 = new Player(p2SelectedColor, 2);
                Game game = new Game(p1, p2);
                GUIBoard guiBoard = new GUIBoard(game);
                guiBoard.display();
            }
        });
    }

    public static void main(String[] args) {
        new ColorChoice();
    }
}
