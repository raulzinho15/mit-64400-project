package rayTracing.primitives;

import java.util.ArrayList;

import math.Ray;
import math.Vector3D;

public class BoundingBox {

	/** The starting coordinates. Has start <= end, element-wise. */
	private Vector3D start = Vector3D.ZERO;

	/** The end coordinates. Has start <= end, element-wise. */
	private Vector3D end = Vector3D.ZERO;
	
	/** The primitives contained in this bounding box. */
	private final ArrayList<Primitive> primitives = new ArrayList<Primitive>();
	
	/** The bounding boxed recursively contained in this bounding box. */
	private final ArrayList<BoundingBox> boxes = new ArrayList<BoundingBox>();

	/**
	 * Creates a new bounding box that can hold different
	 * primitives and other bounding boxes.
	 */
	public BoundingBox() {}
	
	/**
	 * Adds the given node to the bounding box.
	 * Primitives should be uniquely added to bounding boxes.
	 * 
	 * @param node The node to be added.
	 */
	public void add(Primitive node) {
		primitives.add(node);
		start = Vector3D.min(start, node.minBounds());
		end = Vector3D.max(end, node.maxBounds());
	}
	
	/**
	 * Adds the given bounding box to the bounding box.
	 * Bounding boxes should be uniquely added to other bounding boxes. 
	 * 
	 * @param box The bounding to be added.
	 */
	public void add(BoundingBox box) {
		boxes.add(box);
		start = Vector3D.min(start, box.start);
		end = Vector3D.max(end, box.end);
	}
	
	/**
	 * Checks whether the given ray intersects with the bounding box.
	 * 
	 * @param ray The ray to check for bounding box intersection.
	 * @param tMin The minimum t to use for intersecting.
 	 * @return Whether a valid intersection happens.
	 */
	public boolean intersects(Ray ray, double tMin) {
		
		// Computes the start t values
		final double tStartX = ray.tAtX(start.x);
		final double tStartY = ray.tAtY(start.y);
		final double tStartZ = ray.tAtZ(start.z);
		
		// Computes the end t values
		final double tEndX = ray.tAtX(end.x);
		final double tEndY = ray.tAtY(end.y);
		final double tEndZ = ray.tAtZ(end.z);
		
		// Stores the best t values for x
		double tStart, tEnd;
		if (tStartX <= tEndX) {
			tStart = tStartX;
			tEnd = tEndX;
		} else {
			tEnd = tStartX;
			tStart = tEndX;
		}
		
		// Stores the best t values for y
		if (tStartY <= tEndY) {
			tStart = tStartY > tStart ? tStartY : tStart;
			tEnd = tEndY < tEnd ? tEndY : tEnd;
		} else {
			tStart = tEndY > tStart ? tEndY : tStart;
			tEnd = tStartY < tEnd ? tStartY : tEnd;
		}
		
		// Stores the best t values for z
		if (tStartZ <= tEndZ) {
			tStart = tStartZ > tStart ? tStartZ : tStart;
			tEnd = tEndZ < tEnd ? tEndZ : tEnd;
		} else {
			tStart = tEndZ > tStart ? tEndZ : tStart;
			tEnd = tStartZ < tEnd ? tStartZ : tEnd;
		}
		
		// Checks for misses
		if (tStart > tEnd) return false;
		if (tEnd < tMin) return false;
		
		// Hit!
		return true;
	}
	
	/**
	 * Computes which nodes are being intersected by the ray, using recursive
	 * bounding box checks.
	 * 
	 * @param ray The ray to check for bounding box intersection.
	 * @param tMin The minimum t to use for intersecting.
	 * @return The primitives with which the ray intersects.
	 */
	public ArrayList<Primitive> intersectingNodes(Ray ray, double tMin) {
		
		// Will store all the intersecting primitives
		final ArrayList<Primitive> nodes = new ArrayList<Primitive>();
		
		// Checks for intersection
		if (intersects(ray, tMin)) {
			
			// Adds all nodes uniquely in this bounding box
			nodes.addAll(primitives);
			
			// Adds all intersecting nodes from sub-bounding boxes
			for (BoundingBox box : boxes)
				nodes.addAll(box.intersectingNodes(ray, tMin));
		}
		
		return nodes;
	}
	
	/**
	 * Resets the bounding box.
	 */
	public void reset() {
		for (BoundingBox box : boxes)
			box.reset();
		boxes.clear();
		primitives.clear();
		start = end = Vector3D.ZERO;
	}
}
