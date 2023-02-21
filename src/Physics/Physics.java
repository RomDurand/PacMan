package Physics;

import Gameplay.IGetterComponent;
import Noyau.IPhysic;

import java.util.ArrayList;
import java.util.HashMap;

public class Physics implements IPhysic {

    private IKernelFromPhysics kernel;

    private volatile HashMap<Integer, Vector> positions =  new HashMap<>();
    private volatile HashMap<Integer, Vector> speeds = new HashMap<>();
    private volatile HashMap<Integer, IGetterComponent> components = new HashMap<>();
    private volatile HashMap<Integer, Box> boxes = new HashMap<>();
    private volatile ArrayList<Integer> idObjects =  new ArrayList<>();

    /**
     * Create physic motor
     * @param kernel need a kernel to be created
     */
    public Physics(IKernelFromPhysics kernel){
        this.kernel =  kernel;
    }

    /**
     * update the position of every mobile object
     */
    public synchronized void update() {
        for (Integer id : idObjects){
            if (components.get(id).isMobile() && speeds.get(id) != Vector.ZERO){
                Vector newPos = positions.get(id).add(speeds.get(id));
                positions.put(id, newPos);
                PositionInformation newPosInfo = new PositionInformation(newPos, id);
                kernel.updatePosition(newPosInfo);
                boxes.get(id).setPos(newPos);

                if (components.get(id).canCollide()){
                    for (int otherId : idObjects) {
                        if (id != otherId && components.get(otherId).canCollide()){
                            if(boxes.get(id).intersects(boxes.get(otherId))){
                                new ReactionThread(kernel,(int) id, otherId).start();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * create a new character, his speed, his hitbox and his position
     * @param personnage
     */
    public synchronized void add(IGetterComponent personnage) {
        idObjects.add(personnage.getId());
        positions.put(personnage.getId(), personnage.getPosition());
        speeds.put(personnage.getId(), Vector.ZERO);
        components.put(personnage.getId(), personnage);
        boxes.put(personnage.getId(), new Box(personnage.getPosition().getX(),personnage.getPosition().getY(), personnage.getSprite().height(), personnage.getSprite().weight()));
    }

    /**
     * Delete a component
     * @param id the ID of the component to delete
     */
    public synchronized void removeComponent(int id){
        for (int i = 0; i < idObjects.size(); i++) {
            if (idObjects.get(i) == id){
                idObjects.remove(i);
                positions.remove(id);
                speeds.remove(id);
                components.remove(id);
                boxes.remove(id);
                return;
            }
        }
    }

    /**
     * set the speed of an object
     * @param id the id of the object
     * @param speed the speed
     */
    public synchronized void setSpeed(int id, Vector speed){
        speeds.put(id, speed);
    }

    /**
     * increase the speed vector of an object
     * @param id the id of the object
     * @param speed the amound of speed to add to the vector
     */
    public synchronized void addSpeed(int id, Vector speed){
        speeds.put(id, speeds.get(id).add(speed));
    }

    /**
     * set the position of an object
     * @param id the id of the object
     * @param pos the position of the object
     */
    public void setPosition(int id, Vector pos) {
        positions.replace(id, pos);
        PositionInformation newPosInfo = new PositionInformation(pos, id);
        kernel.updatePosition(newPosInfo);
    }

    public void addPosition(int id, Vector pos) {
        positions.replace(id, positions.get(id).add(pos));
        PositionInformation newPosInfo = new PositionInformation(pos, id);
        kernel.updatePosition(newPosInfo);
    }

    /**
     * Make a component step back
     * @param id the id of the object
     */
    public void stepBack(int id) {
        if (components.get(id).isMobile() && speeds.get(id) != Vector.ZERO) {
            Vector newPos = positions.get(id).add(speeds.get(id).negate());
            positions.put(id, newPos);
            PositionInformation newPosInfo = new PositionInformation(newPos, id);
            kernel.updatePosition(newPosInfo);
            boxes.get(id).setPos(newPos);
            if (components.get(id).canCollide()) {
                for (int otherId : idObjects) {
                    if (id != otherId && components.get(otherId).canCollide()) {
                        if (boxes.get(id).intersects(boxes.get(otherId))) {
                            new ReactionThread(kernel, (int) id, otherId).start();
                        }
                    }
                }
            }
        }
    }

    public void inverseSpeed(int id) {
        speeds.put(id, speeds.get(id).negate());
    }
}
