package rayTracing.lights;

import math.Vector3D;

public abstract interface LightSource {
	
	/**
	 * Calculates the intensity of the light at the given point.
	 * 
	 * @param point The point at which intensity will be calculated.
	 * 
	 * @return The intensity of the light at the given point.
	 */
	public abstract Vector3D intensity(Vector3D point);

	/**
	 * Calculates the distance from the given point to the light.
	 * 
	 * @param point The point to which the distance will be calculated.
	 * 
	 * @return The distance from the point to the light.
	 */
	public abstract double distanceFrom(Vector3D point);

	/**
	 * Calculates the direction from the given point to the light.
	 * 
	 * @param point The point to which the direction will be calculated.
	 * 
	 * @return The (normalized) direction from the point to the light.
	 */
	public abstract Vector3D directionFrom(Vector3D point);
	
	/**
	 * @return The light type of this light.
	 */
	public abstract LightType type();
	
}
