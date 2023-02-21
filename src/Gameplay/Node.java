package Gameplay;

import java.util.ArrayList;
import java.util.List;

public class Node {
    int x;
    int y;
    List<Node> succesor;

    public Node(int x,int y){
        this.x = x;
        this.y = y;
        this.succesor = new ArrayList<Node>();
    }

    public void addSuccesor(Node succesor){
        this.succesor.add(succesor);
    }

    public int getX(){ return x;}

    public int getY(){ return y;}

    public List<Node> getSuccesor(){ return succesor;}

}