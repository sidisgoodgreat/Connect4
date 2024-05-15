package CSAFinalProject;

public class Board {
    private int[][] board;

    public Board() {
        board = new int[6][7]; // Initialize a 6x7 board
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean dropPiece(int player, int column) {
        if (column < 0 || column >= board[0].length) {
            return false; // Invalid column
        }

        for (int i = board.length - 1; i >= 0; i--) {
            if (board[i][column] == 0) { // Empty spot found
                board[i][column] = player;
                return true;
            }
        }
        return false; // Column is full
    }

    public boolean isColumnValid(int column) {
        return column >= 0 && column < board[0].length && board[0][column] == 0;
    }

    public void clearBoard() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = 0;
            }
        }
    }

    public int winCheckAll() {
        for (int i = 1; i <= 4; i++) {
            int winNum = winCheck(i);
            if (winNum != -1) {
                return winNum;
            }
        }
        return -1;
    }

    private int winCheck(int id) {
        int outer1 = (id == 1) ? 0 : 3, outer2 = 5,
                inner1 = (id == 4) ? 3 : 0, inner2 = (id == 2 || id == 4) ? 6 : 3;

        for (int i = outer1; i <= outer2; i++) {
            for (int j = inner1; j <= inner2; j++) {
                int value = board[i][j];
                if (value != 0 && recWin(i, j, value, 0, id)) {
                    return value;
                }
            }
        }
        return -1;
    }

    private boolean recWin(int row, int column, int value, int count, int id) {
        if (count == 4) {
            return true;
        } else if (board[row][column] == value) {
            int newRow = row, newColumn = column;
            switch (id) {
                case 1: newColumn++; break; // Horizontal
                case 2: newRow++; break; // Vertical
                case 3: newRow++; newColumn--; break; // BL Diagonal
                case 4: newRow++; newColumn++; break; // BR Diagonal
            }
            if (newRow >= 0 && newRow < board.length && newColumn >= 0 && newColumn < board[0].length) {
                return recWin(newRow, newColumn, value, count + 1, id);
            }
        }
        return false;
    }
}
