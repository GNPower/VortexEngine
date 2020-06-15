package rendering.buffers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL40;

import core.maths.vector.Vector2f;
import core.utils.BufferUtil;

/**
 * <h1>PatchVBO Class</h1>
 * <p>
 * A Vertex Buffer Object (VBO) to hold Mesh Vertex data for an object using primitive patch rendering method
 * 
 * @author Graham
 * @version 2.0.0
 * @see 2019-05-16
 */
public class PatchVBO implements VBO{

	private int id;
	
	private Vector2f[] vertices;
	private int dataSize;
	
	/**
	 * creates a PatchVBO filling the VBO with the given data only on the CPU side
	 * 
	 * @param vertices A list of the vertices contained in the patch
	 */
	public PatchVBO(Vector2f[] vertices) {
		id = GL15.glGenBuffers();
		dataSize = 2;
		this.vertices = vertices;
	}
	
	/**
	 * fills the MeshVBO with the data passed in on creation on the GPU side
	 * 
	 * @param index The VBO index within the VAO to store the VBO, using any already used data will overwrite it, using index 0 will overwrite the IndicesVBO, making the object un-renderable
	 */
	public void create(int index) {
		bind();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtil.createFlippedBuffer(vertices), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, dataSize, GL11.GL_FLOAT, false, 0, 0);
		GL40.glPatchParameteri(GL40.GL_PATCH_VERTICES, vertices.length);
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
		return vertices.length;
	}
}
