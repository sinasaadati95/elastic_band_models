package elasticBand2;

import java.util.ArrayList;

import javax.swing.JOptionPane;


import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;

public class Builder implements ContextBuilder<Object> {

	private ElasticBand el ;

	@Override
	public Context build(Context<Object> context) { 

		context.setId("ElasticBand2");
		int width = 1100 , height = 700 ; 
		
		
		
		
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory( null );
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space", context , new RandomCartesianAdder < Object >() ,  	new repast.simphony.space.continuous.WrapAroundBorders() , width , height );
		
		
		GridFactory gridFactory = GridFactoryFinder.createGridFactory( null );
		Grid < Object > grid = gridFactory . createGrid ("grid", context ,	 new GridBuilderParameters<Object>( new WrapAroundBorders() , new SimpleGridAdder<Object>() , true , width , height ));
		
		
		this.el = new ElasticBand(width , height , space , grid);
		
		
		int numberOfFingers = Integer.parseInt(JOptionPane.showInputDialog("Please enter number of Fingers. \n\nNote: For simplicity, we have assumed that the environment is inside of a circle(radius=300,center of the circle(x_center = " + (width / 2) + " , y_center = " + (height / 2) + " ). Please put the fingers inside of the circle. )" ) ) ; 
		for(int i = 0 ; i < numberOfFingers ; i ++ ) {
			int x = Integer.parseInt(JOptionPane.showInputDialog("Please enter x value of finger " + (i+1) + "." ) ) ;
			int y = Integer.parseInt(JOptionPane.showInputDialog("Please enter y value of finger " + (i+1) + "." ) ) ;
			this.el.addFinger(x, y);
			//Nail n = new Nail(x , y , space , grid);
			//context.add(n);
			//n.refreshLocation();
		}
		
		if(numberOfFingers > 00) {
			for(int j = 0 ; j < 10000 ; j ++ ) {
				this.el.nextTime(); 
			}
		}
		
		this.el.clearNails();
		this.el.active_Repast_Tools();
		
		
		ArrayList<Nail> nails = new ArrayList<Nail>();
		

		int numberOfNails = Integer.parseInt(JOptionPane.showInputDialog("Please enter number of Nails." ) ) ; 
		for(int i = 0 ; i < numberOfNails ; i ++ ) {
			int x = Integer.parseInt(JOptionPane.showInputDialog("Please enter x value of nail " + (i+1) + "." ) ) ;
			int y = Integer.parseInt(JOptionPane.showInputDialog("Please enter y value of nail " + (i+1) + "." ) ) ;
			//this.el.addNail(x, y); // Repast Tools have been used instead. 
			Nail n = new Nail(x , y , space , grid); 
			nails.add(n);
			context.add(n);
			n.refreshLocation();
			//this.setNail(x, y , space , grid , context );
		}
		
		
		for(Nail x : nails) {
			x.refreshLocation();
		}

		for(Atom x : this.el.array ) {
			context.add( x );
		}
		
		

		for(Atom x : this.el.array ) {
			x.refreshLocation();
		}
		
		
		
		
		for ( Object obj : context ) {
				NdPoint pt = space.getLocation( obj );
			 	grid.moveTo( obj , ( int ) pt.getX() , ( int ) pt.getY());
			}

		
		
		JOptionPane.showMessageDialog(null, "Sina Saadati Elastic Band ");
		
		return context;
		

		
	}
	
	
	

    
	
	
}
