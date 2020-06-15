package rendering.config;

/**
 * <h1>Default Class</h1>
 * <p>
 * This class is the default render config which does nothing when enabled or
 * disabled, relying on the engines default settings to render the associated
 * mesh
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-20
 */
public class Default implements RenderConfig{

	@Override
	public void enable() {
	}

	@Override
	public void disable() {
	}
}
