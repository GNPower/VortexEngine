package resources.texturing;

import core.maths.vector.Vector3f;
import core.utils.Constants;

/**
 * <h1>Material Class</h1>
 * <p>
 * Holds all the textures and colour information needed for a model to be
 * rendered realistically
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class Material {

	private String name;
	
	//currently used
	private Texture2D diffusemap = null;
	private Texture2D specularmap = null;
	
	private Texture2D normalmap = null;
	private Texture2D displacemap = null;
	private Texture2D ambientmap = null;
	private Texture2D alphamap = null;
	
	private Vector3f colour = new Vector3f(0.1f,0.1f,1.0f);
	private float alpha = 1;
	private float displaceScale = 1;
	private float horizontalScale = 1;
	private float emission = 0;
	private float reflectivity = 1;
	
	public Material() {
		diffusemap = Texture2D.createTexture(Constants.DEFAULT_TEXTURE_LOCATION + "diffuse.png");
		specularmap = Texture2D.createTexture(Constants.DEFAULT_TEXTURE_LOCATION + "specular.png");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Texture2D getDiffusemap() {
		return diffusemap;
	}

	public void setDiffusemap(Texture2D diffusemap) {
		this.diffusemap = diffusemap;
	}

	public Texture2D getNormalmap() {
		return normalmap;
	}

	public void setNormalmap(Texture2D normalmap) {
		this.normalmap = normalmap;
	}

	public Texture2D getDisplacemap() {
		return displacemap;
	}

	public void setDisplacemap(Texture2D displacemap) {
		this.displacemap = displacemap;
	}

	public Texture2D getAmbientmap() {
		return ambientmap;
	}

	public void setAmbientmap(Texture2D ambientmap) {
		this.ambientmap = ambientmap;
	}

	public Texture2D getSpecularmap() {
		return specularmap;
	}

	public void setSpecularmap(Texture2D specularmap) {
		this.specularmap = specularmap;
	}

	public Texture2D getAlphamap() {
		return alphamap;
	}

	public void setAlphamap(Texture2D alphamap) {
		this.alphamap = alphamap;
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public float getDisplaceScale() {
		return displaceScale;
	}

	public void setDisplaceScale(float displaceScale) {
		this.displaceScale = displaceScale;
	}

	public float getHorizontalScale() {
		return horizontalScale;
	}

	public void setHorizontalScale(float horizontalScale) {
		this.horizontalScale = horizontalScale;
	}

	public float getEmission() {
		return emission;
	}

	public void setEmission(float emission) {
		this.emission = emission;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
}