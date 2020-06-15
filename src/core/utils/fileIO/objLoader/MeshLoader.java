package core.utils.fileIO.objLoader;

import java.util.ArrayList;
import java.util.HashMap;

import core.utils.Util;
import resources.model.Mesh;
import resources.model.Vertex;

/**
 * <h1>OBJLoader Class</h1>
 * <p>
 * This class is a utilities class for loading mesh from external files into
 * mesh usable by the engine.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class MeshLoader {

	// map to store all mesh by the name of the file they were loaded from
	public static HashMap<String, Mesh> meshMap = new HashMap<String, Mesh>();

	/**
	 * Loads in mesh from external files to a engine usable format
	 * 
	 * @param fileName
	 *            the name of the file that contains the mesh, sub-directories after
	 *            ./res/models must be specified
	 * @return A mesh that represents the data in the mesh file, now usable by the
	 *         engine
	 */
	public static Mesh loadMesh(String fileName) {
		// if mesh has already been loaded returns the already loaded mesh
		if (meshMap.containsKey(fileName))
			return meshMap.get(fileName);

		String[] file = fileName.split("\\.");
		String ext = file[file.length - 1];

		IndexedModel model = null;

		switch (ext) {
		case "obj":
			model = loadFromOBJ(fileName);
			break;
		default:
			System.err.println("file " + Util.toString(file, '.') + " type not supported for obj loading: " + ext);
			new Exception().printStackTrace();
			System.exit(1);
		}

		ArrayList<Vertex> vertices = new ArrayList<Vertex>();

		for (int i = 0; i < model.getPositions().size(); i++) {
			vertices.add(
					new Vertex(model.getPositions().get(i), model.getTextureCoord().get(i), model.getNormals().get(i)));
		}

		Vertex[] vertexData = new Vertex[vertices.size()];
		vertices.toArray(vertexData);

		Integer[] indexData = new Integer[model.getIndices().size()];
		model.getIndices().toArray(indexData);

		Mesh result = new Mesh(vertexData, Util.toIntArray(indexData));

		meshMap.put(fileName, result);

		return result;
	}

	private static IndexedModel loadFromOBJ(String fileName) {
		OBJModel objModel = new OBJModel(fileName);
		IndexedModel model = objModel.toIndexedModel();
		// TODO: remove if unnecessary (may already be done in objmodel)
		model.calcNormals();

		return model;
	}
}
