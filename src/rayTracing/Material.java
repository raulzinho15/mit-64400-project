package rayTracing;

import math.Vector3D;

public class Material {
	
	/** The color to be used for ambient shading. */
	public final Vector3D ambientColor;
	
	/** The color to be used for diffuse shading. */
	public final Vector3D diffuseColor;
	
	/** The color to be used for specular shading. */
	public final Vector3D specularColor;
	
	/** The shininess value for specular shading. */
	public final double shininess;

	/**
	 * Creates a new material with the given properties.
	 * 
	 * @param ambientColor The color to be used for ambient shading.
	 * @param diffuseColor The color to be used for diffuse shading.
	 * @param specularColor The color to be used for specular shading.
	 * @param shininess The shininess value for specular shading.
	 */
	public Material(Vector3D ambientColor, Vector3D diffuseColor, Vector3D specularColor, double shininess) {
		this.ambientColor = ambientColor;
		this.diffuseColor = diffuseColor;
		this.specularColor = specularColor;
		this.shininess = shininess;
	}
}
