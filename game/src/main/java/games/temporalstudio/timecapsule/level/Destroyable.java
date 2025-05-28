package games.temporalstudio.timecapsule.level;


public class Destroyable {
    private boolean canBeDestroyed;

    public Destroyable(boolean canBeDestroyed) {
        this.canBeDestroyed = canBeDestroyed;
    }

    public boolean isDestroyable() {
        return canBeDestroyed;
    }
}
