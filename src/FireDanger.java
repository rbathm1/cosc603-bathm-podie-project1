/**
 * The FireDanger class calculates and represents
 * the computed National Fire Danger Rating Indexes
 * 
 */
public class FireDanger {
	
	private double dryingFactor; // drying factor
	private double fuelMoisture; // fuel moisture
	private double lagFuelMoisture; // adjusted fuel moisture (10-day lag)
	private double grassSpreadIndex; // grass spread index
	private double timberSpreadIndex; // timber spread index
	private double fireLoadRating; // fire load rating
	private double buildUpIndex; // new build up index
	
	public FireDanger(ForestConditions forestConditions){
		// TODO: compute indexes
	}
}
