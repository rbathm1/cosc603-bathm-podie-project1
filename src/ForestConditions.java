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
	
	private double dryBulbTemp_; // dry bulb temperature in degrees Fahrenheit
	private double wetBulbTemp_; // wet bulb temperature in degrees Fahrenheit
	private double precipitation_; // precipitation in inches
	private boolean isSnow_; // true if snow on ground, otherwise false
	private double windSpeed_; // wind speed in miles per hour
	private double prevBuildUpIndex_; // previous day's build up index
	private HerbStage herbState_; // state of vegetation
	
	public ForestConditions(double dryBulbTemp, double wetBulbTemp, double precipitation,
			boolean isSnow, double windSpeed, double prevBuildUpIndex,
			HerbStage herbState) {
		this.dryBulbTemp_ = dryBulbTemp;
		this.wetBulbTemp_ = wetBulbTemp;
		this.precipitation_ = precipitation;
		this.isSnow_ = isSnow;
		this.windSpeed_ = windSpeed;
		this.prevBuildUpIndex_ = prevBuildUpIndex;
		this.herbState_ = herbState;
	}
	
	
	
	/**
	 * Gets the dry bulb temperature
	 * 
	 * @return the dry bulb temperature in degrees Fahrenheit
	 */
	public double getDryBulbTemp() {
		return dryBulbTemp_;
	}
	
	
	
	/**
	 * Gets the web bulb temperature
	 * 
	 * @return the web bulb temperature in degrees Fahrenheit
	 */
	public double getWetBulbTemp() {
		return wetBulbTemp_;
	}
	
	
	
	/**
	 * Gets the last 24 hours precipitation
	 * 
	 * @return the last 24 hours precipitation in inches
	 */
	public double getPrecipitation(){
		return precipitation_;
	}
	
	
	
	/**
	 * Gets if there is snow on the ground
	 * 
	 * @return true if snow is on the ground, otherwise false
	 */
	public boolean isSnow() {
		return isSnow_;
	}
	
	
	
	/**
	 * Gets the wind speed
	 * 
	 * @return the wind speed in miles per hour
	 */
	public double getWindSpeed() {
		return windSpeed_;
	}
	
	
	
	/**
	 * 
	 * @return the previous value of the build up index
	 */
	public double getPrevBuildUpIndex() {
		return prevBuildUpIndex_;
	}
	
	
	
	/**
	 * 
	 * @return the stage of vegetation
	 */
	public HerbStage getHerbState() {
		return herbState_;
	}
	
	
	
	/**
	 * Prompts the user on standard out and gathers input from standard in
	 * to create a ForestConditions instance
	 * 
	 * @return a ForestConditions object built from standard input
	 */
	public static ForestConditions createFromCommandLine(){
		double dryBulbTemp, wetBulbTemp, precipitation, windSpeed, prevBuildUpIndex;
		boolean isSnow;
		HerbStage herbState;
		
		Scanner scanner = new Scanner(System.in);
		
		try{
			System.out.print("Enter dry bulb temperature (degrees Fahrenheit): ");
			dryBulbTemp = scanner.nextDouble();
			scanner.nextLine();

			System.out.print("Enter wet bulb temperature (degrees Fahrenheit): ");
			wetBulbTemp = scanner.nextDouble();
			scanner.nextLine();

			System.out.print("Enter past 24 hours precipitation (inches): ");
			precipitation = scanner.nextDouble();
			scanner.nextLine();

			System.out.print("Enter snow on ground (true/false): ");
			isSnow = scanner.nextBoolean();
			scanner.nextLine();

			System.out.print("Enter wind speed (miles per hour): ");
			windSpeed = scanner.nextDouble();
			scanner.nextLine();

			System.out.print("Enter last buildup index: ");
			prevBuildUpIndex = scanner.nextDouble();
			scanner.nextLine();

			// User may enter herb stage in any case
			System.out.print("Enter herb state (Cured, Transition, Green): ");
			herbState = HerbStage.valueOf(scanner.nextLine().toUpperCase());
		}
		finally{
			scanner.close();
		}
		
		return new ForestConditions(dryBulbTemp, wetBulbTemp, precipitation,
				isSnow, windSpeed, prevBuildUpIndex, herbState);
	}
}

