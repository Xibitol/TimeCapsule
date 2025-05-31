package games.temporalstudio.temporalengine.component;

import games.temporalstudio.temporalengine.Game;
import games.temporalstudio.temporalengine.LifeCycleContext;
import games.temporalstudio.temporalengine.listeners.MouseListener;
import games.temporalstudio.temporalengine.physics.Collider2D;
import games.temporalstudio.temporalengine.physics.Transform;
import games.temporalstudio.temporalengine.physics.shapes.AABB;
import games.temporalstudio.temporalengine.rendering.component.ColorRender;
import games.temporalstudio.temporalengine.rendering.component.Render;
import games.temporalstudio.temporalengine.rendering.component.View;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class MouseActionner extends GameObject {
    // Components
    private Transform transform;
    private Render render; // Only used for debugging
    private Collider2D collider2D;
    private View view;

    /**
     * MouseActionner component for handling mouse events on a GameObject.
     * This component allows you to detect when the mouse is over a GameObject
     * and trigger actions accordingly.
     *
     * @param name The name of the MouseActionner, used for debugging purposes. Defaults to "Mouse Actionner" if null.
     * @param view The View component used to convert screen coordinates to world coordinates. (Camera View Component)
     */
    public MouseActionner(@Nullable String name, @NotNull View view) {
        super(name == null ? "Mouse Actionner" : name);
        this.view = view;

        this.transform = new Transform(new Vector2f(0.5f), new Vector2f(0.0f)); // TODO : This is a dummy transform, it will not be used for rendering.
        this.render = new ColorRender(new Vector4f(1f, 0, 0f, 1f)); // TODO : This is a dummy render, it will not be used for rendering.
        this.collider2D = new Collider2D(transform);
        this.collider2D.setShape(new AABB(transform));

        this.addComponent(transform);
        this.addComponent(render);
        this.addComponent(collider2D);
    }

    @Override
    public void start(LifeCycleContext context) {
        this.collider2D.setOnIntersects((ctx, other) -> {
            Game.LOGGER.info("MouseActionner intersects with " + other);
            if (!(other instanceof GameObject gameObject)) { return; }
            if (!(gameObject.hasComponent(MouseActionable.class))) { return; }

            MouseActionable mouseActionable = gameObject.getComponent(MouseActionable.class);
            mouseActionable.setMouseOver(true);
        });
        this.collider2D.setOnSeparates((ctx, other) -> {
            if (!(other instanceof GameObject gameObject)) { return; }
            if (!(gameObject.hasComponent(MouseActionable.class))) { return; }
            //Game.LOGGER.info("MouseActionner separates from " + gameObject.getName());

            MouseActionable mouseActionable = gameObject.getComponent(MouseActionable.class);
            mouseActionable.setMouseOver(false);
        });
    }

    @Override
    public void update(LifeCycleContext context, float delta) {
        super.update(context, delta);
        this.transform.setPosition(view.screenToWorldCoord(
                new Vector2f(MouseListener.getX(), MouseListener.getY())
        ));
    }
}
