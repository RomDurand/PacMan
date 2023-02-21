package Gameplay;

import Graphic.SpriteInformation;
import Noyau.IGameplay;
import Physics.Vector;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Gameplay implements IGameplay,IGameplayFromGhostMouvement {
    Node[][] nodeArray = new Node[22][19];
    private int compteurSpritePacMan=0;
    private int idFleche;

    public enum STATE{START,PLAY,SCORE,PAUSE,OPTION,CREDIT, WAITING,STOP}

    public enum DIR{UP,DOWN,LEFT,RIGHT,IMM}

    private volatile DIR dir = DIR.IMM;
    int xScale = 38;
    int yScale = 26;
    int xMargin = -1;
    int yMargin = 48;

    private volatile boolean isOpened= false;
    private volatile STATE state = STATE.START;
    private final IKernelFromGamplay kernel;
    private ArrayList<IInteractComponent> listePersonnage;

    private int idCount = 0;
    private final int width = 700;
    private final int height = 700;
    private int dotScore = 157; // nb de dot

    IGetterComponent PersonnageActif;
    int lives = 3;
    int artificialIntelligenceID = 1;
    private int MenuID = 1;
    int compteur = 0;
    public boolean Energizer = false;

    public Gameplay(IKernelFromGamplay kernel) {
        this.kernel = kernel;
    }

    private ArrayList<IInteractComponent> ghostList = new ArrayList<>();
    private ArrayList<Ghost> ghostTimer = new ArrayList<>();
    private ArrayList<IInteractComponent> Walls = new ArrayList<>();
    private ArrayList<IInteractComponent> DotScores = new ArrayList<>();
    private ArrayList<IInteractComponent> Teleporter = new ArrayList<>();
    private ArrayList<IInteractComponent> Energizers = new ArrayList<>();


    /**
     * Create the gameplay with the background, the character, the IA object, etc
     * In order to launch the scenarioin argument
     */
    public void InitPersonnage(STATE scenario) {
        switch (scenario) {
            case PLAY -> {


                IInteractComponent perso = new GameObject(idCount++, "sprites/pm_f.png", 23, 23, 1, 140, 100, true,true,true);
                kernel.newObject(perso);
                PersonnageActif=(IGetterComponent) perso;

                PersonnageActif=(IGetterComponent) perso;
                createMap();
                IInteractComponent background = new GameObject(idCount++, "sprites/map-pacman.png", 640, 684, 3, 0, 0, true);
                kernel.newObject(background);
                IInteractComponent background_noir = new GameObject(idCount++, "sprites/noiiiiiiiir.png", 700, 700, 4, 0, 0, true);
                kernel.newObject(background_noir);
                IInteractComponent tp1 = new GameObject(idCount++, "sprites/dot.png", 23, 2, 1, 0, 305, false,false,true);
                kernel.newObject(tp1);
                Teleporter.add(tp1);
                IInteractComponent tp2 = new GameObject(idCount++, "sprites/dot.png", 23, 2, 1, 684, 305, false,false,true);
                kernel.newObject(tp2);
                Teleporter.add(tp2);
                IInteractComponent Inky = new GameObject(idCount++, "sprites/Inky.png", 23,23,1, 297, 300, true,true,true);
                listePersonnage.add(Inky ); //Blue ghost, try to position themselves in front of Pac-Man
                ghostList.add(Inky);
                ghostTimer.add(new Ghost(0, nodeArray[10][8],this));
                IInteractComponent Pinky  = new GameObject(idCount++, "sprites/pinky.png", 23,23,1, 335, 300, true,true,true);
                listePersonnage.add(Pinky ); //Fantome rose, try to position themselves in front of Pac-Man
                ghostList.add(Pinky);
                ghostTimer.add(new Ghost(1, nodeArray[10][9],this));
                IInteractComponent Blinky = new GameObject(idCount++, "sprites/Blinky.png", 23,23,1, 373, 300, true,true,true);
                listePersonnage.add(Blinky); //Red ghost, direct chase to Pac-Man
                ghostList.add(Blinky);
                ghostTimer.add(new Ghost(2, nodeArray[10][10],this));
                IInteractComponent Clyde = new GameObject(idCount++, "sprites/Clyde.png", 23,23,1, 335, 274, true,true,true);
                listePersonnage.add(Clyde); //Orange ghost, will switch between chasing Pac-Man and fleeing from him
                ghostList.add(Clyde);
                ghostTimer.add(new Ghost(3, nodeArray[9][9],this));

                for (IInteractComponent personnage : listePersonnage) {
                    kernel.newObject(personnage);
                }
            }
            case START -> {
                MenuID=1;

                listePersonnage = new ArrayList<>();
                IInteractComponent StartMenu1 = new GameObject(idCount++, "StartMenu/StartMenu.PNG", 660, 700, 3, -13, 2, false);
                idFleche = idCount++;
                IInteractComponent FlecheVerte = new GameObject(idFleche, "StartMenu/flecheverte.PNG", 45, 45, 1, 240, 290, true);
                listePersonnage.add(FlecheVerte);
                listePersonnage.add(StartMenu1);

                for (IInteractComponent personnage : listePersonnage) {
                    kernel.newObject(personnage);
                }
            }
            case OPTION-> {
                MenuID=1;
                listePersonnage = new ArrayList<>();
                IInteractComponent OPTIONbackground= new GameObject(idCount++, "StartMenu/Option/OPTION.PNG", 500, 500, 3,-2 , -1, false);
                idFleche = idCount++;
                IInteractComponent FlecheVerte = new GameObject(idFleche, "StartMenu/flecheverte.PNG", 30, 30, 1, 130, 105, true);
                listePersonnage.add(FlecheVerte);
                listePersonnage.add(OPTIONbackground);

                for (IInteractComponent personnage : listePersonnage) {
                    kernel.newObject(personnage);
                }
            }
            case SCORE-> {
                MenuID=1;
                listePersonnage = new ArrayList<>();
                IInteractComponent OPTIONbackground= new GameObject(idCount++, "StartMenu/Score/score.PNG", 500, 500, 3,-2 , -1, false);
                idFleche = idCount++;
                IInteractComponent FlecheVerte = new GameObject(idFleche, "StartMenu/flecheverte.PNG", 30, 30, 1, 175, 467, true);
                listePersonnage.add(FlecheVerte);
                listePersonnage.add(OPTIONbackground);

                for (IInteractComponent personnage : listePersonnage) {
                    kernel.newObject(personnage);
                }
            }
            case CREDIT-> {
                MenuID=1;
                listePersonnage = new ArrayList<>();
                IInteractComponent OPTIONbackground= new GameObject(idCount++, "StartMenu/CREDIT/creditt.PNG", 500, 500, 3,-2 , -1, false);
                idFleche = idCount++;
                IInteractComponent FlecheVerte = new GameObject(idFleche, "StartMenu/flecheverte.PNG", 30, 30, 1, 175, 467, true);
                listePersonnage.add(FlecheVerte);
                listePersonnage.add(OPTIONbackground);
                for (IInteractComponent personnage : listePersonnage) {
                    kernel.newObject(personnage);
                }
            }

        }
    }

    private void destroyall() {
        while (listePersonnage.size() != 0) {
            kernel.destroy(listePersonnage.get(0).getId());
        }
    }

    /**
     * Tells which action do according to where the cursor is on the menu
     *
     * @param idmenu the id of the menu
     */
    private void Enter(int idmenu) {
        switch (idmenu) {
            case 1 -> {
                synchronized (this) {
                    setState(STATE.WAITING);
                    destroyall();
                    InitPersonnage(STATE.PLAY);
                    setState(STATE.PLAY);
                    this.notifyAll();
                }
            }
            case 2 -> { synchronized (this) {
                setState(STATE.WAITING);
                destroyall();
                InitPersonnage(STATE.SCORE);
                setState(STATE.SCORE);
                this.notifyAll();
            }
            }
            case 3 -> { synchronized (this) {
                setState(STATE.WAITING);
                destroyall();
                InitPersonnage(STATE.OPTION);
                setState(STATE.OPTION);
                this.notifyAll();
            }}
            case 4 -> { synchronized (this) {
                setState(STATE.WAITING);
                destroyall();
                InitPersonnage(STATE.CREDIT);
                setState(STATE.CREDIT);
                this.notifyAll();
            }}
            case 5 -> { synchronized (this) {
                setState(STATE.WAITING);
                destroyall();
                InitPersonnage(STATE.STOP);
                setState(STATE.STOP);
                this.notifyAll();
            }
            }
            case 6 -> { synchronized (this) {
                setState(STATE.WAITING);
                destroyall();
                InitPersonnage(STATE.START);
                setState(STATE.START);
                this.notifyAll();
            }
            }
        }
    }

    public synchronized STATE getState() {
        return state;
    }

    public void destroy(int id) {
        for (int i = 0; i < listePersonnage.size(); i++) {
            if (listePersonnage.get(i).getId() == id) {
                listePersonnage.remove(i);
                return;
            }
        }
    }

    /**
     * update this.state with a new value
     *
     * @param state
     */
    public synchronized void setState(STATE state) {
        this.state = state;
    }

    /**
     * return the width
     * @return witdh
     */    public int getWidth() {
        return width;
    }

    /**
     * return the height
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }


    /**
     * Tells how to deal with each pressed key
     *
     * @param e the key pressed
     */
    public void keyPressed(KeyEvent e) {

        switch (state) {
            case PLAY -> {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> {
                        dir=DIR.LEFT;
                        SpriteInformation sprite;
                        if (isOpened){
                            sprite = new SpriteInformation("sprites/pm_f.PNG", 25, 25, 0);
                        }
                        else{
                            sprite = new SpriteInformation("sprites/pacman_g.png", 25, 25, 0);
                        }

                        kernel.updateSprite(PersonnageActif.getId(),sprite);
                        kernel.setSpeed(PersonnageActif.getId(), new Vector(-2, 0));
                    }
                    case KeyEvent.VK_RIGHT ->{
                        dir=DIR.RIGHT;
                        SpriteInformation sprite;
                        if (isOpened){
                            sprite = new SpriteInformation("sprites/pm_f.PNG", 25, 25, 0);
                        }
                        else{
                            sprite = new SpriteInformation("sprites/pacman_d.png", 25, 25, 0);
                        }


                        //System.out.println(idPersonnageActif);
                        kernel.updateSprite(PersonnageActif.getId(),sprite);
                        kernel.setSpeed(PersonnageActif.getId(), new Vector(2, 0));
                    }

                    case KeyEvent.VK_UP ->{
                        dir=DIR.UP;
                        //System.out.println(listePersonnage.get(idPersonnageActif));
                        SpriteInformation sprite;
                        if (isOpened){
                            sprite = new SpriteInformation("sprites/pm_f.PNG", 25, 25, 0);
                        }
                        else{
                            sprite = new SpriteInformation("sprites/pacman_h.png", 25, 25, 0);
                        }


                        //System.out.println(idPersonnageActif);
                        kernel.updateSprite(PersonnageActif.getId(),sprite);
                        kernel.setSpeed(PersonnageActif.getId(), new Vector(0, -2));
                    }
                    case KeyEvent.VK_DOWN ->{
                        dir=DIR.DOWN;
                        //System.out.println(listePersonnage.get(idPersonnageActif));
                        SpriteInformation sprite;
                        if (isOpened){
                            sprite = new SpriteInformation("sprites/pm_f.PNG", 25, 25, 0);
                        }
                        else{
                            sprite = new SpriteInformation("sprites/pacman_b.png", 25, 25, 0);
                        }



                        //System.out.println(idPersonnageActif);
                        kernel.updateSprite(PersonnageActif.getId(),sprite);
                        kernel.setSpeed(PersonnageActif.getId(), new Vector(0, 2));
                    }
                    case KeyEvent.VK_Q -> kernel.setSpeed(PersonnageActif.getId(), new Vector(-5, 0));
                    case KeyEvent.VK_D -> kernel.setSpeed(PersonnageActif.getId(), new Vector(5, 0));
                    case KeyEvent.VK_Z -> kernel.setSpeed(PersonnageActif.getId(), new Vector(0, -5));
                    case KeyEvent.VK_S -> kernel.setSpeed(PersonnageActif.getId(), new Vector(0, 5));
                    case KeyEvent.VK_R -> {
                        kernel.setPosition(PersonnageActif.getId(), new Vector(200, 275));
                        kernel.setSpeed(PersonnageActif.getId(), new Vector(0, 0));
                    }
                    case KeyEvent.VK_P -> {
                        int IdPause=idCount++;
                        kernel.newObject(new GameObject(IdPause, "StartMenu/PAUSE/pausee.PNG", 500, 500, -1,-2 , -1, false));
                        setState(STATE.WAITING); if(e.getKeyCode()==KeyEvent.VK_ESCAPE){kernel.destroy(IdPause);}}
                    case KeyEvent.VK_T -> kernel.newObject(
                            new GameObject(idCount++, "green.png", 100, 100, 2, 100 * idCount - 300, 100, true));
                }

            }
            case START -> {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER -> {
                        Enter(MenuID);
                    }
                    case KeyEvent.VK_UP -> {
                        switch (MenuID) {
                            case 1 -> {
                                kernel.setPosition(idFleche, new Vector(240, 518));
                                MenuID = 5;
                            }
                            case 2 -> {
                                kernel.setPosition(idFleche, new Vector(240, 290));
                                MenuID = 1;
                            }
                            case 3 -> {
                                kernel.setPosition(idFleche, new Vector(240, 347));
                                MenuID = 2;
                            }
                            case 4 -> {
                                kernel.setPosition(idFleche, new Vector(240, 404));
                                MenuID = 3;
                            }
                            case 5 -> {
                                kernel.setPosition(idFleche, new Vector(240, 461));
                                MenuID = 4;
                            }
                        }
                    }
                    case KeyEvent.VK_DOWN -> {
                        switch (MenuID) {
                            case 1 -> {
                                kernel.setPosition(idFleche, new Vector(240, 347));
                                MenuID = 2;
                            }
                            case 2 -> {
                                kernel.setPosition(idFleche, new Vector(240, 404));
                                MenuID = 3;
                            }
                            case 3 -> {
                                kernel.setPosition(idFleche, new Vector(240, 461));
                                MenuID = 4;
                            }
                            case 4 -> {
                                kernel.setPosition(idFleche, new Vector(240, 518));
                                MenuID = 5;
                            }
                            case 5 -> {
                                kernel.setPosition(idFleche, new Vector(240, 290));
                                MenuID = 1;
                            }
                        }
                    }
                }
            }
            case SCORE-> {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Enter(6);
                }
            }
            case CREDIT-> {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Enter(6);
                }
            }
            case OPTION-> {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER -> {if (MenuID==3){Enter(6);};}
                    case KeyEvent.VK_RIGHT -> {
                        if (MenuID==1)
                             {kernel.setPosition(idFleche,new Vector(225,105));MenuID=2;}
                        }
                    case KeyEvent.VK_DOWN -> {
                        switch (MenuID){
                            case 1 -> {kernel.setPosition(idFleche,new Vector(175,467));MenuID=3;}
                            case 2 -> {kernel.setPosition(idFleche,new Vector(175,467));MenuID=3;}
                            case 3 -> {kernel.setPosition(idFleche,new Vector(130,105));MenuID=1;}

                        }
                    }

                    case KeyEvent.VK_UP -> {
                        if (MenuID == 3) {
                            kernel.setPosition(idFleche, new Vector(130, 105));
                            MenuID = 1;
                        }
                    }
                    case KeyEvent.VK_LEFT -> {
                        if (MenuID == 2) {
                            kernel.setPosition(idFleche, new Vector(130, 105));
                            MenuID = 1;
                        }
                    }
                }
            }
        }
    }

    /**
     * tells if a character is a ghost
     *
     * @param id the id of the object
     * @return ghost or not
     */
    public Boolean isGhost(int id) {
        for (IInteractComponent ghost : ghostList) {
            if (ghost.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * tells if the object is a dotscore
     *
     * @param id the id of the object
     * @return dotscore or not
     */
    public Boolean isDotScore(int id) {
        for (IInteractComponent dotscore : DotScores) {
            if (dotscore.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * tells if the object is a teleporter
     *
     * @param id the id of the object
     * @return teleporter or not
     */
    public Boolean isTeleporter(int id) {
        for (IInteractComponent teleporter : Teleporter) {
            if (teleporter.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * tells  if the object is a wall
     *
     * @param id the id of the object
     * @return wall or not
     */
    public Boolean isWall(int id) {
        for (IInteractComponent wall : Walls) {
            if (wall.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * tell if the object if an energizer
     *
     * @param id the id of the object
     * @return energizer or not
     */
    public Boolean isEnergizer(int id) {
        for (IInteractComponent energizer : Energizers) {
            if (energizer.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * refresh the screen at each frame
     */
    public void update(){



        if(state==STATE.PLAY) {
            if (Energizer) {
                for (int i = 0; i < ghostTimer.size(); i++) {
                    ghostTimer.get(i).update();
                }
            }
            else{
                for (int i = 0; i < ghostTimer.size(); i++) {
                    ghostTimer.get(i).update();
                }
            }
            if (Energizer) {
                Energizer = true;
                for (IInteractComponent ghost : ghostList) {
                    SpriteInformation s=new SpriteInformation("sprites/scared ghost.png", 23,23,1);
                    kernel.updateSprite(ghost.getId(),s);

                }
            }
            if (compteur > 1) {
                compteur--;
            }
            if (compteur == 1) {
                Energizer = false;
                SpriteInformation s1=new SpriteInformation("sprites/Inky.png", 23,23,1);
                kernel.updateSprite(ghostList.get(0).getId(),s1);
                SpriteInformation s2=new SpriteInformation("sprites/Clyde.png", 23,23,1);
                kernel.updateSprite(ghostList.get(1).getId(),s2);
                SpriteInformation s3=new SpriteInformation("sprites/Blinky.png", 23,23,1);
                kernel.updateSprite(ghostList.get(2).getId(),s3);
                SpriteInformation s4=new SpriteInformation("sprites/pinky.png", 23,23,1);
                kernel.updateSprite(ghostList.get(3).getId(),s4);
                for (Ghost elem:ghostTimer) {
                    elem.setFlee(false);
                }
            }
            if (compteurSpritePacMan == 4) {
                isOpened = !isOpened;
                String path = "";
                switch (dir) {
                    case LEFT -> path = "sprites/pacman_g.png";
                    case UP -> path = "sprites/pacman_h.png";
                    case DOWN -> path = "sprites/pacman_b.png";
                    case RIGHT -> path = "sprites/pacman_d.png";
                }

                if (isOpened) {
                    SpriteInformation sprite = new SpriteInformation("sprites/pm_f.PNG", 25, 25, 0);
                    kernel.updateSprite(PersonnageActif.getId(), sprite);
                } else {
                    SpriteInformation sprite = new SpriteInformation(path, 25, 25, 0);
                    kernel.updateSprite(PersonnageActif.getId(), sprite);
                }
                compteurSpritePacMan=0;

            }
            compteurSpritePacMan++;
        }
    }

    public void victory(){
        destroyall();
        IInteractComponent victory=new GameObject(idCount++,"sprites/victory.png", 640, 684, 0, 0, 0, true);
        kernel.newObject(victory);
        setState(STATE.WAITING);

    }
    /**
     * Shows the game over screen
     */
    public void gameOver(){
        destroyall();
        IInteractComponent gameover=new GameObject(idCount++,"sprites/gameover.jpg", 640, 684, 0, 0, 0, true);
        kernel.newObject(gameover);
        setState(STATE.WAITING);
    }

    /**
     * Deals with the collision between 2 objects
     *
     * @param id1 the id of the first object
     * @param id2 the id of the second object
     */
    public void collided(int id1, int id2) {


        if (id1 == PersonnageActif.getId() && isTeleporter(id2) && dir == DIR.LEFT) {
            Vector v = new Vector(655, 300);
            kernel.setPosition(id1, v);
        }
        if (id2 == PersonnageActif.getId() && isTeleporter(id1) && dir == DIR.RIGHT) {
            Vector v = new Vector(2, 300);
            kernel.setPosition(id2, v);
        }
        if (id1 == PersonnageActif.getId() && isTeleporter(id2) && dir == DIR.RIGHT) {
            Vector v = new Vector(2, 300);
            kernel.setPosition(id1, v);
        }

        if (isGhost(id1) && isTeleporter(id2)) {
            if (id1 == ghostList.get(0).getId()) {
                Vector v = new Vector(297, 300);
                kernel.setPosition(id1, v);
                ghostTimer.set(0, new Ghost(0, nodeArray[10][8],this));

            } else if (id1 == ghostList.get(1).getId()) {
                Vector v = new Vector(335, 300);
                kernel.setPosition(id1, v);
                ghostTimer.set(1, new Ghost(1, nodeArray[10][9],this));

            } else if (id1 == ghostList.get(2).getId()) {
                Vector v = new Vector(373, 300);
                kernel.setPosition(id1, v);
                ghostTimer.set(2, new Ghost(2, nodeArray[10][10],this));

            } else if (id1 == ghostList.get(3).getId()) {
                Vector v = new Vector(335, 274);
                kernel.setPosition(id1, v);
                ghostTimer.set(3, new Ghost(3, nodeArray[9][9],this));
            }

        }


        /*if (id1 == PersonnageActif.getId() && isDotScore(id2)) { // Mange un dotscore
            kernel.destroy(id2);
            if(dotScore==1){
                victory();
            }
            dotScore--;
        }*/

        /*if (id2 == PersonnageActif.getId() && isDotScore(id1)) { // Mange un dotscore
            kernel.destroy(id1);
            if(dotScore==1){
                victory();
            }
            dotScore--;
        }*/
        if (id1 == PersonnageActif.getId() && isEnergizer(id2)) { // Mange un energizer
            kernel.destroy(id2);
            Energizer = true;
            compteur = 500;
            for (Ghost elem:ghostTimer) {
                elem.setFlee(true);
            }
        }
        if (id2 == PersonnageActif.getId() && isEnergizer(id1)) { // Mange un energizer
            kernel.destroy(id1);
            Energizer = true;
            compteur = 500;
            for (Ghost elem:ghostTimer) {
                elem.setFlee(true);
            }
        }


        if (id1 == PersonnageActif.getId() && !Energizer) {

            if (isWall(id2)) { //Si pacman se prend un mur
                kernel.stepBack(PersonnageActif.getId());
                kernel.setSpeed(PersonnageActif.getId(), Vector.ZERO);
            }

            if (isGhost(id2)) { //Si pacman se fait manger par un fantome
                gameOver();

            }


            if (id1 == PersonnageActif.getId() && isDotScore(id2)) { // Mange un dotscore
                kernel.destroy(id2);
                if(dotScore==1){
                    victory();
                }
                dotScore--;
            }
            if (id2 == PersonnageActif.getId() && isDotScore(id1)) { // Mange un dotscore

                kernel.destroy(id1);
                if(dotScore==1){
                    victory();
                }
                dotScore--;
            }

        }

        if (id1 == PersonnageActif.getId() && Energizer) {

        if (id1 == PersonnageActif.getId() && isDotScore(id2)) { // Mange un dotscore
            kernel.destroy(id2);
            if(dotScore==1){
                victory();
            }
            dotScore--;
        }

        if (id2 == PersonnageActif.getId() && isDotScore(id1)) { // Mange un dotscore
            kernel.destroy(id1);
            if(dotScore==1){
                victory();
            }
            dotScore--;
        }

            if (isWall(id2)) {
                kernel.stepBack(PersonnageActif.getId());
                kernel.setSpeed(PersonnageActif.getId(), Vector.ZERO);
            }

            if (isGhost(id2)) {
                if (id2 == ghostList.get(0).getId()) {
                    Vector v = new Vector(297, 300);
                    kernel.setPosition(id2, v);
                    ghostTimer.set(0, new Ghost(0, nodeArray[10][8],this));
                    ghostTimer.get(0).setFlee(true);

                } else if (id2 == ghostList.get(1).getId()) {
                    Vector v = new Vector(335, 300);
                    kernel.setPosition(id2, v);
                    ghostTimer.set(1, new Ghost(1, nodeArray[10][9],this));
                    ghostTimer.get(0).setFlee(true);

                } else if (id2 == ghostList.get(2).getId()) {
                    Vector v = new Vector(373, 300);
                    kernel.setPosition(id2, v);
                    ghostTimer.set(2, new Ghost(2, nodeArray[10][10],this));
                    ghostTimer.get(0).setFlee(true);

                } else if (id2 == ghostList.get(3).getId()) {
                    Vector v = new Vector(335, 274);
                    kernel.setPosition(id2, v);
                    ghostTimer.set(3, new Ghost(3, nodeArray[9][9],this));
                    ghostTimer.get(0).setFlee(true);
                }

        /*if(isGhost(id1) && id2 == PersonnageActif.getId()){
            if(Energizer){
                Vector v=new Vector(280, 225);
                kernel.setPosition(id1,v);
            }
            else{
                //Fonction loose kernel
            }
        }*/
            }
            System.out.println(dotScore);
        }

    }

    /**
     * Create the map with all the walls, the pac dots and the energizers
     */
    void createMap() {

        int xAdjust=-2;
        int yAdjust=-10;

        int[][] matrice = new int[][]{
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new int[]{0, 2, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 3, 3, 2, 0},
                new int[]{0, 3, 0, 0, 3, 0, 0, 0, 3, 0, 3, 0, 0, 0, 3, 0, 0, 3, 0},
                new int[]{0, 3, 0, 0, 3, 0, 0, 0, 3, 0, 3, 0, 0, 0, 3, 0, 0, 3, 0},
                new int[]{0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0},
                new int[]{0, 3, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 3, 0},
                new int[]{0, 3, 3, 3, 3, 0, 3, 3, 3, 0, 3, 3, 3, 0, 3, 3, 3, 3, 0},
                new int[]{0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 3, 0, 4, 4, 4, 4, 4, 4, 4, 0, 3, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 3, 0, 4, 1, 1, 4, 1, 1, 4, 0, 3, 0, 0, 0, 0},
                new int[]{4, 4, 4, 4, 3, 4, 4, 1, 4, 4, 4, 1, 4, 4, 3, 4, 4, 4, 4},
                new int[]{0, 0, 0, 0, 3, 0, 4, 1, 1, 1, 1, 1, 4, 0, 3, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 3, 0, 4, 4, 4, 4, 4, 4, 4, 0, 3, 0, 0, 0, 0},
                new int[]{0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0},
                new int[]{0, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 3, 3, 3, 0},
                new int[]{0, 3, 0, 0, 3, 0, 0, 0, 3, 0, 3, 0, 0, 0, 3, 0, 0, 3, 0},
                new int[]{0, 3, 3, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 0},
                new int[]{0, 0, 3, 0, 3, 0, 3, 0, 0, 0, 0, 0, 3, 0, 3, 0, 3, 0, 0},
                new int[]{0, 2, 3, 3, 3, 0, 3, 3, 3, 0, 3, 3, 3, 0, 3, 3, 3, 2, 0},
                new int[]{0, 3, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 3, 0},
                new int[]{0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0},
                new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

    /*
    0 mean wall
    1 mean spawn
    2 mean open with Biggum
    3 mean open with small gum
    4 mean open without gum
     */

        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                nodeArray[i][j] = new Node(i, j);
            }
        }

        List<GameObject> gameObjectList = new LinkedList();
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                int[] voisin = new int[4];
                //les voisins sont rangés dans l'ordre haut gauche bas droite
                if (i == 0) {
                    voisin[0] = matrice[matrice.length-1][j];
                    if (matrice[i][j] > 1 && matrice[matrice.length-1][j] > 1) {
                        nodeArray[i][j].addSuccesor(nodeArray[matrice.length-1][j]);
                        nodeArray[matrice.length-1][j].addSuccesor(nodeArray[i][j]);
                    }
                } else {
                    voisin[0] = matrice[i][j];
                    if (matrice[i][j] > 1 && matrice[i - 1][j] > 1) {
                        nodeArray[i][j].addSuccesor(nodeArray[i - 1][j]);
                        nodeArray[i - 1][j].addSuccesor(nodeArray[i][j]);
                    }
                }
                if (j == 0) {
                    voisin[0] = matrice[i][matrice[0].length-1];
                    if (matrice[i][j] > 1 && matrice[i][matrice[0].length-1] > 1) {
                        nodeArray[i][j].addSuccesor(nodeArray[i][matrice[0].length-1]);
                        nodeArray[i][matrice[0].length-1].addSuccesor(nodeArray[i][j]);
                    }
                } else {
                    voisin[1] = matrice[i][j];
                    if (matrice[i][j] > 1 && matrice[i][j - 1] > 1) {
                        nodeArray[i][j].addSuccesor(nodeArray[i][j - 1]);
                        nodeArray[i][j - 1].addSuccesor(nodeArray[i][j]);
                    }
                }
                if (i == matrice.length - 1) {
                    voisin[0] = matrice[0][j];
                } else {
                    voisin[0] = matrice[i][j];
                }
                if (i == matrice[0].length - 1) {
                    voisin[0] = matrice[i][0];
                } else {
                    voisin[0] = matrice[i][j];
                }

                switch (matrice[i][j]) {
                    case 0:
                        GameObject wall=new GameObject(idCount++, "green.png", xScale*1/2, yScale, 2, xScale * j + xMargin +xAdjust , yScale * i + yMargin +yAdjust , false, false, true);
                        gameObjectList.add(wall);
                        Walls.add(wall);

                        break;
                    case 1:
                            GameObject wallb=new GameObject(idCount++, "rouge.png", xScale, yScale*3/4, 2, xScale * j  + xMargin+xAdjust, yScale * i + yMargin+yAdjust, false, false, true);
                            gameObjectList.add(wallb);
                            Walls.add(wallb);
                            break;

                    case 2:

                        GameObject bigdot=new GameObject(idCount++, "sprites/dot.png", Math.round(xScale/2), Math.round(yScale/2), 2, xScale * j + xMargin, yScale * i + yMargin, false, true, true);
                        gameObjectList.add(bigdot);
                        Energizers.add(bigdot);
                        if (voisin[2] == 2 || voisin[2] == 3) {
                            GameObject lildot =new GameObject(idCount++, "sprites/dot.png", Math.round(xScale/3), Math.round(yScale/3), 2, xScale * j + Math.round(xScale/2) + xMargin, yScale * i + yMargin, false, true, true);
                            gameObjectList.add(lildot);
                            DotScores.add(lildot);
                            break;
                        }
                        if (voisin[3] == 2 || voisin[3] == 3) {
                            GameObject lildot2 =new GameObject(idCount++, "sprites/dot.png", Math.round(xScale/3), Math.round(yScale/3), 2, xScale * j  + xMargin, yScale * i + Math.round(yScale/2) + yMargin, false, true, true);
                            gameObjectList.add(lildot2);
                            DotScores.add(lildot2);
                            break;
                        }
                        break;

                    case 3:


                        GameObject lilgum =new GameObject(idCount++, "sprites/dot.png", Math.round(xScale/3), Math.round(yScale/3), 2, xScale * j + xMargin, yScale * i + yMargin, false, true, true);
                        gameObjectList.add(lilgum);
                        DotScores.add(lilgum);
                        if (voisin[2] == 2 || voisin[2] == 3) {
                            GameObject lilgum2=new GameObject(idCount++, "sprites/dot.png", Math.round(xScale/3), Math.round(xScale/3), 2, xScale * j + Math.round(xScale/2) + xMargin, yScale * i + yMargin, false, true, true);
                            gameObjectList.add(lilgum2);
                            DotScores.add(lilgum2);
                            break;
                        }
                        if (voisin[3] == 2 || voisin[3] == 3) {
                            GameObject lilgum3=new GameObject(idCount++, "sprites/dot.png", Math.round(xScale/3), Math.round(yScale/3), 2, xScale * j + xMargin, yScale * i + Math.round(yScale/2) + yMargin, false, true, true);
                            gameObjectList.add(lilgum3);
                            DotScores.add(lilgum3);
                            break;
                        }
                        break;
                    }
                }
            }
        for (IInteractComponent personnage : gameObjectList) {
            kernel.newObject(personnage);
        }

        }
    Node pacManNode(){
//        System.out.println(Math.round((PersonnageActif.getPosition().getY()-(yMargin-20))/(yScale)) +"   "+Math.round((PersonnageActif.getPosition().getX()-(xMargin-20))/(xScale)));
        int x = Math.round((PersonnageActif.getPosition().getY()-(yMargin-17))/(yScale));
        int y = Math.round((PersonnageActif.getPosition().getX()-(xMargin-20))/(xScale-1));
        if (x <= 0){x=1;}
        if (x >= 19){x=17;}
        if (y >= 22){y=20;}
        if (y <= 0){y=0;}
        Node node = nodeArray[x][y];
        if(node.getSuccesor().size() !=0 ){
            return node;
        }
        else{
            if(node.getX() > 0){
                if(nodeArray[node.getX()-1][node.getY()].getSuccesor().size() != 0) {
                    return nodeArray[node.getX() - 1][node.getY()];
                }
            }
            if(node.getY() > 0){
                if(nodeArray[node.getX()][node.getY()-1].getSuccesor().size() != 0){
                    return nodeArray[node.getX()][node.getY()-1];
                }
            }
            if(node.getX() < nodeArray.length){
                if(nodeArray[node.getX()+1][node.getY()].getSuccesor().size() != 0){
                    return nodeArray[node.getX()+1][node.getY()];
                }
            }
            if(node.getY() < nodeArray[0].length){
                if(nodeArray[node.getX()][node.getY()+1].getSuccesor().size() != 0) {
                    return nodeArray[node.getX()][node.getY() + 1];
                }
            }
            return node;
        }
    }

    public Node nextNode(Node from, int n){
        return kernel.directionTo(from, pacManNode(), n);
    }

    public void takeDirection(DIR direction, int n) {
        switch (direction) {
            case UP -> {kernel.setSpeed(ghostList.get(n).getId(), new Vector(0,-2));
            }
            case DOWN -> {kernel.setSpeed(ghostList.get(n).getId(), new Vector(0,2));
            }
            case LEFT -> {kernel.setSpeed(ghostList.get(n).getId(), new Vector(-2,0));
            }
            case RIGHT -> {kernel.setSpeed(ghostList.get(n).getId(), new Vector(2,0));
            }
            case IMM -> {kernel.setSpeed(ghostList.get(n).getId(), Vector.ZERO);
            }
        }
    }
}