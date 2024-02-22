package rayTracing.primitives;

import static math.MiscFuncs.determinant;

import math.Ray;
import math.Vector3D;
import rayTracing.Material;
import rayTracing.Record;

public class WindowWall implements Primitive {
	
	private static final Material WINDOW = new Material(
		new Vector3D(0.9, 0.9, 1), // Ambient
		new Vector3D(0.9, 0.9, 1), // Diffuse
		new Vector3D(0.9, 0.9, 1), // Specular
		20
	);
	
	/** The bottom-left corner. */
	public final Vector3D cornerBL;
	
	/** The bottom-right corner. */
	public final Vector3D cornerBR;
	
	/** The top-left corner. */
	public final Vector3D cornerTL;
	
	/** The material of the wall. */
	public final Material wallMaterial;

	/** The number of windows to have in the horizontal direction. */
	public final int windowsH; 

	/** The number of windows to have in the vertical direction. */
	public final int windowsV; 
	
	/**
	 * Creates a wall with windows with the given corners.
	 * 
	 * @param cornerBL The bottom-left corner of the window wall.
	 * @param cornerBR The bottom-right corner of the window wall.
 	 * @param cornerTL The top-left corner of the window wall.
 	 * @param wallMaterial The material to be used for the wall.
 	 * @param windowsH The number of windows to have in the horizontal direction.
 	 * @param windowsV The number of windows to have in the vertical direction.
	 */
	public WindowWall(Vector3D cornerBL, Vector3D cornerBR, Vector3D cornerTL, Material wallMaterial, int windowsH, int windowsV) {
		this.cornerBL = cornerBL;
		this.cornerBR = cornerBR;
		this.cornerTL = cornerTL;
		this.wallMaterial = wallMaterial;
		this.windowsH = windowsH;
		this.windowsV = windowsV;
	}

	public boolean intersects(Ray ray, double tMin, Record record) {
		
		// Stores the linear system to be solved
		final Vector3D ACol1 = cornerBL.subtract(cornerBR);
		final Vector3D ACol2 = cornerBL.subtract(cornerTL);
		final Vector3D ACol3 = ray.direction;
		final Vector3D b = cornerBL.subtract(ray.origin);
		
		// Computes the relevant determinants
		final double detA = determinant(ACol1, ACol2, ACol3);
		if (Math.abs(detA) <= 1e-4) return false;
		final double beta = determinant(b, ACol2, ACol3)/detA;
		final double gamma = determinant(ACol1, b, ACol3)/detA;
		final double t = determinant(ACol1, ACol2, b)/detA;
		
		// Checks whether an intersection happened
		if (t < tMin) return false;
		if (beta >= 0 && beta <= 1 && gamma >= 0 && gamma <= 1) {
			
			Material material = wallMaterial;
			if (beta >= 0.05 && (beta-0.05) % (0.95/windowsH) <= 0.95/windowsH-0.05
				&& gamma >= 0.3 && (gamma-0.3) % (0.7/windowsV) <= 0.7/windowsV-0.05)
				material = WINDOW;
			
			return record.update(t, ACol2.cross(ACol1), material);
		}
		return false;
	}

	public Vector3D minBounds() {return Vector3D.min(Vector3D.min(cornerBL, cornerBR), Vector3D.min(cornerTL, cornerTL.add(cornerBR).subtract(cornerBL)));}
	public Vector3D maxBounds() {return Vector3D.max(Vector3D.max(cornerBL, cornerBR), Vector3D.max(cornerTL, cornerTL.add(cornerBR).subtract(cornerBL)));}
}
