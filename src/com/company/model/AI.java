package com.company.model;

/**
 * Класс, отвечающий за игру компьютера
 */

public class AI {
    private final int side;
    private final Data board = new Data();

    public AI(int player) {
        side = player;
    }

    public Move findBestMove() {
        Move bestMove = null;
        Move[] moves;
        int cost;
        int bestCost = -100;
        moves = board.getMoves(side);
        for (Move move : moves) {
            if (bestCost < (cost = checkMove(move))) {
                bestCost = cost;
                bestMove = move;
            }
        }
        return bestMove;
    }

    public Move findEats(Move move){
        Move[] moves;
        moves = board.getEats(getSide(), move.toRow, move.toCol);
        if (moves != null) return moves[0];
        return null;
    }
    /**
     * Функция, которая оценивает ход. Пока что сделана проверка на то, сколько чужих шашек будет рядом после хода. Чем меньше, тем лучше.
     * Ещё сделана проверка на то, станет ли шашка дамкой у компьютера после этого хода. Если станет, то ход становится приоритетным
     * @param move Ход, который берется для рассмотрения.
     * @return count, который является оценкой хода.
     */
    public int checkMove(Move move) {
        int count = 0;
        int piece1;
        if ((move.toRow == 7 && side == Data.BLACK && board.pieceAt(move.fromRow, move.fromCol) == side)) count++;
        if ((move.toRow == 0 && side == Data.WHITE && board.pieceAt(move.fromRow, move.fromCol) == side)) count++;
        if (move.toRow + 1 < 8 && move.toRow - 1 >= 0 && move.toCol - 1 >= 0 && move.toCol + 1 < 8){
            if ((piece1 = board.pieceAt(move.toRow+1, move.toCol+1)) != Data.EMPTY && piece1 != side && piece1 != side+1) {
                count--;
            }
            if ((piece1 = board.pieceAt(move.toRow+1, move.toCol-1)) != Data.EMPTY && piece1 != side && piece1 != side+1) {
                count--;
            }
            if ((piece1 = board.pieceAt(move.toRow-1, move.toCol-1)) != Data.EMPTY && piece1 != side && piece1 != side+1) {
                count--;
            }
            if ((piece1 = board.pieceAt(move.toRow-1, move.toCol+1)) != Data.EMPTY && piece1 != side && piece1 != side+1) {
                count--;
            }
        }
        return count;
    }

    public int getSide() {
        return side;
    }
}
