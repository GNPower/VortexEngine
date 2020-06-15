package core.structure;

import java.util.ArrayList;
import java.util.List;

import core.maths.Transform;

/**
 * <h1> Node Class </h1>
 * <p>
 * This class is a basic implementation of a tree based Node structure
 * in a 3D world, where each instance of a Node has a link to its parent,
 * a list of links to its children, as well as two transformations that 
 * represent their position in the world both globally and locally 
 * (i.e. in relation to its parent)
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-14
 */
public class Node {

	private Node parent;
	private List<Node> children;
	private Transform worldTransform, localTransform;
	
	public Node() {
		worldTransform = new Transform();
		localTransform = new Transform();
		children = new ArrayList<Node>();
	}
	
	/**
	 * adds the passed in node to the this Nodes list of children
	 * @param child the Node to be added to the list
	 */
	public void addChild(Node child) {
		child.setParent(this);
		children.add(child);
	}
	
	/**
	 * calls the input method for every child Node associated with it
	 */
	public void input() {
		for(Node child : children) {
			child.input();
		}
	}
	
	/**
	 * calls the update method for every child Node associated with it
	 */
	public void update() {
		for(Node child: children) {
			child.update();
		}
	}
	
	/**
	 * calls the render method for every child Node associated with it
	 */
	public void render() {
		for(Node child : children) {
			child.render();
		}
	}
	
	/**
	 * calls the shutdown method for every child Node associated with it
	 */
	public void shutdown() {
		for(Node child : children) {
			child.shutdown();
		}
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public Transform getWorldTransform() {
		return worldTransform;
	}

	public void setWorldTransform(Transform worldTransform) {
		this.worldTransform = worldTransform;
	}

	public Transform getLocalTransform() {
		return localTransform;
	}

	public void setLocalTransform(Transform localTransform) {
		this.localTransform = localTransform;
	}
}
