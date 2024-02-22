package math;

public class MiscFuncs {

	/**
	 * Computes the determinant of the matrix with the given columns.
	 * 
	 * @param col1 The matrix's first column.
	 * @param col2 The matrix's second column.
	 * @param col3 The matrix's third column.
	 * 
	 * @return The matrix's determinant.
	 */
	public static double determinant(Vector3D col1, Vector3D col2, Vector3D col3) {
		return col1.x * (col2.y*col3.z - col2.z*col3.y)
				- col2.x * (col1.y*col3.z - col1.z*col3.y)
				+ col3.x * (col1.y*col2.z - col1.z*col2.y);
	}
}
