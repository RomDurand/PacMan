package Noyau;

import Gameplay.Gameplay;
import Gameplay.IInteractComponent;
import Gameplay.Node;

import java.awt.event.KeyEvent;
import java.util.ArrayList;


public interface IGameplay {

    int getWidth();
    int getHeight();
    void keyPressed(KeyEvent e);
    void collided(int id1, int id2);
    void InitPersonnage(Gameplay.STATE scenario);
    Gameplay.STATE getState();
    void destroy(int id);
    void update();
}
