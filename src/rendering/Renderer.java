package rendering;

import core.structure.Component;
import rendering.buffers.VAO;
import rendering.config.RenderConfig;

/**
 * <h1>Renderer Class</h1>
 * <p>
 * A component responsible for rendering a GameObject using OpenGL. Stores the
 * GameObjects mesh information in VAO format so it can be directly passed to
 * the GPU and rendered as well as some render config allowing for the engines
 * default rendering settings to be changed to suit specific GameObjects
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-21
 */
public class Renderer extends Component {

	private VAO vao;
	private RenderConfig config;
	private Shader shader;

	public Renderer() {
	}

	/**
	 * Renders parent GameObject using attached VAO, render configurations, and Shader
	 */
	public void render() {
		config.enable();
		shader.bind();
		shader.updateUniforms(getParent());
		vao.render(false);
		config.disable();
	}

	public VAO getVao() {
		return vao;
	}

	public void setVao(VAO vao) {
		this.vao = vao;
	}

	public RenderConfig getConfig() {
		return config;
	}

	public void setConfig(RenderConfig config) {
		this.config = config;
	}

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) {
		this.shader = shader;
	}
}
