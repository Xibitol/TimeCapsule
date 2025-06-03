package games.temporalstudio.timecapsule.Entity;

import games.temporalstudio.temporalengine.component.GameObject;
import games.temporalstudio.temporalengine.physics.Collider2D;
import games.temporalstudio.temporalengine.physics.PhysicsBody;
import games.temporalstudio.temporalengine.physics.Transform;
import games.temporalstudio.temporalengine.physics.shapes.AABB;
import games.temporalstudio.temporalengine.rendering.component.ColorRender;
import games.temporalstudio.temporalengine.rendering.component.Render;
import games.temporalstudio.timecapsule.objects.TimeObject;
import org.joml.Vector2f;
import org.joml.Vector4f;

public abstract class Entity implements TimeObject {

    protected GameObject p;
    protected Transform transform;


    public Entity(String name, Vector2f position, Vector2f scale,float[] physBody, Vector4f color) {
        p=new GameObject(name);
        if (physBody.length != 4){
            throw new IllegalArgumentException("physBody.length must be 4");
        }
        transform = new Transform(position, scale);
        PhysicsBody physicsBody = new PhysicsBody(physBody[0], physBody[1], physBody[2], physBody[3]);
        Render render=new ColorRender(color);

        p.addComponent(transform);
        p.addComponent(physicsBody);
        p.addComponent(render);
    }

    public Transform getTransform() {return this.transform;}
    public PhysicsBody getPhysicsBody() {return p.getComponent(PhysicsBody.class);}
    public Render getRender() {return p.getComponent(Render.class);}
    public GameObject getGameObject() {return p;}

    public void moveUp(float force) {
        getPhysicsBody().applyForce(new Vector2f(0, force));}

    public void moveDown(float force){
        getPhysicsBody().applyForce(new Vector2f(0, -force));}

    public void moveLeft(float force){
        getPhysicsBody().applyForce(new Vector2f(-force, 0));}

    public void moveRight (float force){
        getPhysicsBody().applyForce(new Vector2f(force, 0));}

    public void jump (float force){
        getPhysicsBody().applyForce(new Vector2f(0, force));
    }


}

