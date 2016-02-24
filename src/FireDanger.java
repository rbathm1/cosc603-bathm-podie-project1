/**
 * The FireDanger class calculates and represents
 * the computed National Fire Danger Rating Indexes
 * 
 */
import java.util.*;
import java.lang.Math;

public class FireDanger {
	
	private double dryingFactor; // drying factor
	private double fuelMoisture; // fuel moisture
	private double lagFuelMoisture; // adjusted fuel moisture (10-day lag)
	private double grassSpreadIndex; // grass spread index
	private double timberSpreadIndex; // timber spread index
	private double fireLoadRating; // fire load rating
	private double buildUpIndex; // new build up index
	private double precipitationValue; // precipitation value
	
	
	//output values
	
	private double adfm = 99;
	private double FFM = 99;
	private double DF = 0; // drying factor returned
	private double FLOAD = 0; 
	
	public FireDanger(ForestConditions forestConditions){
		// TODO: compute indexes
		final double A[] = { -0.185900, -0.8590, -0.059660, -0.077373};
		final double B[] = { 30.0, 19.2, 13.8, 22.5};
		final double C[] = { 4.5, 12.5, 27.5};
		final double D[] = { 16.0, 10.0, 7.0, 5.0, 4.0, 3.0};
		
		//Equations
		
		fuelMoisture = (A*Math.exp(B) ) ;
		lagFuelMoisture = (.9*fuelMoisture+9.5*Math.exp((-buildUpIndex/50));
		
		if (isSnow > 0){
			return true;
			System.out.println(" It is snowing outside");
			timberSpreadIndex = 0;
			grassSpreadIndex = 0;
			fireLoadRating = 0;
				}
		else {
			return false;
			System.out.println(" It is not snowing outside");
			fuelMoisture = (A*Math.exp(B) ) ;
			System.out.println("Fine Fuel Moisture = " fuelMoisture);
			lagFuelMoisture = (.9*fuelMoisture+9.5*Math.exp((-buildUpIndex/50));
			System.out.println("Adjusted Fuel Moisture = " lagFuelMoisture);
			
		}
		
		if (precipitationValue> = .1){
		buildUpIndex = (-50 *(Math.log(1-(Math.exp(buildUpIndex/50))* Math.exp(1.175*precipitationValue-.1))));	
		}
		}
		
	}




	
	
}

}
/**
 * @param 
 */

