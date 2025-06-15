// SnakeGame.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakeGame extends JPanel implements KeyListener {


    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;
    private static final int BLOCK_SIZE = 10;
    private static final int DELAY = 100;


    private int[] x = new int[WIDTH / BLOCK_SIZE];
    private int[] y = new int[HEIGHT / BLOCK_SIZE];
    private int score = 0;
    private int direction = 1; 
    private boolean gameOver = false;

    
    private int foodX;
    private int foodY;

    
    private Timer timer;

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocus();
        addKeyListener(this);

        
        for (int i = 0; i < x.length; i++) {
            x[i] = 0;
            y[i] = 0;
        }
        score = 0;
        direction = 1;
        gameOver = false;

        Random random = new Random();
        foodX = random.nextInt(WIDTH / BLOCK_SIZE) * BLOCK_SIZE;
        foodY = random.nextInt(HEIGHT / BLOCK_SIZE) * BLOCK_SIZE;

        timer = new Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
                repaint();
            }
        });
        timer.start();
    }

    private void updateGame() {

        for (int i = x.length - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 1:
                x[0] += BLOCK_SIZE;
                break;
            case 2:
                y[0] += BLOCK_SIZE;
                break;
            case 3:
                x[0] -= BLOCK_SIZE;
                break;
            case 4:
                y[0] -= BLOCK_SIZE;
                break;
        }


        if (x[0] == foodX && y[0] == foodY) {
            score++;
            Random random = new Random();
            foodX = random.nextInt(WIDTH / BLOCK_SIZE) * BLOCK_SIZE;
            foodY = random.nextInt(HEIGHT / BLOCK_SIZE) * BLOCK_SIZE;
        }

    
        if (x[0] < 0 || x[0] >= WIDTH || y[0] < 0 || y[0] >= HEIGHT) {
            gameOver = true;
        }
        for (int i = 1; i < x.length; i++) {
            if (x[0] == x[i] && y[0] == y[i]) {
                gameOver = true;
                break;
            }
        }


        if (gameOver) {
            timer.stop();
            JOptionPane.showMessageDialog(null, "Game Over! Your score is " + score);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        for (int i = 0; i < x.length; i++) {
            g.fillRect(x[i], y[i], BLOCK_SIZE, BLOCK_SIZE);
        }
        g.fillOval(foodX, foodY, BLOCK_SIZE, BLOCK_SIZE);
        g.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_RIGHT:
                direction = 1;
                break;
            case KeyEvent.VK_DOWN:
                direction = 2;
                break;
            case KeyEvent.VK_LEFT:
                direction = 3;
                break;
            case KeyEvent.VK_UP:
                direction = 4;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SnakeGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}