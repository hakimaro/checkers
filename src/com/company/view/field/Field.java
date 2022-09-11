package com.company.view.field;

import com.company.io.IO;
import com.company.model.AI;
import com.company.model.Data;
import com.company.model.Move;
import com.company.view.GameMenu;
import com.company.view.StoryMenu;
import com.company.view.resourses.buttons.ButtonMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;

public class Field extends JPanel implements ActionListener, MouseListener {
    private final Data board;
    private static boolean gameInProgress = true;
    private static int currentPlayer;
    private static boolean mustEat = true;
    private static boolean together = true;
    private static boolean pause = false;
    private int selectedRow, selectedCol;
    private Move[] moves;
    private final JButton resignButton;
    private final JButton newGameButton;
    private final JButton menuButton;
    private final JButton returnButton;
    private final JButton continueButton;
    private final JLabel message;
    private final JFrame frame;
    private static AI ai;

    public Field(JFrame frame, boolean continueGame){
        this(frame);
        if (continueGame) previousGame();
        else newGame();
    }

    public Field(JFrame frame) {
        this.frame = frame;
        setBackground(new Color(0x7F7F7F));
        addMouseListener(this);
        menuButton = new ButtonMenuItem("меню", this);
        resignButton = new ButtonMenuItem("сдаться", this);
        newGameButton = new ButtonMenuItem("новая игра", this);
        returnButton = new ButtonMenuItem("вернуть ход", this);
        continueButton = new ButtonMenuItem("продолжить", this);
        message = new JLabel("", JLabel.CENTER);
        message.setFont(new Font("Serif", Font.BOLD, 32));
        message.setForeground(Color.black);
        board = new Data();
        currentGame();
    }

    public static void createAi(int side){
        ai = new AI(side);
    }

    public JButton getResignButton(){
        return resignButton;
    }

    public JButton getNewGameButton(){
        return newGameButton;
    }

    public JButton getMenuButton() { return menuButton; }

    public JButton getReturnButton() {
        return  returnButton;
    }

    public JButton getContinueButton() {
        return continueButton;
    }

    public JLabel getMessage(){
        return message;
    }

    public void newGame() {
        board.startNewGame();
        StoryMenu.recreatePanel();
        currentPlayer = Data.WHITE;
        moves = board.getMoves(Data.WHITE);
        message.setText("Ход белых");
        gameInProgress = true;
        newGameButton.setEnabled(false);
        returnButton.setEnabled(false);
        resignButton.setEnabled(true);
        if (!together && ai.getSide() == Data.WHITE){
            doMakeMove(ai.findBestMove());
        }
        repaint();
    }

