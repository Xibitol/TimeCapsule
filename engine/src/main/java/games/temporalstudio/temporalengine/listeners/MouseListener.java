package games.temporalstudio.temporalengine.listeners;

import games.temporalstudio.temporalengine.component.GameObject;
import games.temporalstudio.temporalengine.component.MouseActionable;
import games.temporalstudio.temporalengine.physics.Collider2D;
import games.temporalstudio.temporalengine.physics.Transform;
import games.temporalstudio.temporalengine.physics.shapes.AABB;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener extends GameObject {
	private static MouseListener instance;

	private double scrollX, scrollY;
	private double xPos, yPos, lastX, lastY;
	private boolean[] mouseButtonPressed = new boolean[3];
	private boolean isDragging;

	private Transform transform;
	private Collider2D collider2D;

	private MouseListener(){
        super("MouseListener");
        this.scrollX = 0;
		this.scrollY = 0;
		this.xPos = 0;
		this.yPos = 0;
		this.lastX = 0;
		this.lastY = 0;

		this.transform = new Transform(new Vector2f(Float.MIN_VALUE, Float.MIN_VALUE), new Vector2f(0.0f, 0.0f)); // TODO : This is a dummy transform, it will not be used for rendering.
		this.collider2D = new Collider2D(transform);
		this.collider2D.setShape(new AABB(transform));

		this.collider2D.setOnIntersects((context, other) -> {
			if (!(other instanceof GameObject gameObject)) { return; }
			if (!(gameObject.hasComponent(MouseActionable.class))) { return; }

			MouseActionable mouseActionable = gameObject.getComponent(MouseActionable.class);
			mouseActionable.setMouseOver(true);
		});
		this.collider2D.setOnSeparates((context, other) -> {
			if (!(other instanceof GameObject gameObject)) { return; }
			if (!(gameObject.hasComponent(MouseActionable.class))) { return; }

			MouseActionable mouseActionable = gameObject.getComponent(MouseActionable.class);
			mouseActionable.setMouseOver(false);
		});

		this.addComponent(transform);
		this.addComponent(collider2D);
	}

	public static MouseListener get(){
		if (MouseListener.instance == null) {
			MouseListener.instance = new MouseListener();
		}
		return MouseListener.instance;
	}

	public static void mousePosCallback(long window, double xpos, double ypos) {
		get().lastX = get().xPos;
		get().lastY = get().yPos;
		get().xPos = xpos;
		get().yPos = ypos;
		get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];

		get().transform.setPosition(new Vector2f((float) xpos, (float) ypos));
	}

	public static void mouseButtonCallback(long window, int button, int action, int mods){
		if (action == GLFW_PRESS){
			if (button < get().mouseButtonPressed.length) {
				get().mouseButtonPressed[button] = true;
			}
		} else if (action == GLFW_RELEASE) {
			if (button < get().mouseButtonPressed.length) {
				get().mouseButtonPressed[button] = false;
				get().isDragging = false;
			}
		}
	}

	public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
		get().scrollX = xOffset;
		get().scrollY = yOffset;
	}

	public static void endFrame() {
		get().scrollX = 0;
		get().scrollY = 0;
		get().lastX = get().xPos;
		get().lastY = get().yPos;
	}

	public static float getX() {
		return (float) get().xPos;
	}

	public static float getY() {
		return (float) get().yPos;
	}

	public static float getDx() {
		return (float) (get().lastX - get().xPos);
	}

	public static float getDy() {
		return (float) (get().lastY - get().yPos);
	}

	public static float getScrollX() {
		return (float) get().scrollX;
	}

	public static float getScrollY() {
		return (float) get().scrollY;
	}

	public static boolean isDragging() {
		return get().isDragging;
	}

	public static boolean mouseButtonDown(int button) {
		if (button < get().mouseButtonPressed.length) {
			return get().mouseButtonPressed[button];
		} else {
			return false;
		}
	}
}
