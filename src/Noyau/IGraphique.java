package Noyau;

import Gameplay.IGetterComponent;
import Gameplay.IInteractComponent;

import javax.swing.*;
import java.util.ArrayList;

public interface IGraphique {
    void drawFrame();
    void addGameObject(IGetterComponent personnage);
    void removeGameObject(int id);
    void removeAll();
    void changeMobilityTo(IGetterComponent personnage, boolean b);
    void updateSprite(int id);
    void stopFrame();
}
