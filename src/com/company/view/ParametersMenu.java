package com.company.view;

import com.company.view.field.Field;
import com.company.view.resourses.verticalLayout.VerticalLayout;
import com.company.view.resourses.buttons.ButtonMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParametersMenu extends JPanel implements ActionListener {
    JFrame frame;

    public ParametersMenu(JFrame frame) {
        super(new VerticalLayout());
        this.frame = frame;
        setBackground(new Color(0x7F7F7F));

        JLabel params = new JLabel("ПАРАМЕТРЫ");
        params.setHorizontalAlignment(JLabel.CENTER);
        params.setFont(new Font("Serif", Font.BOLD, 58));

        JButton together;
        if (Field.isTogether()) together = new ButtonMenuItem("вдвоем", this);
        else together = new ButtonMenuItem("против компьютера", this);
        JButton mustEat;
        if (Field.isMustEat()) mustEat = new ButtonMenuItem("обязательно бить", this);
        else mustEat = new ButtonMenuItem("необязательно бить", this);
        JButton exit = new ButtonMenuItem("выйти в меню", this);

        add(params);
        add(together);
        add(mustEat);
        add(exit);

        this.frame.setContentPane(this);
    }

    public JPanel getPanel(JFrame frame) {
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        switch (button.getText()) {
            case "выйти в меню" -> {
                frame.setContentPane(new MainMenu(frame).getPanel(frame));
                frame.setVisible(true);
            }
            case "вдвоем", "против компьютера" -> {
                Field.changeTogether();
                if (Field.isTogether()) button.setText("вдвоем");
                else button.setText("против компьютера");
            }
            case "обязательно бить", "необязательно бить" -> {
                Field.changeMustEat();
                if (Field.isMustEat()) button.setText("обязательно бить");
                else button.setText("необязательно бить");
            }
        }
    }
}
