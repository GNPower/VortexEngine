package core.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import core.maths.vector.Vector2f;

/**
 * <h1>Input Class</h1>
 * <p>
 * This class stores the various forms of input from both the keyboard and the
 * mouse, supporting the standard English US Keyboard and any mouse with 12
 * buttons or less.
 * 
 * @author Graham
 * @version 2.0.0
 * @since 2019-05-16
 */
public class Input {

	public static final int MAX_KEYCODES = 256;
	public static final int MAX_MOUSECODES = 8;

	/*
	 * instanced because each device can only have one keyboard without key
	 * conflicts so the creation of multiple instances of this class would be
	 * pointless and only waste space
	 */
	private static Input instance = null;

	private boolean[] currentKeys = new boolean[MAX_KEYCODES];
	private boolean[] pressedKeys = new boolean[MAX_KEYCODES];
	private boolean[] releasedKeys = new boolean[MAX_KEYCODES];

	private boolean[] currentButtons = new boolean[MAX_MOUSECODES];
	private boolean[] pressedButtons = new boolean[MAX_MOUSECODES];
	private boolean[] releasedButtons = new boolean[MAX_MOUSECODES];

	private Vector2f cursorPosition;
	private Vector2f lockedCursorPosition;

	private float scrollOffset;

	/**
	 * Returns the instance of the Input class and creates one if one did not
	 * previously exist
	 * 
	 * @return A working instance of this class
	 */
	public static Input getInstance() {
		if (instance == null)
			instance = new Input();
		return instance;
	}

