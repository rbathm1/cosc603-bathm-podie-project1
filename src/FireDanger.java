import java.lang.Math;

/**
 * The FireDanger class calculates and represents
 * the computed National Fire Danger Rating Indexes
 * 
 */
public class FireDanger {
	
	private double dryingFactor_ = 0; // the drying factor
	private double fuelMoisture_ = 99; // fuel moisture
	private double lagFuelMoisture_ = 99; // adjusted fuel moisture (10-day lag)
	private double grassSpreadIndex_; // grass spread index
	private double timberSpreadIndex_; // timber spread index
	private double fireLoadRating_ = 0; // fire load rating
	private double buildUpIndex_; // new build up index
	
	private final double A[] = { -0.185900, -0.8590, -0.059660, -0.077373};
	private final double B[] = { 30.0, 19.2, 13.8, 22.5};
	private final double C[] = { 4.5, 12.5, 27.5};
	private final double D[] = { 16.0, 10.0, 7.0, 5.0, 4.0, 3.0};
	
	public FireDanger(ForestConditions forestConditions){
		// First, Set the current build up index to the previous build up index
		buildUpIndex_ = forestConditions.getPrevBuildUpIndex();		
		
		if (forestConditions.isSnow()){
			// Snow is on the ground, set timber and grass spread
			// indices to zero
			timberSpreadIndex_ = 0;
			grassSpreadIndex_ = 0;
			
			if(forestConditions.getPrecipitation() > 0.1){
				// There is more than 0.1 inches of precipitation
				buildUpIndex_ = -50*Math.log(1-Math.exp(-buildUpIndex_/50))*
						Math.exp(-1.175*(forestConditions.getPrecipitation() - 0.1));
				
				// Do not allow the build up index to be negative
				buildUpIndex_ = Math.max(buildUpIndex_, 0);
			}
		}
		else { // There is no snow on the ground
			
			// Calculate the initial fine fuel moisture, but
			// it will be altered after computing the drying
			// factor
			{
				double dif = forestConditions.getDryBulbTemp() - forestConditions.getWetBulbTemp();
			
				int index = 1;
				while(index < 4 && dif - C[index-1] > 0)
					index++;
			
				fuelMoisture_ = B[index-1]*Math.exp(A[index-1]*dif);
			}
			
			// Calculate the drying factor
			{
				int index = 1;
				while(index < 7 && fuelMoisture_ - D[index-1] > 0){
					index++;
			
				if(index == 7){
					// While loop completed all possible iterations
					dryingFactor_ = 7;
				}
				else
					// While loop exited prior to end of search
					dryingFactor_ = index - 1;
				}
			}
			
			// Fine fuel moisture must be at least one
			fuelMoisture_ = Math.max(fuelMoisture_, 1);
			
			// Adjust fine fuel moisture for herb stage
			switch(forestConditions.getHerbState()){
			case TRANSITION:
				// Add five percent to fuel moisture for TRANSITION
				fuelMoisture_ += 5;
				break;
			case GREEN:
				// Add ten percent to fuel moisture for GREEN
				fuelMoisture_ += 10;
				break;
			case CURED:
				// no adjustment for CURED
				break;
			}
			
			// Adjust build up index for precipitation
			if(forestConditions.getPrecipitation() > 0.1){
				buildUpIndex_ = -50*Math.log(1-(1-Math.exp(-buildUpIndex_/50))*
						Math.exp(-1.175*(forestConditions.getPrecipitation() - 0.1)));
				
				// Build up index cannot be less than zero
				buildUpIndex_ = Math.max(buildUpIndex_, 0);
			}
			
			// Add drying factor to build up index
			buildUpIndex_ += dryingFactor_;
			
			// Compute adjusted (lag) fuel moisture
			lagFuelMoisture_ = .9*fuelMoisture_ + 0.5 + 9.5*Math.exp((-buildUpIndex_/50));
			
			// Compute timber and grass spread indexes
			timberSpreadIndex_ = computeTimberIndex(forestConditions);
			grassSpreadIndex_ = computeGrassIndex(forestConditions);
			
			// Compute the fire load rating
			if(timberSpreadIndex_ > 0 && grassSpreadIndex_ > 0){
				fireLoadRating_ = 1.75*Math.log10(timberSpreadIndex_)+0.32*Math.log10(buildUpIndex_)-1.64;
				
				// Ensure the fire load rating is at least zero
				fireLoadRating_ = Math.max(fireLoadRating_, 0);
				
				// Get the logarithmic fire load rating
				fireLoadRating_ = Math.pow(10, fireLoadRating_);
			}
		}
	}
	
	
	
	/**
	 * Computes the timber spread index
	 * 
	 * @param forestConditions current forest conditions
	 * @return the computed timber spread index
	 */
	private double computeTimberIndex(ForestConditions forestConditions){
		double timberIndex;
		if(lagFuelMoisture_ >= 30){
			// Adjust for fuel moisture greater than 30%
			timberIndex = 1;
		}
		else if(forestConditions.getWindSpeed() < 14){
			timberIndex = 0.1312*(forestConditions.getWindSpeed()+6) * Math.pow(33 - lagFuelMoisture_, 1.65) - 3;
		}
		else{
			timberIndex = 0.00918*(forestConditions.getWindSpeed() + 14)*Math.pow(33 - lagFuelMoisture_, 1.65) - 3;
		}
		
		// The timber spread index must be within the range of 1-99
		return Math.max(Math.min(timberIndex, 99), 1);
	}
	
	
	
	/**
	 * Computes the grass spread index
	 * 
	 * @param forestConditions current forest conditions
	 * @return the computed grass spread index
	 */
	private double computeGrassIndex(ForestConditions forestConditions){
		double grassIndex;
		if(lagFuelMoisture_ >= 30 && fuelMoisture_ >= 30){
			// Adjust for fuel/lag moisture greater than 30%
			grassIndex = 1;
		}
		else if(forestConditions.getWindSpeed() < 14){
			grassIndex = 0.1312*(forestConditions.getWindSpeed()+6) * Math.pow(33 - fuelMoisture_, 1.65) - 3;
		}
		else{
			grassIndex = 0.00918*(forestConditions.getWindSpeed() + 14)*Math.pow(33 - fuelMoisture_, 1.65) - 3;
		}
		
		// The grass spread index must be within the range of 1-99
		return Math.max(Math.min(grassIndex, 99), 1);
	}	
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Drying factor: " + dryingFactor_);
		builder.append("\n");
		builder.append("Fuel moisture: " + fuelMoisture_);
		builder.append("\n");
		builder.append("Adjusted (lag) fuel moisture: " + lagFuelMoisture_);
		builder.append("\n");
		builder.append("Timber spread index: " + timberSpreadIndex_);
		builder.append("\n");
		builder.append("Grass spread index: " + grassSpreadIndex_);
		builder.append("\n");
		builder.append("Fire load rating: " + fireLoadRating_);
		builder.append("\n");
		builder.append("Build up index: " + buildUpIndex_);
		builder.append("\n");
		
		return builder.toString();
	}

	
	
	/**
	 * Main method that gathers input, computes and displays the fire danger indexes
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args){
		// Get forest conditions from command line
		ForestConditions forestConditions = ForestConditions.createFromCommandLine();
		
		// Create a new FireDanager and show on standard out
		FireDanger fireDanger = new FireDanger(forestConditions);
		System.out.println(fireDanger.toString());
	}
}
