package com.snake;

import com.snake.util.Game;
import javax.swing.*;

public class LaunchGame{

    public LaunchGame(){
        JFrame frame = new JFrame();
        frame.add(new Game());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setBounds(500,100,500,600);
        frame.pack();
        frame.setTitle("Snake Game");

    }

    public static void main(String[] args) {
        new LaunchGame();



    }

}
