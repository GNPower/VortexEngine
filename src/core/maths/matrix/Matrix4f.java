package core.maths.matrix;

import core.maths.Quaternion;
import core.maths.vector.Vector3f;
import core.window.Window;

/**
 * <h1>Matrix4f Class</h1>
 * <p>
 * This class represents a uniform 4x4 matrix to be used for calculations
 * throughout the engine. Contents of this class are based off information gathered from
 * the following three sources...
 * 
 * Eric Lengyel. Mathematics for 3D Game Programming and Computer Graphics,
 * Second Edition. Hingham, MA: Charles River Media, 2003. Jason Gregory.
 * 
 * Game Engine Architecture, Second Edition. Boca Raton, FL: Taylor & Francis
 * Group, 2015. Tomas Akenine-Moller, Eric Haines.
 * 
 * Real-Time Rendering, Fourth Edition. Boca Raton, FL: Taylor & Francis Group,
 * 2018.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-15
 */
public class Matrix4f {

	public float[][] m;
	
	/**
	 * creates a new Matrix4f with all empty entries
	 */
	public Matrix4f() {
		m = new float[4][4];
	}
	
	/**
	 * sets all entries in this matrix to zero
	 * 
	 * @return This matrix for use in calculations
	 */
	public Matrix4f setZero() {
		m[0][0] = 0; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
		m[1][0] = 0; m[1][1] = 0; m[1][2] = 0; m[1][3] = 0;
		m[2][0] = 0; m[2][1] = 0; m[2][2] = 0; m[2][3] = 0;
		m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 0;
	
		return this;
	}
	
	/**
	 * sets this matrix equal to the identity matrix
	 * 
	 * @return This matrix for use in calculations
	 */
	public Matrix4f setIdentity() {
		m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
		m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = 0;
		m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = 0;
		m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;
	
		return this;
	}
	
	/**
	 * sets this matrix to the appropriate transformation matrix to simulate an affine
	 * translation from one point to another
	 * 
	 * @param translation
	 * 				The vector that represents the translation of the current point
	 * 
	 * @return
	 * 				A new matrix that represents an affine translation
	 */
	public Matrix4f setTranslation(Vector3f translation) {
		m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = translation.getX();
		m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = translation.getY();
		m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = translation.getZ();
		m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;
	
		return this;
	}
	
	/**
	 * sets this matrix to the appropriate transformation matrix to simulate an affine
	 * rotation in 3D space
	 * 
	 * @param rotation
	 * 				A Vector3f who's x,y,z components represent rotation angles around
	 * 				the x,y,z axis accordingly
	 * 
	 * @return
	 * 				A new matrix that represents an affine rotation
	 */
	public Matrix4f setRotation(Vector3f rotation) {
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();
		
		float x = (float)Math.toRadians(rotation.getX());
		float y = (float)Math.toRadians(rotation.getY());
		float z = (float)Math.toRadians(rotation.getZ());
		
		rz.m[0][0] = (float)Math.cos(z); rz.m[0][1] = -(float)Math.sin(z); 	 rz.m[0][2] = 0; 				   rz.m[0][3] = 0;
		rz.m[1][0] = (float)Math.sin(z); rz.m[1][1] = (float)Math.cos(z);  	 rz.m[1][2] = 0; 				   rz.m[1][3] = 0;
		rz.m[2][0] = 0; 				 rz.m[2][1] = 0; 				   	 rz.m[2][2] = 1; 				   rz.m[2][3] = 0;
		rz.m[3][0] = 0; 				 rz.m[3][1] = 0; 				   	 rz.m[3][2] = 0; 				   rz.m[3][3] = 1;
		
		rx.m[0][0] = 1; 				 rx.m[0][1] = 0;					 rx.m[0][2] = 0; 				   rx.m[0][3] = 0;
		rx.m[1][0] = 0; 				 rx.m[1][1] = (float)Math.cos(x); 	 rx.m[1][2] = -(float)Math.sin(x); rx.m[1][3] = 0;
		rx.m[2][0] = 0; 				 rx.m[2][1] = (float)Math.sin(x); 	 rx.m[2][2] = (float)Math.cos(x);  rx.m[2][3] = 0;
		rx.m[3][0] = 0; 				 rx.m[3][1] = 0; 				 	 rx.m[3][2] = 0;				   rx.m[3][3] = 1;
		
		ry.m[0][0] = (float)Math.cos(y); ry.m[0][1] = 0; 					 ry.m[0][2] = (float)Math.sin(y);  ry.m[0][3] = 0;
		ry.m[1][0] = 0; 				 ry.m[1][1] = 1; 				 	 ry.m[1][2] = 0; 				   ry.m[1][3] = 0;
		ry.m[2][0] = -(float)Math.sin(y);ry.m[2][1] = 0;					 ry.m[2][2] = (float)Math.cos(y);  ry.m[2][3] = 0;
		ry.m[3][0] = 0; 				 ry.m[3][1] = 0; 					 ry.m[3][2] = 0; 				   ry.m[3][3] = 1;
	
		m =  rz.mul(ry.mul(rx)).getM();
		
		return this;
	}
	
