package snakePackage;

import javax.swing.*;

public class Frame extends JFrame {
    public GamePanel panel;

    public Frame(){
        panel = new GamePanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Snake");
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

}
