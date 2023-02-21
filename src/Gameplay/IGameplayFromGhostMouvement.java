package Gameplay;
import Gameplay.Gameplay.DIR;

public interface IGameplayFromGhostMouvement {
    public Node nextNode(Node from, int n);
    public void takeDirection(DIR direction, int n);
}
