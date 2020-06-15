package rendering.buffers;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL15;

import core.utils.BufferUtil;

/**
 * <h1>IndicesVBO Class</h1>
 * <p>
 * A Vertex Buffer Object (VBO) to hold Vertex Indices data
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class IndicesVBO implements VBO{

	private int id;
	
	private int[] data;
	
	/**
	 * creates an IndicesVBO filling the IndicesVBO with the given data only on the CPU side 
	 * 
	 * @param data The index data to fill the IndicesVBO with
	 */
	public IndicesVBO(int[] data) {
		id = GL15.glGenBuffers();
		this.data = data;
	}
	
	/**
	 * fills the IndicesVBO with the data passed in on creation on the GPU side
	 */
	public void create() {
		IntBuffer buffer = BufferUtil.createFlippedBuffer(data);
		bind();
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * binds this VBO as the active VBO, replacing any previously active VBO
	 */
	@Override
	public void bind() {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, id);
	}

	/**
	 * un-binds this VBO, removing it as the active VBO
	 */
	@Override
	public void unbind() {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	/**
	 * deletes this VBO's data from the GPU, leaving the CPU data for standard garbage collection
	 */
	@Override
	public void delete() {
		GL15.glDeleteBuffers(id);
	}

	@Override
	public int getId() {
		return id;
	}
}
