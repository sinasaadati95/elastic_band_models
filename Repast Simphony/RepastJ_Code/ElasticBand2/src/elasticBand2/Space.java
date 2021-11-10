package elasticBand2;

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
    }
    
    
    
    

    
    public void setFinger(int x , int y){
        int d = 10 ; 
        for (int i = x - d ; i < x + d ; i++) {
            for (int j = y - d ; j < y + d ; j++) {
            	if(this.isInCircle(i, j, x, y, d)) {
	                try{
	                    this.environment[i][j] = false ; 
	                }catch(ArrayIndexOutOfBoundsException ex){
	                    int doNothing = 123 ; /// Its OK. :)
	                }
            	}
            }
        } 
    }
    
    
    
    

    
    public boolean isInCircle(double x , double y , double circleX , double circleY , double r){
        double dx = x - circleX ; 
        double dy = y - circleY ; 
        double distance = (dx * dx) + (dy*dy) ; 
        double r2 = r * r ; 
        if(distance <= r2){
            return true; 
        }else{
            return false ; 
        }
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
    }
    

}
