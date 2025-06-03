package games.temporalstudio.timecapsule.levels;

import java.util.HashSet;
import java.util.Set;

import games.temporalstudio.temporalengine.Game;
import games.temporalstudio.temporalengine.Scene;
import games.temporalstudio.temporalengine.component.GameObject;
import games.temporalstudio.timecapsule.objects.CapsuleReceiver;
import games.temporalstudio.timecapsule.objects.CapsuleSender;
import games.temporalstudio.timecapsule.objects.Exit;
import games.temporalstudio.timecapsule.objects.Pickupable;
import games.temporalstudio.timecapsule.objects.Player;
import games.temporalstudio.timecapsule.objects.Seed;
import games.temporalstudio.timecapsule.objects.ThrowableBottle;
import games.temporalstudio.timecapsule.objects.TimeObject;
import games.temporalstudio.timecapsule.objects.Wall;


public class Zone1_lvl1 implements TimeLevel{
	private Scene pastScene;
	private Scene futurScene;
	private Set<TimeObject> pastTimeObjects;
	private Set<TimeObject> futurTimeObjects;

	public Zone1_lvl1(
			GameObject pastCamera, GameObject futurCamera,
			Game game, Player pastPlayer, Player futurPlayer,
			CapsuleReceiver zone1_futurCapsuleReceiver
	) {
		this.pastScene = new Scene("Zone1_lvl1_Past");
		this.futurScene = new Scene("Zone1_lvl1_Futur");
		this.futurScene.addGameObject(futurCamera);
		this.pastScene.addGameObject(pastCamera);

		ThrowableBottle throwableBottle = new ThrowableBottle("ThrowableBottle", pastPlayer);

		Set<TimeObject> pastWalls = new HashSet<>();
		for (int x = 0; x < 16; x++) {
			if (x != 8 && x != 9) {
				pastWalls.add(new Wall("Past_BottomWall_" + x, x, 16));
			}
			pastWalls.add(new Wall("Past_TopWall_" + x, x, 0));
		}

		for (int y = 1; y < 16; y++) {
			pastWalls.add(new Wall("Past_LeftWall_" + y, 0, y));
			if (y != 7 && y != 8) {
				pastWalls.add(new Wall("Past_RightWall_" + y, 16, y));
			}
		}


		Set<TimeObject> futurWalls = new HashSet<>();
		for (int x = 0; x < 16; x++) {
			futurWalls.add(new Wall("Futur_BottomWall_" + x, x, 16));
			futurWalls.add(new Wall("Futur_TopWall_" + x, x, 0));
		}
		for (int y = 1; y < 16; y++) {
			futurWalls.add(new Wall("Futur_LeftWall_" + y, 0, y));
			if (y != 7 && y != 8) {
				futurWalls.add(new Wall("Futur_RightWall_" + y, 16, y));
			}
		}

		pastTimeObjects = new HashSet<>(pastWalls);
		pastTimeObjects.addAll(Set.of(
				new Exit(
						"Zone1_lvl1_CapsuleExit", 8.0f, 0.0f, pastPlayer,
						"Zone1_pastCapsule", game::changeLeftScene
				),
				new Exit(
						"Zone1_lvl1_Exit", 16.0f, 8.0f, pastPlayer,
						"Zone1_lvl2_Past", game::changeLeftScene
				),
				new Pickupable("Bottle", 5.0f, 5.0f, pastPlayer, throwableBottle),
				throwableBottle,
				pastPlayer
		));

		pastTimeObjects.forEach((timeObject) -> this.pastScene.addGameObject(timeObject.getGameObject()));

		CapsuleSender sender = new CapsuleSender(
                "capsuleSender", pastPlayer,
                new Pickupable("seedPickup", 3, 3, pastPlayer, new Seed("seed", pastPlayer)),
                zone1_futurCapsuleReceiver
        );
		pastPlayer.addToInventory(sender);
		pastTimeObjects.add(sender);

		futurTimeObjects = new HashSet<>(futurWalls);
		futurTimeObjects.addAll(Set.of(
				new Exit(
						"Zone1_lvl1_Exit", 16.0f, 8.0f, futurPlayer,
						"Zone1_lvl2_Futur", game::changeRightScene
				),
				futurPlayer,
				zone1_futurCapsuleReceiver
		));


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