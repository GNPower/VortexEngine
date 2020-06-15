package rendering.config;

/**
 * <h1>RenderConfig Class</h1>
 * <p>
 * An interface to define all the things a render config class should be able to
 * do. Which are enable specific OpenGL rendering rules through the render
 * method and then disable those same rules once the rendering of its accosted
 * object is complete
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public interface RenderConfig {

	public void enable();
	
	public void disable();
}
