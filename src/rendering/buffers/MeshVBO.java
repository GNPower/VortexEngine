package rendering.buffers;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import core.utils.BufferUtil;

/**
 * <h1>MeshVBO Class</h1>
 * <p>
 * A Vertex Buffer Object (VBO) to hold Mesh Vertex data
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class MeshVBO implements VBO{

	private int id;
	
	private float[] data;
	private int dataSize;
	
	/**
	 * creates a MeshVBO filling the VBO with the given data only on the CPU side
	 *  
	 * @param data The mesh data to fill the VBO with
	 * @param dataSize The size of the mesh data being passed in, or how many floats are required to represent each vertex
	 */
	public MeshVBO(float[] data, int dataSize) {
		id = GL15.glGenBuffers();
		this.data = data;
		this.dataSize = dataSize;
	}
	
	/**
	 * fills the MeshVBO with the data passed in on creation on the GPU side
	 * 
	 * @param index The VBO index within the VAO to store the VBO, using any already used data will overwrite it, using index 0 will overwrite the IndicesVBO, making the object un-renderable
	 */
	public void create(int index) {
		FloatBuffer buffer = BufferUtil.createFlippedBuffer(data);
		bind();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, dataSize, GL11.GL_FLOAT, false, 0, 0);
		unbind();
	}
	
	/**
	 * binds this VBO as the active VBO, replacing any previously active VBO
	 */
	@Override
	public void bind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
	}

	/**
	 * un-binds this VBO, removing it as the active VBO
	 */
	@Override
	public void unbind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
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
	
	public int getDataSize() {
		return dataSize;
	}
	
	public int getDataLength() {
		return data.length;
	}
}
