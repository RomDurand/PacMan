package Physics;

public class ReactionThread extends Thread{
    IKernelFromPhysics kernel;
    int id1;
    int id2;

    public ReactionThread(IKernelFromPhysics kernel, int id, int otherId){
        this.kernel = kernel;
        this.id1 = id;
        this.id2 = otherId;

    }

    public void run() {
        kernel.collided(id1, id2);
    }
}
