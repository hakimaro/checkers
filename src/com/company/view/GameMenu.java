package com.company.view;

import com.company.io.IO;
import com.company.view.field.Field;
import com.company.model.Data;
import com.company.view.resourses.verticalLayout.VerticalLayout;
import com.company.view.resourses.buttons.ButtonMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameMenu extends JPanel implements ActionListener {
    JFrame frame;

    public GameMenu(JFrame frame){
        super(new VerticalLayout());
        this.frame = frame;
        setBackground(new Color(0x7F7F7F));

        JLabel menu = new JLabel("МЕНЮ");
        menu.setHorizontalAlignment(JLabel.CENTER);
        menu.setFont(new Font("Serif", Font.BOLD, 64));

        JButton continueGame = new ButtonMenuItem("продолжить партию", this);
        JButton storyOfGame = new ButtonMenuItem("история игры", this);
        JButton saveGame = new ButtonMenuItem("сохранить партию", this);
        JButton exit = new ButtonMenuItem("выйти", this);

        add(menu);
        add(continueGame);
        add(saveGame);
        add(storyOfGame);
        add(exit);

        this.frame.setContentPane(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        switch (button.getText()) {
            case "продолжить партию" -> {
                new BoardMenu(frame);
                frame.setVisible(true);
            }
            case "история игры" -> {
                new StoryMenu(frame);
                frame.setVisible(true);
            }
            case "сохранить партию" -> {
                if (!Field.isGameInProgress()) return;
                try {
                    IO.saveCurrentGame();
                } catch (IOException fileNotFoundException) {
                    System.out.println("Не нашелся файл");
                    fileNotFoundException.printStackTrace();
                }
            }
            case "выйти" -> {
                new MainMenu(frame);
                frame.setVisible(true);
            }
        }
    }
}
