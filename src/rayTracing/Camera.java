package rayTracing;

import math.Vector3D;

public class Camera {
	
	/** The origin of the camera. */
	public final Vector3D origin;
	
	/** The forward (normalized) direction the camera is facing. */
	public final Vector3D direction;
	
	/** The upward (normalized) direction for the camera. */
	public final Vector3D up;
	
	/** The rightward (normalized) direction for the camera. */
	public final Vector3D right;

	/**
	 * Creates the camera at the specified point.
	 *  
	 * @param origin The origin of the camera.
	 * @param direction The direction the camera is facing.
	 * @param up The upward direction for the camera.
	 */
	public Camera(Vector3D origin, Vector3D direction, Vector3D up) {
		this.origin = origin;
		this.direction = direction.normalize();
		this.up = up.normalize();
		right = this.direction.cross(this.up);
	}
}
