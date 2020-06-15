package core.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import core.maths.Quaternion;
import core.maths.matrix.Matrix4f;
import core.maths.vector.Vector2f;
import core.maths.vector.Vector3f;

/**
 * <h1>BufferUtil Class</h1>
 * <p>
 * This class contains helper methods for buffer manipulation to be used
 * throughout the game engine
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-19
 */
public class BufferUtil {

	/**
	 * creates a float buffer of the requested size. This method is to provide a
	 * buffer layer between this engine and Java's buffer creation calls so if the
	 * syntax were to ever change support could be easily implemented
	 * 
	 * @param size
	 *            The size of the buffer to be created
	 * 
	 * @return An empty float buffer of the specified size
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * creates an int buffer of the requested size. This method is to provide a
	 * buffer layer between this engine and Java's buffer creation calls so if the
	 * syntax were to ever change support could be easily implemented
	 * 
	 * @param size
	 *            The size of the buffer to be created
	 * 
	 * @return An empty int buffer of the specified size
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * creates a double buffer of the requested size. This method is to provide a
	 * buffer layer between this engine and Java's buffer creation calls so if the
	 * syntax were to ever change support could be easily implemented
	 * 
	 * @param size
	 *            The size of the buffer to be created
	 * 
	 * @return An empty double buffer of the specified size
	 */
	public static DoubleBuffer createDoubleBuffer(int size) {
		return BufferUtils.createDoubleBuffer(size);
	}

	/**
	 * creates a byte buffer of the requested size. This method is to provide a
	 * buffer layer between this engine and Java's buffer creation calls so if the
	 * syntax were to ever change support could be easily implemented
	 * 
	 * @param size
	 *            The size of the buffer to be created
	 * 
	 * @return An empty byte buffer of the specified size
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	/**
	 * creates a char buffer of the requested size. This method is to provide a
	 * buffer layer between this engine and Java's buffer creation calls so if the
	 * syntax were to ever change support could be easily implemented
	 * 
	 * @param size
	 *            The size of the buffer to be created
	 * 
	 * @return An empty char buffer of the specified size
	 */
	public static CharBuffer createCharBuffer(int size) {
		return BufferUtils.createCharBuffer(size);
	}

	/**
	 * creates a flipped buffer containing the passed in array of data
	 * 
	 * @param data
	 *            The float data to put in the buffer
	 * 
	 * @return The flipped float buffer containing the data
	 */
	public static FloatBuffer createFlippedBuffer(float... data) {
		FloatBuffer buffer = createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();

		return buffer;
	}

	/**
	 * creates a flipped buffer containing the passed in array of data
	 * 
	 * @param data
	 *            The int data to put in the buffer
	 * 
	 * @return The flipped float buffer containing the data
	 */
	public static IntBuffer createFlippedBuffer(int... data) {
		IntBuffer buffer = createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();

		return buffer;
	}

	/**
	 * creates a flipped buffer containing the passed in array of data
	 * 
	 * @param data
	 *            The double data to put in the buffer
	 * 
	 * @return The flipped float buffer containing the data
	 */
	public static DoubleBuffer createFlippedBuffer(double... data) {
		DoubleBuffer buffer = createDoubleBuffer(data.length);
		buffer.put(data);
		buffer.flip();

		return buffer;
	}

	/**
	 * creates a flipped buffer containing the passed in array of data
	 * 
	 * @param data
	 *            The byte data to put in the buffer
	 * 
	 * @return The flipped float buffer containing the data
	 */
	public static ByteBuffer createFlippedBuffer(byte... data) {
		ByteBuffer buffer = createByteBuffer(data.length);
		buffer.put(data);
		buffer.flip();

		return buffer;
	}

	/**
	 * creates a flipped buffer containing the passed in array of data
	 * 
	 * @param data
	 *            The char data to put in the buffer
	 * 
	 * @return The flipped float buffer containing the data
	 */
	public static CharBuffer createFlippedBuffer(char... data) {
		CharBuffer buffer = createCharBuffer(data.length);
		buffer.put(data);
		buffer.flip();

		return buffer;
	}

	/**
	 * creates a flipped float buffer containing the vertex data where each x,y
	 * coordinate of the vertex is passed into the float buffer individually and in
	 * order.
	 * 
	 * @param data
	 *            The array of vertices to create the float buffer from
	 * 
	 * @return A float buffer containing the passed in vertices
	 */
	public static FloatBuffer createFlippedBuffer(Vector2f[] data) {
		FloatBuffer buffer = createFloatBuffer(data.length * 2 * Float.BYTES);

		for (int i = 0; i < data.length; i++) {
			buffer.put(data[i].getX());
			buffer.put(data[i].getY());
		}

		buffer.flip();

		return buffer;
	}

	/**
	 * creates a flipped float buffer containing the vertex data where each x,y,z
	 * coordinate of the vertex is passed into the float buffer individually and in
	 * order.
	 * 
	 * @param data
	 *            The array of vertices to create the float buffer from
	 * 
	 * @return A float buffer containing the passed in vertices
	 */
	public static FloatBuffer createFlippedBuffer(Vector3f[] data) {
		FloatBuffer buffer = createFloatBuffer(data.length * 3 * Float.BYTES);

		for (int i = 0; i < data.length; i++) {
			buffer.put(data[i].getX());
			buffer.put(data[i].getY());
			buffer.put(data[i].getZ());
		}

		buffer.flip();

		return buffer;
	}

	/**
	 * creates a flipped float buffer containing the vertex data where each x,y,z,w
	 * coordinate of the vertex is passed into the float buffer individually and in
	 * order.
	 * 
	 * @param data
	 *            The array of quaternions to create the float buffer from
	 * 
	 * @return A float buffer containing the passed in quaternions
	 */
	public static FloatBuffer createFlippedBuffer(Quaternion[] data) {
		FloatBuffer buffer = createFloatBuffer(data.length * 4 * Float.BYTES);

		for (int i = 0; i < data.length; i++) {
			buffer.put(data[i].getX());
			buffer.put(data[i].getY());
			buffer.put(data[i].getZ());
			buffer.put(data[i].getW());
		}

		buffer.flip();

		return buffer;
	}

	/**
	 * creates a flipped float buffer containing the matrix data where each i,j
	 * coordinate of the matrix is passed into the float buffer individually and in
	 * column major order.
	 * 
	 * @param data
	 *            The array of matrices to create the float buffer from
	 *            
	 * @return A float buffer containing the passed in matrices
	 */
	public static FloatBuffer createFlippedBuffer(Matrix4f[] data) {
		FloatBuffer buffer = createFloatBuffer(data.length * 4 * 4);

		for (Matrix4f mat : data) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					buffer.put(mat.get(i, j));
				}
			}
		}

		buffer.flip();

		return buffer;
	}

	/**
	 * creates a flipped float buffer containing a single matrices data where each i,j
	 * coordinate of the matrix is passed into the float buffer individually and in
	 * column major order.
	 * 
	 * @param matrix
	 * @return
	 */
	public static FloatBuffer createFlippedBuffer(Matrix4f matrix) {
		FloatBuffer buffer = createFloatBuffer(4 * 4);
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				buffer.put(matrix.get(i, j));
			}
		}
		
		buffer.flip();
		
		return buffer;
	}
}
