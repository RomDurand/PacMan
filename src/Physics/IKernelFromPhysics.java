package Physics;

public interface IKernelFromPhysics {
    void collided(int id1, int id2);
    void updatePosition(PositionInformation newPosInfo);
}