	// adds the mouse and keyboard to the OpenGL context
	protected Input() {
		cursorPosition = new Vector2f();
		lockedCursorPosition = new Vector2f(Display.getWidth() / 2f, Display.getHeight() / 2f);

		try {
			Keyboard.create();
		} catch (LWJGLException e) {
			System.err.println("Could not initialize keyboard interfacing");
			e.printStackTrace();
			System.exit(1);
		}

		try {
			Mouse.create();
		} catch (LWJGLException e) {
			System.err.println("Could not initialize mosue interfacing");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Updates the lists of all pressed, released, and held buttons for both the
	 * keyboard and mouse as well as updating the scroll wheel offset
	 */
	public void update() {

		for (int i = 0; i < releasedKeys.length; i++) {
			releasedKeys[i] = false;
			if (!key(i) && currentKeys[i])
				releasedKeys[i] = true;
		}

		for (int i = 0; i < pressedKeys.length; i++) {
			pressedKeys[i] = false;
			if (key(i) && !currentKeys[i])
				pressedKeys[i] = true;
		}

		for (int i = 0; i < currentKeys.length; i++) {
			currentKeys[i] = false;
			if (key(i))
				currentKeys[i] = true;
		}

		for (int i = 0; i < releasedButtons.length; i++) {
			releasedButtons[i] = false;
			if (!mouse(i) && currentButtons[i])
				releasedButtons[i] = true;
		}

		for (int i = 0; i < pressedButtons.length; i++) {
			pressedButtons[i] = false;
			if (mouse(i) && !currentButtons[i])
				pressedButtons[i] = true;
		}
		
		//TODO: loop for current buttons

		scrollOffset = Mouse.getDWheel();
	}

	private boolean key(int keyCode) {
		return Keyboard.isKeyDown(keyCode);
	}

	private boolean mouse(int keyCode) {
		return Mouse.isButtonDown(keyCode);
	}

	/**
	 * Checks if the key corresponding to the passed in key code is currently
	 * pressed
	 * 
	 * @param keyCode
	 *            The ID of the key to check, can be statically accessed by key name
	 *            from the Keys class
	 * 
	 * @return True if the key is pressed, False otherwise
	 */
	public boolean getKey(int keyCode) {
		return currentKeys[keyCode];
	}

	/**
	 * Checks if the key corresponding to the passed in key code was pressed after
	 * the last call to the update method (i.e. has just been pressed down)
	 * 
	 * @param keyCode
	 *            The ID of the key to check, can be statically accessed by key name
	 *            from the Keys class
	 * 
	 * @return True if the key was just pressed, False otherwise
	 */
	public boolean isKeyPushed(int keyCode) {
		return pressedKeys[keyCode];
	}

	/**
	 * Checks if the key corresponding to the passed in key code was released after
	 * the last call to the update method (i.e. was pressed, has just been released)
	 * 
	 * @param keyCode
	 *            The ID of the key to check, can be statically accessed by key name
	 *            from the Keys class
	 * 
	 * @return True if the key was just released, False otherwise
	 */
	public boolean isKeyReleased(int keyCode) {
		return releasedKeys[keyCode];
	}

	/**
	 * Checks if the button corresponding to the passed in mouse button code is
	 * currently pressed
	 * 
	 * @param keyCode
	 *            The ID of the mouse button to check, can be statically accessed by
	 *            key name from the Keys class
	 * 
	 * @return True if the button is pressed, False otherwise
	 */
	public boolean getButton(int mouseButton) {
		return currentButtons[mouseButton];
	}

	/**
	 * Checks if the button corresponding to the passed in mouse button code was
	 * pressed after the last call to the update method (i.e. has just been pressed)
	 * 
	 * @param keyCode
	 *            The ID of the mouse button to check, can be statically accessed by
	 *            key name from the Keys class
	 * 
	 * @return True if the button was just pressed, False otherwise
	 */
	public boolean isButtonPushed(int mouseButton) {
		return pressedButtons[mouseButton];
	}

	/**
	 * Checks if the button corresponding to the passed in mouse button code was
	 * released after the last call to the update method (i.e. was pressed, has just
	 * been released)
	 * 
	 * @param keyCode
	 *            The ID of the mouse button to check, can be statically accessed by
	 *            key name from the Keys class
	 * 
	 * @return True if the button was just released, False otherwise
	 */
	public boolean isButtonReleased(int mouseButton) {
		return releasedButtons[mouseButton];
	}

	/**
	 * Grabs the mouse, thus making the mouse invisible
	 */
	public void enableCursor() {
		Mouse.setGrabbed(true);
	}

	/**
	 * Released the mouse, thus making the mouse visible again
	 */
	public void disableCursor() {
		Mouse.setGrabbed(false);
	}

	/**
	 * Sets the position of the mouse cursor relative to the OpenGL window origin
	 * 
	 * @param position
	 *            The vector representing the x,y position of the cursor
	 */
	public void setCursorPosition(Vector2f position) {
		this.cursorPosition = position;
		Mouse.setCursorPosition((int) position.getX(), (int) position.getY());
	}

	public Vector2f getLockedCursorPosition() {
		return lockedCursorPosition;
	}

	public void setLockedCursorPosition(Vector2f lockedCursorPosition) {
		this.lockedCursorPosition = lockedCursorPosition;
	}

	public void setLockedCursorPosition(int x, int y) {
		this.lockedCursorPosition.setX(x);
		this.lockedCursorPosition.setY(y);
	}

	/**
	 * Get the list of all keys that are currently pressed
	 * 
	 * @return A boolean array where each index is the key code of the key the
	 *         stored boolean is representing
	 */
	public boolean[] getCurrentKeys() {
		return currentKeys;
	}

	/**
	 * Get the list of all keys that were just pressed
	 * 
	 * @return A boolean array where each index is the key code of the key the
	 *         stored boolean is representing
	 */
	public boolean[] getPressedKeys() {
		return pressedKeys;
	}

	/**
	 * Get the list of all keys that were just released
	 * 
	 * @return A boolean array where each index is the key code of the key the
	 *         stored boolean is representing
	 */
	public boolean[] getReleasedKeys() {
		return releasedKeys;
	}

	/**
	 * Get the list of all mouse buttons that are currently pressed
	 * 
	 * @return A boolean array where each index is the mouse button code of the
	 *         button the stored boolean is representing
	 */
	public boolean[] getCurrentButtons() {
		return currentButtons;
	}

	/**
	 * Get the list of all mouse buttons that were just pressed
	 * 
	 * @return A boolean array where each index is the mouse button code of the
	 *         button the stored boolean is representing
	 */
	public boolean[] getPressedButtons() {
		return pressedButtons;
	}

	/**
	 * Get the list of all mouse buttons that were just released
	 * 
	 * @return A boolean array where each index is the mouse button code of the
	 *         button the stored boolean is representing
	 */
	public boolean[] getReleasedButtons() {
		return releasedButtons;
	}

	/**
	 * Gets the position of the mouse cursor relative to the OpenGL window origin
	 * 
	 * @return The vector representing the x,y position of the cursor
	 */
	public Vector2f getCursorPosition() {
		return cursorPosition;
	}

	/**
	 * Gets the offset of the mouse scroll wheel
	 * 
	 * @return A float representing the position of the mouse scroll wheel
	 */
	public float getScrollOffset() {
		return scrollOffset;
	}

	/**
	 * Sets the scroll offset of the mouse wheel
	 * 
	 * @param scrollOffset
	 *            A float representing the position of the mouse scroll wheel
	 */
	public void setScrollOffset(float scrollOffset) {
		this.scrollOffset = scrollOffset;
	}

	/**
	 * Removes both the keyboard and the mouse from the OpenGL context, clearing
	 * them from memory
	 */
	public static void cleanUp() {
		Keyboard.destroy();
		Mouse.destroy();
	}
}
