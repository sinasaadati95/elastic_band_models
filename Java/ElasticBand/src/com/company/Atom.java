package com.company;

import java.awt.Color;
import java.awt.Graphics;

public class Atom {


    protected double x , y ;
    protected double v_x = 0.0 , v_y = 0.0 ;

    protected Atom next = null , back = null ;
    private Space environment = null ;

    public Atom(int x , int y , Space environment){
        this.x = x ;
        this.y = y ;
        this.environment = environment ;
    }

    public Atom(double x , double y , Space environment){
        this.x = x ;
        this.y = y ;
        this.environment = environment ;
    }



    public void draw(Graphics graph){
        Color cl = graph.getColor() ;
        graph.setColor(Color.BLACK);
        int d = 2 ;
        graph.fillArc(this.X() - d , this.Y() - d , 2*d, 2*d, 00, 360);
        graph.drawLine(this.X(), this.Y(), this.next.X(), this.next.Y());
        graph.setColor(cl);
    }





    public int X(){
        return ((int)(this.x)) ;
    }

    public int Y(){
        return ((int)(this.y)) ;
    }


    public double V(){
        return Math.pow(this.sq(this.v_x) + this.sq(this.v_y), 0.500 ) ;
    }

    /*
        Calculates the acceleration and updates the speed.
    */
    public void calculateAcceleration(){
        Force rightForce = new Force(this , this.next);
        Force leftForce = new Force(this , this.back);
        Force total = new Force(leftForce , rightForce);
        double mass = 2.000 ;
        this.v_x += ( total.x / mass ) ;
        this.v_y += ( total.y / mass ) ;
        this.addFrictionForce();
    }



    /*
        Add Friction Force to the particle due to the situation.
    */
    private void addFrictionForce(){
        double v = this.V() ;
        double stopLimit = 0.05 ;
        if(v >= stopLimit){
            double newV = v - stopLimit ;
            double theta = Math.atan2(this.v_y , this.v_x ) ;
            this.v_x = newV * Math.cos(theta);
            this.v_y = newV * Math.sin(theta);
        }else{
            this.v_x = 00.000 ;
            this.v_y = 00.000 ;
        }
    }



    /*
        Moves the particle in new location based on the speed.
        movement is applied if environment if free of any other particle.
    */
    public void move(){
        double x0 = this.x , y0 = this.y ;
        double x1 = this.x + this.v_x , y1 = this.y + this.v_y ;

        double distance = Math.pow(Math.pow((x1 - x0), 2.0 ) + Math.pow((y1 - y0), 2.0 ) , 0.500 ) ;
        double moverX = x0 , moverY = y0 ;
        double dx = (x1 - x0) / distance , dy = (y1 - y0) / distance ;


        double movedDistance = 00.000 ;
        while(movedDistance < distance){
            moverX += dx ;
            moverY += dy ;
            if(this.environment.isAllowed(moverX, moverY)){
                this.x = moverX ;
                this.y = moverY ;
            }else{
                this.v_x = 00.000 ;
                this.v_y = 00.000 ;
                return ;
            }
            movedDistance = Math.pow( this.sq(moverX - x0) + this.sq(moverY - y0) , 0.500 ) ;
        }
    }



    public double sq(double t){
        return (t * t) ;
    }


}
