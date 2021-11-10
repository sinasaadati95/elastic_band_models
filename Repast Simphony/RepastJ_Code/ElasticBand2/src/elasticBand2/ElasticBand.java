package elasticBand2;

import java.util.LinkedList;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class ElasticBand {

    private Space environment ;
    protected LinkedList<Atom> array = new LinkedList<Atom>();
    
    protected int width , height ; 
    
    public ElasticBand(int width , int height ,  ContinuousSpace<Object> space , Grid<Object> grid ){
        this.width = width ;
        this.height = height ; 
        this.environment = new Space( width , height );
        this.makeCircle(space , grid);
        //JOptionPane.showMessageDialog(null, "Done1" + this.array.size());
    }
    
    
    private void makeCircle( ContinuousSpace<Object> space , Grid<Object> grid ){
        double dTheta = Math.PI / 100.000 ; 
        double r = 300 ; 
        for (double theta = 0; theta < (2.0 * Math.PI); theta+=dTheta) {
            double x = r * Math.cos(theta);
            double y = r * Math.sin(theta);
            Atom newAtom = new Atom(x + (((double)this.width / 2)) , y + (((double)this.height / 2)) , this.environment , space , grid ) ; 
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
    
    
    
    public int timeCounter = 00 ; 
    
    
    @ScheduledMethod( start = 1 , interval = 1)
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
    
    

    
    public void addFinger(int x , int y){
        this.environment.setFinger(x, y);
    }
    
    
    
    
    protected void active_Repast_Tools() {
    	for(Atom atom : this.array) {
    		atom.repast_tools_active = true ; 
    	}
    }
    
    
    
}
