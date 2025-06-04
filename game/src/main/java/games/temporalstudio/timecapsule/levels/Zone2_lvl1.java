package games.temporalstudio.timecapsule.levels;

import games.temporalstudio.temporalengine.Scene;
import games.temporalstudio.temporalengine.component.GameObject;
import games.temporalstudio.temporalengine.Game;
import games.temporalstudio.temporalengine.rendering.component.MapRender;
import games.temporalstudio.timecapsule.Entity.Enemy;
import games.temporalstudio.timecapsule.Entity.Medusa;
import games.temporalstudio.timecapsule.Entity.Player;
import games.temporalstudio.timecapsule.objects.*;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.Set;

public class Zone2_lvl1 implements SingleLevel {
    private Scene pastScene;

    public Zone2_lvl1(
            GameObject pastCamera,
            Game game, Player pastPlayer, Medusa pastMedusa
    ) {
        this.pastScene = new Scene("Zone2_lvl1_Past");
        this.pastScene.addGameObject(pastCamera);

        GameObject pastZone2Map = new GameObject("Past Map");
        pastZone2Map.addComponent(new MapRender("past", "zone2_lvl1_past"));
        this.pastScene.addGameObject(pastZone2Map);

        Set.of(
                new Wall(new Vector2f(0, 3), new Vector2f(32, 3)), // Bottom wall
                new Wall(new Vector2f(9.3f, 0), new Vector2f(9.3f, 32)), // Left wall
                new Wall(new Vector2f(20.7f, 0), new Vector2f(20.7f, 32)), // Right wall
                new Wall(new Vector2f(0, 23), new Vector2f(32, 23)), // Top wall

                new Exit(
                        "Zone1_lvl1_CapsuleExit", 4.0f, 1.0f, pastPlayer,pastMedusa ,
                        "Zone1_lvl1_Past", game::changeLeftScene, new Vector2f(7.0f, 28.0f)
                )
        ).forEach((timeObject) -> this.pastScene.addGameObject(timeObject.getGameObject()));
    }

    @Override
    public Scene getScene() {
        return this.pastScene;
    }
}
