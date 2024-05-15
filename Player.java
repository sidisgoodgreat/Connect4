package CSAFinalProject;

import java.awt.Color;

public class Player {
    private int wins = 0;
    private Color playerCol;
    private int id;

    public Player(Color c, int id) {
        this.id = id;
        this.playerCol = c;
    }

    public void incWin() {
        wins++;
    }

    public int getWins() {
        return wins;
    }

    public Color getColor() {
        return playerCol;
    }

    public int getID() {
        return id;
    }
}
