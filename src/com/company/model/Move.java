package com.company.model;

public class Move {
    public final int fromRow;
    public final int fromCol;
    public final int toRow;
    public final int toCol;

    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }

    public boolean isEat(Data board) {
        if (fromRow - toRow > 1) {
            if (toCol < fromCol) {
                for (int i = toRow, j = toCol; i < fromRow && j < fromCol; i++, j++) {
                    if (board.pieceAt(i, j) != Data.EMPTY) {
                        return true;
                    }
                }
            }
            else {
                for (int i = toRow, j = toCol; i < fromRow && j > fromCol; i++, j--) {
                    if (board.pieceAt(i, j) != Data.EMPTY) {
                        return true;
                    }
                }
            }
        }

        else if (fromRow - toRow < -1) {
            if (toCol < fromCol) {
                for (int i = toRow, j = toCol; i > fromRow && j < fromCol; i--, j++) {
                    if (board.pieceAt(i, j) != Data.EMPTY) {
                        return true;
                    }
                }
            } else {
                for (int i = toRow, j = toCol; i > fromRow && j > fromCol; i--, j--) {
                    if (board.pieceAt(i, j) != Data.EMPTY) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
