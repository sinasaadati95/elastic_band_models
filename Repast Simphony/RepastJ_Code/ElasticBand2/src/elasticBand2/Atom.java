package elasticBand2;

import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.grid.Grid;

public class Atom {
	


	private ContinuousSpace<Object> space ;
	private Grid<Object> grid ;

    
    protected double x , y ;
    protected double v_x = 0.0 , v_y = 0.0 ;
    
    protected Atom next = null , back = null ; 
    private Space environment = null ; 
    
    public Atom(int x , int y , Space environment ,  ContinuousSpace<Object> space , Grid<Object> grid  ){
        this.x = x ; 
        this.y = y ;
        this.environment = environment ; 
        this.space = space ; 
        this.grid = grid ; 
    }
    
    public Atom(double x , double y , Space environment ,  ContinuousSpace<Object> space , Grid<Object> grid ){
        this.x = x ; 
        this.y = y ;
        this.environment = environment ;
        this.space = space ; 
        this.grid = grid ; 
    }
    
    

    
    

    private int timeCounter = 0 ;
    
    @ScheduledMethod( start = 1 , interval = 1)
    public void nextTime(){
        this.timeCounter ++ ; 
    	if(this.timeCounter % 2 == 00) {
    		this.calculateAcceleration();
    	}else{
    		this.move();
    		this.refreshLocation();
    	}
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
    
    
    
    
    private boolean moved ; 
    public boolean stick = false ;
    public boolean amIAllowed = true ;
    
    
    protected double nxx , nyy ; 
    protected boolean repast_tools_active = false ; 
    
    
    /*
        Moves the particle in new location based on the speed.
        movement is applied if environment if free of any other particle.
    */
    public void move(){
    	
    	if(this.stick) {
    		return ; 
    	}
    	
        double x0 = this.x , y0 = this.y ; 
        double x1 = this.x + this.v_x , y1 = this.y + this.v_y ; 
        
        double distance = Math.pow(Math.pow((x1 - x0), 2.0 ) + Math.pow((y1 - y0), 2.0 ) , 0.500 ) ; 
        double moverX = x0 , moverY = y0 ; 
        double dx = (x1 - x0) / distance , dy = (y1 - y0) / distance ; 
        
        
        double movedDistance = 00.000 ; 
        while(movedDistance < distance){
            moverX += dx ; 
            moverY += dy ; 
            {
            	this.nxx = moverX ; 
            	this.nyy = moverY ; 
            }
            this.moved = true ; 
            if(this.repast_tools_active) { 
            	if(this.amIAllowed) {
            		this.setLocation(moverX, moverY); 
            		if(this.stick) {
            			return ; 
            		} 
            	}else{
            		this.v_x = 00.000 ; 
            		this.v_y = 00.000 ; 
            		return ; 
            	}

            }else {
            	if(this.environment.isAllowed(moverX, moverY)){
            		this.setLocation(moverX, moverY); 
            		if(this.stick) {
            			return ; 
            		} 
            	}else{
            		this.v_x = 00.000 ; 
            		this.v_y = 00.000 ; 
            		return ; 
            	}

            }
             
            movedDistance = Math.pow( this.sq(moverX - x0) + this.sq(moverY - y0) , 0.500 ) ; 
        }
    }
    
    
    
    public double sq(double t){
        return (t * t) ; 
    }
    
    
    protected void setLocation(double x , double y){
        this.x = x ; 
        this.y = y ; 
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
	
	
	

	public double realX() {
		return  this.x ;
	}
	
	public double realY() {
		return  this.y ;
	}
	

	public double getRealVX() {
		return this.v_x ; 
	}
	
	public double getRealVY() {
		return this.v_y ; 
	}




}
