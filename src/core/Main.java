package core;

import core.kernel.Root;

/**
 * <h1>Main Class</h1>
 * <p>
 * Main class of the graphics engine, responsible for initialisation calls to all engine systems
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-12
 */
public class Main {

	public static void main(String[] args) {
		
		Root game = new Root();
		game.init();
		game.launch();
		
	}
}