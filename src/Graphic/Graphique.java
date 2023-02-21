package Graphic;

import Gameplay.IGetterComponent;
import Noyau.IGraphique;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Graphique implements IGraphique {
    final private JFrame frame;
    private HashMap<Integer, JLabel> backgroundLabel = new HashMap<>();
    private HashMap<Integer, IGetterComponent> movingGameObject = new HashMap<>();
    private HashMap<Integer, JLabel> movingLabel = new HashMap<>();
    private ArrayList<Integer> depthIndex = new ArrayList<>();

    public Graphique(int width, int height, JFrame frame){

        this.frame = frame;
        this.frame.setSize(width,height);
        this.frame.setLayout(null);
        this.frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    public void drawFrame() {

        for (int id : movingLabel.keySet()) {
            movingLabel.get(id).setLocation(movingGameObject.get(id).getPosition().getX(), movingGameObject.get(id).getPosition().getY());
        }
        frame.repaint();
    }

    public void stopFrame(){
        frame.dispose();
    }

    public void addGameObject(IGetterComponent personnage) {
        JLabel label = labelFromGameObject(personnage);
        if (personnage.isMobile()){
            movingGameObject.put(personnage.getId(), personnage);
            movingLabel.put(personnage.getId(), label);
        }
        else{
            backgroundLabel.put(personnage.getId(), label);
        }
        frame.validate();
        frame.repaint();
    }

    public void removeGameObject(int id) {
        if (backgroundLabel.containsKey(id)){
            frame.remove(backgroundLabel.get(id));
            backgroundLabel.remove(id, backgroundLabel.get(id));
            depthIndex.remove(depthIndex.size()-1);
        }
        if (movingLabel.containsKey(id)){
            frame.remove(movingLabel.get(id));
            movingLabel.remove(id, movingLabel.get(id));
            depthIndex.remove(depthIndex.size()-1);
        }
    }

    public void removeAll() {
        backgroundLabel = new HashMap<>();
        movingLabel = new HashMap<>();
        movingGameObject = new HashMap<>();
        frame.revalidate();
        frame.repaint();
        depthIndex = new ArrayList<>();
    }

    public void changeMobilityTo(IGetterComponent personnage, boolean b) {
        removeGameObject(personnage.getId());
        addGameObject(personnage);
    }




    private JLabel labelFromGameObject(IGetterComponent object){
        ImageIcon image = new ImageIcon("resources/" + object.getSprite().path());
        image = new ImageIcon(image.getImage().getScaledInstance(object.getSprite().weight(), object.getSprite().height(), Image.SCALE_DEFAULT));
        JLabel label = new JLabel(image);
        label.setSize(object.getSprite().weight(), object.getSprite().height());
        label.setLocation(object.getPosition().getX(), object.getPosition().getY());
        int i = newObjectAtDepth(object.getSprite().depth());
        frame.add(label);
        frame.getContentPane().setComponentZOrder(label, i);
        return label;
    }

    private int newObjectAtDepth(int depth){
        for (int i = 0; i < depthIndex.size(); i++) {
            if (depth <= depthIndex.get(i)) {
                depthIndex.add(i, depth);
                return i;
            }
        }
        depthIndex.add(depth);
        return depthIndex.size()-1;
    }

    public void updateSprite(int id) {
        IGetterComponent gameObject = movingGameObject.get(id);
        ImageIcon image = new ImageIcon("resources/" + gameObject.getSprite().path());
        image = new ImageIcon(image.getImage().getScaledInstance(gameObject.getSprite().weight(), gameObject.getSprite().height(), Image.SCALE_DEFAULT));
        movingLabel.get(id).setIcon(image);
    }
}


