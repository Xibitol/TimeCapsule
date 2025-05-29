package games.temporalstudio.temporalengine.component;

import games.temporalstudio.temporalengine.Game;
import games.temporalstudio.temporalengine.LifeCycleContext;

import games.temporalstudio.temporalengine.listeners.MouseListener;
import games.temporalstudio.temporalengine.physics.Collider2D;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * MouseAction component for handling mouse events on a GameObject.
 * This component allows you to define actions for mouse enter, hover, exit,
 * and click events.
 * @author Adnan FAIZE
 */
public class MouseActionable implements Component {
    private Consumer<LifeCycleContext> onEnterAction;
    private Consumer<LifeCycleContext> onHoverAction;
    private Consumer<LifeCycleContext> onExitAction;

    private Consumer<LifeCycleContext>[] onClickDownAction;
    private Consumer<LifeCycleContext>[] onClickAction;
    private Consumer<LifeCycleContext>[] onClickUpAction;

    private boolean isMouseOver = false;
    private boolean wasMouseOver = false;
    private boolean[] wasMouseDown = new boolean[3];

    /**
     * Constructor for MouseAction.
     * At least one action must be provided, otherwise an error will be logged.
     *
     * @param onEnterAction Action to perform when the mouse enters the GameObject.
     * @param onHoverAction Action to perform when the mouse hovers over the GameObject.
     * @param onExitAction Action to perform when the mouse exits the GameObject.
     * @param onClickDownAction Actions to perform when the mouse button is pressed down on the GameObject.
     * @param onClickAction Actions to perform when the mouse button is clicked on the GameObject.
     * @param onClickUpAction Actions to perform when the mouse button is released on the GameObject.
     */
    public MouseActionable(
        @Nullable Consumer<LifeCycleContext> onEnterAction,
        @Nullable Consumer<LifeCycleContext> onHoverAction,
        @Nullable Consumer<LifeCycleContext> onExitAction,
        @Nullable Consumer<LifeCycleContext>[] onClickDownAction,
        @Nullable Consumer<LifeCycleContext>[] onClickAction,
        @Nullable Consumer<LifeCycleContext>[] onClickUpAction
    ) {
        this.onEnterAction = onEnterAction;
        this.onHoverAction = onHoverAction;
        this.onExitAction = onExitAction;
        this.onClickDownAction = onClickDownAction != null ? onClickDownAction : new Consumer[3];
        this.onClickAction = onClickAction != null ? onClickAction : new Consumer[3];
        this.onClickUpAction = onClickUpAction != null ? onClickUpAction : new Consumer[3];
    }

    public MouseActionable() {
        this.onEnterAction = null;
        this.onHoverAction = null;
        this.onExitAction = null;
        this.onClickDownAction = new Consumer[3];
        this.onClickAction = new Consumer[3];
        this.onClickUpAction = new Consumer[3];
    }

    public void setMouseOver(boolean isMouseOver) { this.isMouseOver = isMouseOver; }

    public void setOnEnterAction(Consumer<LifeCycleContext> onEnterAction) { this.onEnterAction = onEnterAction; }

    public void setOnHoverAction(Consumer<LifeCycleContext> onHoverAction) { this.onHoverAction = onHoverAction; }

    public void setOnExitAction(Consumer<LifeCycleContext> onExitAction) { this.onExitAction = onExitAction; }

    public void setOnClickDownAction(Consumer<LifeCycleContext>[] onClickDownAction) { this.onClickDownAction = onClickDownAction; }

    public void setOnClickDownAction(Consumer<LifeCycleContext> onClickDownAction, int button) { this.onClickDownAction[button] = onClickDownAction; }

    public void setOnClickAction(Consumer<LifeCycleContext>[] onClickAction) { this.onClickAction = onClickAction; }

    public void setOnClickAction(Consumer<LifeCycleContext> onClickAction, int button) { this.onClickAction[button] = onClickAction; }

    public void setOnClickUpAction(Consumer<LifeCycleContext>[] onClickUpAction) { this.onClickUpAction = onClickUpAction; }

    public void setOnClickUpAction(Consumer<LifeCycleContext> onClickUpAction, int button) { this.onClickUpAction[button] = onClickUpAction; }


    private boolean isActionNull(Consumer<LifeCycleContext> action) { return action == null; }

    private boolean isActionNull(Consumer<LifeCycleContext>[] action, int index) { return action == null || action[index] == null; }

    @Override
    public void update(LifeCycleContext context, float delta) {
        if (isMouseOver) {
            if (!wasMouseOver) {
                if (!isActionNull(onEnterAction)) { onEnterAction.accept(context); }
                wasMouseOver = true;
            }

            for (int i = 0; i < wasMouseDown.length; i++) {
                if (MouseListener.mouseButtonDown(i)) {
                    if (!wasMouseDown[i]) {
                        if (!isActionNull(onClickDownAction, i)) { onClickDownAction[i].accept(context); }
                        wasMouseDown[i] = true;
                    }
                    if (!isActionNull(onClickAction, i)) { onClickAction[i].accept(context); }
                }
            }
        }
        if (!isMouseOver && wasMouseOver) {
            if (!isActionNull(onExitAction)) { onExitAction.accept(context); }
            wasMouseOver = false;
        }
        for (int i = 0; i < wasMouseDown.length; i++) {
            if (!MouseListener.mouseButtonDown(i)) {
                if (wasMouseDown[i]) {
                    if (!isActionNull(onClickUpAction, i)) { onClickUpAction[i].accept(context); }
                    wasMouseDown[i] = false;
                }
            }
        }
    }

    @Override
    public void init(LifeCycleContext context) {
        if (!(context instanceof GameObject gameObject)){
            Game.LOGGER.warning("MouseAction can only be used with GameObject context.");
            destroy(context);
            return;
        }
        if (!gameObject.hasComponent(Collider2D.class)) {
            Game.LOGGER.warning("Trigger can only be used with GameObject context.");
            destroy(context);
        }
    }

    @Override
    public void start(LifeCycleContext context) {
        if (Stream.of(onEnterAction, onHoverAction, onExitAction, onClickDownAction, onClickAction, onClickUpAction)
                .allMatch(Objects::isNull)) {
            Game.LOGGER.warning("At least one action must be provided.");
        }
    }

    @Override
    public void destroy(LifeCycleContext context) { }
}
