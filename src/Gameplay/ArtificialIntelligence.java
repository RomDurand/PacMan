package Gameplay;
import Graphic.SpriteInformation;
import Physics.Vector;

import java.util.ArrayList;

public class ArtificialIntelligence<Step> extends Gameplay{

    IKernelFromGamplay kernel;
    private ArrayList<IInteractComponent> listePersonnage;
    public ArtificialIntelligence(IKernelFromGamplay kernel) {
        super(kernel);
    }

    public ArrayList possibleSteps;

    public void iaRandomMovement(){
        kernel.setSpeed(artificialIntelligenceID, new Vector(1, 0));
    }

    public void iaSeMetDevantPacMan(){}

    public void iaPlusCourtChemin(){
        if(listePersonnage.get(PersonnageActif.getId()).getPosition().getX() > listePersonnage.get(artificialIntelligenceID).getPosition().getX() +5 )
            kernel.setSpeed(artificialIntelligenceID, new Vector(1, 0));
        if(listePersonnage.get(PersonnageActif.getId()).getPosition().getX() < listePersonnage.get(artificialIntelligenceID).getPosition().getX() - 5 )
            kernel.setSpeed(artificialIntelligenceID, new Vector(-1, 0));
        if(listePersonnage.get(PersonnageActif.getId()).getPosition().getY() > listePersonnage.get(artificialIntelligenceID).getPosition().getY() + 5)
            kernel.setSpeed(artificialIntelligenceID, new Vector(0, 1));
        if(listePersonnage.get(PersonnageActif.getId()).getPosition().getY() < listePersonnage.get(artificialIntelligenceID).getPosition().getY() - 5 )
            kernel.setSpeed(artificialIntelligenceID, new Vector(0, -1));
        if(Math.abs(listePersonnage.get(PersonnageActif.getId()).getPosition().getX() - listePersonnage.get(artificialIntelligenceID).getPosition().getX()) < 100 && Math.abs(listePersonnage.get(PersonnageActif.getId()).getPosition().getY() - listePersonnage.get(artificialIntelligenceID).getPosition().getY()) < 100)
            kernel.setSpeed(artificialIntelligenceID, Vector.ZERO);
    }

    public void EnergizerGhost(){
        //fuir pacman
    }
}
