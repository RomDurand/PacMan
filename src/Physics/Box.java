package Physics;

import java.awt.*;

public class Box extends Rectangle {
    /**
     * create an hitbox
     * @param x
     * @param y
     * @param height
     * @param width
     */
    public Box(int x, int y, int height, int width){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width =  width;
    }

    void setPos(Vector pos){
        x = pos.getX();
        y = pos.getY();
    }
}
