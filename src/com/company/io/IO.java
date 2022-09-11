package com.company.io;

import com.company.model.Data;
import com.company.view.field.Field;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class IO {
    public static void saveCurrentGame() throws IOException {
        FileWriter f1 = new FileWriter("Save.txt");
        int[][] board = Data.getBoard();
        StringBuilder print = new StringBuilder();
        print.append(Field.getCurrentPlayer()).append(" ");
        print.append(Field.isMustEat()).append(" ");
        print.append(Field.isTogether()).append(" ");
        if (!Field.isTogether()) print.append(Field.getAiSide());
        print.append('\n');
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                print.append(board[i][j]).append(" ");
            }
            print.append('\n');
        }
        f1.write(print.toString());
        f1.flush();
    }

    public static void startPrevGame() throws FileNotFoundException {
        Scanner in = new Scanner(new FileInputStream("Save.txt"));
        Field.setCurrentPlayer(in.nextInt());
        int[][] board = new int[8][8];
        if (!in.nextBoolean()) Field.falseMustEat();
        else if (!Field.isMustEat()) Field.changeMustEat();
        if (!in.nextBoolean()) {
            Field.falseTogether();
            Field.createAi(in.nextInt());
        }
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                board[row][column] = in.nextInt();
            }
        }
        Data.setBoard(board);
    }
}
