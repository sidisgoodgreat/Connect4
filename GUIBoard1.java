package CSAFinalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIBoard1 implements ActionListener {

    private JFrame frame;
    private JTextField status;
    private Board b = new Board();
    private JLabel[][] displayBoard = new JLabel[6][7];
    private JButton restart, placePiece;
    private JToggleButton playerToggle = new JToggleButton();
    private JLabel header, playerLabel = new JLabel("Choose the boxes at the top row to drop a piece!");
    private JButton[] buttons = new JButton[7];
    private JTextArea P1Score;
    private JTextArea P2Score;

    private Player p1;
    private Player p2;
    private boolean playAgainstAI;
    private int aiDifficulty;
    private Opponent aiOpponent;

    public GUIBoard1(boolean playAgainstAI, int aiDifficulty) {
        this.playAgainstAI = playAgainstAI;
        this.aiDifficulty = aiDifficulty;

        p1 = new Player(Color.red, 1);
        p2 = new Player(Color.yellow, 2);

        if (playAgainstAI) {
            aiOpponent = new Opponent(p2, b);
        }

        createDisplay();
    }

    public GUIBoard1(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        createDisplay();
    }

    public void createDisplay() {
        frame = new JFrame();
        frame.getContentPane().setFont(new Font("Segoe UI Black", Font.BOLD, 11));
        frame.setBounds(800, 800, 800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        status = new JTextField();
        status.setFont(new Font("Tahoma", Font.BOLD, 12));
        status.setBounds(249, 634, 298, 91);
        frame.getContentPane().add(status);
        status.setColumns(10);

        playerLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        playerLabel.setBounds(256, 98, 298, 96);
        frame.getContentPane().add(playerLabel);

        header = new JLabel("Connect 4!");
        header.setFont(new Font("Tahoma", Font.BOLD, 40));
        header.setBounds(288, 11, 266, 68);
        frame.getContentPane().add(header);

        placePiece = new JButton("Start Game");
        placePiece.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < buttons.length; i++) {
                    buttons[i].setVisible(true);
                }
                placePiece.setVisible(false);
                restart.setVisible(false);
                playerLabel.setVisible(true);
                P1Score.setVisible(true);
                P2Score.setVisible(true);
            }
        });
        placePiece.setFont(new Font("Tahoma", Font.BOLD, 11));
        placePiece.setBackground(new Color(0, 255, 128));
        placePiece.setBounds(302, 200, 200, 30);
        frame.getContentPane().add(placePiece);

        restart = new JButton("Restart Game");
        restart.setBackground(Color.RED);
        restart.setBounds(302, 200, 200, 30);
        frame.getContentPane().add(restart);

        playerToggle.setSelected(true);
        playerToggle.setBounds(62, 98, 161, 29);
        frame.getContentPane().add(playerToggle);

        P1Score = new JTextArea();
        P1Score.setBounds(594, 11, 200, 53);
        P1Score.setText("Player 2 Score: 0");
        frame.getContentPane().add(P1Score);

        P2Score = new JTextArea();
        P2Score.setBounds(594, 74, 200, 53);
        P2Score.setText("Player 1 Score: 0");
        frame.getContentPane().add(P2Score);
        playerLabel.setVisible(false);
        playerToggle.setEnabled(false);

        int x, y = 252;
        for (int r = 0; r < displayBoard.length; r++) {
            x = 150;
            for (int c = 0; c < displayBoard[0].length; c++) {
                x += 55;
                displayBoard[r][c] = new JLabel();
                displayBoard[r][c].setBackground(Color.white);
                displayBoard[r][c].setOpaque(true);
                displayBoard[r][c].setBounds(x, y, 50, 50);
                frame.getContentPane().add(displayBoard[r][c]);
            }
            y += 65;
        }

        int x1 = 150;
        int y1 = 200;
        for (int i = 0; i < buttons.length; i++) {
            x1 += 55;
            buttons[i] = new JButton(i + "");
            buttons[i].setBounds(x1, y1, 50, 50);
            frame.getContentPane().add(buttons[i]);
            buttons[i].addActionListener(this);
        }
        y1 += 65;
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setVisible(false);
        }

        updatePlayerToggle(coinFlip());
        initialize();
        frame.setVisible(true);
    }

    private boolean coinFlip() {
        return (int) (Math.random() * 2) == 1;
    }

    private void updateBoard(Player p1, Player p2) {
        for (int row = 0; row < b.getBoard().length; row++) {
            for (int column = 0; column < b.getBoard()[0].length; column++) {
                int point = b.getBoard()[row][column];
                displayBoard[row][column].setBackground(
                        (point == p1.getID()) ? p1.getColor() :
                                (point == p2.getID() ? p2.getColor() : Color.white));
            }
        }
    }

    private void updatePlayerToggle(boolean init) {
        playerToggle.setSelected(init);
        playerToggle.setText((init ? "Player 1" : "Player 2"));
    }

    private void winCheck() {
        if (b.winCheckAll() > 0) {
            status.setBackground(Color.GREEN);
            status.setText("Player " + (!playerToggle.isSelected() ? 1 : 2) + " scored a WIN!");
            placePiece.setVisible(false);
            restart.setVisible(true);
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setVisible(false);
            }
            playerLabel.setVisible(false);
            if (!playerToggle.isSelected())
                p1.incWin();
            else
                p2.incWin();
            P1Score.setText("Player 1 Score: " + p1.getWins());
            P2Score.setText("Player 2 Score: " + p2.getWins());
        }
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]) {
                if (b.dropPiece(playerToggle.isSelected() ? 1 : 2, i)) {
                    updateBoard(p1, p2);
                    winCheck();
                    if (playAgainstAI && !playerToggle.isSelected()) {
                        aiMove();
                    } else {
                        updatePlayerToggle(!playerToggle.isSelected());
                    }
                }
            }
        }
    }

    private void aiMove() {
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int column = (aiDifficulty == 1) ? aiOpponent.randomMove() : aiOpponent.findStrategicMove();
                b.dropPiece(p2.getID(), column);
                updateBoard(p1, p2);
                winCheck();
                updatePlayerToggle(true);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void initialize() {
        P1Score.setVisible(false);
        P2Score.setVisible(false);
        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                status.setText("");
                status.setBackground(Color.WHITE);

                b.clearBoard();
                updateBoard(p1, p2);

                updatePlayerToggle(coinFlip());
                restart.setVisible(false);
                for (int i = 0; i < buttons.length; i++) {
                    buttons[i].setVisible(true);
                }
                playerLabel.setVisible(true);
                P1Score.setVisible(true);
                P2Score.setVisible(true);
                P1Score.setText("Player 1 Score: " + p1.getWins());
                P2Score.setText("Player 2 Score: " + p2.getWins());
            }
        });
        playerToggle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerToggle.setText((playerToggle.isSelected() ? "Player 1" : "Player 2"));
            }
        });
    }
}

