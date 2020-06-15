package core.maths.vector;

import core.maths.Quaternion;

/**
 * *
 * <h1>Vector3f Class</h1>
 * <p>
 * This class represents a 3D Vector to be used for calculations throughout the
 * engine. Contents of this class are based off information gathered from the
 * following three sources...
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
public class Vector3f {

	private float x, y, z;

	/**
	 * creates a vector with all components set to zero
	 */
	public Vector3f() {
		this.setX(0);
		this.setY(0);
		this.setZ(0);
	}

	/**
	 * creates a vector with components set to those passed in
	 * 
	 * @param x
	 *            The x component of the vector
	 * @param y
	 *            The y component of the vector
	 * @param z
	 *            The z component of the vector
	 */
	public Vector3f(float x, float y, float z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	/**
	 * duplicates the vector passed in
	 * 
	 * @param vec
	 *            The vector to create a duplicate of
	 */
	public Vector3f(Vector3f vec) {
		this.x = vec.getX();
		this.y = vec.getY();
		this.z = vec.getZ();
	}

	/**
	 * calculates the length of the vector
	 * 
	 * @return The length of the vector
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * normalizes this vector and returns it so it can be directly used without
	 * subsequent calls
	 * 
	 * @return The normalized vector as a result
	 */
	public Vector3f normalize() {
		float length = this.length();

		x /= length;
		y /= length;
		z /= length;

		return this;
	}

	/**
	 * calculates the dot product of this vector and the one passed in
	 * 
	 * @param r
	 *            The second vector to be used in the dot product calculation
	 * @return The result of the dot product calculation
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * calculates the cross product of this vector and the one passed in with the
	 * calculation cross(this, vec)
	 * 
	 * @param vec
	 *            The second vector to be used in the cross product calculation
	 * @return A new vector that is the result of the cross product calculation
	 */
	public Vector3f cross(Vector3f vec) {
		float x = this.y * vec.getZ() - this.z * vec.getY();
		float y = this.z * vec.getX() - this.x * vec.getZ();
		float z = this.x * vec.getY() - this.y * vec.getX();

		return new Vector3f(x, y, z);
	}

	/**
	 * calculates the result of this vector rotated around an arbitrary axis
	 * 
	 * @param angle
	 *            The magnitude of the angle to rotate the vector
	 * @param axis
	 *            The axis the vector should be rotated around
	 * @return This vector after the rotation has been applied
	 */
	public Vector3f rotate(float angle, Vector3f axis) {
		//makes use of quaternions to rotate around an arbitrary axis
		//code derived from Chapter 4.3.2 of Real-Time Rendering, 4th Edition
		float sinHalfAngle = (float) Math.sin(Math.toRadians(angle / 2));
		float cosHalfAngle = (float) Math.cos(Math.toRadians(angle / 2));

		float rX = axis.getX() * sinHalfAngle;
		float rY = axis.getY() * sinHalfAngle;
		float rZ = axis.getZ() * sinHalfAngle;
		float rW = cosHalfAngle;

		Quaternion rotation = new Quaternion(rX, rY, rZ, rW);
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		x = w.getX();
		y = w.getY();
		z = w.getZ();

		return this;
	}

	/**
	 * returns a new vector that is the result of the component addition between
	 * this vector and the vector passed in
	 * 
	 * @param vec
	 *            The second vector to be used in the component addition
	 * @return A new vector that is the result of the component addition
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(this.x + r.getX(), this.y + r.getY(), this.z + r.getZ());
	}

	/**
	 * returns a new vector that is the result of the scalar addition between this
	 * vector and the float passed in
	 * 
	 * @param r
	 *            The float to be used in the scalar addition
	 * @return A new vector that is the result of the scalar addition
	 */
	public Vector3f add(float r) {
		return new Vector3f(this.x + r, this.y + r, this.z + r);
	}

	/**
	 * returns a new vector that is the result of the component subtraction between
	 * this vector and the vector passed in
	 * 
	 * @param vec
	 *            The second vector to be used in the component subtraction
	 * @return A new vector that is the result of the component subtraction
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(this.x - r.getX(), this.y - r.getY(), this.z - r.getZ());
	}

	/**
	 * returns a new vector that is the result of the scalar subtraction between
	 * this vector and the float passed in
	 * 
	 * @param r
	 *            The float to be used in the scalar subtraction
	 * @return A new vector that is the result of the scalar subtraction
	 */
	public Vector3f sub(float r) {
		return new Vector3f(this.x - r, this.y - r, this.z - r);
	}

	/**
	 * returns a new vector that is the result of the component multiplication
	 * between this vector and the vector passed in
	 * 
	 * @param vec
	 *            The second vector to be used in the component multiplication
	 * @return A new vector that is the result of the component multiplication
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(this.x * r.getX(), this.y * r.getY(), this.z * r.getZ());
	}

	/**
	 * returns a new vector that is the result of the component multiplication
	 * between this vector and the floats passed in
	 * 
	 * @param x
	 *            The float to be used as the second x value in the component
	 *            multiplication
	 * @param y
	 *            The float to be used as the second y value in the component
	 *            multiplication
	 * @param z
	 *            The float to be used in the second z value in the component
	 *            multiplication
	 * @return A new vector that is the result of the component multiplication
	 */
	public Vector3f mul(float x, float y, float z) {
		return new Vector3f(this.x * x, this.y * y, this.z * z);
	}

	/**
	 * returns a new vector that is the result of the scalar multiplication between
	 * this vector and the float passed in
	 * 
	 * @param r
	 *            The float to be used in the scalar multiplication
	 * @return A new vector that is the result of the scalar multiplication
	 */
	public Vector3f mul(float r) {
		return new Vector3f(this.x * r, this.y * r, this.z * r);
	}

	/**
	 * returns a new vector that is the result of the component division between
	 * this vector and the vector passed in
	 * 
	 * @param vec
	 *            The second vector to be used in the component division
	 * @return A new vector that is the result of the component division
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(this.x / r.getX(), this.y / r.getY(), this.getZ() / r.getZ());
	}

	/**
	 * returns a new vector that is the result of the component division between
	 * this vector and the floats passed in
	 * 
	 * @param x
	 *            The float to be used as the second x value in the component
	 *            division
	 * @param y
	 *            The float to be used as the second y value in the component
	 *            division
	 * @param z
	 *            The float to be used in the second z value in the component
	 *            division
	 * @return A new vector that is the result of the component division
	 */
	public Vector3f div(float x, float y, float z) {
		return new Vector3f(this.x / x, this.y / y, this.z / z);
	}

	/**
	 * returns a new vector that is the result of the scalar division between this
	 * vector and the float passed in
	 * 
	 * @param r
	 *            The float to be used in the scalar division
	 * @return A new vector that is the result of the scalar division
	 */
	public Vector3f div(float r) {
		return new Vector3f(this.x / r, this.y / r, this.z / r);
	}

	/**
	 * takes the absolute value of this vector
	 * 
	 * @return A new vector who's components are the absolute values of this vector
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * checks if this vectors components are equals to those of the vector passed in
	 * 
	 * @param vec
	 *            The vector to compare this vectors components to
	 * @return True if all components are equal, false otherwise
	 */
	public boolean equals(Vector3f vec) {
		if (x == vec.getX() && y == vec.getY() && z == vec.getZ())
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "[" + this.x + "," + this.y + "," + this.z + "]";
	}

	/**
	 * sets this vector equal by components to the vector passed in
	 * 
	 * @param vec
	 *            The vector who's components are to be copied
	 */
	public void set(Vector3f vec) {
		this.x = vec.getX();
		this.y = vec.getY();
		this.z = vec.getZ();
	}

	/**
	 * sets this vectors components equal to the passed in floats
	 * 
	 * @param x
	 *            The x value to be copied
	 * @param y
	 *            The y value to be copied
	 * @param z
	 *            The z value to be copied
	 */
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
}
