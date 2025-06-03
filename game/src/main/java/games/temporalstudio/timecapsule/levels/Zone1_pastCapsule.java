package games.temporalstudio.timecapsule.levels;

import java.util.HashSet;
import java.util.Set;

import games.temporalstudio.temporalengine.Game;
import games.temporalstudio.temporalengine.Scene;
import games.temporalstudio.temporalengine.component.GameObject;
import games.temporalstudio.timecapsule.objects.CapsuleReceiver;
import games.temporalstudio.timecapsule.objects.Exit;
import games.temporalstudio.timecapsule.objects.Pickupable;
import games.temporalstudio.timecapsule.objects.Player;
import games.temporalstudio.timecapsule.objects.Seed;
import games.temporalstudio.timecapsule.objects.TimeObject;
import games.temporalstudio.timecapsule.objects.Wall;

public class Zone1_pastCapsule implements SingleLevel {
    private Scene scene;
    private Set<TimeObject> timeObjects;

    public Zone1_pastCapsule(GameObject pastCamera, Game game, Player pastPlayer, CapsuleReceiver zone1_pastCapsuleReceiver, Seed seed) {
        this.scene = new Scene("Zone1_pastCapsule");
        this.scene.addGameObject(pastCamera);

        Set<TimeObject> walls = new HashSet<>();
        for (int x = 0; x <= 16; x++) {
            walls.add(new Wall("Zone1_pastCapsule_TopWall_" + x, x, 0));
            walls.add(new Wall("Zone1_pastCapsule_BottomWall_" + x, x, 16));
        }
        for (int y = 1; y < 16; y++) {
            walls.add(new Wall("Zone1_pastCapsule_LeftWall_" + y, 0, y));
            walls.add(new Wall("Zone1_pastCapsule_RightWall_" + y, 16, y));
        }

        Pickupable seedPickup = new Pickupable("seedPickup", 5.0f, 10.0f, pastPlayer, seed);

        timeObjects = new HashSet<>(walls);
        timeObjects.add(seedPickup);

        timeObjects.add(new Exit(
            "Zone1_pastCapsule_Exit", 8.0f, 0.0f, pastPlayer,
            "Zone1_lvl1_Past", game::changeLeftScene
        ));
        timeObjects.add(pastPlayer);
        timeObjects.add(zone1_pastCapsuleReceiver);

        timeObjects.forEach(timeObject -> this.scene.addGameObject(timeObject.getGameObject()));
    }

    public Scene getScene() {
        return scene;
    }
}
