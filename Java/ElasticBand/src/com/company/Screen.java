package com.company;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class Screen extends JApplet implements MouseListener , KeyListener , Runnable {


    private ElasticBand el = new ElasticBand(1100 , 700);

    public Screen(){
        this.init();
    }

    private int state = 1 ;
    private int fingerCounter ;


    @Override
    public void init(){
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.setSize(1100, 650);
        this.fingerCounter = Integer.parseInt(JOptionPane.showInputDialog("Please enter number of fingers.")) ;
    }


    @Override
    public void paint(Graphics graph){
        super.paint(graph);
        this.requestFocus();
        this.el.draw(graph);
        this.drawMessages(graph);
    }


    private void drawMessages(Graphics graph){
        if(this.state == 1){
            graph.drawString("Locate the fingers using the mouse." , 10 , 30 );
        }else if(this.state == 2){
            graph.drawString("Click the plane to create points.\nPress ENTER to start the simulation." , 10 , 30 );
        }else if(this.state == 3){
            graph.drawString("Time = " + this.time , 10 , 30 );
        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        this.el.addNail(x, y);
        this.fingerCounter -- ;

        if(this.fingerCounter <= 00 && this.state == 1){
            JOptionPane.showMessageDialog(this , "Please wait.");
            for(int i = 0 ; i < 20000 ; i ++ ){
                this.el.nextTime();
            }
            this.state = 2 ;
            this.el.clearNails();
            JOptionPane.showMessageDialog(this , "Elastic band has been created. Please make the nails.");
        }
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode){
            case KeyEvent.VK_ENTER:
                if(this.state == 2){
                    this.Engine.start();
                    this.state = 3 ;
                }
                break;
            case KeyEvent.VK_T :
            case KeyEvent.VK_SPACE:
                //this.el.nextTime();
                //this.repaint();
                break;
            case KeyEvent.VK_C :
                //this.el.clearNails();
                break;
            case KeyEvent.VK_ESCAPE :
                System.exit(00);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }



    private Thread Engine = new Thread(this);
    private int time = 00 ;

    public void run(){
        while(true){
            this.el.nextTime();
            this.time ++ ;
            this.repaint();
            try {
                this.Engine.sleep(50);
            } catch (InterruptedException e) {
                System.err.println(e.toString());
            }
        }
    }


}

