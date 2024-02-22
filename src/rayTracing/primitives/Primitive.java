package rayTracing.primitives;

import math.Ray;
import math.Vector3D;
import rayTracing.Record;

public abstract interface Primitive {
	
	/**
	 * Checks whether this primitive intersects with the ray at a new closest point.
	 * 
	 * @param ray The ray to check for an intersection with.
	 * @param tMin The minimum t for an intersection.
	 * @param record The record intersection so far, to be modified in the function.
	 * 
	 * @return Whether a new closest intersection happens in the correct range.
	 */
	public abstract boolean intersects(Ray ray, double tMin, Record record);
	
	/**
	 * @return The point with the minimum bounds for the primitive's volume.
	 */
	public abstract Vector3D minBounds();
	
	/**
	 * @return The point with the maximum bounds for the primitive's volume.
	 */
	public abstract Vector3D maxBounds();
}
