package rayTracing;

import math.Vector3D;

public class Record {

	/** The closest intersection's t. */
	private double t = Double.POSITIVE_INFINITY;
	
	/** The normal from the closest intersection. */
	private Vector3D normal = Vector3D.ZERO;
	
	/** The material of the closest intersection. */
	private Material material = null;
	
	/**
	 * Creates a new record with the given starting value for t.
	 * 
	 * @param startingT The starting value for t.
	 */
	public Record(double startingT) { t = startingT; }
	
	/**
	 * Creates a new record with t set to Double.POSITIVE_INFINITY.
	 */
	public Record() {}
	
	/**
	 * Updates the record if the given t is better.
	 * 
	 * @param t The intersection's t.
	 * @param normal The normal from the intersection.
	 * @param material The material from the intersection.
	 * 
	 * @return Whether a new closest intersection happened.
	 */
	public boolean update(double t, Vector3D normal, Material material) {
		if (t < this.t) {
			this.t = t;
			this.normal = normal.normalize();
			this.material = material;
			return true;
		}
		return false;
	}
	
	/**
	 * @return The t at which the closest intersection happens.
	 */
	public double getT() {
		return t;
	}
	
	/**
	 * @return The (normalized) normal from the closest intersection.
	 */
	public Vector3D getNormal() {
		return normal;
	}
	
	/**
	 * @return The material of the closest intersection.
	 */
	public Material getMaterial() {
		return material;
	}
	
	/**
	 * @return Whether the ray missed every primitive.
	 */
	public boolean missed() {
		return material == null;
	}
}
