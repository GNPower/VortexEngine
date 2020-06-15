package modules.entity;

import core.maths.vector.Vector3f;
import core.structure.GameObject;
import core.utils.Constants;
import core.utils.Constants.RenderComponents;
import core.utils.fileIO.objLoader.MeshLoader;
import rendering.Renderer;
import rendering.config.Default;
import rendering.config.Points;
import rendering.config.Wireframe;
import resources.model.Model;
import resources.texturing.Material;
import resources.texturing.Texture2D;

/**
 * <h1>Entity Class</h1>
 * <p>
 * Represents any non-specialised Game Object that can be rendered into the engine.
 * Stores an updatable model, 3D position and rotation, as well as a Render component,
 * to allow the entity the ability to render itself.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class Entity extends GameObject{

	private Model model;
	private Vector3f positon, rotation;
	private RenderComponents activeRenderComponent;
	
	/**
	 * Creates all the data required to render the entity from passed in files and data
	 * 
	 * @param file The .obj file for the mesh associated with this entity, sub-directories after ./res/models must be specified in the file name
	 * @param texture The .png file for the diffuse map associated with this entity, all directories including ./res/models must be specified 
	 * @param position The 3D world position to render the entity in
	 * @param rotation The 3D rotation to render the entity ins
	 */
	public Entity(String file, String texture, Vector3f position, Vector3f rotation) {
		model = new Model();
		model.setMesh(MeshLoader.loadMesh(file));
		model.setMaterial(new Material());
		model.getMaterial().setDiffusemap(Texture2D.createTexture(texture));
		model.getMaterial().setSpecularmap(Texture2D.createTexture("./res/models/default/specular.png"));
		
		this.positon = position;
		this.rotation = rotation;
		
		Renderer renderer = new Renderer();
		renderer.setVao(model.getMesh().getVao());
		renderer.setConfig(new Default());
		renderer.setShader(EntityShader.getInstance());
		
		addComponent(Constants.RenderComponents.RENDERER_COMPONENT, renderer);
		
		Renderer wireframe = new Renderer();
		wireframe.setVao(model.getMesh().getVao());
		wireframe.setConfig(new Wireframe());
		wireframe.setShader(EntityShader.getInstance());
		
		addComponent(Constants.RenderComponents.WIREFRAME_RENDERER_COMPONENT, wireframe);
		
		Renderer points = new Renderer();
		points.setVao(model.getMesh().getVao());
		points.setConfig(new Points());
		points.setShader(EntityShader.getInstance());
		
		addComponent(Constants.RenderComponents.POINT_RENDERER_COMPONENT, points);
		
		activeRenderComponent = Constants.RenderComponents.RENDERER_COMPONENT;
		
		getWorldTransform().setTranslation(position);
		getWorldTransform().setRotation(rotation);		
	}
	
	/**
	 * Updates the transformation matrices of the entity before calling the render method of its active render component
	 */
	@Override
	public void render() {
		getWorldTransform().setTranslation(positon);
		getWorldTransform().setRotation(rotation);
		
		if(getComponents().get(activeRenderComponent) != null) {
			getComponents().get(activeRenderComponent).render();
		}else {
			getComponents().get(RenderComponents.RENDERER_COMPONENT).render();
		}
	}
	
	/**
	 * Moves the entity in the direction and magnitude of the passed in vector, using the math newPos = oldPos + vec
	 * 
	 * @param vec The vector representing the magnitude and direction of the movement
	 */
	public void move(Vector3f vec) {
		this.positon = positon.add(vec);
	}
	
	/**
	 * Rotates the entity in the direction and magnitude of the passed in vector, using the math newRot = oldRot + vec
	 * 
	 * @param vec The vector representing the magnitude and direction of the rotation
	 */
	public void rotate(Vector3f vec) {
		this.rotation = rotation.add(vec);
	}

	public Vector3f getPositon() {
		return positon;
	}

	public void setPositon(Vector3f positon) {
		this.positon = positon;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public Model getModel() {
		return model;
	}

	public RenderComponents getActiveRenderComponent() {
		return activeRenderComponent;
	}

	public void setActiveRenderComponent(RenderComponents activeRenderComponent) {
		this.activeRenderComponent = activeRenderComponent;
	}
}
