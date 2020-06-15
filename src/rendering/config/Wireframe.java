package rendering.config;

import org.lwjgl.opengl.GL11;

/**
 * <h1>Wireframe Class</h1>
 * <p>
 * Render configuration that changes the OpenGL polygon mode from front-face fill (default)
 * to front and back face line. Effectively rendering the object as a see through mesh, with
 * lines connecting the vertex points
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2020-01-19
 */
public class Wireframe implements RenderConfig{

	@Override
	public void enable() {
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
	}

	@Override
	public void disable() {
		GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

}
