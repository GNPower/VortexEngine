package core.utils.fileIO.objLoader;

/**
 * <h1>OBJIndex Class</h1>
 * <p>
 * This class provides cross format communication for vertex indexing between
 * the obj file format and the engines internal vertex indexing. This allows the
 * obj files system of having different indices for vertex, textures, and
 * normals to be held under a single 'index' to better match the single index
 * for all data method used internally by the engine
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-21
 */
public class OBJIndex {

	private int vertexIndex, textureIndex, normalIndex;
	
	@Override
	public boolean equals(Object obj) {
		OBJIndex index = (OBJIndex) obj;
		
		return vertexIndex == index.vertexIndex
				&& textureIndex == index.textureIndex
				&& normalIndex == index.normalIndex;
	}
	
	@Override
	public int hashCode() {
		final int BASE = 17;
		final int MULTIPLIER = 31;
		
		int result = BASE;
		
		result = MULTIPLIER * result + vertexIndex;
		result = MULTIPLIER * result + textureIndex;
		result = MULTIPLIER * result + normalIndex;
		
		return result;
	}
	
	public int getVertexIndex() {
		return vertexIndex;
	}

	public void setVertrxIndex(int vertrxIndex) {
		this.vertexIndex = vertrxIndex;
	}

	public int getTextureIndex() {
		return textureIndex;
	}

	public void setTextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}

	public int getNormalIndex() {
		return normalIndex;
	}

	public void setNormalIndex(int normalIndex) {
		this.normalIndex = normalIndex;
	}
}
