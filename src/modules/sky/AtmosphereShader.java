package modules.sky;

import core.structure.GameObject;
import core.utils.fileIO.ResourceLoader;
import rendering.Shader;

/**
 * <h1>AtmosphereShader Class</h1>
 * <p>
 * A single instanced class to represent the shader code required to render a SkyDome
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class AtmosphereShader extends Shader{

	private static AtmosphereShader instance = null;
	
	/**
	 * Returns the current instance of the AtmosphereShader class, or if no instance exists,
	 * creates a new one before returning it
	 * 
	 * @return The current instance of the AtmosphereShader class
	 */
	public static AtmosphereShader getInstance() {
		if(instance == null)
			instance = new AtmosphereShader();
		return instance;
	}
	
	/**
	 * created an instance of AtmosphereShader by loading and compiling all shader files
	 */
	protected AtmosphereShader() {
		super();
		
		addVertexShader(ResourceLoader.loadShader("res/modules/sky/atmosphere_VS.glsl"));
		addFragmentShader(ResourceLoader.loadShader("res/modules/sky/atmosphere_FS.glsl"));
		
		compileShader();
		
		addUniform("m_MVP");
		addUniform("m_World");
	}
	
	/**
	 * when passed an GameObject, extracts all data necessary to render and
	 * passes them to GPU memory in preparation for rendering
	 */
	public void updateUniforms(GameObject object) {
		setUniform("m_MVP", object.getWorldTransform().getMVPMatrix());
		setUniform("m_World", object.getWorldTransform().getModelMatrix());
	}
}
