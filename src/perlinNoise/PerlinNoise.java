package perlinNoise;

public class PerlinNoise {
	
	/** Stores the gradients. */
	private static final double[][] GRADS = new double[2][10000];
	static {
		// Initializes the gradients
		for (int i = 0; i < GRADS.length; i++)
			GRADS[i] = new double[] {2*Math.random()-1, 2*Math.random()-1};
	}
	
	/**
	 * @param x The x coordinate of the gradient.
	 * @param y The y coordinate of the gradient.
	 * @param maxX The maximum number of gradients along the x direction.
	 * 
	 * @return The gradient at the given position.
	 */
	private static double[] getGrad(int x, int y, int maxX) {
		return GRADS[(y*maxX + x)%GRADS.length];
	}
	
	/**
	 * Computes the noise at the given 2D point.
	 * 
	 * @param pos The point at which the interpolation will happen.
	 * @param maxX The maximum number of gradients along the x direction.
	 * 
	 * @return The noise at the given point.
	 */
	public static double noise(double[] pos, int maxX) {
		
		// Calculates the double/integer coordinates
		final double x = pos[0];
		final double y = pos[1];
		final int xInt = (int)x;
		final int yInt = (int)y;
		
		// Calculates the corner gradients
		/*  
		 * g1 | g2
		 * -------
		 * g3 | g4  
		 */
		final double[] g1 = getGrad(xInt, yInt, maxX);
		final double[] g2 = getGrad(xInt+1, yInt, maxX);
		final double[] g3 = getGrad(xInt, yInt+1, maxX);
		final double[] g4 = getGrad(xInt+1, yInt+1, maxX);
		
		// Stores the other relevant vectors
		final double[] g1ToPos = {x-(xInt), y-(yInt)};
		final double[] g2ToPos = {x-(xInt+1), y-(yInt)};
		final double[] g3ToPos = {x-(xInt), y-(yInt+1)};
		final double[] g4ToPos = {x-(xInt+1), y-(yInt+1)};
		
		// Interpolates
		final double lerp1 = lerp(dot(g1, g1ToPos), dot(g2, g2ToPos), g1ToPos[0]);
		final double lerp2 = lerp(dot(g3, g3ToPos), dot(g4, g4ToPos), g1ToPos[0]);
		return lerp(lerp1, lerp2, g1ToPos[1]);
	}
	
	/**
	 * Computes the noise at the given 1D point.
	 * 
	 * @param x The x coordinate at which noise will be computed.
	 * 
	 * @return The noise at the given point.
	 */
	public static double noise(double x) {
		
		// Calculates the boundary gradients
		final int xInt = (int)x;
		final double[] g1 = getGrad(xInt, 0, 0);
		final double[] g2 = getGrad(xInt+1, 0, 0);
		x -= xInt;
		
		return lerp(g1[0]*x, g2[0]*(1-x), x);
	}
	
	/**
	 * Interpolates the points.
	 * 
	 * @param val1 The first value to interpolate.
	 * @param val2 The second value to interpolate.
	 * @param t The value at which to interpolate in [0,1].
	 * @return The interpolated value.
	 */
	private static double lerp(double val1, double val2, double t) {
		
		// Calculates the fade function value
		final double fade = t*t*t*(10 + t*(-15 + 6*t));
		
		return (1-fade)*val1 + fade*val2;
	}
	
	/**
	 * Computes the dot product of the two 2-length arrays.
	 * 
	 * @param arr1 The first array in the dot product.
	 * @param arr2 The second array in the dot product.
	 * @return The dot product of the two arrays as though they were 2D vectors.
	 */
	private static double dot(double[] arr1, double[] arr2) {
		return arr1[0]*arr2[0] + arr1[1]*arr2[1];
	}
}
