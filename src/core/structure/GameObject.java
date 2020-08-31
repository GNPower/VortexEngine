package core.structure;

import java.util.HashMap;

import core.utils.Constants;

/**
 * <h1>GameObject Class</h1>
 * <p>
 * This class represents a general object that will be used in the game engine.
 * Each GameObject is a type of Node and has a list of components that determine 
 * the objects functionality within the engine. Anything that physically exists in
 * the engine is a type of GameObject.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-14
 */
public class GameObject extends Node{

	private HashMap<Constants.RenderComponents, Component> components;
	
	public GameObject() {
		components = new HashMap<Constants.RenderComponents, Component>();
	}
	
	/**
	 * calls the input method of every component in the components list
	 * as well as the input method of the GamObjects Node superclass
	 */
	@Override
	public void input() {
		for(Constants.RenderComponents key : components.keySet()) {
			components.get(key).input();
		}
		super.input();
	}
	
	/**
	 * calls the update method of every component in the components list
	 * as well as the update method of the GamObjects Node superclass
	 */
	@Override
	public void update() {
		for(Constants.RenderComponents key : components.keySet()) {
			components.get(key).update();
		}
		super.update();
	}
	
	/**
	 * calls the render method of every component in the components list
	 * as well as the render method of the GamObjects Node superclass
	 */
	@Override
	public void render() {
		for(Constants.RenderComponents key : components.keySet()) {
			components.get(key).render();
		}
		super.render();
	}
	
	/**
	 * adds a component to the GameObjects component list
	 * @param name This is the RenderComponents enum that is the name of the component being added
	 * @param component This is the component to be added to the list
	 */
	public void addComponent(Constants.RenderComponents name, Component component) {
		component.setParent(this);
		components.put(name, component);
	}

	public HashMap<Constants.RenderComponents, Component> getComponents() {
		return components;
	}

	public void setComponents(HashMap<Constants.RenderComponents, Component> components) {
		this.components = components;
	}
}