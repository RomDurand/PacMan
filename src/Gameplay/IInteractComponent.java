package Gameplay;

import Graphic.SpriteInformation;
import Physics.Vector;

public interface IInteractComponent {
    int getId();
    Vector getPosition();
    void setPosition(Vector pos);
    SpriteInformation getSprite();
    void setSprite(SpriteInformation sprite);
    boolean isMobile();
    boolean isVisible();
    boolean canCollide();
    void setMobile(boolean b);
    void setVisible(boolean b);
    void setCollide(boolean b);

}
