package rendering.buffers;

/**
 * <h1>VBO Class</h1>
 * <p>
 * In interface to specify the requirements for any VBO type
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public interface VBO {

	public void bind();
	
	public void unbind();
	
	public void delete();
	
	public int getId();
}
