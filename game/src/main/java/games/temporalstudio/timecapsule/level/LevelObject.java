package games.temporalstudio.timecapsule.level;
import games.temporalstudio.temporalengine.component.GameObject;

public interface LevelObject {
    void interact(GameObject player);
    boolean isStockable();
}