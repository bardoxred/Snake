package com.snake.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


public class Game extends JPanel implements KeyListener, ActionListener {

    private final int WIDTH = 600;
    private final int HEIGHT = 600;

    private int[] snakeXLength = new int[751];
    private int[] snakeYLength = new int[751];

    private int[] enemyXPos = {60,90,120,150,180,210,240,270,300,330,360,390,420,450,480,510,540,570};
    private int[] enemyYPos = {60,90,120,150,180,210,240,270,300,330,360,390,420,450,480,510,540,570};

    private int[] appleXPos = {60,90,120,150,180,210,240,270,300,330,360,390,420,450,480,510,540};
    private int[] appleYPos = {60,90,120,150,180,210,240,270,300,330,360,390,420,450,480,510,540};

    private ImageIcon enemy;
    private ImageIcon apple;
    private Random random = new Random();
    private int xPos = random.nextInt(17)+1;
    private int yPos = random.nextInt(17)+1;

    private int xAPos = random.nextInt(17)+1;
    private int yAPos = random.nextInt(17)+1;
    private int points = 0;

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    private boolean isPlaying = true;

    private ImageIcon goUp;
    private ImageIcon goDown;
    private ImageIcon goRight;
    private ImageIcon goLeft;

    private ImageIcon snakeBody;
    private int snakeLength = 3;
    private int moves = 0;

    private Timer timer;
    private int delay = 100;


    public Game(){

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();


    }

    public void paint(Graphics g){

        //Starting position
        if(moves==0){
            snakeXLength[2] = 30;
            snakeXLength[1] = 60;
            snakeXLength[0] = 90;

            snakeYLength[2] = 30;
            snakeYLength[1] = 30;
            snakeYLength[0] = 30;
        }


        //Game Board
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(new Color(0,0,0));
        g.fillRect(0,0,WIDTH,HEIGHT);

        //Draw Lines
        g.setColor(Color.YELLOW);
        for(int i=0; i<WIDTH/30; i++){
            g.drawLine(i*30,0,i*30,HEIGHT);
        }
        for(int i=0; i<HEIGHT/30; i++){
            g.drawLine(0,i*30,HEIGHT,i*30);
        }
        g.drawLine(0,HEIGHT,WIDTH,HEIGHT);
        g.drawLine(WIDTH,0,HEIGHT,WIDTH);
        image(g);

    }

    private void image(Graphics g) {

        snakeIcon(g);
        enemyIcon(g);
        appleIcon(g);
        g.dispose();
        repaint();
    }

    private void appleIcon(Graphics g) {
        apple = new ImageIcon("src/com/snake/image/apple.jpg");

        if(appleXPos[xAPos] == snakeXLength[0] && appleYPos[yAPos] == snakeYLength[0]) {
            points += 10;
            snakeLength++;
            xAPos = random.nextInt(16) + 1;
            yAPos = random.nextInt(16) + 1;
        }
        apple.paintIcon(this, g, appleXPos[xAPos], appleYPos[yAPos]);

        }

    private void enemyIcon(Graphics g) {
        enemy = new ImageIcon("src/com/snake/image/enemy.jpg");
        if (enemyXPos[xPos] == snakeXLength[0] && enemyYPos[yPos] == snakeYLength[0]) {
            if (snakeLength == 1) {
                gameOver();
            }
            xPos = random.nextInt(17) + 1;
            yPos = random.nextInt(17) + 1;
            points -= 10;
            snakeLength--;

        }
        enemy.paintIcon(this, g, enemyXPos[xPos], enemyYPos[yPos]);
    }


    private void gameOver() {
        isPlaying=false;
        timer.stop();
        JOptionPane.showMessageDialog(null,"You lose! Points: "+points+"\nPress Space to restart!");

    }

