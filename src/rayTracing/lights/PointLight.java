package rayTracing.lights;

import math.Vector3D;

public class PointLight implements LightSource {
	
	/** The position of the light. */
	public final Vector3D position;
	
	/** The color of the light in [0,255]. */
	public final Vector3D color;
	
	public final double attenuation;
	
	/**
	 * Creates the given point light.
	 * 
	 * @param pos The position of the point light.
	 * @param color The color of the point light [0,255].
	 * @param attenuation The attenuation of the light.
	 */
	public PointLight(Vector3D pos, Vector3D color, double attenuation) {
		position = pos;
		this.color = color;
		this.attenuation = attenuation;
	}
	
	public Vector3D intensity(Vector3D point) {
		final double distance = position.subtract(point).magnitude();
		return color.scale(1/(attenuation*distance*distance));
	}

	public LightType type() {
		return LightType.POINT;
	}

	public double distanceFrom(Vector3D point) {
		return position.subtract(point).magnitude();
	}

	public Vector3D directionFrom(Vector3D point) {
		return position.subtract(point).normalize();
	}
}
