package com.company;

import java.awt.Graphics;
import java.util.LinkedList;

public class ElasticBand {


    private Space environment ;
    private LinkedList<Atom> array = new LinkedList<Atom>();

    private int width , height ;

    public ElasticBand(int width , int height){
        this.width = width ;
        this.height = height ;
        this.environment = new Space( width , height );
        this.makeCircle();
    }


    private void makeCircle(){
        double dTheta = Math.PI / 100.000 ;
        double r = 300 ;
        for (double theta = 0; theta < (2.0 * Math.PI); theta+=dTheta) {
            double x = r * Math.cos(theta);
            double y = r * Math.sin(theta);
            Atom newAtom = new Atom(x + ((int)(this.width / 2)) , y + ((int)(this.height / 2)) , this.environment ) ;
            this.array.add(newAtom);
        }
        this.makeChain();
    }




    private void makeChain(){
        for (int i = 0 ; i < this.array.size() - 1 ; i++) {
            Atom temp = this.array.get(i) ;
            Atom next = this.array.get(i+1) ;
            temp.next = next ;
            next.back = temp ;
        }
        Atom first = this.array.get(0) ;
        Atom last = this.array.get(this.array.size() - 1 ) ;
        last.next = first ;
        first.back = last ;
    }




    public void draw(Graphics graph){
        this.environment.draw(graph);
        this.drawChain(graph);
    }



    private void drawChain(Graphics graph){
        for(Atom a : this.array){
            a.draw(graph);
        }
    }



    public int timeCounter = 00 ;

    public void nextTime(){
        for (int i = 0; i < this.array.size() ; i++) {
            Atom particle = this.array.get(i);
            particle.calculateAcceleration();
        }
        for(Atom a : this.array){
            a.move();
        }
        this.timeCounter ++ ;
    }



    public void clearNails(){
        this.environment.clearNails();
    }


    public void addNail(int x , int y){
        this.environment.setNail(x, y);
    }





}
