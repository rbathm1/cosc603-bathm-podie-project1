/**
 * The FireDanger class calculates and represents
 * the computed National Fire Danger Rating Indexes
 * 
 */
import java.util.*;
import java.lang.Math;

public class FireDanger {
	
	private double dryingFactor = 0; // drying factor
	private double fuelMoisture = 99; // fuel moisture
	private double lagFuelMoisture = 99; // adjusted fuel moisture (10-day lag)
	private double grassSpreadIndex; // grass spread index
	private double timberSpreadIndex; // timber spread index
	private double fireLoadRating = 0; // fire load rating
	private double buildUpIndex; // new build up index
	private double precipitationValue; // precipitation value
	
	public FireDanger(ForestConditions forestConditions){
		// TODO: compute indexes
		final double A[] = { -0.185900, -0.8590, -0.059660, -0.077373};
		final double B[] = { 30.0, 19.2, 13.8, 22.5};
		final double C[] = { 4.5, 12.5, 27.5};
		final double D[] = { 16.0, 10.0, 7.0, 5.0, 4.0, 3.0};
		
		// First, Set the current build up index to the previous build up index
		buildUpIndex = forestConditions.getPrevBuildUpIndex();		
		
		if (forestConditions.isSnow()){
			// Snow is on the ground, set timber and grass spread
			// indices to zero
			timberSpreadIndex = 0;
			grassSpreadIndex = 0;
			
			if(forestConditions.getPrecipitation() > 0.1){
				// There is more than 0.1 inches of precipitation
				buildUpIndex = -50*Math.log(1-Math.exp(-buildUpIndex/50))*
						Math.exp(-1.175*(forestConditions.getPrecipitation() - 0.1));
				
				// Do not allow the build up index to be negative
				if(buildUpIndex < 0){
					buildUpIndex = 0;
					return;
				}
			}
		}
		else {
			// There is no snow on the ground
			
			// Calculate the fine fuel moisture
			double dif = forestConditions.getDryBulbTemp() - forestConditions.getWetBulbTemp();
			
			int cIndex = 1;
			while(dif - C[cIndex] > 0 && cIndex < 4)
				cIndex++;
			
			fuelMoisture = B[cIndex]*Math.exp(A[cIndex]*dif);
			
			// Calculate the drying factor
			int index = 1;
			while(fuelMoisture - D[index] > 0 && index < 7){
				index++;
			}
			
			if(index == 7)
				dryingFactor = 7;
			else
				dryingFactor = index - 1;
			
			// Fine fuel moisture must be at least one
			fuelMoisture = Math.max(fuelMoisture, 1);
			
			// Adjust fine fuel moisture for herb stage
			switch(forestConditions.getHerbState()){
			case TRANSITION:
				// Add five percent to fuel moisture for TRANSITION
				fuelMoisture += 5;
				break;
			case GREEN:
				// Add ten percent to fuel moisture for GREEN
				fuelMoisture += 10;
				break;
			case CURED:
				// no adjustment for CURED
				break;
			}
			
			// Adjust build up index for precipitation
			if(forestConditions.getPrecipitation() > 0.1){
				buildUpIndex = -50*Math.log(1-(1-Math.exp(-buildUpIndex/50))*
						Math.exp(-1.175*(forestConditions.getPrecipitation() - 0.1)));
				
				// Build up index cannot be less than zero
				buildUpIndex = Math.max(buildUpIndex, 0);
			}
			
			buildUpIndex += dryingFactor;
			
			// Compute adjusted (lag) fuel moisture
			lagFuelMoisture = .9*fuelMoisture + 0.5 + 9.5*Math.exp((-buildUpIndex/50));
			
			// Adjust fuel moistures greater than 30%
			
		}
		
	}
	
	private double computeTimberIndex(ForestConditions forestConditions){
		double timberIndex;
		if(lagFuelMoisture >= 30){
			timberIndex = 1;
		}
		else if(forestConditions.getWindSpeed() < 14){
			timberIndex = 0.1312*(forestConditions.getWindSpeed()+6) * Math.pow(33 - lagFuelMoisture, 1.65) - 3;
		}
		else{
			timberIndex = 0.00918*(forestConditions.getWindSpeed() + 14)*Math.pow(33 - lagFuelMoisture, 1.65) - 3;
		}
		
		return Math.max(Math.min(timberIndex, 99), 1);
	}
	
	private double computeGrassIndex(ForestConditions forestConditions){
		double grassIndex;
		if(lagFuelMoisture >= 30 && fuelMoisture >= 30){
			grassIndex = 1;
		}
		else if(forestConditions.getWindSpeed() < 14){
			grassIndex = 0.1312*(forestConditions.getWindSpeed()+6) * Math.pow(33 - fuelMoisture, 1.65) - 3;
		}
		else{
			grassIndex = 0.00918*(forestConditions.getWindSpeed() + 14)*Math.pow(33 - fuelMoisture, 1.65) - 3;
		}
		
		return Math.max(Math.min(grassIndex, 99), 1);
	}
}
