package games.temporalstudio.timecapsule.levels;

import java.util.Set;

import games.temporalstudio.temporalengine.Game;
import games.temporalstudio.temporalengine.Scene;
import games.temporalstudio.temporalengine.component.GameObject;
import games.temporalstudio.timecapsule.objects.Exit;
import games.temporalstudio.timecapsule.objects.Player;
import games.temporalstudio.timecapsule.objects.TimeObject;
import games.temporalstudio.timecapsule.objects.Wall;

public class Zone1_pastCapsule implements SingleLevel{
	private Scene scene;
	private Set<TimeObject> timeObjects;

	public Zone1_pastCapsule(GameObject pastCamera, Game game, Player pastPlayer) {
		this.scene = new Scene("Zone1_pastCapsule");
		this.scene.addGameObject(pastCamera);

		timeObjects = Set.of(
				new Wall("Zone1_pastCapsule_Wall1", 1f, 5.0f),
				new Wall("Zone1_pastCapsule_Wall2", 2f, 5.0f),
				new Wall("Zone1_pastCapsule_Wall3", 3f, 5.0f),
				new Wall("Zone1_pastCapsule_Wall4", 4f, 5.0f),
				new Wall("Zone1_pastCapsule_Wall5", 5f, 5.0f),

                //new Pickupable("Zone1_pastCapsule_AirCapsule", 3.0f, 2.0f, pastPlayer, new AirCapsule("AirCapsuleObject")),
               // new Pickupable("Zone1_pastCapsule_Tool", 4.0f, 2.0f, pastPlayer, new Tool("ToolObject")),
              //  new Pickupable("Zone1_pastCapsule_Seed", 2.0f, 2.0f, pastPlayer, new Seed("SeedObject")),

				new Exit(
						"Zone1_pastCapsule_Exit", 1.0f, 1.0f, pastPlayer.getGameObject(),
						"Zone1_lvl1_Past", game::changeLeftScene
				),
				pastPlayer
		);

		timeObjects.forEach((timeObject) -> this.scene.addGameObject(timeObject.getGameObject()));
	}

	public Scene getScene(){
		return scene;
	}
}
