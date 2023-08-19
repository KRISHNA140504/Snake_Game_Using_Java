import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int UNIT_SIZE = 20;
    private static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private static final int DELAY = 100;
    
    private final ArrayList<Point> snakeParts = new ArrayList<>();
    private int snakeLength = 1;
    
    private Point food;
    
    private char direction = 'R';
    private boolean running = true;
    
    private final Timer timer;
    private final Random random;
    
    public SnakeGame() {
        random = new Random();
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        
        startGame();
        
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public void startGame() {
        snakeParts.clear();
        snakeParts.add(new Point(WIDTH / 2, HEIGHT / 2));
        
        spawnFood();
    }
    
    public void spawnFood() {
        int x = random.nextInt(WIDTH / UNIT_SIZE) * UNIT_SIZE;
        int y = random.nextInt(HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        food = new Point(x, y);
    }
    
    public void move() {
        Point newHead = (Point) snakeParts.get(0).clone();
        
        switch (direction) {
            case 'U':
                newHead.y -= UNIT_SIZE;
                break;
            case 'D':
                newHead.y += UNIT_SIZE;
                break;
            case 'L':
                newHead.x -= UNIT_SIZE;
                break;
            case 'R':
                newHead.x += UNIT_SIZE;
                break;
        }
        
        snakeParts.add(0, newHead);
        
        if (snakeParts.size() > snakeLength) {
            snakeParts.remove(snakeParts.size() - 1);
        }
    }
    
    
    public void checkFoodCollision() {
        if (snakeParts.get(0).equals(food)) {
            snakeLength++;
            spawnFood();
        }
    }
    
    public void checkCollisions() {
        // Check if the snake hits its body
        for (int i = 1; i < snakeParts.size(); i++) {
            if (snakeParts.get(0).equals(snakeParts.get(i))) {
                running = false;
                break;
            }
        }
        
        if (running) {
            // Check if the snake hits the walls
            if (snakeParts.get(0).x < 0 || snakeParts.get(0).x >= WIDTH || 
                snakeParts.get(0).y < 0 || snakeParts.get(0).y >= HEIGHT) {
                running = false;
            }
        }
        
        if (!running) {
            timer.stop();
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFoodCollision();
            checkCollisions();
        }
        
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.RED);
            g.fillOval(food.x, food.y, UNIT_SIZE, UNIT_SIZE);
            
            for (Point part : snakeParts) {
                g.setColor(Color.GREEN);
                g.fillRect(part.x, part.y, UNIT_SIZE, UNIT_SIZE);
            }
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Score: " + (snakeLength - 1), 10, 30);
        } else {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over", WIDTH / 2 - 100, HEIGHT / 2);
        }
    }
    
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
            }
        }
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}