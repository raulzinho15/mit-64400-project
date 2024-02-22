package math;


public class Vector3D {
	
	/** The zero vector. */
	public static final Vector3D ZERO = new Vector3D(0, 0, 0);
	
	/** The unit vector pointing right, +i. */
	public static final Vector3D RIGHT = new Vector3D(1, 0, 0);
	
	/** The unit vector pointing left, -i. */
	public static final Vector3D LEFT = new Vector3D(-1, 0, 0);
	
	/** The unit vector pointing down, +j. */
	public static final Vector3D DOWN = new Vector3D(0, 1, 0);
	
	/** The unit vector pointing up, -j. */
	public static final Vector3D UP = new Vector3D(0, -1, 0);
	
	/** The unit vector pointing to the front, +k. */
	public static final Vector3D FRONT = new Vector3D(0, 0, 1);
	
	/** The unit vector pointing to the back, -k. */
	public static final Vector3D BACK = new Vector3D(0, 0, -1);

	/** The x coordinate. */
	public final double x;

	/** The y coordinate. */
	public final double y;

	/** The z coordinate. */
	public final double z;
	
	/**
	 * Creates an immutable 3D vector with the specified coordinates.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param z The z coordinate.
	 */
	public Vector3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Creates an immutable 3D vector with the specified repeated coordinates.
	 * @param x The coordinate to put in each vector's entry.
	 */
	public Vector3D(double x) {
		this.x = y = z = x;
	}
	
	/**
	 * @return Whether this vector is the zero vector.
	 */
	public boolean isZero() {
		return x == 0 && y == 0 && z == 0;
	}
	
	/**
	 * Scales this vector by the given amount.
	 * 
	 * @param scalar The amount by which to scale.
	 * @return The scaled vector.
	 */
	public Vector3D scale(double scalar) {
		return new Vector3D(scalar*x, scalar*y, scalar*z);
	}
	
	/**
	 * @return The vector flipped toward the negative direction.
	 */
	public Vector3D flip() {
		return scale(-1);
	}
	
	/**
	 * @return The normalized version of the vector.
	 */
	public Vector3D normalize() {
		return scale(1/magnitude());
	}
	
	/**
	 * Adds this vector to the given vector.
	 * 
	 * @param vec The vector to sum.
	 * @return The vector sum.
	 */
	public Vector3D add(Vector3D vec) {
		return new Vector3D(x+vec.x, y+vec.y, z+vec.z);
	}
	
	/**
	 * Subtracts the given vector from this vector.
	 * 
	 * @param vec The vector to subtract.
	 * @return The difference of the vectors.
	 */
	public Vector3D subtract(Vector3D vec) {
		return add(vec.scale(-1));
	}
	
	/**
	 * Computes the element-wise multiplication on the two vectors.
	 * 
	 * @param vec The vector with which to compute element-wise multiplication.
	 * @return The element-wise product.
	 */
	public Vector3D multiply(Vector3D vec) {
		return new Vector3D(x*vec.x, y*vec.y, z*vec.z);
	}
	
	/**
	 * Computes the dot product with the given vector.
	 * 
	 * @param vec The vector with which the product will be computed.
	 * @return The dot product with the given vector.
	 */
	public double dot(Vector3D vec) {
		return x*vec.x + y*vec.y + z*vec.z;
	}
	
	/**
	 * Computes the cross product with the given vector.
	 * 
	 * @param vec The vector with which to compute the cross product.
	 * 
	 * @return The cross product of the two vectors.
	 */
	public Vector3D cross(Vector3D vec) {
		return new Vector3D(
			y*vec.z - z*vec.y,
			z*vec.x - x*vec.z,
			x*vec.y - y*vec.x
		);
	}
	
	/**
	 * @return The magnitude of the vector.
	 */
	public double magnitude() {
		return Math.sqrt(magnitude2());
	}
	
	/**
	 * @return The magnitude squared of the vector.
	 */
	public double magnitude2() {
		return dot(this);
	}
	
	/**
	 * @return A unit vector pointing in a random direction.
	 */
	public static Vector3D randomUnit() {
		return new Vector3D(2*Math.random()-1, 2*Math.random()-1, 2*Math.random()-1).normalize();
	}
	
	/**
	 * Computes the vector with the minimum coordinates from the given vectors.
	 * 
	 * @param vec1 The first vector.
	 * @param vec2 The second vector.
	 * @return A vector whose coordinates are the minimum of the two vectors' corresponding coordinates.
	 */
	public static Vector3D min(Vector3D vec1, Vector3D vec2) {
		return new Vector3D(Math.min(vec1.x, vec2.x), Math.min(vec1.y, vec2.y), Math.min(vec1.z, vec2.z));
	}
	
	/**
	 * Computes the vector with the maximum coordinates from the given vectors.
	 * 
	 * @param vec1 The first vector.
	 * @param vec2 The second vector.
	 * @return A vector whose coordinates are the maximum of the two vectors' corresponding coordinates.
	 */
	public static Vector3D max(Vector3D vec1, Vector3D vec2) {
		return new Vector3D(Math.max(vec1.x, vec2.x), Math.max(vec1.y, vec2.y), Math.max(vec1.z, vec2.z));
	}
	
	public String toString() {
		return "Vector3D(x: " + x + ", y: " + y + ", z: " + z + ")";
	}
	
	public boolean equals(Object obj) {
		try {
			final Vector3D vec = (Vector3D) obj;
			return x == vec.x && y == vec.y && z == vec.z;
		} catch (ClassCastException e) {}
		return false;
	}
}
