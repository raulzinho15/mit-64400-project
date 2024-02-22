package rayTracing.lights;

import math.Vector3D;

public class DirectionalLight implements LightSource {
	
	/** The direction of the light. */
	public final Vector3D direction;
	
	/** The color of the light in [0,255]. */
	public final Vector3D color;
	
	/**
	 * Creates the given directional light.
	 * 
	 * @param direction The direction of the directional light.
	 * @param color The color of the directional light [0,255].
	 */
	public DirectionalLight(Vector3D direction, Vector3D color) {
		this.direction = direction.normalize();
		this.color = color;
	}
	
	public Vector3D intensity(Vector3D point) {
		return color;
	}

	public LightType type() {
		return LightType.DIRECTIONAL;
	}

	public double distanceFrom(Vector3D point) {
		return Double.POSITIVE_INFINITY;
	}

	public Vector3D directionFrom(Vector3D point) {
		return direction.flip();
	}
}
