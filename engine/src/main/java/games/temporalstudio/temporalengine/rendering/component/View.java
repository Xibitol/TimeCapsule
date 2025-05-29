package games.temporalstudio.temporalengine.rendering.component;

import games.temporalstudio.temporalengine.window.Window;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import games.temporalstudio.temporalengine.Game;
import games.temporalstudio.temporalengine.LifeCycleContext;
import games.temporalstudio.temporalengine.component.Component;
import games.temporalstudio.temporalengine.component.GameObject;
import games.temporalstudio.temporalengine.physics.Transform;
import org.joml.Vector4f;

public class View implements Component{

	private static final float Z_NEAR = 0;
	private static final float Z_FAR = 100;
	private static final Vector3f CAMERA_FRONT = new Vector3f(0, 0, -1);
	private static final Vector3f CAMERA_UP = new Vector3f(0, 1, 0);

	public final float zNear;
	public final float zFar;
	private final Matrix4f projection = new Matrix4f();
	private final Matrix4f view = new Matrix4f();

	public View(float zNear, float zFar){
		this.zNear = zNear;
		this.zFar = zFar;
	}
	public View(){
		this(Z_NEAR, Z_FAR);
	}

	// GETTERS
	public Matrix4f getProjection(){ return new Matrix4f(projection); }
	public Matrix4f getView(){ return new Matrix4f(view); }

	public Vector2f screenToWorldCoord(Vector2f screenCoord) {
		// Normalize Screen Coordinates
		float x = (2.0f * screenCoord.x) / Window.getWidth() - 1.0f;
		float y = ((2.0f * screenCoord.y) / Window.getHeight() - 1.0f) * -1.0f; // Invert Y axis
		Vector4f ndcPos = new Vector4f(x, y, 0.0f, 1.0f); // z = 0 for 2D space
		// Uproject to World Space
		Matrix4f inverseVP = getProjection().mul(getView()).invert();
		Vector4f worldPos = new Vector4f();
		inverseVP.transform(ndcPos, worldPos);
		// Convert to 2D World Position
		return new Vector2f(worldPos.x, worldPos.y);
	}

	// LIFECYCLE FUNCTIONS
	@Override
	public void init(LifeCycleContext context){}
	@Override
	public void start(LifeCycleContext context){}
	@Override
	public void update(LifeCycleContext context, float delta){
		if(!(context instanceof GameObject go)){
			Game.LOGGER.severe(
				"View update method requires a GameObject context."
			);
			return;
		}
		
		Transform transform = new Transform();
		if(go.hasComponent(Transform.class))
			transform = go.getComponent(Transform.class);

		// Matrices
		projection.identity().ortho(
			0, transform.getScale().x(),
			0, transform.getScale().y(),
			zNear, zFar
		);

		view.identity().lookAt(
			new Vector3f(transform.getPosition(), 0),
			CAMERA_FRONT.add(new Vector3f(transform.getPosition(), 0)),
			CAMERA_UP
		);
	}
	@Override
	public void destroy(LifeCycleContext context){}
}
