package games.temporalstudio.timecapsule.levels;

import games.temporalstudio.temporalengine.Scene;
import games.temporalstudio.temporalengine.component.GameObject;
import games.temporalstudio.temporalengine.Game;
import games.temporalstudio.temporalengine.rendering.component.MapRender;
import games.temporalstudio.timecapsule.Entity.Medusa;
import games.temporalstudio.timecapsule.Entity.Player;
import games.temporalstudio.timecapsule.objects.*;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.Set;

public class Zone1_lvl1 implements TimeLevel{
	private Scene pastScene;
	private Scene futurScene;
	private Set<TimeObject> pastTimeObjects;
	private Set<TimeObject> futurTimeObjects;
	private Set<TimeObject> commons;

	public Zone1_lvl1(
			GameObject pastCamera, GameObject futurCamera,
			Game game, Player pastPlayer, Player futurPlayer, Medusa pastMedusa, Medusa futurMedusa,
			CapsuleReceiver zone1_pastCapsuleReceiver
	) {
		this.pastScene = new Scene("Zone1_lvl1_Past");
		this.futurScene = new Scene("Zone1_lvl1_Futur");
		this.futurScene.addGameObject(futurCamera);
		this.pastScene.addGameObject(pastCamera);

		pastPlayer.getTransform().setPosition(new Vector2f(16, 5));
		futurPlayer.getTransform().setPosition(new Vector2f(16, 5));

		commons = Set.of(
				new Wall(new Vector2f(0, 3), new Vector2f(32, 3)), // Bottom wall
				new Wall(new Vector2f(0.3f, 0), new Vector2f(0.3f, 32)), // Left wall
				new Wall(new Vector2f(31.7f, 0), new Vector2f(31.7f, 32)), // Right wall
				new Wall(new Vector2f(0, 30), new Vector2f(7.8f, 30)), // Top wall part 1
				new Wall(new Vector2f(9.2f, 30), new Vector2f(32, 30)),  // Top wall part 2

				// Water
				new Wall(new Vector2f(0, 17.8f), new Vector2f(1.2f, 17.8f)),
				new Wall(new Vector2f(1.2f, 17.8f), new Vector2f(1.2f, 18.8f)),
				new Wall(new Vector2f(1.2f, 18.8f), new Vector2f(5.2f, 18.8f)),
				new Wall(new Vector2f(5.2f, 18.8f), new Vector2f(5.2f, 19.8f)),

				new Wall(new Vector2f(5.2f, 19.8f), new Vector2f(6.2f, 19.8f)),

				new Wall(new Vector2f(8.2f, 19.8f), new Vector2f(9.8f, 19.8f)),

				new Wall(new Vector2f(9.8f, 19.8f), new Vector2f(9.8f, 18.8f)),
				new Wall(new Vector2f(9.8f, 18.8f), new Vector2f(11.8f, 18.8f)),
				new Wall(new Vector2f(11.8f, 18.8f), new Vector2f(11.8f, 17.8f)),
				new Wall(new Vector2f(11.8f, 17.8f), new Vector2f(12.8f, 17.8f)),
				new Wall(new Vector2f(12.8f, 17.8f), new Vector2f(12.8f, 14.8f)),
				new Wall(new Vector2f(8.8f, 15.2f), new Vector2f(14.8f, 15.2f)),
				new Wall(new Vector2f(12.8f, 15.2f), new Vector2f(12.8f, 16.8f)),
				new Wall(new Vector2f(15.2f, 15.8f), new Vector2f(15.2f, 18.2f)),
				new Wall(new Vector2f(14.2f, 15.2f), new Vector2f(14.2f, 26.8f)),
				new Wall(new Vector2f(14.2f, 16.8f), new Vector2f(14.8f, 16.8f)),
				new Wall(new Vector2f(15.5f, 26.8f), new Vector2f(15.6f, 13.8f)),

				// Approx collisions

				new Wall(new Vector2f(16.8f, 15.8f), new Vector2f(16.8f, 26.8f)),

				new Wall(new Vector2f(16.8f, 15.8f), new Vector2f(18.8f, 15.8f)),
				new Wall(new Vector2f(18.8f, 16.8f), new Vector2f(18.8f, 14.8f)),

				new Wall(new Vector2f(18.8f, 14.8f), new Vector2f(26.8f, 14.8f)),
				new Wall(new Vector2f(23.8f, 14.8f), new Vector2f(23.8f, 12.8f)),
				new Wall(new Vector2f(23.8f, 12.8f), new Vector2f(28.8f, 12.8f)),
				new Wall(new Vector2f(26.8f, 12.8f), new Vector2f(26.8f, 20.8f)),
				new Wall(new Vector2f(26.8f, 18.8f), new Vector2f(32, 18.8f)),

				new Wall(new Vector2f(5.2f, 21.2f), new Vector2f(5.2f, 27.2f)),
				new Wall(new Vector2f(5.2f, 21.2f), new Vector2f(6.2f, 21.2f)),

				new Wall(new Vector2f(8.2f, 21.2f), new Vector2f(8.2f, 21.8f)),
				new Wall(new Vector2f(8.8f, 21.2f), new Vector2f(8.8f, 22.2f)),
				new Wall(new Vector2f(8.8f, 22.2f), new Vector2f(9.6f, 22.2f)),
				new Wall(new Vector2f(9.6f, 22.2f), new Vector2f(9.6f, 27.2f)),

				new Wall(new Vector2f(0, 27.2f), new Vector2f(5.2f, 27.2f)),
				new Wall(new Vector2f(9.6f, 27.2f), new Vector2f(13.8f, 27.2f)),
				new Wall(new Vector2f(16.8f, 27.2f), new Vector2f(32, 27.2f))
		);

		commons.forEach((timeObject) -> this.pastScene.addGameObject(timeObject.getGameObject()));
		commons.forEach((timeObject) -> this.futurScene.addGameObject(timeObject.getGameObject()));

		GameObject pastMap = new GameObject("Past Map");
		pastMap.addComponent(new MapRender("past", "zone1_lvl1_past"));
		this.pastScene.addGameObject(pastMap);

		ThrowableBottle throwableBottle = new ThrowableBottle("ThrowableBottle", pastPlayer);

		pastTimeObjects = Set.of(
				new Wall(new Vector2f(6.2f, 21.2f), new Vector2f(8.2f, 21.2f)), // Block root passage
				new Wall(new Vector2f(6.2f, 19.8f), new Vector2f(8.2f, 19.8f)), // Block root passage
				new Exit(
						"Zone1_lvl1_Exit", 8, 30, pastPlayer,pastMedusa ,
						"Zone1_lvl2_Past", game::changeLeftScene, new Vector2f(1, 1)
				),
				new Exit(
						"Zone2_lvl1_Past", 31, 16, pastPlayer, pastMedusa,
						"Zone2_lvl1_Past", game::changeLeftScene, new Vector2f(7.0f, 28.0f)
				),
				new Pickupable("Bottle", 5.0f, 5.0f, pastPlayer, throwableBottle),
				throwableBottle,
				new Medusa("pastMedusa",
						new Vector2f(0.5f, 0.5f),
						new Vector4f(0.25f,0,0.75f,1), pastPlayer),
				pastPlayer,
				new SeedEmplacement("Zone1_lvl1_seedEmplacement",0.0f,0.0f,pastPlayer, pastScene,futurScene)

		);

		pastTimeObjects.forEach((timeObject) -> this.pastScene.addGameObject(timeObject.getGameObject()));

		GameObject futureMap = new GameObject("Future Map");
		futureMap.addComponent(new MapRender("future", "zone1_lvl1_future"));
		this.futurScene.addGameObject(futureMap);

		CapsuleSender sender = new CapsuleSender(
				"capsuleSender", futurPlayer,
				new Pickupable("seedPickup", 3, 3, pastPlayer, new Seed("seed", pastPlayer)),
				zone1_pastCapsuleReceiver
		);
		futurPlayer.addToInventory(sender);

		futurTimeObjects = Set.of(
				new Wall(new Vector2f(14.2f, 15.8f), new Vector2f(16.8f, 15.8f)), // Block main passage
				new Wall(new Vector2f(14.2f, 26.8f), new Vector2f(16.8f, 26.8f)), // Block main passage
				new Exit(
						"Zone1_lvl1_Exit", 8, 30, futurPlayer,futurMedusa ,
						"Zone1_lvl2_Futur", game::changeRightScene, new Vector2f(1.0f, 1.0f)
				),
				new Medusa("pastMedusa",
						new Vector2f(0.5f, 0.5f),
						new Vector4f(0.25f,0,0.75f,1), futurPlayer),
				futurPlayer,
                sender
		);


		futurTimeObjects.forEach((timeObject) -> this.futurScene.addGameObject(timeObject.getGameObject()));
	}

	@Override
	public Scene getPastScene() {
		return pastScene;
	}

	@Override
	public Scene getFuturScene() {
		return futurScene;
	}
}
