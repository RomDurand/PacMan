package Gameplay;

import Graphic.SpriteInformation;
import Physics.Vector;

public interface IGetterComponent {
    int getId();
    Vector getPosition();
    SpriteInformation getSprite();
    boolean isMobile();
    boolean isVisible();
    boolean canCollide();
}
