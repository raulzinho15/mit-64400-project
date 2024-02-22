package perlinNoise;

public class Noise3D {
	
	/** Stores the gradients. */
	private static final double[][] GRADS = new double[10000][3];
	
	static {
		// Initializes the gradients
		for (int i = 0; i < GRADS.length; i++)
			GRADS[i] = new double[] {
				2*Math.random()-1,
				2*Math.random()-1, 
				2*Math.random()-1
			};
	}
	
	private static double[] getGrad(int x, int y, int t, int maxX, int maxY) {
		return GRADS[(t*maxX*maxY + y*maxX + x)%GRADS.length];
	}
	
	public static double noise(double x, double y, double t, int maxX, int maxY) {
		
		// Calculates the integer coordinates
		final int xInt = (int)x;
		final int yInt = (int)y;
		final int tInt = (int)t;
		
		// Calculates the normalized coordinates
		final double dx = x-xInt;
		final double dy = y-yInt;
		final double dt = t-tInt;
		
		// Calculates the corner gradients
		/*  
		 *  FRONT     BACK
		 * g1 | g2   g5 | g6
		 * -------   -------
		 * g3 | g4   g7 | g8
		 */
		final double[] g1 = getGrad(xInt, yInt, tInt, maxX, maxY);
		final double[] g2 = getGrad(xInt+1, yInt, tInt, maxX, maxY);
		final double[] g3 = getGrad(xInt, yInt+1, tInt, maxX, maxY);
		final double[] g4 = getGrad(xInt+1, yInt+1, tInt, maxX, maxY);
		
		final double[] g5 = getGrad(xInt, yInt, tInt+1, maxX, maxY);
		final double[] g6 = getGrad(xInt+1, yInt, tInt+1, maxX, maxY);
		final double[] g7 = getGrad(xInt, yInt+1, tInt+1, maxX, maxY);
		final double[] g8 = getGrad(xInt+1, yInt+1, tInt+1, maxX, maxY);
	
		// Calculates vectors pointing to point
		final double[] g1ToPos = {dx, dy, dt};
		final double[] g2ToPos = {dx-1, dy, dt};
		final double[] g3ToPos = {dx, dy-1, dt};
		final double[] g4ToPos = {dx-1, dy-1, dt};
		
		final double[] g5ToPos = {dx, dy, dt-1};
		final double[] g6ToPos = {dx-1, dy, dt-1};
		final double[] g7ToPos = {dx, dy-1, dt-1};
		final double[] g8ToPos = {dx-1, dy-1, dt-1};
		
		// Interpolates across x axis
		final double lerp12 = lerp(dot(g1, g1ToPos), dot(g2, g2ToPos), dx);
		final double lerp34 = lerp(dot(g3, g3ToPos), dot(g4, g4ToPos), dx);
		final double lerp56 = lerp(dot(g5, g5ToPos), dot(g6, g6ToPos), dx);
		final double lerp78 = lerp(dot(g7, g7ToPos), dot(g8, g8ToPos), dx);
		
		// Interpolates across y axis
		final double lerp1234 = lerp(lerp12, lerp34, dy);
		final double lerp5678 = lerp(lerp56, lerp78, dy);
		
		// Interpolates across t axis
		return lerp(lerp1234, lerp5678, dt);
	}
	
	private static double dot(double[] v1, double[] v2) {
		return v1[0]*v2[0] + v1[1]*v2[1] + v1[2]*v2[2];
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
}
