import java.util.Scanner;

/**
 * The ForestConditions class encapsulates input
 * needed to compute National Fire Danger Rating Indexes
 * 
 */
public class ForestConditions {
	
	/**
	 * The HerbStage enumeration defines the herbaceous state
	 * of vegetation
	 *
	 */
	public enum HerbStage{CURED, TRANSITION, GREEN}
	
	private double dryBulbTemp; // dry bulb temperature in degrees Fahrenheit
	private double wetBulbTemp; // wet bulb temperature in degrees Fahrenheit
	private boolean isSnow; // true if snow on ground, otherwise false
	private double windSpeed; // wind speed in miles per hour
	private double prevBuildUpIndex; // previous day's build up index
	private HerbStage herbState; // state of vegetation
	
	public ForestConditions(double dryBulbTemp, double wetBulbTemp,
			boolean isSnow, double windSpeed, double prevBuildUpIndex,
			HerbStage herbState) {
		this.dryBulbTemp = dryBulbTemp;
		this.wetBulbTemp = wetBulbTemp;
		this.isSnow = isSnow;
		this.windSpeed = windSpeed;
		this.prevBuildUpIndex = prevBuildUpIndex;
		this.herbState = herbState;
	}
	
	public double getDryBulbTemp() {
		return dryBulbTemp;
	}
	public double getWetBulbTemp() {
		return wetBulbTemp;
	}
	public boolean isSnow() {
		return isSnow;
	}
	public double getWindSpeed() {
		return windSpeed;
	}
	public double getPrevBuildUpIndex() {
		return prevBuildUpIndex;
	}
	public HerbStage getHerbState() {
		return herbState;
	}
	
	//set initial input value
	public void getUserInput(ForestConditions forestconditions){
		Scanner userInput = new Scanner(System.in);
		System.out.println("Enter in Dry Bulb Temperature");
		this.dryBulbTemp = userInput.nextDouble();
		System.out.println("Enter in Wet Bulb Temperature");
		this.wetBulbTemp = userInput.nextDouble();
		System.out.println("Is it Snowing, yes is true, no is false");
		this.isSnow = userInput.hasNextBoolean();
		System.out.println("Enter the current wind speed in miles per hour");
		this.windSpeed = userInput.nextDouble();
		System.out.println("Enter in the last value of the buildup index");
		this.prevBuildUpIndex = userInput.nextDouble();
		System.out.println("Enter in the current Herb State as follows 1 = Cured, 2 = Transition, 3= Green");
		this.herbState = getHerbState();
		

}
}

