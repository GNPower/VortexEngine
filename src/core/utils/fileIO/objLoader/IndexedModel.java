package core.utils.fileIO.objLoader;

import java.util.ArrayList;
import java.util.List;

import core.maths.vector.Vector2f;
import core.maths.vector.Vector3f;

/**
 * <IndexedModel Class</h1>
 * <p>
 * A file format independent representation of a 3D model to be used as an
 * intermediary step between loading a 3D model file format and representing it
 * as a renderable mesh
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-21
 */
public class IndexedModel {

	private List<Vector3f> positions, normals;
	private List<Vector2f> textureCoords;
	private List<Integer> indices;
	
	/**
	 * Constructor to create new IndexedModel, sets all attributes to empty lists.
	 */
	public IndexedModel(){
		positions = new ArrayList<Vector3f>();
		textureCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		indices = new ArrayList<Integer>();
	}
	
	/**
	 * calculates the missing normals of a mesh using the smooth shading normals method
	 */
	public void calcNormals() {
		for(int i = 0; i < indices.size(); i += 3) {
			int i0 = indices.get(i);
			int i1 = indices.get(i + 1);
			int i2 = indices.get(i + 2);
			
			Vector3f v1 = positions.get(i1).sub(positions.get(i0));
			Vector3f v2 = positions.get(i2).sub(positions.get(i0));
			
			Vector3f normal = v1.cross(v2).normalize();
			
			normals.get(i0).set(normals.get(i0).add(normal));
			normals.get(i1).set(normals.get(i1).add(normal));
			normals.get(i2).set(normals.get(i2).add(normal));
		}
		
		for(int i = 0; i < normals.size(); i++) {
			normals.get(i).set(normals.get(i).normalize());	
		}
	}
	
	public List<Vector3f> getPositions() {
		return positions;
	}
	public List<Vector3f> getNormals() {
		return normals;
	}
	public List<Vector2f> getTextureCoord() {
		return textureCoords;
	}

	public List<Integer> getIndices() {
		return indices;
	}
}
