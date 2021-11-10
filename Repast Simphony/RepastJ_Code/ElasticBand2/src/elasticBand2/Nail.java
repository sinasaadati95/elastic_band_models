package elasticBand2;

import java.util.List;

import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;

public class Nail {


	private ContinuousSpace<Object> space ;
	private Grid<Object> grid ;

	private int x , y ; 

    public Nail(int x , int y ,  ContinuousSpace<Object> space , Grid<Object> grid  ){
        this.x = x ; 
        this.y = y ;
        this.space = space ; 
        this.grid = grid ; 
    }
    
    
    

    
    public void refreshLocation() {
    	this.moveTo(this.X(), this.Y());
    }

    
    
    


	public void moveTo( int x , int y ) { 
		NdPoint myPoint = space.getLocation( this ); 
		NdPoint otherPoint = new NdPoint( x  , y );
		double angle = SpatialMath.calcAngleFor2DMovement( space , myPoint , otherPoint );
		space.moveByVector( this , this.getVectorSize( myPoint , x , y ) , angle , 0); 
		myPoint = space.getLocation( this );
		grid.moveTo( this , ( int ) myPoint.getX() , ( int ) myPoint.getY()); 
	}
	
	
		

	
	public double getVectorSize(NdPoint now , int nextX , int nextY ) {
		double x0 = now.getX();
		double y0 = now.getY();
		double x1 = nextX ;
		double y1 = nextY ;
		
		double dx = x1 - x0 ; 
		double dy = y1 - y0 ;
		
		double dx2PLUSdy2 = ( dx * dx ) + ( dy * dy ) ;
		double distance = Math.pow(dx2PLUSdy2, 0.500);
		
		return distance ; 
	}
	
	
	

    
    public int X(){
        return ((int)(this.x)) ; 
    }
    
    public int Y(){
        return ((int)(this.y)) ; 
    }
    
    
    

	@Watch ( watcheeClassName = "elasticBand2.Atom", 
			watcheeFieldNames = "moved",
			query = "within_moore 10",
			whenToTrigger = WatcherTriggerSchedule.IMMEDIATE )
	public void wall() { 
		GridPoint here = grid.getLocation(this);
		GridCellNgh<Atom> nghCreator = new GridCellNgh<Atom>( this.grid , here , Atom.class , 10 , 10 );   
		List< GridCell<Atom> > gridCells = nghCreator.getNeighborhood( true );
		int counter = 0 ; 
		int atoms = 0 ; 
		for(GridCell<Atom> cell : gridCells) {
			int size = cell.size();
			Iterable<Atom> y = cell.items();
			for(Atom atom : y) {
				this.stopAtom2(atom);
			}
		} 
	}
	
	
	
	private void stopAtom(Atom atom) {
		double angle1 = this.angle(atom.realX() , atom.realY() , this.x + 10 , this.y + 10) ;
		double angle2 = this.angle(atom.realX() , atom.realY() , this.x - 10 , this.y + 10) ;
		double angle3 = this.angle(atom.realX() , atom.realY() , this.x + 10 , this.y - 10) ;
		double angle4 = this.angle(atom.realX() , atom.realY() , this.x - 10 , this.y - 10) ; 

		double minAngle = this.minimum( angle1 , angle2 , angle3 , angle4 ) ; 
		double maxAngle = this.maximum( angle1 , angle2 , angle3 , angle4 ) ;

		double speedAngle = this.angle(atom.realX(), atom.realY(), atom.getRealVX(), atom.getRealVY()) ; 
		
		if(speedAngle > maxAngle | speedAngle < minAngle) {
			int doNothing = 123 ; ///Atom wants to go away!
			///atom.stick = false ;
			atom.amIAllowed = true ; 
		}else {
			///atom.stick = true ;
			atom.amIAllowed = false ; 
		}
		
	}
	
	
	
	private void stopAtom2(Atom atom) {
		double d0 = this.distance(this.x, this.y, atom.realX(), atom.realY()) ; 
		double d1 = this.distance(this.x, this.y, atom.nxx, atom.nyy) ; 
		if(d1 > d0) {
			atom.amIAllowed = true ; 
		}else {
			atom.amIAllowed = false ; 
		}
	}
	
	
	

	
	/**
	 * Computes angle of p0p1 from x axis.
	 * 
	 * @param x0 x value of first point
	 * @param y0 y value of first point
	 * @param x1 x value of second point
	 * @param y1 y value of second point
	 * @return angle of line p0p1. (0.0 <= angle <= 2PI)
	 */
	public double angle(double x0 , double y0, double x1 , double y1) {
		double dy = y1 - y0 ; 
		double dx = x1 - x0 ; 
		double r = Math.atan2(dy, dx) ; 
		if(r < 0.00) {
			return Math.PI + this.qadr(r) ; 
		}else {
			return r ; 
		} 
	}
	
	
	
	public double qadr(double u) {
		if(u >= 00.000) 
			return u ; 
		return (-u) ; 
	}
	
	
	
	public double minimum(double...args) {
		double r = args[0] ; 
		for(double x : args) {
			if(x < r) {
				r = x ; 
			}
		}
		return r ; 
	}
	
	

	
	public double maximum(double...args) {
		double r = args[0] ; 
		for(double x : args) {
			if(x > r) {
				r = x ; 
			}
		}
		return r ; 
	}
	
	
	
	public double distance(double x0 , double y0 , double x1 , double y1 ) {
		double dx = x1 - x0 ; 
		double dy = y1 - y0 ; 
		double dx2PLUSdy2 = (dx * dx) + (dy * dy) ; 
		double r = Math.pow(dx2PLUSdy2, 0.500) ; 
		return r ; 
	}
	
	
}
