package core.kernel;

import core.input.Input;
import core.input.Keys;
import core.maths.Quaternion;
import core.maths.matrix.Matrix4f;
import core.maths.vector.Vector3f;
import core.utils.Constants;
import core.window.Window;

/**
 * <h1>Camera Class</h1>
 * <p>
 * The camera class represents a virtual camera from which the scene will be rendered.
 * The camera is capable of moving itself based on user input, and generates and stores
 * the view and projection matrices to be used to render the scene.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class Camera {

	public static final Vector3f yAxis = new Vector3f(0,1,0);
	public static final Vector3f xAxis = new Vector3f(1,0,0);
	public static final Vector3f zAxis = new Vector3f(0,0,1);
	
	//engine can only have one camera at a time, so the class is a single instance class
	private static Camera instance = null;
	
	private Vector3f position, forward, up;
	private Vector3f previousPosition, previousForward;
	
	private float moveAmt = 0.1f, rotAmt = 0.8f, maxSpeed = 24.0f, maxRot = 24.0f;
	
	private Matrix4f viewMatrix, projectionMatrix, viewProjectionMatrix;
	private Matrix4f previousViewMatrix, previousViewProjectionMatrix;
	
	private boolean camMoved, camRotated;
	
	private float width, height, fovY;
	
	private float rotYstride, rotYamt, rotYcounter;
	private float rotXstride, rotXamt, rotXcounter;
	private boolean rotYinitiated = false, rotXinitiated = false;
	
	private float mouseSensitivity = 0.8f;
	
	private Quaternion[] frustumPlanes = new Quaternion[8];
	private Vector3f[] frustumCorners=  new Vector3f[8];
	
	/**
	 * returns the current instance of the camera class, or if none exists, it creates
	 * a new current instance before returning it
	 * @return the current instance of the camera class
	 */
	public static Camera getInstance() {
		if(instance == null)
			instance = new Camera();
		return instance;
	}
	
	/**
	 * creates a camera with default projection variables
	 */
	protected Camera() {
		setPosition(new Vector3f(0,0,0));
		setForward(zAxis);
		setUp(yAxis);
		
		setProjection(70, Window.getInstance().getWidth(), Window.getInstance().getHeight());
		
		setViewMatrix(new Matrix4f().setView(forward, up).mul(new Matrix4f().setTranslation(position.mul(-1))));
		
		setViewProjectionMatrix(new Matrix4f().setZero());
		
		previousViewMatrix = new Matrix4f().setZero();
		previousViewProjectionMatrix = new Matrix4f().setZero();
		
	}
	
	/**
	 * updates the cameras position an rotation based on user inputs, 
	 * and changes the view matrix accordingly
	 */
	public void update() {
		previousPosition = new Vector3f(position);
		previousForward = new Vector3f(forward);
		
		camMoved = false;
		camRotated = false;
		
		moveAmt += (0.04f * Input.getInstance().getScrollOffset());
		if(moveAmt < 0.2f)
			moveAmt = 0.2f;
		moveAmt = Math.min(0.02f, moveAmt);
		
		if(Input.getInstance().getKey(Keys.KEY_W))
			move(getForward(), moveAmt);
		if(Input.getInstance().getKey(Keys.KEY_S))
			move(getForward(), -moveAmt);
		if(Input.getInstance().getKey(Keys.KEY_A))
			move(getLeft(), moveAmt);
		if(Input.getInstance().getKey(Keys.KEY_D))
			move(getRight(), moveAmt);
		
		if(Input.getInstance().getKey(Keys.KEY_UP))
			rotateX(-rotAmt/8f);
		if(Input.getInstance().getKey(Keys.KEY_DOWN))
			rotateX(rotAmt/8f);
		if(Input.getInstance().getKey(Keys.KEY_LEFT))
			rotateY(-rotAmt/8f);
		if(Input.getInstance().getKey(Keys.KEY_RIGHT))
			rotateY(rotAmt/8f);
		
		if(Input.getInstance().getKey(Keys.KEY_E))
			move(up, moveAmt);
		if(Input.getInstance().getKey(Keys.KEY_Q))
			move(up, -moveAmt);

		if(!position.equals(previousPosition))
			camMoved = true;
		if(!forward.equals(previousForward))
			camRotated = true;
		
		previousViewMatrix = viewMatrix;
		previousViewProjectionMatrix = viewProjectionMatrix;
		
		setViewMatrix(new Matrix4f().setView(forward, up).mul(new Matrix4f().setTranslation(position.mul(-1))));
		setViewProjectionMatrix(projectionMatrix.mul(viewMatrix));
	}
	
	/**
	 * moves the camera to a new positions based on the vector addition
	 * newPosition = currentPosition + direction
	 * @param direction the vector to be added to the camera current position
	 * @param amount the magnitude of the displacement
	 */
	public void move(Vector3f direction, float amount) {
		setPosition(position.add(direction.mul(amount)));
	}
	
	/**
	 * rotates the camera around its Y axis by the angle specified
	 * @param angle the angle of rotation to displace the camera by
	 */
	public void rotateY(float angle) {
		Vector3f hAxis = yAxis.cross(forward).normalize();
		
		forward.rotate(angle, yAxis).normalize();
		
		up = forward.cross(hAxis).normalize();
	}
	
	/**
	 * rotates the camera around its X axis by the angle specified
	 * @param angle the angle of rotation to displace the camera by
	 */
	public void rotateX(float angle) {
		Vector3f hAxis = yAxis.cross(forward).normalize();
		
		forward.rotate(angle, hAxis).normalize();
		
		up = forward.cross(hAxis).normalize();
	}
	
	public Vector3f getRight() {
		return up.cross(forward).normalize();
	}
	
	public Vector3f getLeft() {
		return forward.cross(up).normalize();
	}
	
	/**
	 * sets the cameras projection matrix using the variables passed in
	 * @param fovY the field of view of the Y axis, expressed as angle from the Z axis
	 * @param width the width of the viewport being rendered to
	 * @param height the height of the viewport being rendered to
	 */
	public void setProjection(float fovY, float width, float height) {
		this.fovY = fovY;
		this.width = width;
		this.height = height;
		
		this.projectionMatrix = new Matrix4f().setPerspectiveProjection(fovY, width, height, Constants.ZNEAR, Constants.ZFAR);
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getForward() {
		return forward;
	}

	public void setForward(Vector3f forward) {
		this.forward = forward;
	}

	public Vector3f getUp() {
		return up;
	}

	public void setUp(Vector3f up) {
		this.up = up;
	}

	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}

	public void setViewMatrix(Matrix4f viewMatrix) {
		this.viewMatrix = viewMatrix;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public void setProjectionMatrix(Matrix4f projectionMatrix) {
		this.projectionMatrix = projectionMatrix;
	}

	public Matrix4f getViewProjectionMatrix() {
		return viewProjectionMatrix;
	}

	public void setViewProjectionMatrix(Matrix4f viewProjectionMatrix) {
		this.viewProjectionMatrix = viewProjectionMatrix;
	}

	public boolean isCamMoved() {
		return camMoved;
	}

	public void setCamMoved(boolean camMoved) {
		this.camMoved = camMoved;
	}

	public boolean isCamRotated() {
		return camRotated;
	}

	public void setCamRotated(boolean camRotated) {
		this.camRotated = camRotated;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getFovY() {
		return fovY;
	}

	public void setFovY(float fovY) {
		this.fovY = fovY;
	}

	public Quaternion[] getFrustumPlanes() {
		return frustumPlanes;
	}

	public void setFrustumPlanes(Quaternion[] frustumPlanes) {
		this.frustumPlanes = frustumPlanes;
	}

	public Vector3f[] getFrustumCorners() {
		return frustumCorners;
	}

	public void setFrustumCorners(Vector3f[] frustumCorners) {
		this.frustumCorners = frustumCorners;
	}

	public Vector3f getPreviousPosition() {
		return previousPosition;
	}

	public Vector3f getPreviousForward() {
		return previousForward;
	}

	public Matrix4f getPreviousViewMatrix() {
		return previousViewMatrix;
	}

	public Matrix4f getPreviousViewProjectionMatrix() {
		return previousViewProjectionMatrix;
	}	
}
