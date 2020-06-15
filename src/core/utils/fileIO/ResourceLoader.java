package core.utils.fileIO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * <h1>ResourceLoader Class</h1>
 * <p>
 * This class makes use of the Slick-Util Image library to load an image into
 * the engine, as well as load shader files into the engine.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-18
 */
public class ResourceLoader {
	
	private static HashMap<String, String> shaders = new HashMap<String, String>();

	/**
	 * Will load an image using the Slick-Util image library into openGL and return
	 * the OpenGL images ID as well as the images width and height
	 * 
	 * @param fileName
	 *            The location of the image to load, relative to the game engines
	 *            directory
	 * 
	 * @return An integer array of length three containing the images ID, width, and
	 *         height in that order
	 */
	public static int[] loadImage(String fileName) {
		String[] splitName = fileName.split("\\.");
		String ext = splitName[splitName.length - 1];

		Texture texture = null;

		try {
			texture = TextureLoader.getTexture(ext, new FileInputStream(fileName));
			// TODO: enable texture mipmapping when supported
		} catch (FileNotFoundException e) {
			// TODO: create error handler
			System.err.println("File not found!");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Error loading the texture file!");
			e.printStackTrace();
			System.exit(1);
		}

		int texID = texture.getTextureID();

		return new int[] { texID, (int) texture.getWidth(), (int) texture.getHeight() };
	}
	
	/**
	 * Will load any .glsl shader into the engine and return a String containing the shader code 
	 * @param fileName The file location of the .glsl shader file, all sub-directories after "./" must be specified
	 * @return
	 */
	public static String loadShader(String fileName) {
		if(shaders.containsKey(fileName))
			return shaders.get(fileName);
		
		StringBuilder source = new StringBuilder();
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader("./" + fileName));
			
			String line;
			while((line = reader.readLine()) != null) {
				source.append(line).append("\n");
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println("Could not find the shader file!");
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Error reading the shader file ./res/" + fileName);
			e.printStackTrace();
			System.exit(1);
		}
		
		shaders.put(fileName, source.toString());
		
		return source.toString();
	}
	
	public static void cleanUp() {
		shaders.clear();
	}
}
