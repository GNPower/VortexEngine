package core.utils.fileIO.objLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.maths.vector.Vector2f;
import core.maths.vector.Vector3f;
import core.utils.Util;

/**
 * <h1>OBJModel Class</h1>
 * <p>
 * A class to closely represent the format used by obj models so they can be
 * easily loaded form the file and stored in memory before being optimzed and
 * converted to engine format for rendering
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-21
 */
public class OBJModel {

	private List<Vector3f> positions, normals;
	private List<Vector2f> textureCoords;
	private List<OBJIndex> indices;
	private boolean hasTextures, hasNormals;

	/**
	 * Constructor for OBJModel creates model from file upon instance creation
	 * @param fileName the location of the file to be loaded to an OBJModel, sub-directories after ./res/models/ must be specified
	 */
	public OBJModel(String fileName) {
		positions = new ArrayList<Vector3f>();
		textureCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		indices = new ArrayList<OBJIndex>();
		
		hasTextures = false;
		hasNormals = false;
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader("./res/models/" + fileName));
			
			String line;
			while((line = reader.readLine()) != null) {
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);
				
				if(tokens.length == 0 || tokens[0].equals("#"))
					continue;
				else if(tokens[0].equals("v")) {
					positions.add(new Vector3f(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2]),
							Float.valueOf(tokens[3])));
				} else if(tokens[0].equals("vt")) {
					textureCoords.add(new Vector2f(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2])));
				} else if(tokens[0].equals("vn")) {
					normals.add(new Vector3f(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2]),
							Float.valueOf(tokens[3])));
				} else if(tokens[0].equals("f")) {
					
					for(int i = 0; i < tokens.length - 3; i++) {
						indices.add(parseOBJIndex(tokens[1]));
						indices.add(parseOBJIndex(tokens[2 + i]));
						indices.add(parseOBJIndex(tokens[3 + i]));
					}
				}
			}	
			reader.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Could not find the obj file at the specified locaiton");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Error loading the obj file");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * converts this OBJModel to an indexed model, the intermediate step between a
	 * model file and an engine usable format
	 * @return An indexed model converted from this OBJModel
	 */
	public IndexedModel toIndexedModel() {
		IndexedModel result = new IndexedModel();
		IndexedModel normalModel = new IndexedModel();
		HashMap<OBJIndex, Integer> resultIndexMap = new HashMap<OBJIndex, Integer>();
		HashMap<Integer, Integer> normalIndexMap = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < indices.size(); i++) {
			OBJIndex current = indices.get(i);
			
			Vector3f pos = positions.get(current.getVertexIndex());
			Vector2f tex;
			Vector3f norm;
			
			if(hasTextures)
				tex = textureCoords.get(current.getTextureIndex());
			else
				tex = new Vector2f(0,0);
			
			if(hasNormals)
				norm = normals.get(current.getNormalIndex());
			else
				norm = new Vector3f(0,1,0);
			
			Integer modelVertexIndex = resultIndexMap.get(current);
			
			if(modelVertexIndex == null) {
				modelVertexIndex = result.getPositions().size();
				resultIndexMap.put(current, modelVertexIndex);
				
				result.getPositions().add(pos);
				result.getTextureCoord().add(tex);
				if(hasNormals)
					result.getNormals().add(norm);
			}
			
			Integer normalModelIndex = normalIndexMap.get(current.getVertexIndex());
			
			if(normalModelIndex == null) {
				normalModelIndex = normalModel.getPositions().size();
				normalIndexMap.put(current.getVertexIndex(), normalModelIndex);
				
				normalModel.getPositions().add(pos);
				normalModel.getTextureCoord().add(tex);
				normalModel.getNormals().add(norm);
			}
			
			result.getIndices().add(modelVertexIndex);
			normalModel.getIndices().add(normalModelIndex);
			indexMap.put(modelVertexIndex, normalModelIndex);

		}
		
		if(!hasNormals) {
			normalModel.calcNormals();
			
			for(int i = 0; i < result.getPositions().size(); i++) {
				result.getNormals().add(normalModel.getNormals().get(indexMap.get(i)));
			}
		}
		
		return result;
	}
	
	/**
	 * converts the text representation on an index, found in .obj files to a usable
	 * representation of an index
	 * @param token The string representation of an index, taken directly form the .obj file
	 * @return An OBJIndex, which is temporary representation of an index, usable by an OBJModel
	 */
	private OBJIndex parseOBJIndex(String token) {
		
		String[] tokens = token.split("/");
		
		OBJIndex result = new OBJIndex();
		result.setVertrxIndex(Integer.parseInt(tokens[0]) - 1);
	
		if(tokens.length > 1) {
			hasTextures = true;
			result.setTextureIndex(Integer.parseInt(tokens[1]) - 1);
			
			if(tokens.length > 2) {
				hasNormals = true;
				result.setNormalIndex(Integer.parseInt(tokens[2]) - 1);
			}
		}

		return result;
	}

	public List<Vector3f> getPositions() {
		return positions;
	}

	public List<Vector3f> getNormals() {
		return normals;
	}

	public List<Vector2f> getTextureCoords() {
		return textureCoords;
	}

	public List<OBJIndex> getIndices() {
		return indices;
	}
}