    private void snakeIcon(Graphics g) {
        goRight = new ImageIcon("src/com/snake/image/headRIGHT.jpg");
        goRight.paintIcon(this,g,snakeXLength[0],snakeYLength[0]);

        for(int i=0; i<snakeLength; i++){

            if(i==0 && right){
                goRight = new ImageIcon("src/com/snake/image/headRIGHT.jpg");
                goRight.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);

            }
            if(i==0 && left){
                goLeft = new ImageIcon("src/com/snake/image/headLEFT.jpg");
                goLeft.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);

            }
            if(i==0 && up){
                goUp = new ImageIcon("src/com/snake/image/headUP.jpg");
                goUp.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);

            }
            if(i==0 && down){
                goDown = new ImageIcon("src/com/snake/image/headDOWN.jpg");
                goDown.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);

            }
            if(i!=0){
                snakeBody = new ImageIcon("src/com/snake/image/body.jpg");
                snakeBody.paintIcon(this,g,snakeXLength[i],snakeYLength[i]);
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();
        moveRight();
        moveLeft();
        moveUp();
        moveDown();


    }

    private void moveRight() {
        if(right){

            for(int r = snakeLength-1; r>=0; r--){
                snakeYLength[r+1] = snakeYLength[r];
            }
            for(int r = snakeLength; r>=0; r--){
                if(r==0){
                    snakeXLength[r] = snakeXLength[r]+30;
                }
                else{
                    snakeXLength[r] = snakeXLength[r-1];
                }
                if(snakeXLength[r]>WIDTH){
                    gameOver();
                }
                collision(r);

            }
            repaint();
        }
    }

    private void moveLeft() {
        if(left){

            for(int r = snakeLength-1; r>=0; r--){
                snakeYLength[r+1] = snakeYLength[r];
            }
            for(int r = snakeLength; r>=0; r--){
                if(r==0){
                    snakeXLength[r] = snakeXLength[r]-30;
                }
                else{
                    snakeXLength[r] = snakeXLength[r-1];
                }
                if(snakeXLength[r] < 0){
                    gameOver();
                }
                collision(r);
            }
            repaint();
        }
    }

    private void moveUp() {
        if(up){

            for(int r = snakeLength-1; r>=0; r--){
                snakeXLength[r+1] = snakeXLength[r];
            }
            for(int r = snakeLength; r>=0; r--){
                if(r==0){
                    snakeYLength[r] = snakeYLength[r]-30;
                }
                else{
                    snakeYLength[r] = snakeYLength[r-1];
                }
                if(snakeYLength[r]<0){
                    gameOver();
                }
               collision(r);
            }
            repaint();
        }
    }

    private void moveDown() {
        if(down){

            for(int r = snakeLength-1; r>=0; r--){
                snakeXLength[r+1] = snakeXLength[r];
            }
            for(int r = snakeLength; r>=0; r--){
                if(r==0){
                    snakeYLength[r] = snakeYLength[r]+30;
                }
                else{
                    snakeYLength[r] = snakeYLength[r-1];
                }
                if(snakeYLength[r]>=HEIGHT){
                    gameOver();
                }
                collision(r);
            }
            repaint();

        }
    }

    private void collision(int r) {
        if(snakeXLength[0]==snakeXLength[r+1] && snakeYLength[0] == snakeYLength[r+1]){
            gameOver();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        keyRight(e);
        keyLeft(e);
        keyUp(e);
        keyDown(e);
        keyRestart(e);
        keyExit(e);


    }

    private void keyExit(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
    }

    private void keyRestart(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            moves = 0;
            snakeLength = 3;
            points = 0;

            snakeXLength[2] = 30;
            snakeXLength[1] = 60;
            snakeXLength[0] = 90;

            snakeYLength[2] = 30;
            snakeYLength[1] = 30;
            snakeYLength[0] = 30;
            timer.start();


        }
    }

    private void keyDown(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            down=true;
            if(!up){
                down=true;
            }
            else{
                down=false;
                up=true;
            }
            left=false;
            right=false;
            moves++;
        }
    }

    private void keyUp(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            up=true;
            if(!down){
                up=true;
            }
            else{
                up=false;
                down=true;
            }
            right=false;
            left=false;
            moves++;
        }
    }

    private void keyLeft(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            left=true;
            if(!right){
                left=true;
            }
            else{
                left=false;
                right=true;
            }
            up=false;
            down=false;
            moves++;
        }
    }

    private void keyRight(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            right=true;
            if(!left){
                right=true;
            }
            else{
                right=false;
                left=true;
            }
            up=false;
            down=false;
            moves++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
