package rayTracing.primitives;

import math.Ray;
import math.Vector3D;

public class BoundingBoxTest {

	public static void main(String[] args) {
		
		// Makes the bounding box
		BoundingBox box = new BoundingBox();
		box.add(new Sphere(0.5, new Vector3D(0.5), null));
		box.add(new Sphere(0.5, new Vector3D(1.5), null));
//		box.add(new Triangle(
//			new Vector3D(0),
//			new Vector3D(0, 0, 2),
//			new Vector3D(2),
//			null
//		));
		
		// Tests rays
		Ray ray = new Ray(new Vector3D(1, 1, -1), new Vector3D(0, 0, 1));
		System.out.print(box.intersects(ray, 0) ? "P" : "F");
		ray = new Ray(new Vector3D(1, 1, 3), new Vector3D(0, 0, -1));
		System.out.print(box.intersects(ray, 0) ? "P" : "F");
		ray = new Ray(new Vector3D(1, -1, 1), new Vector3D(0, 1, 0));
		System.out.print(box.intersects(ray, 0) ? "P" : "F");
		ray = new Ray(new Vector3D(1, 3, 1), new Vector3D(0, -1, 0));
		System.out.print(box.intersects(ray, 0) ? "P" : "F");
		ray = new Ray(new Vector3D(-1, 1, 1), new Vector3D(1, 0, 0));
		System.out.print(box.intersects(ray, 0) ? "P" : "F");
		ray = new Ray(new Vector3D(3, 1, 1), new Vector3D(-1, 0, 0));
		System.out.print(box.intersects(ray, 0) ? "P" : "F");
		
		ray = new Ray(new Vector3D(1, 1, -1), new Vector3D(0, 0, 1));
		System.out.print(!box.intersects(ray, 5) ? "P" : "F");
		ray = new Ray(new Vector3D(1, 1, 3), new Vector3D(0, 0, -1));
		System.out.print(!box.intersects(ray, 5) ? "P" : "F");
		ray = new Ray(new Vector3D(1, -1, 1), new Vector3D(0, 1, 0));
		System.out.print(!box.intersects(ray, 5) ? "P" : "F");
		ray = new Ray(new Vector3D(1, 3, 1), new Vector3D(0, -1, 0));
		System.out.print(!box.intersects(ray, 5) ? "P" : "F");
		ray = new Ray(new Vector3D(-1, 1, 1), new Vector3D(1, 0, 0));
		System.out.print(!box.intersects(ray, 5) ? "P" : "F");
		ray = new Ray(new Vector3D(3, 1, 1), new Vector3D(-1, 0, 0));
		System.out.print(!box.intersects(ray, 5) ? "P" : "F");
		
		ray = new Ray(new Vector3D(3, 1, -1), new Vector3D(0, 0, 1));
		System.out.print(!box.intersects(ray, 0) ? "P" : "F");
		ray = new Ray(new Vector3D(-1, 1, 3), new Vector3D(0, 0, -1));
		System.out.print(!box.intersects(ray, 0) ? "P" : "F");
		ray = new Ray(new Vector3D(1, -1, 3), new Vector3D(0, 1, 0));
		System.out.print(!box.intersects(ray, 0) ? "P" : "F");
		ray = new Ray(new Vector3D(1, 3, -1), new Vector3D(0, -1, 0));
		System.out.print(!box.intersects(ray, 0) ? "P" : "F");
		ray = new Ray(new Vector3D(-1, 3, 1), new Vector3D(1, 0, 0));
		System.out.print(!box.intersects(ray, 0) ? "P" : "F");
		ray = new Ray(new Vector3D(3, -1, 1), new Vector3D(-1, 0, 0));
		System.out.print(!box.intersects(ray, 0) ? "P" : "F");

		ray = new Ray(new Vector3D(-1), new Vector3D(1));
		System.out.print(box.intersects(ray, 0) ? "P" : "F");
		ray = new Ray(new Vector3D(-1), new Vector3D(1));
		System.out.print(!box.intersects(ray, 10) ? "P" : "F");
		ray = new Ray(new Vector3D(2, -1, -1), new Vector3D(1));
		System.out.print(!box.intersects(ray, 0) ? "P" : "F");
	}

}
