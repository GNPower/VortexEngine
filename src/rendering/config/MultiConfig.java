package rendering.config;

import java.util.ArrayList;

/**
 * <h1>MultiConfig Class</h1>
 * <p>
 * Render configuration container capable of holding and using multiple
 * render configurations in the place of one for objects that need more
 * than one special rendering configuration
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class MultiConfig implements RenderConfig{
	
	private ArrayList<RenderConfig> configs;
	
	/**
	 * Creates the render configuration with all the passed in configurations
	 * 
	 * @param configs A list of arbitrary size containing all the desired render configurations
	 */
	public MultiConfig(RenderConfig... configs) {
		this.configs = new ArrayList<RenderConfig>();
		for(RenderConfig config : configs) {
			this.configs.add(config);
		}
	}

	@Override
	public void enable() {
		for(RenderConfig config : configs) {
			config.enable();
		}
	}

	@Override
	public void disable() {
		for(RenderConfig config : configs) {
			config.disable();
		}
	}
}
