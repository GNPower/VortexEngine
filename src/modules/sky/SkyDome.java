package modules.sky;

import core.maths.vector.Vector3f;
import core.structure.GameObject;
import core.utils.Constants;
import core.utils.fileIO.objLoader.MeshLoader;
import rendering.Renderer;
import rendering.config.CCW;
import resources.model.Mesh;

/**
 * <h1>SkyDome Class</h1>
 * <p>
 * represents a specialised Game Object, a dome shaped sky box, that can be rendered into
 * the engine. Stores a model as well as a Render component, to allow the entity the 
 * ability to render itself.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class SkyDome extends GameObject{

	/**
	 * Creates all the data required to render the SkyDome
	 */
	public SkyDome() {
		Mesh mesh = MeshLoader.loadMesh("dome/dome.obj");
		
		Renderer renderer = new Renderer();
		renderer.setVao(mesh.getVao());
		renderer.setConfig(new CCW());
		renderer.setShader(AtmosphereShader.getInstance());
		
		addComponent(Constants.RenderComponents.RENDERER_COMPONENT, renderer);
		
		getWorldTransform().setScaling(new Vector3f(Constants.ZFAR * 0.5f, Constants.ZFAR * 0.5f, Constants.ZFAR * 0.5f));
	}
	
	/**
	 * calls the render method of this SkyDomes active render component
	 */
	@Override
	public void render() {
		super.render();
	}
}
