package com.company.view;


import com.company.view.field.Field;

import javax.swing.*;
import java.awt.*;

public class BoardMenu extends JPanel {
    /**
     * Метод вызывается, если создается новая игра или загружается старая из файла
     * @param frame
     * @param continueGame
     */
        public BoardMenu(JFrame frame, boolean continueGame){
            setLayout(null);
            setPreferredSize(new Dimension(800,900));

            setBackground(new Color(0x7F7F7F));

            Field field = new Field(frame, continueGame);

            add(field);
            add(field.getResignButton());
            add(field.getReturnButton());
            add(field.getNewGameButton());
            add(field.getContinueButton());
            add(field.getMessage());
            add(field.getMenuButton());

            field.setBounds(80,50,640,640);
            field.getContinueButton().setBounds(520, 700, 200, 30);
            field.getMenuButton().setBounds(350, 10, 100, 30);
            field.getNewGameButton().setBounds(520, 740, 200, 30);
            field.getReturnButton().setBounds(520, 780, 200,30);
            field.getResignButton().setBounds(520, 820, 200, 30);
            field.getMessage().setBounds(80, 700, 350, 40);

            field.getContinueButton().setEnabled(false);

            frame.setContentPane(this);
        }

    /**
     * Если продолжается текущая игра
     * @param frame
     */
    public BoardMenu(JFrame frame){
        setLayout(null);
        setPreferredSize(new Dimension(800,900));
        setBackground(new Color(0x7F7F7F));
        Field field = new Field(frame);

        add(field);
        add(field.getResignButton());
        add(field.getReturnButton());
        add(field.getNewGameButton());
        add(field.getContinueButton());
        add(field.getMessage());
        add(field.getMenuButton());

        field.setBounds(80,50,640,640);
        field.getContinueButton().setBounds(520, 700, 200, 30);
        field.getMenuButton().setBounds(350, 10, 100, 30);
        field.getNewGameButton().setBounds(520, 740, 200, 30);
        field.getReturnButton().setBounds(520, 780, 200,30);
        field.getResignButton().setBounds(520, 820, 200, 30);
        field.getMessage().setBounds(80, 700, 350, 40);

        frame.setContentPane(this);
    }
}
