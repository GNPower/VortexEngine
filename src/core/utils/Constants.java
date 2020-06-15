package core.utils;

/**
 * <h1>Constants Class</h1>
 * <p>
 * This class has the purpose of storing constant values
 * that may be needed anywhere in the game engine such as engine name
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-14
 */
public class Constants {
	
	public static final String VERSION_ID = "2.0.0";
	
	
	public static final String WINDOW_TITLE = "Vortex Engine " + VERSION_ID;
	public static final double ASPECT_RATIO = 16.0 / 9.0;
	public static final int WINDOW_WIDTH = 1280;
	
	public static final float ZFAR = 10000.0f;
	public static final float ZNEAR = 0.1f;
	
	public static final long NANOSECOND = 1000000000;
	public static final long MICROSECOND = 1000000;

	public static final String DEFAULT_TEXTURE_LOCATION = "./res/models/default/";
	
	public static enum RenderComponents{
		RENDERER_COMPONENT, WIREFRAME_RENDERER_COMPONENT, POINT_RENDERER_COMPONENT;
	}
}
