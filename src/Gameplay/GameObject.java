package Gameplay;

import Graphic.SpriteInformation;
import Physics.Vector;

public class GameObject implements IInteractComponent,IGetterComponent{

    private volatile Vector position;
    private int id;
    private volatile boolean isMobile;
    private volatile boolean isVisible;
    private volatile boolean canCollide;
    private volatile SpriteInformation sprite;

    public GameObject(int id, String path, int height, int weight, int depth, int startingX, int startingY, boolean isMobile, boolean isVisible, boolean canCollide){
        this.id = id;
        this.sprite = new SpriteInformation(path, height, weight, depth);
        this.position = new Vector(startingX, startingY);
        this.isMobile = isMobile;
        this.isVisible = isVisible;
        this.canCollide = canCollide;
    }

    public GameObject(int id, String path, int height, int weight, int depth, int startingX, int startingY, boolean isMobile){
        this.id = id;
        this.sprite = new SpriteInformation(path, height, weight, depth);
        this.position = new Vector(startingX, startingY);
        this.isMobile = isMobile;
        this.isVisible = true;
        this.canCollide = true;
    }

    public int getId() {
        return id;
    }

    public synchronized Vector getPosition() {
        return position;
    }

    public synchronized void setPosition(Vector pos) {
        position = pos;
    }

    public synchronized SpriteInformation getSprite() {
        return sprite;
    }

    public synchronized void setSprite(SpriteInformation sprite) {
        this.sprite = sprite;
    }

    public synchronized boolean isMobile() {
        return isMobile;
    }

    public synchronized boolean isVisible() {
        return isVisible;
    }

    public synchronized boolean canCollide() {
        return canCollide;
    }

    public synchronized void setMobile(boolean b) {
        isMobile = b;
    }

    public synchronized void setVisible(boolean b) {
        isVisible = b;
    }

    public synchronized void setCollide(boolean b) {
        canCollide = b;
    }

    @Override
    public String toString() {
        return "sprite utilisé :" +sprite;
    }
}