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
	private double precipitation; // precipitation in inches
	private boolean isSnow; // true if snow on ground, otherwise false
	private double windSpeed; // wind speed in miles per hour
	private double prevBuildUpIndex; // previous day's build up index
	private HerbStage herbState; // state of vegetation
	
	public ForestConditions(double dryBulbTemp, double wetBulbTemp, double precipitation,
			boolean isSnow, double windSpeed, double prevBuildUpIndex,
			HerbStage herbState) {
		this.dryBulbTemp = dryBulbTemp;
		this.wetBulbTemp = wetBulbTemp;
		this.precipitation = precipitation;
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
	
	public double getPrecipitation(){
		return precipitation;
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
	public static ForestConditions createFromCommandLine(){
		double dryBulbTemp, wetBulbTemp, precipitation, windSpeed, prevBuildUpIndex;
		boolean isSnow;
		HerbStage herbState;
		
		Scanner scanner = new Scanner(System.in);
		
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
		
		System.out.print("Enter herb state (Cured, Transition, Green): ");
		herbState = HerbStage.valueOf(scanner.nextLine().toUpperCase());
		
		return new ForestConditions(dryBulbTemp, wetBulbTemp, precipitation,
				isSnow, windSpeed, prevBuildUpIndex, herbState);
	}
}

