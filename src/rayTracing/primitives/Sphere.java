package rayTracing.primitives;

import math.Ray;
import math.Vector3D;
import rayTracing.Material;
import rayTracing.Record;

public class Sphere implements Primitive {
	
	/** The radius of the sphere. */
	public final double radius;
	
	/** The center point of the sphere. */
	public final Vector3D center;
	
	/** The material of the sphere. */
	public final Material material;

	/**
	 * Creates a sphere with the given properties.
	 * 
	 * @param radius The radius of the sphere.
	 * @param center The center point of the sphere.
	 * @param material The material of the sphere.
	 */
	public Sphere(double radius, Vector3D center, Material material) {
		this.radius = radius;
		this.center = center;
		this.material = material;
	}

	public boolean intersects(Ray ray, double tMin, Record record) {
		
		// Transforms according to the sphere's position
		final Vector3D transOrigin = ray.origin.subtract(center);
		
		// Computes the values for the quadratic equation
		final double a = 1;
		final double b = 2 * ray.direction.dot(transOrigin);
		final double c = transOrigin.magnitude2() - radius*radius;
		final double d2 = b*b - 4*a*c; 
		
		// Checks for a lack of intersection
		if (d2 < 0)
			return false;
		
		// Stores closest intersection
		final double t = (-b-Math.sqrt(d2))/2;
		if (t < tMin) return false;
		return record.update(t, ray.at(t).subtract(center), material);
	}

	public Vector3D minBounds() {return center.subtract(new Vector3D(radius));}
	public Vector3D maxBounds() {return center.add(new Vector3D(radius));}
}
