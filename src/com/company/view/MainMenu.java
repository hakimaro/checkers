package com.company.view;

import com.company.view.field.Field;
import com.company.view.resourses.verticalLayout.VerticalLayout;
import com.company.view.resourses.buttons.ButtonMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainMenu extends JPanel implements ActionListener {
    JFrame frame;

    public MainMenu(JFrame frame) {
        super(new VerticalLayout());
        this.frame = frame;
        setBackground(new Color(0x7F7F7F));
        JLabel checkers = new JLabel("ШАШКИ");
        checkers.setHorizontalAlignment(JLabel.CENTER);
        checkers.setFont(new Font("Serif", Font.BOLD, 64));

        JButton newGame = new ButtonMenuItem("новая игра", this);
        JButton continueGame = new ButtonMenuItem("загрузить игру", this);
        JButton parameters = new ButtonMenuItem("настройки", this);
        JButton exit = new ButtonMenuItem("выйти", this);

        add(checkers);
        add(continueGame);
        add(newGame);
        add(parameters);
        add(exit);

        if (!(new File("Save.txt").exists())) continueGame.setEnabled(false);

        this.frame.setContentPane(this);
    }

    public JPanel getPanel(JFrame frame){
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        switch (button.getText()) {
            case "выйти" -> {
                System.exit(0);
            }
            case "настройки" -> {
                frame.setContentPane(new ParametersMenu(frame).getPanel(frame));
                frame.setVisible(true);
            }
            case "новая игра" -> {
                if (!Field.isTogether()) new NewGameMenu(frame);
                else new BoardMenu(frame, false);
                frame.setVisible(true);
            }
            case "загрузить игру" -> {
                new BoardMenu(frame, true);
                frame.setVisible(true);
            }
        }
    }
}
