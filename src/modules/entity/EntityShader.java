package modules.entity;

import org.lwjgl.opengl.GL13;

import core.kernel.Camera;
import core.structure.GameObject;
import core.utils.fileIO.ResourceLoader;
import modules.light.Light;
import rendering.Shader;

/**
 * <h1>EntityShader Class</h1>
 * <p>
 * A single instance class to represent the shader code required to render an entity
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class EntityShader extends Shader{

	private static EntityShader instance = null;
	
	/**
	 * Returns the current instance of the EntityShader class, or if no instance exists,
	 * creates a new one before returning it
	 * 
	 * @return The current instance of the EntityShader class
	 */
	public static EntityShader getInstance() {
		if(instance == null)
			instance = new EntityShader();
		return instance;
	}
	
	/**
	 * creates a new instance of EntityShader by loading and compiling all shader files
	 */
	protected EntityShader() {
		super();
		
		addVertexShader(ResourceLoader.loadShader("res/modules/entities/entity_VS.glsl"));
		addFragmentShader(ResourceLoader.loadShader("res/modules/entities/entity_FS.glsl"));
		
		compileShader();
		
		addUniform("diffuse_map");
		addUniform("specular_map");
		
		addUniform("m_MVP");
		addUniform("m_Model");
		
		addUniform("cameraPosition");
		
		addUniform("light.position");
		addUniform("light.colour");
		addUniform("light.diffuseIntensity");
		addUniform("light.specularIntensity");
	}
	
	/**
	 * when passed an GameObject of type Entity, extracts all data necessary to render and
	 * passes them to GPU memory in preparation for rendering
	 */
	public void updateUniforms(GameObject object) {
		Entity entity = (Entity) object;
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		entity.getModel().getMaterial().getDiffusemap().bind();
		setUniformi("diffuse_map", 0);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		entity.getModel().getMaterial().getSpecularmap().bind();
		setUniformi("specular_map", 1);
		
		setUniform("m_MVP", entity.getWorldTransform().getMVPMatrix());
		setUniform("m_Model", entity.getWorldTransform().getModelMatrix());
		
		setUniform("cameraPosition", Camera.getInstance().getPosition());
	}
	
	public void updateLights(Light light) {
		setUniform("light.position", light.getPosition());
		setUniform("light.colour", light.getColour());
		setUniform("light.diffuseIntensity", light.getDiffuseIntensity());
		setUniform("light.specularIntensity", light.getSpecularIntensity());
	}
}
