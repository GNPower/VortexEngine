package rendering.config;

import org.lwjgl.opengl.GL11;

/**
 * <h1>CCW Class</h1>
 * <p>
 * Render configuration that changes the vertex draw order from clock-wise (default)
 * to counter-clockwise. If back face culling is enabled it will render the objects texture
 * on the inside as opposed to the outside face of the object.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class CCW implements RenderConfig{

	@Override
	public void enable() {
		GL11.glFrontFace(GL11.GL_CCW);
	}

	@Override
	public void disable() {
		GL11.glFrontFace(GL11.GL_CW);
	}
}
