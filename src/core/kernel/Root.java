package core.kernel;

/***
 * <h1>Root Class</h1>
 * <p>
 * This class is the source of all game engine and associated components,
 * everything is initiated and run from this class
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-15
 */
public class Root {

	protected CoreEngine engine;
	
	public Root() {
		engine = new CoreEngine();
	}
	
	public void init() {
		engine.init();
	}
	
	public void launch() {
		engine.start();
	}

	public CoreEngine getEngine() {
		return engine;
	}

	public void setEngine(CoreEngine engine) {
		this.engine = engine;
	}
}
