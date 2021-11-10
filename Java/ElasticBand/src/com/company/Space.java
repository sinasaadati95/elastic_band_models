package com.company;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Space {


    private int width , height ;
    private boolean environment [][] = null ;

    public Space(int x , int y){
        this.height = y ;
        this.width = x ;
        this.environment = new boolean[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                this.environment[i][j] = true ;
            }
        }
    }



    public void draw(Graphics graph){
        Color cl = graph.getColor();
        graph.setColor(Color.RED);
        /*
        for (int x = 0; x < this.width; x ++ ) {
            for (int y = 0; y < this.height ; y ++ ) {
                if(this.environment[x][y] == false){
                    graph.fillRect(x, y, 1 , 1 );
                }
            }
        }
        */
        int d = 10 ;
        for(Atom a : this.nails){
            graph.fillRect(a.X() - d , a.Y() - d , 2 * d , 2 * d );
        }
        graph.setColor(cl);
    }



    private LinkedList<Atom> nails = new LinkedList<Atom>();



    public void setNail(int x , int y){
        int d = 10 ;
        for (int i = x - d ; i < x + d ; i++) {
            for (int j = y - d ; j < y + d ; j++) {
                try{
                    this.environment[i][j] = false ;
                }catch(ArrayIndexOutOfBoundsException ex){
                    int doNothing = 123 ; /// Its OK. :)
                }
            }
        }
        this.nails.add(new Atom(x,y,null));
    }




    public boolean isAllowed(int x , int y){
        return this.environment[x][y] ;
    }

    public boolean isAllowed(double x , double y){
        return this.environment[ ( int ) x ][ ( int ) y ] ;
    }




    protected void clearNails(){
        this.reset();
    }



    private void reset(){
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height ; j++) {
                this.environment[i][j] = true ;
            }
        }
        this.nails = new LinkedList<Atom>();
    }



}
