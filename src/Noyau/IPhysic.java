package Noyau;

import Gameplay.IGetterComponent;
import Physics.Vector;

public interface IPhysic {
    void update();
    void add(IGetterComponent personnage);
    void removeComponent(int id);
    void setSpeed(int id, Vector speed);
    void addSpeed(int id, Vector speed);
    void setPosition(int id, Vector pos);
    void addPosition(int id, Vector pos);
    void stepBack(int id);
    void inverseSpeed(int id);
}
