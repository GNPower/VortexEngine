package modules.light;

import core.maths.vector.Vector3f;
import core.structure.GameObject;
import core.utils.Constants;
import core.utils.Constants.RenderComponents;
import core.utils.fileIO.objLoader.MeshLoader;
import rendering.Renderer;
import rendering.config.Default;
import resources.model.Model;
import resources.texturing.Material;
import resources.texturing.Texture2D;

public class Light extends GameObject{

	protected Vector3f position, colour, diffuseIntensity, specularIntensity;
	private Model model;
	
	public Light(Vector3f position, Vector3f colour) {
		this.position = position;
		this.colour = colour;
		//these must be set to values other than one to take effect in shading
		this.diffuseIntensity = new Vector3f(0.5f,0.5f,0.5f);
		this.specularIntensity = new Vector3f(1,1,1);
		
		model = new Model();
		model.setMesh(MeshLoader.loadMesh("light/light.obj"));
		model.setMaterial(new Material());
		model.getMaterial().setDiffusemap(Texture2D.createTexture("./res/models/light/diffuse.png"));
		
		Renderer renderer = new Renderer();
		renderer.setVao(model.getMesh().getVao());
		renderer.setConfig(new Default());
		renderer.setShader(LightShader.getInstance());
		
		addComponent(Constants.RenderComponents.RENDERER_COMPONENT, renderer);
		
		getWorldTransform().setRotation(new Vector3f(0,0,0));
		getWorldTransform().setTranslation(position);
	}
	
	@Override
	public void render() {
		getWorldTransform().setTranslation(position);
		
		getComponents().get(RenderComponents.RENDERER_COMPONENT).render();
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

	public Vector3f getDiffuseIntensity() {
		return diffuseIntensity;
	}

	public void setDiffuseIntensity(Vector3f diffuseIntensity) {
		this.diffuseIntensity = diffuseIntensity;
	}

	public Vector3f getSpecularIntensity() {
		return specularIntensity;
	}

	public void setSpecularIntensity(Vector3f specularIntensity) {
		this.specularIntensity = specularIntensity;
	}
	
	public Model getModel() {
		return model;
	}
}