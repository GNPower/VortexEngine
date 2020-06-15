package resources.texturing;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import core.utils.fileIO.ResourceLoader;

/**
 * A container class to hold OpenGL texture information such as width, height,
 * and id. This class does not hold actual texture data only assessor IDs.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class Texture2D {

	private static HashMap<String, Texture2D> textureMap = new HashMap<String, Texture2D>();

	private int id, width, height;
	private String file;

	/**
	 * creates an empty texture class without initializing any variables
	 */
	public Texture2D() {
	}

	/**
	 * creates a new texture form the image at the specified file location if that
	 * texture does not already exits. If the texture already exists it simply
	 * returns the location of the already existing texture
	 * 
	 * @param file
	 *            The location of the image file within the engines directory
	 * 
	 * @return A texture that represents the colour data stored in the image
	 */
	public static Texture2D createTexture(String file) {
		if (textureMap.containsKey(file))
			return textureMap.get(file);
		return new Texture2D(file);
	}

	protected Texture2D(String file) {
		int[] data = ResourceLoader.loadImage(file);
		id = data[0];
		width = data[1];
		height = data[2];
		this.file = file;
		textureMap.put(file, this);
	}

	/**
	 * binds this texture to the OpenGL texture registry, any OpenGL texture
	 * manipulation done after this point will affect this texture unless it is
	 * either unbound or a new texture in bound in its place
	 */
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	/**
	 * generates a new texture id. Note, this will override any texture id already
	 * in this class creating what is essentially an empty texture in place of
	 * whatever texture was here before
	 */
	public void generate() {
		id = GL11.glGenTextures();
	}

	/**
	 * deletes the texture form memory. Note, as all textures are single instanced,
	 * meaning one image file can only create one texture, deleting this texture
	 * will delete its stored image entirely from every material that uses it
	 */
	public void delete() {
		GL11.glDeleteTextures(id);
		textureMap.remove(file);
	}

	/**
	 * unbinds this texture from the OpenGL registry. Any further OpenGL texture
	 * manipulation will not affect this texture unless it is rebound
	 */
	public void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	/**
	 * determines how OpenGL will sample this texture when it is being applied to a
	 * mesh that is either bigger or smaller than this texture. This will simply
	 * take the nearest pixel when sampling and perform no interpolation
	 */
	public void noFilter() {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
	}

	/**
	 * determines how OpenGL will sample this texture when it is being applied to a
	 * mesh that is either bigger or smaller than this texture. This will linearly
	 * interpolate between adjacent texture coordinates in both directions
	 */
	public void bilinearFilter() {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	}

	/**
	 * determines how OpenGL will sample this texture when it is being applied to a
	 * mesh that is either bigger or smaller than this texture. This will linearly
	 * interpolate between adjacent texture coordinates in both directions if
	 * magnification is required, and linearly interpolates over a linearly
	 * generated mipmap if minification is required
	 */
	public void trilinearFilter() {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
	}

	/**
	 * will enable alpha sampling of the texture so that semi-transparent models can
	 * be rendered using this texture
	 */
	public void enableTransparency() {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
	}

	/**
	 * will diable alpha sampling of the texture so that semi-transparent models can
	 * NOT be rendered using this texture, though this is the default setting.
	 */
	//TODO: verify code
	public void disableTransparency() {
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	}

	/**
	 * method to delete every Texture2D from memory, to be called when the game is
	 * closed to clear up used memory
	 */
	public static void cleanUp() {
		for (Texture2D tex : textureMap.values()) {
			GL11.glDeleteTextures(tex.getId());
		}
		textureMap.clear();
	}

	public int getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
