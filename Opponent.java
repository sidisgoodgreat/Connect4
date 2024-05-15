package CSAFinalProject;

import java.util.Random;

public class Opponent extends Player {
    private Board board;

    public Opponent(Player player, Board board) {
        super(player.getColor(), player.getID()); // Initialize Player class with color and ID
        this.board = board;
    }

    public int randomMove() {
        Random rand = new Random();
        int col;
        do {
            col = rand.nextInt(7);
        } while (!board.isColumnValid(col) || !board.dropPiece(getID(), col));
        return col;
    }

    public int findStrategicMove() {
        int opponentID = getID() == 1 ? 2 : 1;

        // Check for blocking opponent's 3-in-a-row
        for (int col = 0; col < board.getBoard()[0].length; col++) {
            for (int row = board.getBoard().length - 1; row >= 0; row--) {
                if (board.getBoard()[row][col] == 0) {
                    board.getBoard()[row][col] = opponentID;
                    if (hasThreeInARow(row, col, opponentID)) {
                        board.getBoard()[row][col] = 0; // Reset the board state
                        if (board.dropPiece(getID(), col)) { // Place the block
                            return col;
                        }
                    }
                    board.getBoard()[row][col] = 0; // Reset the board state
                    break;
                }
            }
        }

        // If no strategic move is found, make a random move
        return randomMove();
    }

    private boolean hasThreeInARow(int row, int col, int playerID) {
        // Check all directions for 3-in-a-row
        return countSequence(row, col, playerID, 3, 1) >= 3 || // Horizontal
                countSequence(row, col, playerID, 3, 2) >= 3 || // Vertical
                countSequence(row, col, playerID, 3, 3) >= 3 || // Diagonal BL to TR
                countSequence(row, col, playerID, 3, 4) >= 3;  // Diagonal TL to BR
    }

    private int countSequence(int row, int col, int playerID, int targetCount, int directionID) {
        int count = 1;
        int rowAdd = (directionID == 1) ? 0 : (directionID == 2) ? 1 : (directionID == 3) ? 1 : 1;
        int colAdd = (directionID == 1) ? 1 : (directionID == 2) ? 0 : (directionID == 3) ? 1 : -1;
        int i = row + rowAdd, j = col + colAdd;
        while (isValidPosition(i, j) && board.getBoard()[i][j] == playerID && count < targetCount) {
            count++;
            i += rowAdd;
            j += colAdd;
        }
        i = row - rowAdd;
        j = col - colAdd;
        while (isValidPosition(i, j) && board.getBoard()[i][j] == playerID && count < targetCount) {
            count++;
            i -= rowAdd;
            j -= colAdd;
        }

        return count;
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < board.getBoard().length && col >= 0 && col < board.getBoard()[0].length;
    }
}
