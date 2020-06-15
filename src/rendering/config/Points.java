package rendering.config;

import org.lwjgl.opengl.GL11;

/**
 * <h1>Points Class</h1>
 * <p>
 * Render configuration that changes the OpenGL polygon mode from front-face fill (default)
 * to front and back face point. Effectively rendering the object as a cluster of single 
 * points where the objects vertices are.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2020-01-19
 */
public class Points implements RenderConfig{

	@Override
	public void enable() {
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_POINT);
	}

	@Override
	public void disable() {
		GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
}
