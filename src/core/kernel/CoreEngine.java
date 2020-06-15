package core.kernel;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL43;

import core.input.Input;
import core.utils.Constants;
import core.utils.RenderUtil;
import core.window.Window;

/**
 * <h1>CoreEngine Class</h1>
 * <p>
 * This class houses the core engine of the game engine. It is responsible for
 * running and monitoring all subsidiary engines such as the render and physics
 * engine among others. It also initializes and provides access to core engine
 * functionality such as profiling and timing systems.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-15
 */
public class CoreEngine {

	private static int fps;
	private static float framerate = 200;
	private static float frameTime = 1.0f/framerate;
	private boolean isRunning;
	private RenderEngine renderEngine;
	
	/**
	 * initializes any subsystems that the core engine is responsible for managing and
	 * prints out the properties of the device the engine is running on
	 */
	public CoreEngine() {
		renderEngine = new RenderEngine();
		getDeviceProperties();	
	}
	
	/**
	 * used to initialize any subsystem that must be called in any
	 * way prior to the normal running of the core engine
	 */
	public void  init() {
		RenderUtil.init();
		renderEngine.init();
	}
	
	/**
	 * the method called to start the engine. Each engine can only be started once
	 * and any calls to this method after the engine has started will immediately return.
	 */
	public void start(){
		if(isRunning)
			return;
		run();
	}
	
	/**
	 * method which contains the runtime loop for the engine. Handles simple timing
	 * of engine execution to ensure everything runs smoothly as well as profiles and 
	 * displays FPS to the terminal
	 */
	private void run() {
		isRunning = true;
		
		int frames = 0;
		long frameCounter = 0;
		
		long lastTime = System.nanoTime();
		double unprocessedTime = 0;
		
		while(isRunning) {
			if(Window.getInstance().isCloseRequested())
				stop();
			boolean render = false;
			
			long startTime = System.nanoTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime / (double) Constants.NANOSECOND;
			frameCounter += passedTime;
			
			while(unprocessedTime > frameTime) {
				render = true;
				unprocessedTime -= frameTime;
				
				if(Window.getInstance().isCloseRequested())
					stop();
				
				update();
				
				if(frameCounter >= Constants.NANOSECOND) {
					fps = frames;
					System.out.println("fps: " + frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			
			if(render) {
				render();
				frames++;
			}else {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		cleanUp();
	}
	
	/**
	 * method to update all engine subsystems and core processes
	 */
	private void update() {
		Input.getInstance().update();
		Camera.getInstance().update();
		renderEngine.update();
	}
	
	/**
	 * method to render all engine subsystems as well as last minute updates
	 * to core processes to ensure a more accurate rendering
	 */
	private void render() {
		Input.getInstance().update();
		Camera.getInstance().update();
		renderEngine.render();
	}
	
	private void stop() {
		if(!isRunning) return;
		isRunning = false;
	}
	
	private void cleanUp() {
		Window.getInstance().destroyWindow();
		System.exit(0);
	}
	
	private void getDeviceProperties() {
		System.out.println("OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION));
		System.out.println("Max Geometry Uniform Blocks: " + GL31.GL_MAX_GEOMETRY_UNIFORM_BLOCKS);
		System.out.println("Max Geometry Shader Invocations: " + GL40.GL_MAX_GEOMETRY_SHADER_INVOCATIONS);
		System.out.println("Max Uniform Buffer Bindings: " + GL31.GL_MAX_UNIFORM_BUFFER_BINDINGS);
		System.out.println("Max Uniform Block Size: " + GL31.GL_MAX_UNIFORM_BLOCK_SIZE);
		System.out.println("Max SSBO Block Size: " + GL43.GL_MAX_SHADER_STORAGE_BLOCK_SIZE);
	}

	public static float getFrameTime() {
		return frameTime;
	}

	public static int getFps() {
		return fps;
	}

	public static void setFramerate(float framerate) {
		CoreEngine.framerate = framerate;
	}
}
