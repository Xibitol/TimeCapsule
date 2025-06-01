package games.temporalstudio.timecapsule.test;

import static org.lwjgl.glfw.GLFW.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import games.temporalstudio.timecapsule.Entity.Enemy;
import org.joml.Vector2f;
import org.joml.Vector4f;

import games.temporalstudio.temporalengine.Game;
import games.temporalstudio.temporalengine.Scene;
import games.temporalstudio.temporalengine.component.GameObject;
import games.temporalstudio.temporalengine.component.Input;
import games.temporalstudio.temporalengine.component.Trigger;
import games.temporalstudio.temporalengine.component.Triggerable;
import games.temporalstudio.temporalengine.physics.Collider2D;
import games.temporalstudio.temporalengine.physics.PhysicsBody;
import games.temporalstudio.temporalengine.physics.Transform;
import games.temporalstudio.temporalengine.physics.shapes.AABB;
import games.temporalstudio.temporalengine.rendering.component.ColorRender;
import games.temporalstudio.temporalengine.rendering.component.Render;
import games.temporalstudio.temporalengine.rendering.component.View;

public class TestGame extends Game{

	private static final String IDENTIFIER = "temporalengine";

	public TestGame(){
		super(null, null);

		setTitle(getI18n().getSentence("game.title", getVersion()));

		setMainMenu(createMainMenu());
		setFirstLeftScene(this.createPastScenes());
		setFirstRightScene(this.createFutureScenes());
	}

	// GETTERS
	@Override
	public String getIdentifier(){ return IDENTIFIER; }

	// FUNCTIONS
	private Scene createMainMenu(){
		Scene mainMenu = new Scene("MainMenu");

		GameObject camera = new GameObject("camera");
		camera.addComponent(new Transform());
		camera.addComponent(new View(.1f));
		mainMenu.addGameObject(camera);

		return mainMenu;
	}
	private Scene createPastScenes(){
		Scene past = new Scene("Past");

		// Game objects
		GameObject camera = new GameObject("PastCamera");

		GameObject player = createPlayer(new int[]{
			GLFW_KEY_W, GLFW_KEY_S, GLFW_KEY_A, GLFW_KEY_D,
			GLFW_KEY_Q
		});
		GameObject rulietta = new GameObject("Rulietta");
		GameObject compulsiveMerger = new GameObject("Adrien");
		Vector2f[] coords={
				new Vector2f(1,5),
				new Vector2f(1,3),
				new Vector2f(3,2),
				new Vector2f(4,2),
		};
		Enemy chauvesouris= new Enemy("Dracula", new Vector2f(1, 2), new Vector2f(1,1),
				new Vector4f(0, .50f, .50f, 1),coords );

		// Components
		camera.addComponent(new Transform());
		camera.addComponent(new View(.1f));

		rulietta.addComponent(new Transform(
		 	new Vector2f(1, 2), new Vector2f(.85f, .85f)
		));
		rulietta.addComponent(new ColorRender(
			new Vector4f(1, 0, 1, 1)
		));

		Vector4f lowPurple = new Vector4f(64f/255, 0, 1, 1);
		Vector4f highPurple = new Vector4f(192f/255, 0, 1, 1);
		compulsiveMerger.addComponent(new Transform(
			new Vector2f(1, 3), new Vector2f(.5f, .5f)
		));
		compulsiveMerger.addComponent(new ColorRender(List.of(
			lowPurple, lowPurple, lowPurple, highPurple
		)));

		// Scene
		past.addGameObject(camera);
		past.addGameObject(player);
		past.addGameObject(compulsiveMerger);
		past.addGameObject(rulietta);
		past.addGameObject(chauvesouris);

		return past;
	}
	private Scene createFutureScenes(){
		Scene future = new Scene("Future");

		GameObject camera = new GameObject("camera");
		camera.addComponent(new Transform());
		camera.addComponent(new View(.1f));

		GameObject button = createButton();
		GameObject player = createPlayer(new int[]{
			GLFW_KEY_UP, GLFW_KEY_DOWN, GLFW_KEY_LEFT, GLFW_KEY_RIGHT,
			GLFW_KEY_SLASH
		});
		GameObject door = createDoor(button);
		GameObject rock = createBreakableRock(GLFW_KEY_SLASH, future);

		future.addGameObject(camera);
		future.addGameObject(player);
		future.addGameObject(button);
		future.addGameObject(door);
		future.addGameObject(rock);

		return future;
	}