	/**
	 * sets this matrix to the appropriate transformation matrix to simulate scaling
	 * 
	 * @param scaling
	 * 				A Vector3f who's x,y,z components represent scaling multiples
	 * 				for the x,y,z direction accordingly
	 * 
	 * @return
	 * 				A Matrix that represents a scaling
	 */
	public Matrix4f setScaling(Vector3f scaling) {
		m[0][0] = scaling.getX(); 	m[0][1] = 0; 				m[0][2] = 0; 				m[0][3] = 0;
		m[1][0] = 0; 			 	m[1][1] = scaling.getY();	m[1][2] = 0; 				m[1][3] = 0;
		m[2][0] = 0; 				m[2][1] = 0; 				m[2][2] = scaling.getZ(); 	m[2][3] = 0;
		m[3][0] = 0; 				m[3][1] = 0; 				m[3][2] = 0; 				m[3][3] = 1;
	
		return this;
	}
	
	/**
	 * sets this matrix equal to the appropriate transformation matrix to simulate
	 * 2D orthographic projection with a given width and height
	 * 
	 * @param width 
	 * 				The width to be used on the orthographic projection
	 * 
	 * @param height 
	 * 				The height to be used in the orthographic projection
	 * 
	 * @return
	 * 				A new Matrix that represents the required transformations to acheive
	 * 				a 2D orthographic projection
	 */
	public Matrix4f setOrthographic2D(int width, int height) {
		m[0][0] = 2f/(float)width; 	m[0][1] = 0; 			    m[0][2] = 0; m[0][3] = -1;
		m[1][0] = 0;		 		m[1][1] = 2f/(float)height; m[1][2] = 0; m[1][3] = -1;
		m[2][0] = 0; 				m[2][1] = 0; 				m[2][2] = 1; m[2][3] =  0;
		m[3][0] = 0; 				m[3][1] = 0; 				m[3][2] = 0; m[3][3] =  1;
		
		return this;
	}
	
	/**
	 * sets this matrix equal to the appropriate transformation matrix to simulate
	 * 2D orthographic projection with the engine windows width and height
	 * 
	 * @return
	 * 				A new Matrix that represents the required transformations to achieve
	 * 				a 2D orthographic projection
	 */
	public Matrix4f setOrthographic2D() {
		m[0][0] = 2f/(float)Window.getInstance().getWidth();	m[0][1] = 0; 								 			m[0][2] = 0;	 m[0][3] = -1;
		m[1][0] = 0;		 									m[1][1] = 2f/(float)Window.getInstance().getHeight();	m[1][2] = 0;	 m[1][3] = -1;
		m[2][0] = 0; 											m[2][1] = 0; 								 			m[2][2] = 1;	 m[2][3] =  0;
		m[3][0] = 0; 											m[3][1] = 0; 								 			m[3][2] = 0;	 m[3][3] =  1;
		
		return this;
	}
	
