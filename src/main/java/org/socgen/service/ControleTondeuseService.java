package org.socgen.service;

import org.socgen.exception.TondeuseException;
import org.socgen.model.Coordonnees;
import org.socgen.model.Deplacement;
import org.socgen.model.Orientation;
import org.socgen.model.PositionTondeuse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.System.out;

public class ControleTondeuseService {
    private static final String OLD_SEPARATOR = " ";
    private static final String NEW_SEPARATOR = "";
    private static final String UNEXPECTED_VALUE = "Unexpected value: ";

    public List<PositionTondeuse> realiserDeplacementParFichier(String filepath) {
        try (Stream<String> stream = Files.lines(Paths.get(filepath))) {
            List<PositionTondeuse> positionTondeuseList = new ArrayList<>();
            List<String> allEntries = stream.toList();

            Coordonnees coordonneesMax = findMaxCoordonnees(allEntries.get(0));

            allEntries.subList(1, allEntries.size()).forEach(s -> {
                int index = allEntries.indexOf(s);
                if (index % 2 == 0) {
                    String lignePositionInitiale = allEntries.get(index - 1).replace(OLD_SEPARATOR, NEW_SEPARATOR);
                    PositionTondeuse positionTondeuse = new PositionTondeuse(new Coordonnees(
                            Integer.parseInt(lignePositionInitiale.substring(0, 1)),
                            Integer.parseInt(lignePositionInitiale.substring(1, 2))),
                            Orientation.valueOf(lignePositionInitiale.substring(2, 3)));


                    String ligneDeplacements = allEntries.get(index).replace(OLD_SEPARATOR, NEW_SEPARATOR);
                    for (char character : ligneDeplacements.toCharArray()) {
                        if (Deplacement.G.name().equals(String.valueOf(character))) {
                            positionTondeuse = deplacementGauche(positionTondeuse);
                        } else if (Deplacement.A.name().equals(String.valueOf(character))) {
                            positionTondeuse = deplacementAvant(positionTondeuse, coordonneesMax);
                        } else if (Deplacement.D.name().equals(String.valueOf(character))) {
                            positionTondeuse = deplacementDroite(positionTondeuse);
                        } else throw new IllegalStateException(UNEXPECTED_VALUE + character);
                    }
                    positionTondeuseList.add(positionTondeuse);
                    out.print(positionTondeuse);
                }
            });
            return positionTondeuseList;
        } catch (IOException e) {
            throw new TondeuseException(e);
        }
    }

    public PositionTondeuse deplacementGauche(PositionTondeuse positionTondeuse) {
        PositionTondeuse newPosition = new PositionTondeuse(positionTondeuse.getCoordonnees(),
                positionTondeuse.getOrientation());
        switch (positionTondeuse.getOrientation()) {
            case N -> newPosition.setOrientation(Orientation.W);
            case E -> newPosition.setOrientation(Orientation.N);
            case S -> newPosition.setOrientation(Orientation.E);
            case W -> newPosition.setOrientation(Orientation.S);
        }
        return newPosition;
    }

    public PositionTondeuse deplacementDroite(PositionTondeuse positionTondeuse) {
        PositionTondeuse newPosition = new PositionTondeuse(positionTondeuse.getCoordonnees(),
                positionTondeuse.getOrientation());
        switch (positionTondeuse.getOrientation()) {
            case N -> newPosition.setOrientation(Orientation.E);
            case E -> newPosition.setOrientation(Orientation.S);
            case S -> newPosition.setOrientation(Orientation.W);
            case W -> newPosition.setOrientation(Orientation.N);
        }
        return newPosition;
    }

    public PositionTondeuse deplacementAvant(PositionTondeuse positionTondeuse,
                                             Coordonnees coordonneesSuperieurDroite) {

        PositionTondeuse newPosition = new PositionTondeuse(positionTondeuse.getCoordonnees(),
                positionTondeuse.getOrientation());
        Coordonnees nextCoordonnees = findNextCoordonneesByOrientation(positionTondeuse);
        if (hasPositionTondeuseDansPelouse(nextCoordonnees, coordonneesSuperieurDroite)) {
            newPosition.setCoordonnees(nextCoordonnees);
        }
        return newPosition;
    }

    private Coordonnees findNextCoordonneesByOrientation(PositionTondeuse positionTondeuse) {
        switch (positionTondeuse.getOrientation()) {
            case N -> {
                return new Coordonnees(
                        positionTondeuse.getCoordonnees().getX(),
                        positionTondeuse.getCoordonnees().getY() + 1);
            }

            case E -> {
                return new Coordonnees(
                        positionTondeuse.getCoordonnees().getX() + 1,
                        positionTondeuse.getCoordonnees().getY());
            }

            case S -> {
                return new Coordonnees(
                        positionTondeuse.getCoordonnees().getX(),
                        positionTondeuse.getCoordonnees().getY() - 1);
            }
            case W -> {
                return new Coordonnees(
                        positionTondeuse.getCoordonnees().getX() - 1,
                        positionTondeuse.getCoordonnees().getY());
            }
            default -> throw new IllegalStateException(UNEXPECTED_VALUE + positionTondeuse.getOrientation());
        }
    }

    private boolean hasPositionTondeuseDansPelouse(Coordonnees nouvellesCoordonnees,
                                                   Coordonnees coordonneesSuperieurDroite) {
        Coordonnees coordonneesInferieurGauche = new Coordonnees(0, 0);

        return nouvellesCoordonnees.getX() >= coordonneesInferieurGauche.getX()
                && nouvellesCoordonnees.getY() >= coordonneesInferieurGauche.getY()
                && nouvellesCoordonnees.getX() <= coordonneesSuperieurDroite.getX()
                && nouvellesCoordonnees.getY() <= coordonneesSuperieurDroite.getY();
    }

    private Coordonnees findMaxCoordonnees(String s) {
        Coordonnees newCoordonnees = new Coordonnees();
        if (s != null && s.length() == 3) {
            String line = s.replace(OLD_SEPARATOR, NEW_SEPARATOR);
            newCoordonnees.setX(Integer.parseInt(line.substring(0, 1)));
            newCoordonnees.setY(Integer.parseInt(line.substring(1, 2)));
        }
        return newCoordonnees;
    }

}
