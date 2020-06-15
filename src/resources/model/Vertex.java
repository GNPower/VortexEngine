package resources.model;

import core.maths.vector.Vector2f;
import core.maths.vector.Vector3f;

/**
 * <h1>Vertex Class</h1>
 * <p>
 * This class holds all the data needed to represent a Vertex in 3D space as,
 * well as some static constants that is true for every vertex.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class Vertex {

	//public static final int FLOATS = 11;
	public static final int FLOATS = 14;
	public static final int BYTES = FLOATS * Float.BYTES;

	private Vector3f position, normal, tangent, bitangent;
	private Vector2f textureCoord;

	/**
	 * creates a vertex with all data set to zero vectors
	 */
	public Vertex() {
		position = new Vector3f(0, 0, 0);
		normal = new Vector3f(0, 0, 0);
		tangent = new Vector3f(0, 0, 0);
		textureCoord = new Vector2f(0, 0);
	}

	/**
	 * creates a basic vertex with only positional data, all other data is set to
	 * zero vectors
	 * 
	 * @param position
	 *            The position of this vertex in model space
	 */
	public Vertex(Vector3f position) {
		this.position = position;
		normal = new Vector3f(0, 0, 0);
		tangent = new Vector3f(0, 0, 0);
		textureCoord = new Vector2f(0, 0);
	}

	/**
	 * creates a vertex with both positional and texture data, all other data is set
	 * to zero vectors
	 * 
	 * @param position
	 *            The position of the vertex in model space
	 * 
	 * @param textureCoord
	 *            The coordinates on an associated texture that represent this
	 *            vertices colour
	 */
	public Vertex(Vector3f position, Vector2f textureCoord) {
		this.position = position;
		normal = new Vector3f(0, 0, 0);
		tangent = new Vector3f(0, 0, 0);
		this.textureCoord = textureCoord;
	}

	/**
	 * creates a vertex with positional, texture and normal data. The remaining
	 * data, the vertices tangent, is set to the zero vector
	 * 
	 * @param position
	 *            The position of the vertex in model space
	 * @param textureCoord
	 *            The coordinates on an associated texture that represent this
	 *            vertices colour
	 * @param normal
	 *            The direction of the normal vector associated with this vertex in
	 *            model space
	 */
	public Vertex(Vector3f position, Vector2f textureCoord, Vector3f normal) {
		this.position = position;
		this.normal = normal;
		tangent = new Vector3f(0, 0, 0);
		this.textureCoord = textureCoord;
	}

	/**
	 * create a vertex with all data required to represent a complete vertex in
	 * model space
	 * 
	 * @param position
	 *            The position of the vertex in model space
	 * 
	 * @param textureCoord
	 *            The coordinates on an associated texture that represent this
	 *            vertices colour
	 * 
	 * @param normal
	 *            The direction of the normal vector associated with this vertex in
	 *            model space
	 * 
	 * @param tangent
	 *            The direction of the tangent vector associated with this vertex in
	 *            model space
	 */
	public Vertex(Vector3f position, Vector2f textureCoord, Vector3f normal, Vector3f tangent) {
		this.position = position;
		this.normal = normal;
		this.tangent = tangent;
		this.textureCoord = textureCoord;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getNormal() {
		return normal;
	}

	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}

	public Vector3f getTangent() {
		return tangent;
	}

	public void setTangent(Vector3f tangent) {
		this.tangent = tangent;
	}

	public Vector2f getTextureCoord() {
		return textureCoord;
	}

	public void setTextureCoord(Vector2f textureCoord) {
		this.textureCoord = textureCoord;
	}

	public Vector3f getBitangent() {
		return bitangent;
	}

	public void setBitangent(Vector3f bitangent) {
		this.bitangent = bitangent;
	}
}
