package core.maths;


/**
 * <h1>Complex Class</h1>
 * <p>
 * This class represents a complex number to be used for calculations throughout
 * the engine. Contents of this class are based off information gathered from
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
 * @since 2019-05-16
 */
public class Complex {

	private float real, im;

	/**
	 * creates a complex number with both real and imaginary components set to zero
	 */
	public Complex() {
		this.real = 0;
		this.im = 0;
	}

	/**
	 * creates a complex number with real and imaginary components set to the float
	 * values passed in
	 * 
	 * @param real
	 *            The value of the real component of the complex number
	 * @param im
	 *            The value of the imaginary component of the complex number
	 */
	public Complex(float real, float im) {
		this.real = real;
		this.im = im;
	}

	/**
	 * adds two complex number together using standard complex addition
	 * 
	 * @param c
	 *            The complex number to add to this one
	 */
	public void add(Complex c) {
		this.real += c.getReal();
		this.im += c.getIm();
	}

	/**
	 * subtracts two complex numbers using standard complex subtraction
	 * 
	 * @param c
	 *            The complex number to subtract from this one
	 */
	public void sub(Complex c) {
		this.real -= c.getReal();
		this.im -= c.getIm();
	}

	/**
	 * multiplies two complex numbers using standard complex multiplication
	 * 
	 * @param c
	 *            The complex number to multiply this one by
	 */
	public void mul(Complex c) {
		this.real = this.real * c.getReal() - this.im * c.getReal();
		this.im = this.real * c.getIm() + this.im * c.getReal();
	}

	/**
	 * multiplies a scalar throughout a complex
	 * 
	 * @param f
	 *            The scalar to multiply the complex by
	 */
	public void mul(float f) {
		this.real *= f;
		this.im *= f;
	}

	public float getReal() {
		return real;
	}

	public void setReal(float real) {
		this.real = real;
	}

	public float getIm() {
		return im;
	}

	public void setIm(float im) {
		this.im = im;
	}
}
