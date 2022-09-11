package com.company.view;

import com.company.model.Data;
import com.company.view.field.Field;
import com.company.view.resourses.buttons.ButtonMenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StoryMenu implements ActionListener {
    private static JFrame frame;
    private final JButton menu;
    private static ArrayList<JLabel> story = new ArrayList<>();
    private static ArrayList<Integer> players = new ArrayList<>();
    private static int count = 0;
    public static boolean returned = false;
    private static JPanel panel = new JPanel(null);
    private static int x = 200;
    private static int y = 100;

    public StoryMenu(JFrame frame){
        panel.setBackground(new Color(0x7F7F7F));
        menu = new ButtonMenuItem("меню", this);
        menu.setBounds(300,0,200, 30);
        JLabel player1 = new JLabel("Белые");
        player1.setBounds(200, 50, 200, 30);
        JLabel player2 = new JLabel("Черные");
        player2.setBounds(500, 50, 200, 30);

        player1.setFont(new Font("Arial", Font.BOLD, 20));
        player2.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(player1);
        panel.add(player2);
        panel.add(menu);
        StoryMenu.frame = frame;
        frame.setContentPane(panel);
    }

    public static void recreatePanel(){
        panel = new JPanel(null);
        x = 200;
        y = 75;
    }

    /**
     * Метод, который отвечает за добавление записи хода
     * @param currentPlayer Текущий игрок, который сделал ход
     * @param fromRow Строка, с которой произведен ход
     * @param fromColumn Столбец, с которого произведен ход
     * @param toRow Строка, куда произведен ход
     * @param toColumn Столбец, куда произведен ход
     */
    public static void createStory(int currentPlayer, int fromRow, int fromColumn, int toRow, int toColumn) {
        if (currentPlayer == 3) {
            if (count != 0) y+=25;
            x=500;
            count++;
            players.add(currentPlayer);
        }

        else if (currentPlayer == 1) {
            x=200;
            y+=25;
            count++;
            players.add(currentPlayer);
        }

        JLabel label = new JLabel(createString(fromRow, fromColumn, toRow, toColumn));
        label.setBounds(x, y, 200, 20);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        story.add(label);
        panel.add(label);
    }

    public static void makeCountZero(){
        count = 0;
    }

    /**
     *
     * @param fromRow Строка, с которой произведен ход
     * @param fromColumn Столбец, с которого произведен ход
     * @param toRow Строка, куда произведен ход
     * @param toColumn Столбец, куда произведен ход
     * @return Строчную запись хода
     */
    public static String createString(int fromRow, int fromColumn, int toRow, int toColumn) {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < 2; i++){
            if ((i == 0 && fromColumn == 0) || (i == 1 && toColumn == 0)) {
                if (i == 0) str.append("A").append(8-fromRow);
                else str.append("->A").append(8-toRow);
            }
            else if ((i == 0 && fromColumn == 1) || (i == 1 && toColumn == 1)) {
                if (i == 0) str.append("B").append(8-fromRow);
                else str.append("->B").append(8-toRow);
            }
            else if ((i == 0 && fromColumn == 2) || (i == 1 && toColumn == 2)) {
                if (i == 0) str.append("C").append(8-fromRow);
                else str.append("->C").append(8-toRow);
            }
            else if ((i == 0 && fromColumn == 3) || (i == 1 && toColumn == 3)) {
                if (i == 0) str.append("D").append(8-fromRow);
                else str.append("->D").append(8-toRow);
            }
            else if ((i == 0 && fromColumn == 4) || (i == 1 && toColumn == 4)) {
                if (i == 0) str.append("E").append(8-fromRow);
                else str.append("->E").append(8-toRow);
            }
            else if ((i == 0 && fromColumn == 5) || (i == 1 && toColumn == 5)) {
                if (i == 0) str.append("F").append(8-fromRow);
                else str.append("->F").append(8-toRow);
            }
            else if ((i == 0 && fromColumn == 6) || (i == 1 && toColumn == 6)) {
                if (i == 0) str.append("G").append(8-fromRow);
                else str.append("->G").append(8-toRow);
            }
            else if ((i == 0 && fromColumn == 7) || (i == 1 && toColumn == 7)) {
                if (i == 0) str.append("H").append(8-fromRow);
                else str.append("->H").append(8-toRow);
            }
        }
        return str.toString();
    }

    public static void deleteLabel(){
        if (Field.getCurrentPlayer() == Data.BLACK) y-=25;
        panel.remove(story.get(story.size()-1));
        story.remove(story.size()-1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == menu) {
            new GameMenu(frame);
            frame.setVisible(true);
        }
    }
}
