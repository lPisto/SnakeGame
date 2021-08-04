
import javax.imageio.ImageIO;
import javax.swing.*;

import jdk.jshell.spi.ExecutionControl.StoppedException;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;
import java.awt.Color;
import java.awt.Font;



public class Snake extends JFrame {

    int width = 640;
    int height = 480;

    Point snake;
    Point comida;
    private Point position;
    int longitud = 2;

    boolean gameOver = false;
    boolean pause = false;
    boolean start = true;

    ArrayList<Point> lista = new ArrayList<Point>();

    int widthPoint = 10;
    int heightPoint = 10;

    int direccion = KeyEvent.VK_LEFT;
    long frecuencia = 50;
    
    
    ImagenSnake imagenSnake;



    public Snake(){
        setTitle("Snake Game");
        setSize (width,height);
        JFrame.setDefaultLookAndFeelDecorated(false);
		setUndecorated(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        Teclas teclas = new Teclas();
        this.addKeyListener(teclas);
        
        
        
        startGame();
        
        imagenSnake = new ImagenSnake();
        this.getContentPane().add(imagenSnake);

        setVisible (true);
        
        Momento momento = new Momento();
        Thread trid = new Thread(momento);
        trid.start();
    }

    public void startGame() {
        comida = new Point(200,100);
        snake = new Point (320,240);

        lista = new ArrayList<Point>();
        lista.add(snake);

        longitud = lista.size();

        crearComida();
    }

    public void crearComida() {
        Random rnd = new Random();

        comida.x = (rnd.nextInt(width)) + 5;
       
        if((comida.x % 5) > 0) {
            comida.x = comida.x - (comida.x % 5);
        }

        if(comida.x < 5) {
            comida.x = comida.x + 10;
        }

        if(comida.x > width) {
			comida.x = comida.x - 10;
		}
        
        comida.y = (rnd.nextInt(height)) + 5;
        
        if((comida.y % 5) > 0) {
            comida.y = comida.y - (comida.y % 5);
        }

        if(comida.y > height) {
			comida.y = comida.y - 10;
		}

        if(comida.y < 0) {
            comida.y = comida.y + 10;
        }
    }
    public static void main(String[] args) {
        Snake s = new Snake();
    }
    
    public void actualizar() {
        if(pause) {
            
        }else if(pause=false) {
            return;
        }

        if(start) {
        pause=false;
        }else if(start=false) {
            return;
        }

        if (gameOver) {
            pause=false;
            start=false;
        }

        imagenSnake.repaint();
        
        lista.add(0,new Point(snake.x,snake.y));
        lista.remove((lista.size()-1));

        for(int i = 1;i < lista.size();i++) {
            Point punto = lista.get(i); 
            if(snake.x == punto.x && snake.y == punto.y) {
                gameOver = true;
            }
        }
        
        if((snake.x > (comida.x - 10)) && (snake.x < (comida.x + 10)) && (snake.y > (comida.y - 10)) && (snake.y < (comida.y + 10))) {
            lista.add(0,new Point(snake.x,snake.y));
            System.out.println(lista.size());
            crearComida();
            frecuencia--;
        }

    }

    public class ImagenSnake extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if(start) {
                g.setColor(new Color(0,0,139));
            } else {
                g.setColor(new Color(0,0,139));
            }
            
            if(pause) {
                g.setColor(new Color(0,0,139));
            } else {
                g.setColor(new Color(0,0,139));
            }
            
            if(gameOver) {
                g.setColor(new Color(0,0,139));
            } else {
                g.setColor(new Color(0,0,139));
            }
            g.fillRect(0,0, width, height);
            g.setColor(new Color(50,205,50));
    
            if(lista.size() > 0) {
                for(int i=0;i<lista.size();i++) {
                    Point p = (Point)lista.get(i);
                    g.fillRect(p.x,p.y,widthPoint,heightPoint);
                }
            }
            
            g.setColor(new Color(255,0,0));
            g.fillRect(comida.x,comida.y,widthPoint,heightPoint);    
            
            if(start) {
                g.setFont(new Font("TimesRoman", Font.BOLD, 40));
                g.drawString("START", 255, 200);
                
                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g.drawString("ENTER to StartGame", 100, 320);
                g.drawString("ESC to Exit", 100, 340);
                g.drawString("Select difficult: 1-2-3", 100, 380);
            }

            if(gameOver) {
                g.setFont(new Font("TimesRoman", Font.BOLD, 40));
                g.drawString("GAME OVER", 255, 200);
                g.drawString("SCORE "+(lista.size()-1), 300, 240);

                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g.drawString("R to Start New Game", 100, 320);
                g.drawString("ESC to Exit", 100, 340);
            }

            if(pause) {
                g.setFont(new Font("TimesRoman", Font.BOLD, 40));
                g.drawString("PAUSE", 255, 200);

                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g.drawString("P to Resume", 100, 320);
                g.drawString("R to Start New Game", 100, 340);
                g.drawString("ESC to Exit", 100, 360);
            } 
                
        }

    }

    public class Teclas extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                System.exit(0);
            }else if(e.getKeyCode() == KeyEvent.VK_UP) { 
                if (direccion != KeyEvent.VK_DOWN){
                    direccion = KeyEvent.VK_UP;
                }
            }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                if (direccion != KeyEvent.VK_UP){
                    direccion = KeyEvent.VK_DOWN;
                }
            }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                if (direccion != KeyEvent.VK_RIGHT){
                    direccion = KeyEvent.VK_LEFT;
                }
            }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                if (direccion != KeyEvent.VK_LEFT){
                    direccion = KeyEvent.VK_RIGHT;
                }
            } else if(e.getKeyCode() == KeyEvent.VK_R) {
                gameOver = false;
                start=true;
                startGame();				
			}else if(e.getKeyCode() == KeyEvent.VK_P) {
                pause = !pause;
            }else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                start = false;
            }else if(e.getKeyCode() == KeyEvent.VK_1) {
                if (start=true){
                    
                    frecuencia = 50;
                    startGame();
                }
            }else if(e.getKeyCode() == KeyEvent.VK_2) {
                if (start=true){
                
                    frecuencia = 35;
                    startGame();
                }
            }else if(e.getKeyCode() == KeyEvent.VK_3) {
                if (start=true){
                
                    frecuencia = 20;
                    startGame();
                }
            }
        }
    }
    
    public class Momento extends Thread {
        long last = 0;
        public void run(){
            while(true) {
                if((java.lang.System.currentTimeMillis() - last) > frecuencia){
                    if(!gameOver && !pause && !start) {
                        
                        if(direccion == KeyEvent.VK_UP) {
                            snake.y = snake.y - heightPoint;
    
                            if(snake.y > height) {
                                snake.y = 0;
                            }
                            if(snake.y < 0) {
                                snake.y = height - heightPoint;
                            }
                        }else if(direccion == KeyEvent.VK_DOWN){
                            snake.y = snake.y + heightPoint;
                            if(snake.y > height) {
                                snake.y = 0;
                            }
                            if(snake.y < 0) {
                                snake.y = height - heightPoint;
                            }
                        }else if(direccion == KeyEvent.VK_LEFT){
                            snake.x = snake.x - widthPoint;
                            if(snake.x > width) {
                                snake.x = 0;
                            }
                            if(snake.x < 0) {
                                snake.x = width - widthPoint;
                            }
                        }else if(direccion == KeyEvent.VK_RIGHT){
                            snake.x = snake.x + widthPoint;
                            if(snake.x > width) {
                                snake.x = 0;
                            }
                            if(snake.x < 0) {
                                snake.x = width - widthPoint;
                            }

                        }

                    
                    }
                    actualizar();
                    last = java.lang.System.currentTimeMillis(); 
                }
               
            }
        }
    }

}

