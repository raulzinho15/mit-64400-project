package math;

public class Ray {
	
	/** The origin. */
	public final Vector3D origin;
	
	/** The (normalized) direction in which the ray points. */
	public final Vector3D direction;
	
	/** The reciprocal of the direction values, with an epsilon offset where applicable. */
	private final Vector3D oneOverDirection;

	/**
	 * Creates the specified ray.
	 * 
	 * @param origin The origin of the ray.
	 * @param direction The direction in which the ray is pointing.
	 */
	public Ray(Vector3D origin, Vector3D direction) {
		this.origin = origin;
		this.direction = direction.normalize();
		oneOverDirection = new Vector3D(
			this.direction.x == 0 ? 1e4 : 1/this.direction.x,
			this.direction.y == 0 ? 1e4 : 1/this.direction.y,
			this.direction.z == 0 ? 1e4 : 1/this.direction.z
		);
	}
	
	/**
	 * Computes the position of the ray at the given time.
	 * 
	 * @param t The time at which the ray's position will be calculated.
	 * @return The position of the ray at the given time.
	 */
	public Vector3D at(double t) {
		return origin.add(direction.scale(t));
	}
	
	/**
	 * Finds the value of t for the given x-coordinate.
	 * 
	 * @param x The x-coordinate for which to find the t value.
	 * @return The t value at the x-coordinate.
	 */
	public double tAtX(double x) {
		return (x-origin.x) * oneOverDirection.x;
	}
	
	/**
	 * Finds the value of t for the given y-coordinate.
	 * 
	 * @param y The y-coordinate for which to find the t value.
	 * @return The t value at the y-coordinate.
	 */
	public double tAtY(double y) {
		return (y-origin.y) * oneOverDirection.y;
	}
	
	/**
	 * Finds the value of t for the given z-coordinate.
	 * 
	 * @param z The z-coordinate for which to find the t value.
	 * @return The t value at the z-coordinate.
	 */
	public double tAtZ(double z) {
		return (z-origin.z) * oneOverDirection.z;
	}
}
