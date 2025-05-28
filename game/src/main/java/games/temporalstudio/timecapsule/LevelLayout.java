package games.temporalstudio.timecapsule;

import games.temporalstudio.temporalengine.Scene;

import java.util.ArrayList;

public class LevelLayout{
    private Scene pastScene;
    private Scene futureScene;

    public LevelLayout(Scene pastScene, Scene futureScene) {
        this.pastScene = pastScene;
        this.futureScene = futureScene;
    }

    public LevelLayout() {
        this.pastScene = new Scene("PastScene");
        this.futureScene = new Scene("FutureScene");
    }

    public Scene getPastScene() {
        return pastScene;
    }

    public void setPastScene(Scene pastScene) {
        this.pastScene = pastScene;
    }

    public Scene getFutureScene() {
        return futureScene;
    }

    public void setFutureScene(Scene futureScene) {
        this.futureScene = futureScene;
    }

    public void addPastScene(Scene scene){
        pastScene.addChild(scene);
    }
    public void addFutureScene(Scene scene){
        futureScene.addChild(scene);
    }

    //addChildPastScene(String name, String childName) ?
    
}
