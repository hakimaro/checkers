package com.company;

import com.company.view.MainMenu;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    MainWindow(String title){
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setMinimumSize(new Dimension(800, 900));
        new MainMenu(this);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow("Шашки");
    }
}
