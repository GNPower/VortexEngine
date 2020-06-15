package resources.model;

import rendering.buffers.VAO;

/**
 * <h1>Mesh Class</h1>
 * <p>
 * This class holds all the data needed to represent a Mesh (a collection of
 * vertices connected in a listed order) in 3D space.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class Mesh {

	private Vertex[] vertices;
	private int[] indices;
	private VAO vao;
	
	/**
	 * Creates a Mesh given a list of Vertices that make up the Mesh and a list of indices
	 * that dictate the order in which to connect the Vertices
	 * 
	 * @param vertices A Vertex array containing all vertices that make up the Mesh
	 * @param indices An integer array containing the order in which the Vertex array should be connected to form the desired mesh
	 */
	public Mesh(Vertex[] vertices, int[] indices) {
		this.vertices = vertices;
		this.indices = indices;
		
		vao = new VAO(this);
	}

	public Vertex[] getVertices() {
		return vertices;
	}

	public void setVertices(Vertex[] vertices) {
		this.vertices = vertices;
	}

	public int[] getIndices() {
		return indices;
	}

	public void setIndices(int[] indices) {
		this.indices = indices;
	}

	public VAO getVao() {
		return vao;
	}
}