	/**
	 * sets this matrix equal to the appropriate transformation matrix to simulate
	 * 3D orthographic projection with the specified parameters, which represent the
	 * relative bounding coordinates of a cuboid. 
	 * 
	 * @param l the left bounding coordinate of the cuboid
	 * @param r the right bounding coordinate of the cuboid
	 * @param b the bottom boiunting coordinate of the cuboid
	 * @param t the top bounding coordinate of the cuboid
	 * @param n the near bounding coordinate of the cuboid
	 * @param f the far bounding coordinate of the cuboid
	 * @return A new Matrix that represents the required transformations to achieve a 3D orthographic projection
	 */
	public Matrix4f setOrthographicProjection(float l, float r, float b, float t, float n, float f){		
		m[0][0] = 2.0f/(r-l); 	m[0][1] = 0; 			m[0][2] = 0; 			m[0][3] = -(r+l)/(r-l);
		m[1][0] = 0;			m[1][1] = 2.0f/(t-b); 	m[1][2] = 0; 			m[1][3] = -(t+b)/(t-b);
		m[2][0] = 0; 			m[2][1] = 0; 			m[2][2] = 2.0f/(f-n); 	m[2][3] = -(f+n)/(f-n);
		m[3][0] = 0; 			m[3][1] = 0; 			m[3][2] = 0; 			m[3][3] = 1;
	
		return this;
	}
	
	/**
	 * sets this matrix equal to the appropriate transformation matrix to simulate
	 * perspective projection with the specified parameters, which represent the
	 * relative bounding coordinates.
	 * 
	 * @param fovY the field of view angle with respect to the forward Z axis
	 * @param width the width of the viewport being rendered to
	 * @param height the height of the viewport being rendered to
	 * @param zNear the relative bounding Z coordinate of the near plane
	 * @param zFar the relative bounding Z coordinate of the far plane
	 * @return A new Matrix that represents the required transformations to achieve a perspective projection
	 */
	public Matrix4f setPerspectiveProjection(float fovY, float width, float height, float zNear, float zFar) {
		float tanFOV = (float) Math.tan(Math.toRadians(fovY/2));
		float aspectRatio = width/height;
		
		m[0][0] = 1/(tanFOV*aspectRatio); m[0][1] = 0; 		 	   m[0][2] = 0; 				m[0][3] = 0;
		m[1][0] = 0; 					  m[1][1] = 1/tanFOV; 	   m[1][2] = 0; 			 	m[1][3] = 0;
		m[2][0] = 0; 				 	  m[2][1] = 0; 		 	   m[2][2] = zFar/(zFar-zNear);	m[2][3] = zFar*zNear /(zFar-zNear);
		m[3][0] = 0; 				 	  m[3][1] = 0; 		 	   m[3][2] = 1; 				m[3][3] = 1;
	
		return this;
	}
	
	/**
	 * sets this matrix equal to the appropriate transformation matrix to simulate
	 * a view transformation with the specified parameters, which represent the
	 * directions of the local axis of the view transformation
	 * 
	 * @param forward the forward direction of the view transformation
	 * @param up the up direction of the view transformation
	 * @return A new matrix that represents a view transformation
	 */
	public Matrix4f setView(Vector3f forward, Vector3f up) {
		Vector3f f = forward;
		Vector3f u = up;
		Vector3f r = u.cross(f);
		
		m[0][0] = r.getX(); m[0][1] = r.getY(); m[0][2] = r.getZ(); m[0][3] = 0;
		m[1][0] = u.getX(); m[1][1] = u.getY(); m[1][2] = u.getZ(); m[1][3] = 0;
		m[2][0] = f.getX();	m[2][1] = f.getY(); m[2][2] = f.getZ(); m[2][3] = 0;
		m[3][0] = 0; 		m[3][1] = 0; 		m[3][2] = 0; 		m[3][3] = 1;
	
		return this;
	}
	
