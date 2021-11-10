package elasticBand2;


public class Force {
    
    double x , y ; 
    
    public Force(double x , double y){
        this.x = x ; 
        this.y = y ; 
    }
    
    
    public Force(Atom base , Atom pusher){
        double total = Math.pow( this.sq(base.x - pusher.x) + this.sq(base.y - pusher.y) , 0.500 ) ; 
        double k = 0.800 ; 
        if(total > 0.500){
            this.x = k * ( pusher.x - base.x ) ; 
            this.y = k * ( pusher.y - base.y ) ; 
        }else{
            this.x = 0.00 ; 
            this.y = 0.00 ; 
        }
    }
    
    
    public Force(Force a, Force b){
        this.x = a.x + b.x ; 
        this.y = a.y + b.y ; 
    }
    
    
    public double Value(){
        return Math.pow( this.sq(this.x) + this.sq(this.y) , 0.500 ) ; 
    }
    
    
    public double Theta(){
        return Math.atan2(this.y, this.y);
    }
    
    
    public double sq(double x){
        return x * x ; 
    }
    

}
