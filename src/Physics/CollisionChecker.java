package Physics;

import javax.swing.*;
import java.awt.*;

public class CollisionChecker extends JFrame {

    //COLISION : pour chaque objet, un boolean Colision=true si solide, false sinon
    // Classe entité à un un attribue Rectangle SolidArea
    // Chaque entité dynamique à un boolean ColisionOn=ture si il rencontre un object solid

    Box player1;

    Box player2;

    public void CheckCollision(Object o){

//        player1 =  new Box(100,300,50,50, Color.BLUE);
//        player2 =  new Box(100,300,50,50, Color.RED);


            // le but est de predire l'object que vas rencontre l'objet dynamique et si c'est object est un solid (colision=true),
            // alors ColisionOn= tru
    }

    public void checkCollision (){
        if (player1.intersects(player2)){
            System.out.println("Oh no they collided");
        }
    }
}
