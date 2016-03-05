/**
 * The FuelMoisture class calculates and represents
 * the computed fuel and lag moisture indexes
 * 
 */
public class FuelMoisture {
	private double fuelMoisture_ = 99; // the fuel moisture
	private double lagFuelMoisture_ = 99; // the 10-day (lag) fuel moisture

	/**
	 * Gets the computed fuel moisture
	 * 
	 * @return the computed fuel moisture
	 */
	public double getFuelMoisture_() {
		return fuelMoisture_;
	}

	/**
	 * Sets a computed fuel moisture
	 * 
	 * @param fuelMoisture_ the new fuel moisture
	 */
	public void setFuelMoisture_(double fuelMoisture_) {
		this.fuelMoisture_ = fuelMoisture_;
	}

	/**
	 * Gets the lag fuel moisture
	 * 
	 * @return the computed lag moisture
	 */
	public double getLagFuelMoisture_() {
		return lagFuelMoisture_;
	}

	/**
	 * Sets the lag fuel moisture
	 * 
	 * @param lagFuelMoisture_ the new lag fuel moisture
	 */
	public void setLagFuelMoisture_(double lagFuelMoisture_) {
		this.lagFuelMoisture_ = lagFuelMoisture_;
	}

	/**
	 * Computes the timber spread index
	 * @param forestConditions  current forest conditions
	 * @return  the computed timber spread index
	 */
	public double computeTimberIndex(ForestConditions forestConditions) {
		double timberIndex;
		if (lagFuelMoisture_ >= 30) {
			timberIndex = 1;
		} else if (forestConditions.getWindSpeed() < 14) {
			timberIndex = 0.1312 * (forestConditions.getWindSpeed() + 6)
					* Math.pow(33 - lagFuelMoisture_, 1.65) - 3;
		} else {
			timberIndex = 0.00918 * (forestConditions.getWindSpeed() + 14)
					* Math.pow(33 - lagFuelMoisture_, 1.65) - 3;
		}
		return Math.max(Math.min(timberIndex, 99), 1);
	}

	/**
	 * Computes the grass spread index
	 * @param forestConditions  current forest conditions
	 * @return  the computed grass spread index
	 */
	public double computeGrassIndex(ForestConditions forestConditions) {
		double grassIndex;
		if (lagFuelMoisture_ >= 30 && fuelMoisture_ >= 30) {
			grassIndex = 1;
		} else if (forestConditions.getWindSpeed() < 14) {
			grassIndex = 0.1312 * (forestConditions.getWindSpeed() + 6)
					* Math.pow(33 - fuelMoisture_, 1.65) - 3;
		} else {
			grassIndex = 0.00918 * (forestConditions.getWindSpeed() + 14)
					* Math.pow(33 - fuelMoisture_, 1.65) - 3;
		}
		return Math.max(Math.min(grassIndex, 99), 1);
	}
}