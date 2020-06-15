package core.maths.vector;

/**
 * <h1>Vector2f Class</h1>
 * <p>
 * This class represents a 2D Vector to be used for calculations throughout the
 * engine. Contents of this class are based off information gathered from the
 * following three sources...
 * 
 * Eric Lengyel. Mathematics for 3D Game Programming and Computer Graphics,
 * Second Edition. Hingham, MA: Charles River Media, 2003. Jason Gregory. Game
 * Engine Architecture, Second Edition. Boca Raton, FL: Taylor & Francis Group,
 * 2015. Tomas Akenine-Moller, Eric Haines. Real-Time Rendering, Fourth Edition.
 * Boca Raton, FL: Taylor & Francis Group, 2018.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-15
 */
public class Vector2f {

	private float x, y;

	/**
	 * creates a vector with all components set to zero
	 */
	public Vector2f() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * creates a vector with components set to those passed in
	 * 
	 * @param x
	 *            The x component of the vector
	 * @param y
	 *            The y component of the vector
	 */
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * duplicates the vector passed in
	 * 
	 * @param vec
	 *            The vector to create a duplicate of
	 */
	public Vector2f(Vector2f vec) {
		this.x = vec.getX();
		this.y = vec.getY();
	}

	/**
	 * calculates the length of the vector
	 * 
	 * @return The length of the vector
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * normalizes this vector and returns it so it can be directly used without
	 * subsequent calls
	 * 
	 * @return The normalized vector as a result
	 */
	public Vector2f normalize() {
		float length = length();

		x /= length;
		y /= length;

		return this;
	}

	/**
	 * calculates the dot product of this vector and the one passed in
	 * 
	 * @param vec
	 *            The second vector to be used in the dot product calculation
	 * @return The second vector to be used in the dot product calculation
	 */
	public float dot(Vector2f vec) {
		return (x * vec.getX() + y * vec.getY());
	}

	/**
	 * returns a new vector that is the result of the component addition between
	 * this vector and the vector passed in
	 * 
	 * @param vec
	 *            The second vector to be used in the component addition
	 * @return A new vector that is the result of the component addition
	 */
	public Vector2f add(Vector2f vec) {
		return new Vector2f(x + vec.getX(), y + vec.getY());
	}

	/**
	 * returns a new vector that is the result of the scalar addition between this
	 * vector and the float passed in
	 * 
	 * @param r
	 *            The float to be used in the scalar addition
	 * @return A new vector that is the result of the scalar addition
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

	/**
	 * returns a new vector that is the result of the component subtraction between
	 * this vector and the vector passed in
	 * 
	 * @param vec
	 *            The second vector to be used in the component subtraction
	 * @return A new vector that is the result of the component subtraction
	 */
	public Vector2f sub(Vector2f vec) {
		return new Vector2f(x - vec.getX(), y - vec.getY());
	}

	/**
	 * returns a new vector that is the result of the scalar subtraction between
	 * this vector and the float passed in
	 * 
	 * @param r
	 *            The float to be used in the scalar subtraction
	 * @return A new vector that is the result of the scalar subtraction
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * returns a new vector that is the result of the component multiplication
	 * between this vector and the vector passed in
	 * 
	 * @param vec
	 *            The second vector to be used in the component multiplication
	 * @return A new vector that is the result of the component multiplication
	 */
	public Vector2f mul(Vector2f vec) {
		return new Vector2f(x * vec.getX(), y * vec.getY());
	}

	/**
	 * returns a new vector that is the result of the scalar multiplication between
	 * this vector and the float passed in
	 * 
	 * @param r
	 *            The float to be used in the scalar multiplication
	 * @return A new vector that is the result of the scalar multiplication
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * returns a new vector that is the result of the component division between
	 * this vector and the vector passed in
	 * 
	 * @param vec
	 *            The second vector to be used in the component division
	 * @return A new vector that is the result of the component division
	 */
	public Vector2f div(Vector2f vec) {
		return new Vector2f(x / vec.getX(), y / vec.getY());
	}

	/**
	 * returns a new vector that is the result of the scalar division between this
	 * vector and the float passed in
	 * 
	 * @param r
	 *            The float to be used in the scalar division
	 * @return A new vector that is the result of the scalar division
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * takes the absolute value of this vector
	 * 
	 * @return A new vector who's components are the absolute values of this vector
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * checks if this vectors components are equals to those of the vector passed in
	 * 
	 * @param vec
	 *            The vector to compare this vectors components to
	 * @return True if all components are equal, false otherwise
	 */
	public boolean equals(Vector2f vec) {
		if (x == vec.getX() && y == vec.getY())
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "[" + this.x + "," + this.y + "]";
	}

	/**
	 * sets this vector equal by components to the vector passed in
	 * 
	 * @param vec
	 *            The vector who's components are to be copied
	 */
	public void set(Vector2f vec) {
		this.x = vec.getX();
		this.y = vec.getY();
	}

	/**
	 * sets this vectors components equal to the passed in floats
	 * 
	 * @param x
	 *            The x value to be copied
	 * @param y
	 *            The y value to be copied
	 */
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
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
}
