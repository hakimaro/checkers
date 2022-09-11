package com.company.test;

import com.company.model.Data;
import com.company.model.Move;

public class Tests {
    private static int failed = 0;
    private static int total = 0;

    /**
     * Проверяется 2 метода: getEat() и getMove()
     */
    private static void testCanEatOpp(boolean queen){
        total++;
        Data board = new Data();
        int[][] field = new int[8][8];
        if (!queen) {
            field[7][0] = Data.WHITE;
            field[6][1] = Data.BLACK;
        }
        else {
            field[7][0] = Data.WHITE_QUEEN;
            field[4][3] = Data.BLACK;
        }
        Data.setBoard(field);
        Move[] moves = board.getEats(Data.WHITE, 7,0);
        Move[] moves2 = board.getMoves(Data.WHITE);
        if (moves == null) {
            failed++;
            throw new RuntimeException("Can eat test in getEat() is failed");
        }

        if (moves2 == null) {
            failed++;
            throw new RuntimeException("Can eat in getMoves() is failed");
        }
    }

    /**
     * Проверяется метод canMove()
     */
    private static void testCanMove(){
        total++;
        Data board = new Data();
        int[][] field = new int[8][8];
        field[7][0] = Data.WHITE;
        Data.setBoard(field);
        Move[] moves = board.getMoves(Data.WHITE);
        if (moves == null) {
            failed++;
            throw new RuntimeException("Can move test is failed");
        }
    }

    /**
     * Проверяет, может ли игрок ходить чужими шашками
     */
    private static void testCanMoveOppChecks(boolean queen){
        total++;
        Data board = new Data();
        int[][] field = new int[8][8];
        if (!queen) field[7][0] = Data.BLACK;
        else field[7][0] = Data.BLACK_QUEEN;
        Data.setBoard(field);
        Move[] moves = board.getMoves(Data.WHITE);
        if (moves != null) {
            failed++;
            throw new RuntimeException("Can move opponent's checkers test is failed");
        }
    }

    /**
     * Тест на то, может ли дамка ходить по всей диагонали
     */
    private static void testCanMoveQueen(){
        total++;
        Data board = new Data();
        int[][] field = new int[8][8];
        field[7][0] = Data.WHITE_QUEEN;
        Data.setBoard(field);
        Move[] moves = board.getMoves(Data.WHITE);
        if (moves.length != 7) {
            failed++;
            throw new RuntimeException("Can move queen test is failed ");
        }
    }

    /**
     * Проверяет на то, может ли шашка ходить через другие шашки
     */
    private static void testCantMove(boolean queen){
        total++;
        Data board = new Data();
        int[][] field = new int[8][8];
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if ((i + j) % 2 == 0 ) field[i][j] = Data.BLACK;
                if (i == 5 && j == 5 && queen) field[i][j] = Data.WHITE_QUEEN;
                if (i == 5 && j == 5 && !queen) field[i][j] = Data.WHITE;
            }
        }
        Data.setBoard(field);

        if (board.getMoves(Data.WHITE) != null) {
            failed++;
            throw new RuntimeException("Can't move test is failed");
        }
    }

    /**
     * Проверка на работоспособность метода makeMove();
     */
    private static void testMakeMove(){
        total++;
        Data board = new Data();
        int[][] field = new int[8][8];
        field[7][0] = Data.WHITE;
        Data.setBoard(field);
        Move[] moves = board.getMoves(Data.WHITE);
        board.makeMove(moves[0]);
        field = Data.getBoard();
        if (field[7][0] != Data.EMPTY || field[6][1] != Data.WHITE) {
            failed++;
            throw new RuntimeException("Make move test is failed");
        }
    }

    private static void testQueen(){
        testCanMoveQueen();
        testCanEatOpp(true);
        testCanMoveOppChecks(true);
        testCantMove(true);
    }

    private static void testChecker(){
        testCanMove();
        testCanEatOpp(false);
        testCanMoveOppChecks(false);
        testCantMove(false);
        testMakeMove();
    }


    private static void runTests(){
        System.out.println("Тест шашек");
        testChecker();
        System.out.println("Тест дамок");
        testQueen();
    }

    private static void showResult(){
        if (failed == 0) {
            System.out.println("All test: " + total + " is OK");
        }
        else {
            System.out.println("Tests: " + total + ". Failed: " + failed);
        }
    }

    public static void main(String[] args){
        runTests();
        showResult();
    }
}
