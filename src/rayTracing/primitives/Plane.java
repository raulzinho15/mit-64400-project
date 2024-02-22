package rayTracing.primitives;

import math.Ray;
import math.Vector3D;
import rayTracing.Material;
import rayTracing.Record;

public class Plane implements Primitive {
	
	/** The (normalized) normal of the plane. */
	public final Vector3D normal;
	
	/** The offset from the origin of the plane. */
	public final double offset;
	
	/** The material of the sphere. */
	public final Material material;

	/**
	 * Creates a plane (normal*P + offset = 0) with the given properties.
	 * 
	 * @param normal The normal of the plane.
	 * @param offset The offset from the origin of the plane.
	 * @param material The material of the sphere.
	 */
	public Plane(Vector3D normal, double offset, Material material) {
		this.normal = normal.normalize();
		this.offset = offset;
		this.material = material;
	}

	public boolean intersects(Ray ray, double tMin, Record record) {
		
		// Computes the values for intersection checking
		final double nDotRo = normal.dot(ray.origin);
		final double nDotRd = normal.dot(ray.direction);
		
		// Checks for a lack of intersection
		if (nDotRd == 0) // Check here and in sphere for > t_min
			return false;
		
		// Stores closest intersection
		final double t = -(offset + nDotRo)/nDotRd;
		if (t < tMin) return false;
		return record.update(t, normal, material);
	}

	// Improve later to account for planes parallel to any of the three Cartesian planes???
	public Vector3D minBounds() {return new Vector3D(Double.NEGATIVE_INFINITY);}
	public Vector3D maxBounds() {return new Vector3D(Double.POSITIVE_INFINITY);}
}
