package core.structure;

import core.maths.Transform;

/**
 * <h1>Component Class</h1>
 * <p>
 * This class represents a general component that can be
 * added to a GameObject to add some type of functionality 
 * to the GameObject that can occur in either the input, 
 * update, or render stages of the game engine execution.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-14
 */
public abstract class Component {

	private GameObject parent;
	
	public void input() {};
	
	public void update() {};
	
	public void render() {};

	public GameObject getParent() {
		return parent;
	}

	public void setParent(GameObject parent) {
		this.parent = parent;
	}
	 
	public Transform getWorldTransform() {
		return getParent().getWorldTransform();
	}
	
	public Transform getLocalTransform() {
		return getParent().getLocalTransform();
	}
}
