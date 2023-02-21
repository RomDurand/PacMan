package Gameplay;
import Gameplay.Gameplay.DIR;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ghost{
    int choiceNumber;
    Node position;
    int count = 0;
    boolean flee = false;
    int lastCount = 0;
    IGameplayFromGhostMouvement gameplay;
    public Ghost(int n, Node root, IGameplayFromGhostMouvement gameplay){
        this.choiceNumber = n;
        this.position = root;
        this.gameplay = gameplay;
    }

    void setFlee(boolean b){
        flee = b;
    }

    void update(){
        if (count > 0){
            count--;
        } else{
            if(!flee){
                gameplay.takeDirection(nextDirection(gameplay.nextNode(position, choiceNumber)), choiceNumber);
            }
            else{
                List<Node> candidat = new ArrayList<>();
                Node closestNode = gameplay.nextNode(position, choiceNumber);
                for (Node node:position.getSuccesor()) {
                    if (node != closestNode){
                        candidat.add(node);
                    }
                }
                if (candidat.size() == 0){
                    gameplay.takeDirection(DIR.IMM, choiceNumber);
                }
                else {
                    Random rand = new Random();
                    int randomNum = rand.nextInt(candidat.size());
                    gameplay.takeDirection(nextDirection(candidat.get(randomNum)), choiceNumber);
                }
            }
        }
    }

    public DIR nextDirection(Node nextNode){
        if (nextNode.getX() == position.getX() + 1){
            count = 13;
            position = nextNode;
            return DIR.DOWN;
        }
        if (nextNode.getX() == position.getX() - 1){
            count = 13;
            position = nextNode;
            return DIR.UP;
        }
        if (nextNode.getY() == position.getY() + 1){
            count = 19;
            position = nextNode;
            return DIR.RIGHT;
        }
        if (nextNode.getY() == position.getY() - 1){
            count = 19;
            position = nextNode;
            return DIR.LEFT;
        }
        else {
            return DIR.IMM;}
    }
}
