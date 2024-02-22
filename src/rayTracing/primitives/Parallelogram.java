package rayTracing.primitives;

import math.Ray;
import math.Vector3D;

import static math.MiscFuncs.determinant;

import rayTracing.Material;
import rayTracing.Record;


public class Parallelogram implements Primitive {
	
	/** The parallelogram's first vertex. */
	public final Vector3D v1;
	
	/** The parallelogram's second vertex. */
	public final Vector3D v2;
	
	/** The parallelogram's third vertex. */
	public final Vector3D v3;
	
	/** The parallelogram's material. */
	public final Material material;

	/**
	 * Creates a parallelogram with the given vertices.
	 * 
 	 * @param v1 The parallelogram's first vertex.
	 * @param v2 The parallelogram's second vertex.
	 * @param v3 The parallelogram's third vertex.
	 * @param material The parallelogram's material.
	 */
	public Parallelogram(Vector3D v1, Vector3D v2, Vector3D v3, Material material) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		this.material = material;
	}

	public boolean intersects(Ray ray, double tMin, Record record) {
		
		// Stores the linear system to be solved
		final Vector3D ACol1 = v1.subtract(v2);
		final Vector3D ACol2 = v1.subtract(v3);
		final Vector3D ACol3 = ray.direction;
		final Vector3D b = v1.subtract(ray.origin);
		
		// Computes the relevant determinants
		final double detA = determinant(ACol1, ACol2, ACol3);
		if (Math.abs(detA) <= 1e-4) return false;
		final double beta = determinant(b, ACol2, ACol3)/detA;
		final double gamma = determinant(ACol1, b, ACol3)/detA;
		final double t = determinant(ACol1, ACol2, b)/detA;
		
		// Checks whether an intersection happened
		if (t < tMin) return false;
		if (beta >= 0 && beta <= 1 && gamma >= 0 && gamma <= 1) {
			return record.update(t, ACol1.cross(ACol2), material);
		} else
			return false;
	}

	public Vector3D minBounds() {return Vector3D.min(Vector3D.min(v1, v2), v3);}
	public Vector3D maxBounds() {return Vector3D.max(Vector3D.max(v1, v2), v3);}
}
