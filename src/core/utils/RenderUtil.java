package core.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/**
 * <h1>RenderUtil Class</h1>
 * <p>
 * This class houses all required OpenGL calls to provide a layer of
 * independence between the game engine and OpenGL, to allow the engine to
 * easier accommodate for changes in OpenGL calls or functionality
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-15
 */
public class RenderUtil {

	/**
	 * Initialises the game engine by setting some required OpenGL render settings
	 */
	public static void init() {
		GL11.glFrontFace(GL11.GL_CW);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glEnable(GL30.GL_FRAMEBUFFER_SRGB);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	/**
	 * clears the OpenGL depth and colour buffer by reseting the colour buffer 
	 * to opaque black and the depth buffer to one
	 */
	public static void clearScreen() {
		GL11.glClearColor(0, 0, 0, 1);
		GL11.glClearDepth(1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
}
