package Noyau;

import Gameplay.Gameplay;
import Gameplay.IGetterComponent;
import Gameplay.IKernelFromGamplay;
import Gameplay.IInteractComponent;
import Gameplay.Node;
import Graphic.Graphique;
import Graphic.SpriteInformation;
import InAndOut.IAndO;
import InAndOut.IKernelFromInOut;
import Physics.Physics;
import Physics.IKernelFromPhysics;
import Physics.PositionInformation;
import Physics.Vector;
import IA.IA;
import Sound.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class Kernel implements IKernelFromGamplay, IKernelFromPhysics, IKernelFromInOut {

    private String log="";
    private final HashMap<Integer, IInteractComponent> hashMapPersonnage = new HashMap<>();
    private final ArrayList<Integer> listeId = new ArrayList<>();
    private final IGameplay gameplay;
    private final IGraphique graph;
    private final IPhysic phys;
    private final IAndO iaO;

    public Kernel() throws InterruptedException {
        gameplay=new Gameplay(this);
        int w=gameplay.getWidth();
        int h=gameplay.getHeight();

        iaO = new IAndO(this);
        graph = new Graphique(w,h, iaO);
        phys = new Physics(this);
        Sound sound_mus_fond = new Sound();
        Sound sound = new Sound();
        makeSound("sounds/song.wav");


        gameplay.InitPersonnage(Gameplay.STATE.START);

        while (true){
            synchronized (gameplay){
                while(gameplay.getState() == Gameplay.STATE.WAITING){gameplay.wait();}
                if(gameplay.getState()==Gameplay.STATE.STOP){graph.stopFrame();break;}
            }
            long debutBoucle= System.currentTimeMillis();

            phys.update();

            log += "Physics moved one frame \n";
            gameplay.update();


            graph.drawFrame();
            log += "Graphic drew a new frame \n";

            long finBoucle=  System.currentTimeMillis();
            long temps= finBoucle-debutBoucle;
            if (temps<(1./30.)*1000.){sleep((long) ((1./30.)*1000.-temps));}
        }

    }

    /**
     * Return the log
     * @return
     */
    public String getLog() {
        return log;
    }

    /**
     * Tells that 2 objects collided
     * @param id1 the id of the first object
     * @param id2 the id of the second object
     */
    public void collided(int id1, int id2) {
        log=log+"collision between"+id1+" and "+id2+" \n";
        gameplay.collided(id1, id2);
    }

    /**
     * Update the position of an object
     * @param newPosInfo the new position
     */
    public void updatePosition(PositionInformation newPosInfo) {
        hashMapPersonnage.get(newPosInfo.id()).setPosition(newPosInfo.position());
        log=log+"All positions got updated \n";
    }

    public void keyPressed(KeyEvent e) {
        log=log+"key"+e+" pressed \n";
        gameplay.keyPressed(e);
    }

    /**
     * set the position of an object
     * @param id the id of the object
     * @param pos the position
     */
    public void setPosition(int id, Vector pos) {
        phys.setPosition(id, pos);
    }

    /**
     * add a vector to the current position of an object
     * @param id the id of the object
     * @param pos the position
     */
    public void addPosition(int id, Vector pos) {
        phys.addPosition(id, pos);
    }

    /**
     * set the speed of an object
     * @param id the id of the object
     * @param speed the speed
     */
    public void setSpeed(int id, Vector speed) {
        phys.setSpeed(id, speed);
    }

    /**
     * add a speed vector to an object
     * @param id the id of the object
     * @param speed the speed added
     */
    public void addSpeed(int id, Vector speed) {
        phys.addSpeed(id, speed);
    }

    /**
     * Set the visibility of an object
     * @param id the id of the object
     * @param b visible or not
     */
    public void setVisibility(int id, boolean b) {
        IInteractComponent perso = hashMapPersonnage.get(id);
        perso.setVisible(b);
        if(b && !perso.isMobile()){
            graph.addGameObject((IGetterComponent) perso);
            log=log+perso+"is visible \n";
        }
        if(!b){
            graph.removeGameObject(perso.getId());
            log=log+perso+"isn't visible \n";
        }
    }

    /**
     * Set the mobility of an object
     * @param id the id of the object
     * @param b mobile or not
     */
    public void setMobility(int id, boolean b) {
        IInteractComponent perso = hashMapPersonnage.get(id);
        perso.setMobile(b);
        if(b){
            graph.changeMobilityTo((IGetterComponent) perso, b);
            log=log+perso+"is able to move \n";
        }
    }
    /**
     * Set the collidability of an object
     * @param id the id of the object
     * @param b collidable or not
     */
    public void setCollidability(int id, boolean b) {
        IInteractComponent perso = hashMapPersonnage.get(id);
        perso.setCollide(b);
        log=log+perso+"is collidable \n";
    }

    /**
     * update the sprite of an object
     * @param id the id of the object
     * @param sprite the new sprite
     */
    public void updateSprite(int id, SpriteInformation sprite) {
        IInteractComponent perso = hashMapPersonnage.get(id);
        graph.updateSprite(id);
        perso.setSprite(sprite);
        graph.updateSprite(id);
        log=log+"All sprite got updated \n";
    }

    /**
     * create a new object
     * @param personnage the object
     */
    public void newObject(IInteractComponent personnage) {
        listeId.add(personnage.getId());
        hashMapPersonnage.put(personnage.getId(), personnage);
        PositionInformation posInfo = new PositionInformation(personnage.getPosition(), personnage.getId());
        phys.add((IGetterComponent) personnage);
        if(personnage.isVisible()){
            graph.addGameObject((IGetterComponent) personnage);
        }
        log=log+"a new character is created\n";
    }

    /**
     * play a sound
     * @param path the path of the sound
     */
    public void makeSound(String path) {
        Sound.makeSound(path);


        log=log+"le son "+path+"a été joué \n";
    }

    /**
     * destroy an object
     * @param id the id of the object
     */
    public synchronized void destroy(int id) {
        if(hashMapPersonnage.containsKey(id)){
            for (int i = 0; i < listeId.size(); i++) {
                if (listeId.get(i) == id){
                    listeId.remove(i);
                    break;
                }
            }
            hashMapPersonnage.remove(id, hashMapPersonnage.get(id));
            phys.removeComponent(id);
            graph.removeGameObject(id);
            gameplay.destroy(id);
            log=log+" This character got destroyed : "+hashMapPersonnage.get(id)+" \n";
        }
    }

    /**
     * stepback an object
     * @param id the id of the object
     */
    public void stepBack(int id) {
        phys.stepBack(id);
    }

    /**
     * demande à l'ia le prochain noued ou se déplacer
     * @param from noeud d'origine
     * @param to noeud où l'on va
     * @param n numero du fantome, décide du compertement à adopter
     * @return prochain noeud
     */
    public Node directionTo(Node from, Node to, int n) {
        switch (n) {
            case 0 -> {
                return IA.plusCourtChemin(from, to);
            }
            case 1 -> {
                return IA.plusCourtCheminRandom(from, to);
            }
            case 2 -> {
                return IA.plusCourtCheminLefty(from, to);
            }
            case 3 -> {
                return IA.plusCourtCheminMoreRandom(from, to);
            }
        }
        return IA.plusCourtCheminRandom(from, to);
    }

    /**
     * inverse le vecteur speed de l'objet d'id id
     * @param id l'id de l'objet
     */
    public void inverseSpeed(int id) {
        phys.inverseSpeed(id);
    }
}