	/**
	 * calculates the result of matrix multiplication between this matrix (M) and
	 * a passed in matrix (P) in the order MP
	 * 
	 * @param mat
	 * 				The second matrix to be used in the matrix multiplication
	 * 
	 * @return
	 * 				A new matrix that is the result of the matrix multiplication
	 */
	public Matrix4f mul(Matrix4f mat) {
		Matrix4f res = new Matrix4f();
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				res.set(i, j, 	m[i][0] * mat.get(0, j) +
								m[i][1] * mat.get(1, j) +
								m[i][2] * mat.get(2, j) +
								m[i][3] * mat.get(3, j));
			}
		}
		
		return res;
	}
	
	/**
	 * calculates the result of multiplication between this matrix and
	 * a passed in quaternion
	 * 
	 * @param q
	 * 				The quaternion to be used in the multiplication
	 * 
	 * @return
	 * 				A new quaternion that is the result of the multiplication
	 */
	public Quaternion mul(Quaternion q) {
		Quaternion res = new Quaternion(0,0,0,0);
		
		res.setX(m[0][0] * q.getX() + m[0][1] * q.getY() + m[0][2] * q.getZ() + m[0][3] * q.getW());
		res.setY(m[1][0] * q.getX() + m[1][1] * q.getY() + m[1][2] * q.getZ() + m[1][3] * q.getW());
		res.setZ(m[2][0] * q.getX() + m[2][1] * q.getY() + m[2][2] * q.getZ() + m[2][3] * q.getW());
		res.setW(m[3][0] * q.getX() + m[3][1] * q.getY() + m[3][2] * q.getZ() + m[3][3] * q.getW());
		
		return res;
	}
	
	/**
	 * calculates the transpose of this matrix
	 * 
	 * @return
	 * 				A new matrix that is the result of the transpose of this matrix
	 */
	public Matrix4f transpose() {
		Matrix4f result = new Matrix4f();
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				result.set(i, j, get(j,i));
			}
		}
		return result;
	}
	
	/**
	 * calculates the inverse of this matrix, assuming it is invertible.
	 * If the matrix is not invertible an error will be printed to the terminal
	 * and the method will return the matrix before the inversion attempt.
	 * 
	 * @return The inverted matrix if the inversion was possible, otherwise the original matrix
	 */
	public Matrix4f invert() {
		float s0 = get(0, 0) * get(1, 1) - get(1, 0) * get(0, 1);
		float s1 = get(0, 0) * get(1, 2) - get(1, 0) * get(0, 2);
		float s2 = get(0, 0) * get(1, 3) - get(1, 0) * get(0, 3);
		float s3 = get(0, 1) * get(1, 2) - get(1, 1) * get(0, 2);
		float s4 = get(0, 1) * get(1, 3) - get(1, 1) * get(0, 3);
		float s5 = get(0, 2) * get(1, 3) - get(1, 2) * get(0, 3);

		float c5 = get(2, 2) * get(3, 3) - get(3, 2) * get(2, 3);
		float c4 = get(2, 1) * get(3, 3) - get(3, 1) * get(2, 3);
		float c3 = get(2, 1) * get(3, 2) - get(3, 1) * get(2, 2);
		float c2 = get(2, 0) * get(3, 3) - get(3, 0) * get(2, 3);
		float c1 = get(2, 0) * get(3, 2) - get(3, 0) * get(2, 2);
		float c0 = get(2, 0) * get(3, 1) - get(3, 0) * get(2, 1);
		
		
		float div = (s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0);
		if (div == 0) {
			System.err.println("not invertible");
			return this;
		}
		
	    float invdet = 1.0f / div;
	    
	    Matrix4f invM = new Matrix4f();
	    
	    invM.set(0, 0, (get(1, 1) * c5 - get(1, 2) * c4 + get(1, 3) * c3) * invdet);
	    invM.set(0, 1, (-get(0, 1) * c5 + get(0, 2) * c4 - get(0, 3) * c3) * invdet);
	    invM.set(0, 2, (get(3, 1) * s5 - get(3, 2) * s4 + get(3, 3) * s3) * invdet);
	    invM.set(0, 3, (-get(2, 1) * s5 + get(2, 2) * s4 - get(2, 3) * s3) * invdet);

	    invM.set(1, 0, (-get(1, 0) * c5 + get(1, 2) * c2 - get(1, 3) * c1) * invdet);
	    invM.set(1, 1, (get(0, 0) * c5 - get(0, 2) * c2 + get(0, 3) * c1) * invdet);
	    invM.set(1, 2, (-get(3, 0) * s5 + get(3, 2) * s2 - get(3, 3) * s1) * invdet);
	    invM.set(1, 3, (get(2, 0) * s5 - get(2, 2) * s2 + get(2, 3) * s1) * invdet);

	    invM.set(2, 0, (get(1, 0) * c4 - get(1, 1) * c2 + get(1, 3) * c0) * invdet);
	    invM.set(2, 1, (-get(0, 0) * c4 + get(0, 1) * c2 - get(0, 3) * c0) * invdet);
	    invM.set(2, 2, (get(3, 0) * s4 - get(3, 1) * s2 + get(3, 3) * s0) * invdet);
	    invM.set(2, 3, (-get(2, 0) * s4 + get(2, 1) * s2 - get(2, 3) * s0) * invdet);

	    invM.set(3, 0, (-get(1, 0) * c3 + get(1, 1) * c1 - get(1, 2) * c0) * invdet);
	    invM.set(3, 1, (get(0, 0) * c3 - get(0, 1) * c1 + get(0, 2) * c0) * invdet);
	    invM.set(3, 2, (-get(3, 0) * s3 + get(3, 1) * s1 - get(3, 2) * s0) * invdet);
	    invM.set(3, 3, (get(2, 0) * s3 - get(2, 1) * s1 + get(2, 2) * s0) * invdet);
		
		return invM;
	}
	
	/**
	 * checks if this matrices components are equals to the components of the
	 * passed in matrix
	 * 
	 * @param m
	 * 				The matrix to check if this one is equal to
	 * 
	 * @return
	 * 				True if equal, false otherwise
	 */
	public boolean equals(Matrix4f m) {
		if (this.m[0][0] == m.getM()[0][0] && this.m[0][1] == m.getM()[0][1] &&
				this.m[0][2] == m.getM()[0][2] && this.m[0][3] == m.getM()[0][3] &&
				this.m[1][0] == m.getM()[1][0] && this.m[1][1] == m.getM()[1][1] &&
				this.m[1][2] == m.getM()[1][2] && this.m[1][3] == m.getM()[1][3] &&
				this.m[2][0] == m.getM()[2][0] && this.m[2][1] == m.getM()[2][1] &&
				this.m[2][2] == m.getM()[2][2] && this.m[2][3] == m.getM()[2][3] &&
				this.m[3][0] == m.getM()[3][0] && this.m[3][1] == m.getM()[3][1] &&
				this.m[3][2] == m.getM()[3][2] && this.m[3][3] == m.getM()[3][3])
					return true;
			else
				return false;
	}
	
	/**
	 * sets the value of a specified matrix entry to s specified float.
	 * It should be noted that matrix entries for both rows and columns
	 * in this implementation start at zero and end at 3 and is in column
	 * major form, any number outside of this range will result in an error.
	 * 
	 * @param x
	 * 				The column of the matrix
	 * 
	 * @param y
	 * 				The row of the matrix
	 * 
	 * @param val
	 * 				The value to set to the corresponding column and row in the matrix
	 */
	public void set(int x, int y, float val) {
		this.m[x][y] = val;
	}
	
	/**
	 * returns the value stores in the matrix at column x, row y
	 * 
	 * @param x
	 * 				The column of the matrix the requested value is in
	 * 
	 * @param y
	 * 				The row of the matrix the requested value is in
	 * 
	 * @return
	 * 				The value stored at the corresponding row and column of the matrix
	 */
	public float get(int x, int y) {
		return this.m[x][y];
	}
	
	/**
	 * returns a 2D float array that can represent this matrix
	 * 
	 * @return
	 * 				A 2D float array filled with the matrix values
	 */
	public float[][] getM(){
		return m.clone();
	}
	
	/**
	 * sets the entries in this matrix equal to the corresponding entries of a passed
	 * in 2D float array. Note the size of the array must be exactly 4x4 or it will
	 * do nothing
	 * 
	 * @param mat
	 * 				The 2D float array who's values should be stored in this matrix
	 */
	public void setM(float[][] mat) {
		if(mat.length != 4 || mat[0].length != 4)
			return;
		this.m = mat;
	}
	
	@Override
	public String toString() {
		return	"|" + m[0][0] + " " + m[0][1] + " " + m[0][2] + " " + m[0][3] + "|\n" +
				"|" + m[1][0] + " " + m[1][1] + " " + m[1][2] + " " + m[1][3] + "|\n" +
				"|" + m[2][0] + " " + m[2][1] + " " + m[2][2] + " " + m[2][3] + "|\n" +
				"|" + m[3][0] + " " + m[3][1] + " " + m[3][2] + " " + m[3][3] + "|";
	}
}
