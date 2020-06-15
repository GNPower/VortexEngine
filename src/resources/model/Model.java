package resources.model;

import resources.texturing.Material;

/**
 * <h1>Model Class</h1>
 * <p>
 * This class holds all the data needed to represent a Model (a mesh with an
 * associated material) in 3D space.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class Model {

	private Mesh mesh;
	private Material material = null;

	/**
	 * creates an empty model without initializing the mesh data. A model in this
	 * state cannot be used without adding a mesh.
	 */
	public Model() {
		material = new Material();
	}

	/**
	 * creates a model with an empty material and passed in mesh data
	 * 
	 * @param mesh
	 *            The mesh that makes up the shape of this model
	 */
	public Model(Mesh mesh) {
		this.mesh = mesh;
		//material = new Material();
	}

	/**
	 * creates a complete model with both mesh and material data
	 * 
	 * @param mesh
	 *            The mesh that makes up the shape of this model
	 *            
	 * @param material
	 *            The material that describes the appearance of this model
	 */
	public Model(Mesh mesh, Material material) {
		this.mesh = mesh;
		this.material = material;
	}

	public Mesh getMesh() {
		return mesh;
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
}
