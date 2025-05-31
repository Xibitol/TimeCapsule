package games.temporalstudio.timecapsule.level;
import games.temporalstudio.temporalengine.component.GameObject;
import games.temporalstudio.temporalengine.component.Component;

public abstract interface LevelObject extends Component {
    void interact(GameObject player);
    boolean isStockable();
}