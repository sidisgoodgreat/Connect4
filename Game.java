package CSAFinalProject;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    private Board board;
    private Player p1;
    private Player p2;
    private boolean playAgainstAI;
    private int aiDifficulty;
    private Opponent aiOpponent;
    private Runnable onAIMoveComplete;
    private Runnable onGameEnd;

    public Game(boolean playAgainstAI, int aiDifficulty) {
        board = new Board();
        p1 = new Player(Color.red, 1);
        p2 = new Player(Color.yellow, 2);
        this.playAgainstAI = playAgainstAI;
        this.aiDifficulty = aiDifficulty;
        if (playAgainstAI) {
            aiOpponent = new Opponent(p2, board);
        }
    }

    public Game(Player p1, Player p2) {
        board = new Board();
        this.p1 = p1;
        this.p2 = p2;
        this.playAgainstAI = false;
    }

    public Board getBoard() {
        return board;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public boolean isPlayAgainstAI() {
        return playAgainstAI;
    }

    public int getAiDifficulty() {
        return aiDifficulty;
    }

    public Opponent getAiOpponent() {
        return aiOpponent;
    }

    public void setOnAIMoveComplete(Runnable onAIMoveComplete) {
        this.onAIMoveComplete = onAIMoveComplete;
    }

    public void setOnGameEnd(Runnable onGameEnd) {
        this.onGameEnd = onGameEnd;
    }

    public void playerMove(int column) {
        if (isGameOver()) return;
        boolean success = board.dropPiece(p1.getID(), column);
        if (success) {
            if (checkWin()) {
                if (onGameEnd != null) {
                    onGameEnd.run();
                }
                return;
            }
            if (playAgainstAI) {
                aiMove();
            }
        }
    }

    private void aiMove() {
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int column;
                if (aiDifficulty == 1) {
                    column = aiOpponent.randomMove();
                } else {
                    column = aiOpponent.findStrategicMove();
                }
                boolean success = board.dropPiece(p2.getID(), column);
                if (success) {
                    if (checkWin()) {
                        if (onGameEnd != null) {
                            onGameEnd.run();
                        }
                        return;
                    }
                    if (onAIMoveComplete != null) {
                        onAIMoveComplete.run();
                    }
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private boolean checkWin() {
        int winner = board.winCheckAll();
        return winner != -1;
    }

    private boolean isGameOver() {
        return board.winCheckAll() != -1;
    }
}
