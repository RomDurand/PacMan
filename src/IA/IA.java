package IA;

import Gameplay.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class IA {
    /**
     * cherche le prochain noeud à atteindre pour arriver àux noeud to
     * par le plus court chemin
     * @param from noeud de Départ
     * @param To noeud d'arrivée
     * @return le prochain noeud pour arriver à to
     */
    static public Node plusCourtChemin(Node from, Node To){
        HashMap<Node, Node> choix = new HashMap();
        List<Node> parcouru = new ArrayList<>();
        List<Node> traitement = new ArrayList<>(){};
        parcouru.add(from);

        for (Node son:from.getSuccesor()) {
            if (son == To){
                return son;
            }
            choix.put(son, son);
            traitement.add(son);

        }
        while(!traitement.isEmpty()){
            Node node = traitement.get(0);
            parcouru.add(node);
            traitement.remove(0);

            for (Node son: node.getSuccesor()) {
                if (son == To){
                    return choix.get(node);
                }
                Boolean isIn = false;
                for (Node parc:parcouru) {
                    if(son == parc){
                        isIn = true;
                    }
                }
                if(!isIn){
                    traitement.add(son);
                    choix.put(son, choix.get(node));
                }
            }
        }
        return from;
    }

    /**
     * cherche le prochain noeud à atteindre pour arriver àux noeud to
     * en prenant un des chemin obtenu par parcours en longueur
     * @param from noeud de Départ
     * @param To noeud d'arrivée
     * @return le prochain noeud pour arriver à to
     */
    static public Node plusCourtCheminRandom(Node from, Node To){
        HashMap<Node, Node> choix = new HashMap();
        List<Node> parcouru = new ArrayList<>();
        List<Node> traitement = new ArrayList<>(){};
        List<Node> candidat = new ArrayList<>(){};
        parcouru.add(from);

        for (Node son:from.getSuccesor()) {
            if (son == To){
                return son;
            }
            choix.put(son, son);
            traitement.add(son);

        }
        while(!traitement.isEmpty()){
            Node node = traitement.get(0);
            parcouru.add(node);
            traitement.remove(0);

            for (Node son: node.getSuccesor()) {
                if (son == To){
                    candidat.add(choix.get(node));
                }
                Boolean isIn = false;
                for (Node parc:parcouru) {
                    if(son == parc){
                        isIn = true;
                    }
                }
                if(!isIn){
                    traitement.add(son);
                    choix.put(son, choix.get(node));
                }
            }
        }
        Random rand = new Random();
        int randomNum = rand.nextInt(candidat.size());
        return candidat.get(randomNum);
    }

    /**
     * cherche le prochain noeud à atteindre pour arriver àux noeud to
     * en prenant le dernier chemin obtenu par parcours en longueur
     * @param from noeud de Départ
     * @param To noeud d'arrivée
     * @return le prochain noeud pour arriver à to
     */
    static public Node plusCourtCheminLefty(Node from, Node To){
        HashMap<Node, Node> choix = new HashMap();
        List<Node> parcouru = new ArrayList<>();
        List<Node> traitement = new ArrayList<>(){};
        List<Node> candidat = new ArrayList<>(){};
        parcouru.add(from);

        for (Node son:from.getSuccesor()) {
            if (son == To){
                return son;
            }
            choix.put(son, son);
            traitement.add(son);

        }
        while(!traitement.isEmpty()){
            Node node = traitement.get(0);
            parcouru.add(node);
            traitement.remove(0);

            for (Node son: node.getSuccesor()) {
                if (son == To){
                    candidat.add(choix.get(node));
                }
                Boolean isIn = false;
                for (Node parc:parcouru) {
                    if(son == parc){
                        isIn = true;
                    }
                }
                if(!isIn){
                    traitement.add(son);
                    choix.put(son, choix.get(node));
                }
            }
        }
        return candidat.get(candidat.size()-1);
    }

    /**
     * cherche le prochain noeud à atteindre pour arriver àux noeud to
     * en prenant un chemin qui mène à to ou n'est pas une impasse
     * @param from noeud de Départ
     * @param To noeud d'arrivée
     * @return le prochain noeud pour arriver à to
     */
    static public Node plusCourtCheminMoreRandom(Node from, Node To){
        HashMap<Node, Node> choix = new HashMap();
        List<Node> parcouru = new ArrayList<>();
        List<Node> traitement = new ArrayList<>(){};
        List<Node> candidat = new ArrayList<>(){};
        parcouru.add(from);

        for (Node son:from.getSuccesor()) {
            if (son == To){
                return son;
            }
            choix.put(son, son);
            traitement.add(son);

        }
        while(!traitement.isEmpty()){
            Node node = traitement.get(0);
            parcouru.add(node);
            traitement.remove(0);

            for (Node son: node.getSuccesor()) {
                if (son == To){
                    candidat.add(choix.get(node));
                }
                Boolean isIn = false;
                for (Node parc:parcouru) {
                    if(son == parc){
                        candidat.add(choix.get(node));
                        isIn = true;
                    }
                }
                if(!isIn){
                    traitement.add(son);
                    choix.put(son, choix.get(node));
                }
            }
        }
        Random rand = new Random();
        int randomNum = rand.nextInt(candidat.size());
        return candidat.get(randomNum);
    }

    /**
     * cherche le prochain noeud à atteindre completement au hasard
     * @param from noeud de Départ
     * @return le prochain noeud pour arriver à to
     */
    public static Node completeRandom(Node from) {
        List<Node> candidat = new ArrayList<>();
        for (Node node:from.getSuccesor()) {
            candidat.add(node);
        }
        Random rand = new Random();
        int randomNum = rand.nextInt(candidat.size());
        return candidat.get(randomNum);
    }
}
