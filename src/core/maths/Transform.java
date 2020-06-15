package core.maths;

import core.kernel.Camera;
import core.maths.matrix.Matrix4f;
import core.maths.vector.Vector3f;

/**
 * <h1>Transform Class</h1>
 * <p>
 * This class holds the standard transformations for a GameObject
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class Transform {

	private Vector3f translation, rotation, scaling;

	/**
	 * creates a transform with all translation, rotation set to zero and scaling
	 * set to one
	 */
	public Transform() {
		translation = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		scaling = new Vector3f(0, 0, 0);
	}

	/**
	 * generates this Transforms Model Matrix (translation mat * scaling mat *
	 * rotation mat) and returns it
	 * 
	 * @return A new matrix that is the ModelMatrix of this transform
	 */
	public Matrix4f getModelMatrix() {
		return new Matrix4f().setTranslation(translation).mul(new Matrix4f().setScaling(scaling))
				.mul(new Matrix4f().setRotation(rotation));
	}

	public Matrix4f getMVPMatrix() {
		return Camera.getInstance().getViewProjectionMatrix().mul(getModelMatrix());
	}

	/**
	 * generates this Transforms Translation Matrix and returns it
	 * 
	 * @return A new matrix that is the Translation Matrix of this transform
	 */
	public Matrix4f getTranslationMatrix() {
		return new Matrix4f().setTranslation(translation);
	}

	/**
	 * generates this Transforms Rotation Matrix and returns it
	 * 
	 * @return A new matrix that is the Rotation Matrix of this transform
	 */
	public Matrix4f getRoatationMatrix() {
		return new Matrix4f().setRotation(rotation);
	}

	/**
	 * generates this Transforms Scaling Matrix and returns it
	 * 
	 * @return A new matrix that is the Scaling Matrix of this transform
	 */
	public Matrix4f getScalingMatrix() {
		return new Matrix4f().setScaling(scaling);
	}

	public Vector3f getTranslation() {
		return translation;
	}

	public void setTranslation(Vector3f translation) {
		this.translation = translation;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public Vector3f getScaling() {
		return scaling;
	}

	public void setScaling(Vector3f scaling) {
		this.scaling = scaling;
	}
}