	private GameObject createButton(){
		GameObject button = new GameObject("button");

		Render render = new ColorRender(new Vector4f(0, 1, 0, 1));
		Transform transform = new Transform(new Vector2f(1f, .5f));

		AtomicBoolean triggerActivated = new AtomicBoolean(false);
		Trigger trigger = new Trigger(1 , triggerActivated::get);

		Collider2D collider2D = new Collider2D(transform);
		collider2D.setShape(new AABB(transform));
		collider2D.setOnIntersects(
			(context, other) -> triggerActivated.set(true)
		);

		button.addComponent(transform);
		button.addComponent(render);
		button.addComponent(collider2D);
		button.addComponent(trigger);

		return button;
	}
	private GameObject createPlayer(int[] keys){
		GameObject player = new GameObject("player");

		Render render = new ColorRender(new Vector4f(0, 0, 1, 1));
		Transform transform = new Transform(new Vector2f(5, 2));
		PhysicsBody physicsBody = new PhysicsBody(
			1, 1, .1f, 1
		);
		Collider2D collider2D = new Collider2D(transform);
		collider2D.setShape(new AABB(transform));

		Input input = new Input();
		input.addControl(keys[0], (context) -> {
			physicsBody.applyForce(new Vector2f(0, 100));
		});
		input.addControl(keys[1], (context) -> {
			physicsBody.applyForce(new Vector2f(0, -100));
		});
		input.addControl(keys[2], (context) -> {
			physicsBody.applyForce(new Vector2f(-100, 0));
		});
		input.addControl(keys[3], (context) -> {
			physicsBody.applyForce(new Vector2f(100, 0));
		});
		input.addControl(keys[4], (context) -> {});

		player.addComponent(transform);
		player.addComponent(render);
		player.addComponent(physicsBody);
		player.addComponent(input);
		player.addComponent(collider2D);

		return player;
	}
	private GameObject createDoor(GameObject button){
		GameObject door = new GameObject("door");

		Render render = new ColorRender(new Vector4f(1, 0, 0, 1));
		Transform transform = new Transform(new Vector2f(1, 2));

		Collider2D collider2D = new Collider2D(transform);
		collider2D.setShape(new AABB(transform));
		collider2D.setRigid(true);

		Trigger trigger = button.getComponent(Trigger.class);
		var ref = new Object() {
			Triggerable triggerable = null;
		};
		ref.triggerable = new Triggerable(context -> {
			if(context instanceof GameObject buttonObject){
				trigger.removeTriggerable(ref.triggerable);
				button.removeComponent(trigger);
				buttonObject.removeComponent(collider2D);
			}else
				Game.LOGGER.warning(
					"Door trigger action executed with non-GameObject context."
				);
		});
		trigger.addTriggerable(ref.triggerable);

		door.addComponent(transform);
		door.addComponent(render);
		door.addComponent(collider2D);
		door.addComponent(ref.triggerable);

		return door;
	}
	private GameObject createBreakableRock(int key, Scene scene){
		GameObject rock = new GameObject("rock");

		Render render = new ColorRender(
			new Vector4f(.5f, .25f, .1f, 1)
		);
		Transform transform = new Transform(new Vector2f(5, .5f));
		Collider2D collider2D = new Collider2D(transform);
		collider2D.setShape(new AABB(transform));
		collider2D.setRigid(true);
		collider2D.setOnCollide((context, other) -> {
					if (context instanceof GameObject rockObject
							&& other instanceof GameObject player
							&& player.getName().equals("player")
							&& player.getComponent(Input.class).isControlPressed(key)
					) {
						Game.LOGGER.info("Rock broken by player!");
						scene.removeGameObject(rockObject);
						rockObject.destroy(scene);
					}
		});


		rock.addComponent(transform);
		rock.addComponent(render);
		rock.addComponent(collider2D);

		return rock;
	}
}