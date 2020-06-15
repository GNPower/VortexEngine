package rendering;

import java.util.HashMap;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL43;

import core.maths.Quaternion;
import core.maths.matrix.Matrix4f;
import core.maths.vector.Vector2f;
import core.maths.vector.Vector3f;
import core.structure.GameObject;
import core.utils.BufferUtil;

/**
 * <h1>Shader Class</h1>
 * <p>
 * Represents a generic shader, containing everything the shader will need to
 * add and manipulate different shader programs and uniforms.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-24
 */
public class Shader {

	private int programID;
	private HashMap<String, Integer> uniforms;
	
	/**
	 * Creates an empty shader and assigns it an OpenGL shader ID
	 */
	public Shader() {
		programID = GL20.glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		
		if(programID == 0) {
			System.err.println("could not create shader");
			System.exit(1);
		}
	}
	
	/**
	 * binds this shader as the active shader, replacing any previously active shaders.
	 * Any GPU render calls will now use this shader for rendering
	 */
	public void bind() {
		GL20.glUseProgram(programID);
	}
	
	/**
	 * Adds a uniform to the shader. Once added this uniform will be expected to be assigned
	 * a value and passed to the GPU before a rendering call is made.
	 * 
	 * @param uniform The name of the uniform to be added to the shader program
	 */
	public void addUniform(String uniform) {
		int uniformLocation = GL20.glGetUniformLocation(programID, uniform);
		
		if(uniformLocation == 0xFFFFFFFF) {
			System.err.println(this.getClass().getName() + " Error: Could not find the uniform: " + uniform);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		uniforms.put(uniform, uniformLocation);
	}
	
	/**
	 * Adds a uniform block to the shader. Once added this uniform block will be expected
	 * to be assigned a value and passed to the GPU before a rendering call is made.
	 * 
	 * @param uniform The name of the uniform block to be added to the shader program
	 */
	public void addUniformBlock(String uniform) {
		int uniformLocation = GL31.glGetUniformBlockIndex(programID, uniform);
		
		if(uniformLocation == 0xFFFFFFFF) {
			System.err.println(this.getClass().getName() + " Error: Could not find the uniform: " + uniform);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		uniforms.put(uniform, uniformLocation);
	}
	
	/**
	 * Adds a vertex shader to the list of glsl shader scripts contained within this shader program
	 * 
	 * @param text A String of the loaded shader code
	 */
	public void addVertexShader(String text) {
		addProgram(text, GL20.GL_VERTEX_SHADER);
	}
	
	/**
	 * Adds a geometry shader to the list of glsl shader scripts contained within this shader program
	 * 
	 * @param text A String of the loaded shader code
	 */
	public void addGeometryShader(String text) {
		addProgram(text, GL32.GL_GEOMETRY_SHADER);
	}
	
	/**
	 * Adds a fragment shader to the list of glsl shader scripts contained within this shader program
	 * 
	 * @param text A String of the loaded shader code
	 */
	public void addFragmentShader(String text) {
		addProgram(text, GL20.GL_FRAGMENT_SHADER);
	}
	
	/**
	 * Adds a tessellation control shader to the list of glsl shader scripts contained within this shader program
	 * 
	 * @param text A String of the loaded shader code
	 */
	public void addTessellationControlShader(String text) {
		addProgram(text, GL40.GL_TESS_CONTROL_SHADER);
	}
	
	/**
	 * Adds a tessellation evaluation shader to the list of glsl shader scripts contained within this shader program
	 * 
	 * @param text A String of the loaded shader code
	 */
	public void addTessellationEvaluationShader(String text) {
		addProgram(text, GL40.GL_TESS_EVALUATION_SHADER);
	}
	
	/**
	 * Adds a compute shader to the list of glsl shader scripts contained within this shader program
	 * 
	 * @param text A String of the loaded shader code
	 */
	public void addComputeShader(String text) {
		addProgram(text, GL43.GL_COMPUTE_SHADER);
	}
	
	/**
	 * links the program to the GPU then compiles it, ensuring both the linking and compilation are successful
	 */
	public void compileShader() {
		GL20.glLinkProgram(programID);
		
		if(GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == 0) {
			System.err.println(this.getClass().getName() + " " + GL20.glGetProgramInfoLog(programID, 1024));
			System.exit(1);
		}
		
		GL20.glValidateProgram(programID);
		
		if(GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == 0) {
			System.err.println(this.getClass().getName() + " " + GL20.glGetProgramInfoLog(programID, 1024));
			System.exit(1);
		}
	}
	
	/**
	 * Adds a shader script to the list of scripts contained within this shader program
	 * 
	 * @param text A String of the loaded shader code
	 * @param type The OpenGL Shader identification constant that specifies what type of shader script is being loaded
	 */
	private void addProgram(String text, int type) {
		int shader = GL20.glCreateShader(type);
		
		if(shader == 0) {
			System.err.println(this.getClass().getName() + " Shader creation failed");
			System.exit(1);
		}
		
		GL20.glShaderSource(shader, text);
		GL20.glCompileShader(shader);
		
		if(GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == 0) {
			System.err.println(this.getClass().getName() + " " + GL20.glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		GL20.glAttachShader(programID, shader);
	}
	
	/**
	 * Sets a predefined shader uniform to an integer value
	 * 
	 * @param uniformName The name of the predefined shader uniform
	 * @param value The value to store in the uniform
	 */
	public void setUniformi(String uniformName, int value) {
		GL20.glUniform1i(uniforms.get(uniformName), value);
	}
	
	/**
	 * Sets a predefined shader uniform to a float value
	 * 
	 * @param uniformName The name of the predefined shader uniform
	 * @param value The value to store in the uniform
	 */
	public void setUniformf(String uniformName, float value) {
		GL20.glUniform1f(uniforms.get(uniformName), value);
	}
	
	/**
	 * Sets a predefined shader uniform to a Vector2f value
	 * 
	 * @param uniformName The name of the predefined shader uniform
	 * @param value The value to store in the uniform
	 */
	public void setUniform(String uniformName, Vector2f value) {
		GL20.glUniform2f(uniforms.get(uniformName), value.getX(), value.getY());
	}
	
	/**
	 * Sets a predefined shader uniform to a Vector3f value
	 * 
	 * @param uniformName The name of the predefined shader uniform
	 * @param value The value to store in the uniform
	 */
	public void setUniform(String uniformName, Vector3f value) {
		GL20.glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	/**
	 * Sets a predefined shader uniform to a Quaternion value
	 * 
	 * @param uniformName The name of the predefined shader uniform
	 * @param value The value to store in the uniform
	 */
	public void setUniform(String uniformName, Quaternion value) {
		GL20.glUniform4f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ(), value.getW());
	}
	
	/**
	 * Sets a predefined shader uniform to a Matrix4f value
	 * 
	 * @param uniformName The name of the predefined shader uniform
	 * @param value The value to store in the uniform
	 */
	public void setUniform(String uniformName, Matrix4f value) {
		GL20.glUniformMatrix4(uniforms.get(uniformName), true, BufferUtil.createFlippedBuffer(value));
	}
	
	/**
	 * Attaches a predefined global uniform block to a predefined program specific uniform block
	 * 
	 * @param uniformBlockName The name of the predefined program specific uniform block
	 * @param uniformBlockBinding The integer ID of the global uniform block to bind to the local shader programs uniform block
	 */
	public void bindUniformBlock(String uniformBlockName, int uniformBlockBinding) {
		GL31.glUniformBlockBinding(programID, uniforms.get(uniformBlockName), uniformBlockBinding);
	}
	
	/**
	 * Binds a user defined out variable name to a fragment shader colour number
	 * 
	 * @param name The name of the user defined out variable
	 * @param index The colour number to bind to (of type GLuint)
	 */
	public void bindFragDataLocation(String name, int index) {
		GL30.glBindFragDataLocation(programID, index, name);
	}
	
	public int getProgramID() {
		return programID;
	}
	
	/**
	 * A required method for all specific shader instances. Provides the shader with the location
	 * of the object from which all required object rendering data can be found
	 * 
	 * @param object The GameObject from which all rendering data can be found
	 */
	public void updateUniforms(GameObject object) {};
}
