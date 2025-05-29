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
import games.temporalstudio.temporalengine.window.Window;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class MouseActionner extends GameObject {
    private Transform transform;
    private Collider2D collider2D;
    private View view;

    public MouseActionner(String name, View view) {
        super(name);
        this.view = view;

        Render render = new ColorRender(new Vector4f(0f, 1, 0f, 1)); // TODO : This is a dummy render, it will not be used for rendering.
        this.transform = new Transform(new Vector2f(1), new Vector2f(0.0f)); // TODO : This is a dummy transform, it will not be used for rendering.
        this.collider2D = new Collider2D(transform);
        this.collider2D.setShape(new AABB(transform));

        this.collider2D.setOnIntersects((context, other) -> {
            Game.LOGGER.info("MouseActionner intersects with " + other);
            if (!(other instanceof GameObject gameObject)) { return; }
            if (!(gameObject.hasComponent(MouseActionable.class))) { return; }

            MouseActionable mouseActionable = gameObject.getComponent(MouseActionable.class);
            mouseActionable.setMouseOver(true);
        });
        this.collider2D.setOnSeparates((context, other) -> {
            if (!(other instanceof GameObject gameObject)) { return; }
            if (!(gameObject.hasComponent(MouseActionable.class))) { return; }
            Game.LOGGER.info("MouseActionner separates from " + gameObject.getName());

            MouseActionable mouseActionable = gameObject.getComponent(MouseActionable.class);
            mouseActionable.setMouseOver(false);
        });

        this.addComponent(transform);
        this.addComponent(render);
        this.addComponent(collider2D);
    }

    @Override
    public void update(LifeCycleContext context, float delta) {
        super.update(context, delta);
        this.transform.setPosition(
                view.screenToWorldCoord(
                        new Vector2f(MouseListener.getX(), -MouseListener.getY())
                )
        );
        Game.LOGGER.info("MouseActionner position updated to: " + this.transform.getPosition());
        Game.LOGGER.info("Mouse position: " + MouseListener.getX() + ", " + MouseListener.getY());
    }
}
