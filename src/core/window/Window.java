package core.window;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import core.utils.Constants;

/**
 * <h1>Window Class</h1>
 * <p>
 * This class implements a OpenGL context Window capable of displaying
 * images processed by its associated OpenGL program. It is a single instanced
 * class so only one window can be created per game engine
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-15
 */
public class Window {

private static Window instance = null;
	
	private String title;
	private double aspectRatio;
	private int width, height;
	
	/**
	 * will return a OpenGL context Window object, if none is created then
	 * it will create one before returning it
	 * @return The Window object associated with the engine calling it
	 */
	public static Window getInstance() {
		if(instance == null)
			instance = new Window();
		return instance;
	}
	
	protected Window() {
		title = Constants.WINDOW_TITLE;
		aspectRatio = Constants.ASPECT_RATIO;
		width = Constants.WINDOW_WIDTH;
		height = (int) (width / aspectRatio);
		createDisplay();
	}
	
	private void createDisplay() {
		Display.setTitle(title);
		
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			System.err.println("Error creating the display!");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * runs any initialization required for the window to work before
	 * the standard engine runtime
	 */
	public void init() {}
	
	/**
	 * will update the windows context if the engines display method changes.
	 * Does nothing as of 2.0.0 as the engine is currently not capable of
	 * changing window context
	 */
	public void update() {}
	
	/**
	 * will update the frame visible in the display with whatever is in the 
	 * final frame buffer of the OpenGL program
	 */
	public void render() {
		Display.update();
	}
	
	/**
	 * removes the window form memory, effectively deleting it
	 */
	public void destroyWindow() {
		Display.destroy();
	}
	
	/**
	 * checks if the 'x' button at the top right of the window has been pressed
	 * @return True if the button has been pressed
	 */
	public boolean isCloseRequested() {
		return Display.isCloseRequested();
	}

	public String getTitle() {
		return title;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}