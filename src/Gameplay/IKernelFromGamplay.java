package Gameplay;

import Graphic.SpriteInformation;
import Physics.Vector;

import java.awt.*;

public interface IKernelFromGamplay {
    void setPosition(int id, Vector pos);
    void addPosition(int id, Vector pos);
    void setSpeed(int id, Vector pos);
    void addSpeed(int id, Vector speed);
    void setVisibility(int id, boolean b);
    void setMobility(int id, boolean b);
    void setCollidability(int id, boolean b);
    void updateSprite(int id, SpriteInformation sprite);
    void newObject(IInteractComponent personnage);
    void makeSound(String path);
    void destroy(int id);
    void stepBack(int id);
    Node directionTo(Node from, Node to, int n);
    void inverseSpeed(int id);
}
