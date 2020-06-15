package core.kernel;

import core.input.Input;
import core.input.Keys;
import core.maths.vector.Vector3f;
import core.utils.Constants.RenderComponents;
import core.utils.fileIO.ResourceLoader;
import core.utils.fileIO.objLoader.MeshLoader;
import core.utils.RenderUtil;
import core.window.Window;
import modules.entity.Entity;
import modules.entity.EntityShader;
import modules.light.Light;
import modules.sky.SkyDome;
import rendering.buffers.VAO;
import resources.texturing.Texture2D;

/**
 * <h1>RenderEngine Class</h1>
 * <p>
 * This class is the source of the render engine component of
 * the game engine, it is responsible for running everything that will
 * be rendered (i.e. physically appear) in the game engine
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-15
 */
public class RenderEngine {

	private Window window;
	
	
	private Entity testEntity, testEntity2;
	private Light testLight;
	private SkyDome sky;
	
	/**
	 * creates an instance of the window for this engine to render to
	 */
	public RenderEngine() {
		window = Window.getInstance();
		
		testEntity = new Entity("skull/skull.obj", "./res/models/skull/skull.png", new Vector3f(0,0,0), new Vector3f(0,0,0));
		testEntity.getWorldTransform().setScaling(new Vector3f(0.01f,0.01f,0.01f));
		
		testEntity2 = new Entity("dummy/dummy.obj", "./res/models/dummy/dummy.jpg", new Vector3f(0,8,0), new Vector3f(0,0,0));
		testEntity2.getWorldTransform().setScaling(new Vector3f(0.05f,0.05f,0.05f));
		
		testLight = new Light(new Vector3f(-5,5,-5), new Vector3f(1,1,1));
		testLight.getWorldTransform().setScaling(new Vector3f(0.1f,0.1f,0.1f));
		
		sky = new SkyDome();
		
	}
	
	/**
	 * used to initialise any GameObject or system that must be called in any
	 * way prior to the normal running of the render engine
	 */
	public void init() {
		window.init();
		
		//OBJModel test = new OBJModel("monkey.obj");
		//IndexedModel model = test.toIndexedModel();
		//Model test2 = new Model(OBJLoader.loadMesh("monkey.obj"));
		
	}
	
	/**
	 * used to update any required GameObject or system before they are rendered
	 */
	public void update() {
		//TODO: permanent solution to render mode switching (maybe command terminal)
		if(Input.getInstance().isKeyPushed(Keys.KEY_P)) {
			testEntity.setActiveRenderComponent(RenderComponents.POINT_RENDERER_COMPONENT);
			testEntity2.setActiveRenderComponent(RenderComponents.POINT_RENDERER_COMPONENT);
		}
		if(Input.getInstance().isKeyPushed(Keys.KEY_L)) {
			testEntity.setActiveRenderComponent(RenderComponents.WIREFRAME_RENDERER_COMPONENT);
			testEntity2.setActiveRenderComponent(RenderComponents.WIREFRAME_RENDERER_COMPONENT);
		}
		if(Input.getInstance().isKeyPushed(Keys.KEY_O)) {
			testEntity.setActiveRenderComponent(RenderComponents.RENDERER_COMPONENT);
			testEntity2.setActiveRenderComponent(RenderComponents.RENDERER_COMPONENT);
		}
	}
	
	/**
	 * used to render all GameObjects to the screen as well as any systems that must also
	 * receive render calls.
	 */
	public void render() {
		RenderUtil.clearScreen();
		
		EntityShader.getInstance().updateLights(testLight);
		
		testLight.render();
		
		sky.render();
				
		testEntity.render();		
		testEntity2.render();
				
		System.out.println("Entity: " + testEntity.getPositon().toString());
		System.out.println("Camera: " + Camera.getInstance().getPosition().toString());
		System.out.println("Look: " + Camera.getInstance().getForward().toString());
		System.out.println("Light: " + testLight.getPosition().toString());
		window.render();
	}
	
	/**
	 * used to delete all associated GameObjects and systems to free up memory space before
	 * shutting down the render engine
	 */
	public void shutdown() {
		Input.cleanUp();
		VAO.cleanUp();
		Texture2D.cleanUp();
		ResourceLoader.cleanUp();
	}
}
