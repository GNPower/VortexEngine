package core.utils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import core.maths.vector.Vector2f;
import core.maths.vector.Vector3f;
import resources.model.Vertex;

/**
 * <h1>Util Class</h1>
 * <p>
 * This class holds basic utility that doesn't fit into any specific utility
 * class to be used throughout the engine
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-20
 */
public class Util {

	/**
	 * copies the passed in String array to a new array, omitting any indices with
	 * empty strings
	 * 
	 * @param data
	 *            The String array to remove the empty strings from
	 * 
	 * @return A new string array with identical data to the passed in one,
	 *         excluding any empty strings
	 */
	public static String[] removeEmptyStrings(String[] data) {
		ArrayList<String> result = new ArrayList<String>();

		for (String entry : data) {
			if (!entry.equals(""))
				result.add(entry);
		}

		String[] res = new String[result.size()];
		result.toArray(res);

		return res;
	}

	/**
	 * converts an array of Integer objects to an array of primitive type int
	 * 
	 * @param data
	 *            The Integer array to convert
	 * 
	 * @return The same array represented with primitive type int
	 */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++) {
			result[i] = data[i].intValue();
		}

		return result;
	}

	/**
	 * converts a float buffer storing vertex data into an array of Vertices. This
	 * allows the engine to interpret vertex data that was previously passed to the
	 * GPU in buffer form.
	 * 
	 * @param data
	 *            The FloatBuffer of vertex data to be converted to Vertices
	 * 
	 * @return A new Vertex array representing the saem vertex data that was in the
	 *         float buffer
	 */
	public static Vertex[] toVertexArray(FloatBuffer data) {
		Vertex[] vertices = new Vertex[data.limit() / Vertex.FLOATS];

		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vertex();
			vertices[i].setPosition(new Vector3f(data.get(), data.get(), data.get()));
			vertices[i].setTextureCoord(new Vector2f(data.get(), data.get()));
			vertices[i].setNormal(new Vector3f(data.get(), data.get(), data.get()));
		}

		return vertices;
	}

	/**
	 * converts an array list of vertices to an array of vertices. Useful when
	 * performing vertex manipulation on what will be an unknown amount of vertices
	 * until the manipulation is complete, and a format that is interpretable by the
	 * engine (i.e. a Vertex array) is now required
	 * 
	 * @param data
	 *            The array list of vertex data to convert
	 * 
	 * @return An array of vertices with identical data as the array list
	 */
	public static Vertex[] toVertexArray(ArrayList<Vertex> data) {
		Vertex[] vertices = new Vertex[data.size()];

		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vertex();
			vertices[i].setPosition(data.get(i).getPosition());
			vertices[i].setTextureCoord(data.get(i).getTextureCoord());
			vertices[i].setNormal(data.get(i).getNormal());
		}

		return vertices;
	}

	/**
	 * creates three float array each with a different set of data stored in the
	 * passed in vertices and returns them contained in a 2D float array whos
	 * indices contain vertex positions, texutreCoords, and normals in that order
	 * 
	 * @param vertices
	 *            The array of vertices to convert to float data
	 * 
	 * @return A 2D array of size float[3][x] where x is the amount of vertices in
	 *         the list with positions at float[0][x], textureCoords at float[1][x],
	 *         and normals at float[2][x]
	 */
	public static float[][] createFloatArraysOfVertexData(Vertex[] vertices) {
		float[][] result = new float[3][];
		
		float[] positions = new float[vertices.length * 3];
		float[] normals = new float[vertices.length * 3];
		
		for(int i = 0; i < vertices.length * 3; i+= 3) {
			positions[i  ] = vertices[i/3].getPosition().getX();
			positions[i+1] = vertices[i/3].getPosition().getY();
			positions[i+2] = vertices[i/3].getPosition().getZ();
			
			normals[i  ] = vertices[i/3].getNormal().getX();
			normals[i+1] = vertices[i/3].getNormal().getY();
			normals[i+2] = vertices[i/3].getNormal().getZ();
		}
		
		float[] textures = new float[vertices.length * 3];
		
		for(int i = 0; i < vertices.length * 2; i += 2) {
			textures[i  ] = vertices[i/2].getTextureCoord().getX();
			textures[i+1] = vertices[i/2].getTextureCoord().getY();
		}
		
		result[0] = positions;
		result[1] = textures;
		result[2] = normals;
		
		return result;
	}
	
	/**
	 * converts a string array into a single string
	 * @param data The array to convert
	 * @return A single string containing all array elements separated by spaces
	 */
	public static String toString(String[] data) {
		String res = "";
		for(String str : data) {
			res += str + " ";
		}
		return res;
	}
	
	/**
	 * converts a string array into a single string, adding back previously removed delimiters
	 * @param data The array to convert
	 * @return A single string containing all array elements separated by the passed in delimiter
	 */
	public static String toString(String[] data, char delimiter) {
		String res = "";
		for(String str : data) {
			res += str + delimiter;
		}
		return res;
	}
}
