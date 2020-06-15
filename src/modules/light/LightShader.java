package modules.light;

import org.lwjgl.opengl.GL13;

import core.structure.GameObject;
import core.utils.fileIO.ResourceLoader;
import rendering.Shader;

public class LightShader extends Shader{

	private static LightShader instance = null;
	
	public static LightShader getInstance() {
		if(instance == null)
			instance = new LightShader();
		return instance;
	}
	
	protected LightShader() {
		super();
		
		addVertexShader(ResourceLoader.loadShader("res/modules/light/light_VS.glsl"));
		addFragmentShader(ResourceLoader.loadShader("res/modules/light/light_FS.glsl"));
		
		compileShader();
		
		addUniform("diffuse_map");
		
		addUniform("m_MVP");
	}
	
	public void updateUniforms(GameObject object) {
		Light light = (Light) object;
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		light.getModel().getMaterial().getDiffusemap().bind();
		setUniformi("diffuse_map", 0);
		
		setUniform("m_MVP", light.getWorldTransform().getMVPMatrix());
	}
}
