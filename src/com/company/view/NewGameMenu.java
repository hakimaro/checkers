package com.company.view;

import com.company.view.field.Field;
import com.company.view.resourses.verticalLayout.VerticalLayout;
import com.company.view.resourses.buttons.ButtonMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameMenu extends JPanel implements ActionListener {
    JFrame frame;

    NewGameMenu(JFrame frame){
        super(new VerticalLayout());
        this.frame = frame;
        setBackground(new Color(0x7F7F7F));

        JLabel newGame = new JLabel("НОВАЯ ИГРА");
        newGame.setHorizontalAlignment(JLabel.CENTER);
        newGame.setFont(new Font("Serif", Font.BOLD, 58));

        JButton white = new ButtonMenuItem("за белых", this);
        JButton black = new ButtonMenuItem("за черных", this);
        JButton menu = new ButtonMenuItem("выйти в меню", this);

        add(newGame);
        add(white);
        add(black);
        add(menu);

        frame.setContentPane(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        switch (button.getText()) {
            case "за белых" -> {
                Field.createAi(3);
                new BoardMenu(frame, false);
                frame.setVisible(true);
            }

            case "за черных" -> {
                Field.createAi(1);
                new BoardMenu(frame, false);
                frame.setVisible(true);
            }
                case "выйти в меню" -> {
                new MainMenu(frame);
                frame.setVisible(true);
            }
        }
    }
}
