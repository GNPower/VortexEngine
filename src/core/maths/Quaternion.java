package core.maths;

import core.maths.vector.Vector3f;

/**
 * <h1>Quaternion Class</h1>
 * <p>
 * This class represents a Quaternion to be used for calculations throughout the
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
public class Quaternion {

	private float x, y, z, w;

	/**
	 * creates a quaternion with all components set to zero
	 */
	public Quaternion() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 0;
	}

	/**
	 * creates a quaternion with components set to those passed in
	 * 
	 * @param x
	 *            The x component of the quaternion (an imaginary component)
	 * @param y
	 *            The y component of the quaternion (an imaginary component)
	 * @param z
	 *            The z component of the quaternion (an imaginary component)
	 * @param w
	 *            The w component of the quaternion (the real component)
	 */
	public Quaternion(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	/**
	 * create a quaternion from a vector and a float, setting the x,y,z components
	 * of the vector to the imaginary x,y,z components of the quaternion and the
	 * float to the real w component of the quaternion
	 * 
	 * @param vec
	 *            The vector to form the imaginary components of the quaternion
	 * @param w
	 *            The float to form the real w component of the quaternion
	 */
	public Quaternion(Vector3f vec, float w) {
		this.x = vec.getX();
		this.y = vec.getY();
		this.z = vec.getZ();
		this.w = w;
	}

	/**
	 * calculates the length of the quaternion
	 * 
	 * @return The length of the quaternion as a floating point value
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * normalizes this quaternion and returns it for use in calculations
	 * 
	 * @return This quaternion after it has been normalized
	 */
	public Quaternion normalize() {
		float length = length();

		x /= length;
		y /= length;
		z /= length;
		w /= length;

		return this;
	}
	
	/**
	 * calculates the conjugate of this quaternion
	 * @return A new quaternion that is the conjugate of this one
	 */
	public Quaternion conjugate() {
		return new Quaternion(-x, -y, -z, w);
	}
	
	/**
	 * calculates the result of multiplication between this quaternion and the one passed in
	 * @param q The second quaternion to be used in the multiplication
	 * @return A new quaternion that is the result of the quaternion multiplication
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(x_, y_, z_, w_);
	}
	
	/**
	 * calculated the result of multiplication between this quaternion and the 3D vector passed in
	 * @param vec The Vector3f to be used in the multiplication
	 * @return A new quaternion that is the result of the multiplication
	 */
	public Quaternion mul(Vector3f vec) {
		//calculates the components of the new quaternion individually
		float w_ = -x * vec.getX() - y * vec.getY() - z * vec.getZ();
		float x_ = w * vec.getX() + y * vec.getZ() - z * vec.getY();
		float y_ = w * vec.getY() + z * vec.getX() - x * vec.getZ();
		float z_ = w * vec.getZ() + x * vec.getY() - y * vec.getX();
		
		//creates and returns a new quaternion using components calculated above
		return new Quaternion(x_, y_, z_, w_);
	}
	
	/**
	 * calculates the result of multiplication between this quaternion and a passed in scalar
	 * @param r The scalar to be used in the multiplication
	 * @return A new quaternion that is the result of the multiplication
	 */
	public Quaternion mul(float r) {
		return new Quaternion(x*r, y*r, z*r, w*r);
	}
	
	/**
	 * calculates the result of division between this quaternion and a passed in scalar
	 * @param r The scalar to be used in the division
	 * @return A new quaternion that is the result of the division
	 */
	public Quaternion div(float r) {
		return new Quaternion(x/r, y/r, z/r, w/r);
	}
	
	/**
	 * calculates the result of addition between this quaternion and a passed in scalar
	 * @param r The scalar to be used in the addition
	 * @return A new quaternion that is the result of the addition
	 */
	public Quaternion add(float r) {
		return new Quaternion(x+r, y+r, z+r, w+r);
	}
	
	/**
	 * calculates the result of subtraction between this quaternion and a passed in scalar
	 * @param r The scalar to be used in the subtraction
	 * @return A new quaternion that is the result of the subtraction
	 */
	public Quaternion sub(float r) {
		return new Quaternion(x-r, y-r, z-r, w-r);
	}
	
	//subtracts a passed in quaternions components from this one and returns the result as a new quaternion
	/**
	 * calculates the result of subtraction between this quaternion and the one passed in
	 * @param r the quaternion to be used in the subtraction
	 * @return A new quaternion that is the result of the subtraction
	 */
	public Quaternion sub(Quaternion r) {
		float w_ = w - r.getW();
		float x_ = x - r.getX();
		float y_ = y - r.getY();
		float z_ = z - r.getZ();
		return new Quaternion(x_, y_, z_, w_);
	}
		
	//adds two quaternions together and returns the result
	/**
	 * calculates the result of addition between this quaternion and the one passed in
	 * @param r the quaternion to be used in the addition
	 * @return A new quaternion that is the result of the addition
	 */
	public Quaternion add(Quaternion r) {
		float w_ = w + r.getW();
		float x_ = x + r.getX();
		float y_ = y + r.getY();
		float z_ = z + r.getZ();
		return new Quaternion(x_, y_, z_, w_);
	}
	
	/**
	 * creates a vector whos components (x,y,z) are made up of the imaginary components (x,y,z) of this quaternion
	 * @return A new Vector3f made from this quaternion
	 */
	public Vector3f xyz() {
		return new Vector3f(x,y,z);
	}
	
	@Override
	public String toString() {
		return "[" + this.x + "i, " + this.y + "i, " + this.z + "i, " + this.w + "]"; 
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

	public float getW() {
		return w;
	}

	public void setW(float w) {
		this.w = w;
	}
}
