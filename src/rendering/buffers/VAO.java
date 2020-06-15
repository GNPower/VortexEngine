package rendering.buffers;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL40;

import core.utils.Util;
import resources.model.Mesh;

/**
 * <h1>VAO Class</h1>
 * <p>
 * A Vertex Array Object (VAO) to hold all the required GPU data for rendering a GameObject
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class VAO {

	private static List<VAO> vaos = new ArrayList<VAO>();
	
	private int vaoId;
	private boolean drawPatches;
	private int dataSize = -1;
	
	//TODO: implement indexVBO storage
	private IndicesVBO indexVBO;
	private VBO[] vbos = new VBO[15];
	
	private int vboIndex;
	
	/**
	 * creates a VAO given an IndicesVBO and an associated list of MeshVBO's of arbitrary size 
	 * 
	 * @param indices The IndicesVBO that dictates the connection order of the associated MeshVBO's
	 * @param meshVBOs A list of MeshVBO's containing render data for the object
	 */
	public VAO(IndicesVBO indices, MeshVBO... meshVBOs) {
		vaoId = GL30.glGenVertexArrays();
		vboIndex = 0;
		create(indices, meshVBOs);
		drawPatches = false;
		vaos.add(this);
	}
	
	/**
	 * creates a VAO given a list of PatchVBO's of arbitrary size
	 * 
	 * @param patchVBOs A list of PatchVBO's containing render data for the object
	 */
	public VAO(PatchVBO... patchVBOs) {
		vaoId = GL30.glGenVertexArrays();
		vboIndex = 0;
		create(patchVBOs);
		drawPatches = true;
		vaos.add(this);
	}
	
	/**
	 * creates a VAO given a single mesh
	 * 
	 * @param mesh A fully initialised mesh to have its render data extracted to create this VAO
	 */
	public VAO(Mesh mesh) {
		vaoId = GL30.glGenVertexArrays();
		vboIndex = 0;
		dataSize = mesh.getIndices().length;
		float[][] data = Util.createFloatArraysOfVertexData(mesh.getVertices());
		create(new IndicesVBO(mesh.getIndices()),
				new MeshVBO(data[0], 3),
				new MeshVBO(data[1], 2),
				new MeshVBO(data[2], 3));
		drawPatches = false;
		vaos.add(this);
	}
	
	/**
	 * creates a VAO given a single MeshVBO, this VBO is assumed to contain positional data, otherwise the object will not be renderable
	 * 
	 * @param meshVBO The MeshVBO containing render data for the object.
	 */
	public VAO(MeshVBO meshVBO) {
		vaoId = GL30.glGenVertexArrays();
		vboIndex = 0;
		create(meshVBO);
		drawPatches = false;
		vaos.add(this);
	}
	
	private void create(IndicesVBO indices, MeshVBO... meshVBOs) {
		bind();
		indices.create();
		indexVBO = indices;
		for(MeshVBO vbo : meshVBOs) {
			vbo.create(vboIndex);
			vbos[vboIndex++] = vbo;
		}
		unbind();
	}
	
	private void create(PatchVBO...patchVBOs) {
		bind();
		indexVBO = null;
		for(PatchVBO vbo : patchVBOs) {
			vbo.create(vboIndex);
			dataSize = vbo.getDataLength();
			vbos[vboIndex++] = vbo;
		}
		unbind();
	}
	
	private void create(MeshVBO vbo) {
		bind();
		indexVBO = null;
		vbo.create(vboIndex);
		vbos[vboIndex++] = vbo;
		unbind();
	}
	
	/**
	 * binds this VAO as the active VAO, replacing any previously bound VAO's
	 */
	public void bind() {
		GL30.glBindVertexArray(vaoId);
	}
	
	/**
	 * removes this VAO as the active VAO, leaving no VAO's as active
	 */
	public void unbind() {
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * enables all Vertex Attribute Arrays so their data can be used for rendering
	 */
	public void enable() {
		bind();
		for(int i = 0; i < vboIndex; i++) {
			GL20.glEnableVertexAttribArray(i);
		}
	}
	
	/**
	 * disables all Vertex Attribute Arrays so their data can no longer be used for rendering
	 */
	public void disable() {
		for(int i = 0; i < vboIndex; i++) {
			GL20.glDisableVertexAttribArray(i);
		}
		unbind();
	}
	
	/**
	 * renders this VAO, binding all Vertex Attribute Arrays if they have not manually been bound
	 * 
	 * @param preBound Determines if the Vertex Attribute Arrays have been manually bound before the render call, true if no further bounding is necessary, false if the VAO should bind all Vertex Attribute Arrays 
	 */
	public void render(boolean preBound) {
		if(!preBound)
			enable();
		
		if(!drawPatches) {
			GL11.glDrawElements(GL11.GL_TRIANGLES, dataSize, GL11.GL_UNSIGNED_INT, 0);
		}else {
			GL11.glDrawArrays(GL40.GL_PATCHES, 0, dataSize);
		}
		
		if(!preBound)
			disable();
	}
	
	/**
	 * deletes this VAO and all contained VBO's from GPU memory.
	 */
	public void delete() {
		deleteVBOs();
		GL30.glDeleteVertexArrays(vaoId);
		vaos.remove(this);
	}
	
	//TODO: optimize VBO deletion
	private void deleteVBOs() {
		for(int i = 0; i < vboIndex; i++) {
			GL15.glDeleteBuffers(vbos[i].getId());
		}
		vbos = null;
	}

	public int getId() {
		return vaoId;
	}

	public int getVboIndex() {
		return vboIndex;
	}
	
	/**
	 * CleanUp method to remove all VAO data from memory. Only call this when engine is shutting down.
	 */
	public static void cleanUp() {
		for(VAO vao : vaos) {
			vao.deleteVBOs();
			GL30.glDeleteVertexArrays(vao.getId());
		}
		vaos.clear();
	}
}