    public void previousGame() {
        try {
            IO.startPrevGame();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        moves = board.getMoves(currentPlayer);
        newGameButton.setEnabled(false);
        returnButton.setEnabled(false);
        resignButton.setEnabled(true);
        repaint();
    }

    public void currentGame() {
        moves = board.getMoves(currentPlayer);
        newGameButton.setEnabled(false);
        if (Data.allMoves.size() == 0) returnButton.setEnabled(false);
        resignButton.setEnabled(true);
        repaint();
    }

    public void resign() {
        if (currentPlayer == Data.WHITE)
            gameOver("Черные выиграли");
        else
            gameOver("Белые выиграли");
    }

    public void gameOver(String str) {
        message.setText(str);
        newGameButton.setEnabled(true);
        resignButton.setEnabled(false);
        gameInProgress = false;
    }

    public static int getAiSide(){
        return ai.getSide();
    }

    /**
     * Метод, отвечающий за то, что делать при нажатии на клетку
     * @param row Нажатая строка
     * @param col Нажатый столбец
     */

    public void clickOnSquare(int row, int col) {
        if (!pause) {
            for (Move move : moves)
                if (move.fromRow == row && move.fromCol == col) {
                    selectedRow = row;
                    selectedCol = col;
                    if (currentPlayer == Data.WHITE)
                        message.setText("Ход белых");
                    else
                        message.setText("Ход черных");
                    if (together || currentPlayer != ai.getSide()) repaint();
                    return;
                }

            for (Move legalMove : moves) {
                if (legalMove.fromRow == selectedRow && legalMove.fromCol == selectedCol && legalMove.toRow == row && legalMove.toCol == col) {
                    doMakeMove(legalMove);
                    if (!together && currentPlayer == ai.getSide() && gameInProgress) {
                        doMakeMove(ai.findBestMove());
                    }
                }
            }
        }
        else {
            message.setText("Пауза");
        }
    }

    /**
     * Функция, которая отвечает за выполнение хода
     * @param move Ход, который должен быть сделан
     */

    public void doMakeMove(Move move) {
        boolean eat = move.isEat(board);
        returnButton.setEnabled(true);
        board.makeMove(move);
        StoryMenu.createStory(currentPlayer, move.fromRow, move.fromCol, move.toRow, move.toCol);
        if (eat) {
            moves = board.getEats(currentPlayer, move.toRow, move.toCol);
            if (moves != null) {
                message.setText("Вы должны есть");
                if (!together && currentPlayer == ai.getSide()) {
                    if (ai.findEats(move) != null)
                        doMakeMove(ai.findEats(move));
                }
                selectedRow = move.toRow;
                selectedCol = move.toCol;
                repaint();
                return;
            }
        }

        if (currentPlayer == Data.WHITE) {
            StoryMenu.makeCountZero();
            currentPlayer = Data.BLACK;
            moves = board.getMoves(currentPlayer);
            if (moves == null)
                gameOver("Белые выиграли");
            else if (moves[0].isEat(board))
                message.setText("Вы должны съесть");
            else
                message.setText("Ход черных");
        }
        else {
            StoryMenu.makeCountZero();
            currentPlayer = Data.WHITE;
            moves = board.getMoves(currentPlayer);
            if (moves == null)
                gameOver("Черные выиграли");
            else if (moves[0].isEat(board))
                message.setText("Вы должны есть");
            else
                message.setText("Ход белых");
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        int sizeOfSquare = 80;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ( row % 2 == col % 2 )
                    g.setColor(Color.lightGray);
                else
                    g.setColor(Color.darkGray);
                g.fillRect(col * sizeOfSquare, row * sizeOfSquare, sizeOfSquare, sizeOfSquare);
                int sizeOfPawn = sizeOfSquare - (sizeOfSquare / 10);
                int positionInSquare = (sizeOfSquare - sizeOfPawn) / 2;
                final int y = positionInSquare + (row * sizeOfSquare) + sizeOfPawn / 4;
                final int x = positionInSquare + (col * sizeOfSquare) + sizeOfPawn / 4;
                switch (board.pieceAt(row, col)) {
                    case Data.WHITE -> {
                        g.setColor(Color.WHITE);
                        g.fillOval(positionInSquare + col * sizeOfSquare, positionInSquare + row * sizeOfSquare, sizeOfPawn, sizeOfPawn);
                        g.setColor(Color.black);
                        g.drawOval(positionInSquare + col * sizeOfSquare, positionInSquare + row * sizeOfSquare +2, sizeOfPawn, sizeOfPawn -1);
                        g.drawOval(positionInSquare + col * sizeOfSquare, positionInSquare + row * sizeOfSquare +1, sizeOfPawn, sizeOfPawn -1);
                        g.drawOval(positionInSquare + col * sizeOfSquare, positionInSquare + row * sizeOfSquare, sizeOfPawn, sizeOfPawn -1);
                    }
                    case Data.BLACK -> {
                        g.setColor(Color.BLACK);
                        g.fillOval(positionInSquare + col * sizeOfSquare, positionInSquare + row * sizeOfSquare, sizeOfPawn, sizeOfPawn);
                        g.setColor(Color.WHITE);
                        g.drawOval(positionInSquare + col * sizeOfSquare, positionInSquare + row * sizeOfSquare +2, sizeOfPawn, sizeOfPawn -1);
                        g.drawOval(positionInSquare + col * sizeOfSquare, positionInSquare + row * sizeOfSquare +1, sizeOfPawn, sizeOfPawn -1);
                        g.drawOval(positionInSquare + col * sizeOfSquare, positionInSquare + row * sizeOfSquare, sizeOfPawn, sizeOfPawn -1);
                    }
                    case Data.WHITE_QUEEN -> {
                        g.setColor(Color.WHITE);
                        g.fillOval(positionInSquare + col * sizeOfSquare, positionInSquare + row * sizeOfSquare, sizeOfPawn, sizeOfPawn);
                        g.setColor(new Color(0xFF727272, true));
                        g.fillOval(x, y, sizeOfPawn /2, sizeOfPawn /2);
                    }
                    case Data.BLACK_QUEEN -> {
                        g.setColor(Color.BLACK);
                        g.fillOval(positionInSquare + col * sizeOfSquare, positionInSquare + row * sizeOfSquare, sizeOfPawn, sizeOfPawn);
                        g.setColor(new Color(0xFF727272, true));
                        g.fillOval(x, y, sizeOfPawn /2, sizeOfPawn /2);
                    }
                }
            }
        }

        if (gameInProgress) {
            // Рисуется ободок вокруг полей, которыми можно ходить
            g.setColor(new Color(0xB8FF70));
            for (Move legalMove : moves) {
                g.drawRect(legalMove.fromCol * sizeOfSquare, legalMove.fromRow * sizeOfSquare, sizeOfSquare - 1, sizeOfSquare - 1);
                g.drawRect(legalMove.fromCol * sizeOfSquare + 1, legalMove.fromRow * sizeOfSquare + 1, sizeOfSquare - 3, sizeOfSquare - 3);
            }
            // После нажатия на пешку, рисуется то, куда можно походить
            if (selectedRow >= 0) {
                g.setColor(Color.white); // Рисуется ободок вокруг выбранной пешки
                g.drawRect(selectedCol* sizeOfSquare, selectedRow* sizeOfSquare, sizeOfSquare -1, sizeOfSquare -1);
                g.drawRect(selectedCol* sizeOfSquare, selectedRow* sizeOfSquare, sizeOfSquare -3, sizeOfSquare -3);
                g.setColor(Color.green); // рисуется ободок вокруг возможных для хода полей
                for (Move legalMove : moves) {
                    if (legalMove.fromCol == selectedCol && legalMove.fromRow == selectedRow) {
                        g.drawRect(legalMove.toCol * sizeOfSquare, legalMove.toRow * sizeOfSquare, sizeOfSquare -1, sizeOfSquare -1);
                        g.drawRect(legalMove.toCol * sizeOfSquare +1, legalMove.toRow * sizeOfSquare +1, sizeOfSquare -3, sizeOfSquare -3);
                    }
                }
            }
        }
        g.setColor(Color.black);
        g.drawRect(0,0, getSize().width-1, getSize().height-1);
        g.drawRect(1,1, getWidth()-3,getHeight()-3);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameInProgress)
            message.setText("Начните игру");
        else {
            int col = (e.getX()) / 80;
            int row = (e.getY()) / 80;
            if (col >= 0 && col < 8 && row >= 0 && row < 8) {
                clickOnSquare(row, col);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == newGameButton)
            newGame();
        else if (src == resignButton)
            resign();
        else if (src == menuButton) {
            new GameMenu(frame);
            frame.setVisible(true);
        }
        else if (src == returnButton) {
            currentPlayer = Data.player.get(Data.player.size()-1);
            board.returnMove();
            moves = board.getMoves(currentPlayer);
            if (Data.allMoves.size() == 0) returnButton.setEnabled(false);
            continueButton.setEnabled(true);
            pause = true;
            repaint();
        }

        else if (src == continueButton) {
            pause = false;
            continueButton.setEnabled(false);
            if (!together && currentPlayer == ai.getSide()) doMakeMove(ai.findBestMove());
        }
    }

    public static boolean isMustEat(){
        return mustEat;
    }

    public static int getCurrentPlayer(){
        return currentPlayer;
    }

    public static boolean isTogether(){
        return together;
    }

    public static boolean isGameInProgress(){
        return gameInProgress;
    }

    public static void setCurrentPlayer(int currentPlayer){
        Field.currentPlayer = currentPlayer;
    }

    public static void changeMustEat(){
        Field.mustEat = !Field.mustEat;
    }

    public static void changeTogether(){
        Field.together = !Field.together;
    }

    public static void falseTogether() { Field.together = false; }

    public static void falseMustEat() { Field.mustEat = false; }
}
