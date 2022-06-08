package org.socgen.service;

import org.junit.jupiter.api.Test;
import org.socgen.exception.TondeuseException;
import org.socgen.model.Coordonnees;
import org.socgen.model.Orientation;
import org.socgen.model.PositionTondeuse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControleTondeuseServiceTest {
    private final ControleTondeuseService controleTondeuseService = new ControleTondeuseService();

    @Test
    void should_move_to_est_when_deplacement_gauche_and_orientation_south() {
        PositionTondeuse positionTondeuse = new PositionTondeuse(new Coordonnees(0, 0), Orientation.S);

        PositionTondeuse actual = controleTondeuseService.deplacementGauche(positionTondeuse);

        assertEquals(Orientation.E, actual.getOrientation());
    }


    @Test
    void should_move_to_west_when_deplacement_gauche_and_orientation_north() {
        PositionTondeuse positionTondeuse = new PositionTondeuse(new Coordonnees(0, 0), Orientation.N);

        PositionTondeuse actual = controleTondeuseService.deplacementGauche(positionTondeuse);

        assertEquals(Orientation.W, actual.getOrientation());
    }


    @Test
    void should_move_to_north_when_deplacement_gauche_and_orientation_est() {
        PositionTondeuse positionTondeuse = new PositionTondeuse(new Coordonnees(0, 0), Orientation.E);

        PositionTondeuse actual = controleTondeuseService.deplacementGauche(positionTondeuse);

        assertEquals(Orientation.N, actual.getOrientation());
    }


    @Test
    void should_move_to_south_when_deplacement_gauche_and_orientation_west() {
        PositionTondeuse positionTondeuse = new PositionTondeuse(new Coordonnees(0, 0), Orientation.W);

        PositionTondeuse actual = controleTondeuseService.deplacementGauche(positionTondeuse);

        assertEquals(Orientation.S, actual.getOrientation());
    }


    @Test
    void should_move_to_west_when_deplacement_droite_and_orientation_south() {
        PositionTondeuse positionTondeuse = new PositionTondeuse(new Coordonnees(0, 0), Orientation.S);

        PositionTondeuse actual = controleTondeuseService.deplacementDroite(positionTondeuse);

        assertEquals(Orientation.W, actual.getOrientation());
    }

    @Test
    void should_move_to_est_when_deplacement_droite_and_orientation_north() {
        PositionTondeuse positionTondeuse = new PositionTondeuse(new Coordonnees(0, 0), Orientation.N);

        PositionTondeuse actual = controleTondeuseService.deplacementDroite(positionTondeuse);

        assertEquals(Orientation.E, actual.getOrientation());
    }

    @Test
    void should_move_to_south_when_deplacement_droite_and_orientation_est() {
        PositionTondeuse positionTondeuse = new PositionTondeuse(new Coordonnees(0, 0), Orientation.E);

        PositionTondeuse actual = controleTondeuseService.deplacementDroite(positionTondeuse);

        assertEquals(Orientation.S, actual.getOrientation());
    }

    @Test
    void should_move_to_north_when_deplacement_droite_and_orientation_west() {
        PositionTondeuse positionTondeuse = new PositionTondeuse(new Coordonnees(0, 0), Orientation.W);

        PositionTondeuse actual = controleTondeuseService.deplacementDroite(positionTondeuse);

        assertEquals(Orientation.N, actual.getOrientation());
    }

    @Test
    void should_move_when_deplacement_avant_and_new_position_in_surface() {
        PositionTondeuse positionTondeuse = new PositionTondeuse(new Coordonnees(0, 0), Orientation.N);
        Coordonnees coordonnesSuperieureDroite = new Coordonnees(5, 5);
        PositionTondeuse actual = controleTondeuseService.deplacementAvant(positionTondeuse, coordonnesSuperieureDroite);

        assertEquals(Orientation.N, actual.getOrientation());
        assertEquals(1, actual.getCoordonnees().getY());
    }


    @Test
    void should_not_move_when_deplacement_avant_and_new_position_not_in_surface() {
        PositionTondeuse positionTondeuse = new PositionTondeuse(new Coordonnees(0, 0), Orientation.S);
        Coordonnees coordonneesSuperieureDroite = new Coordonnees(5, 5);
        PositionTondeuse actual = controleTondeuseService.deplacementAvant(positionTondeuse, coordonneesSuperieureDroite);

        assertEquals(Orientation.S, actual.getOrientation());
        assertEquals(0, actual.getCoordonnees().getY());
    }


    @Test
    void should_retrieve_tondeuse_exception_when_issue_with_file_path_provided() {
        assertThrows(TondeuseException.class, () -> controleTondeuseService.realiserDeplacementParFichier(""));
    }

    @Test
    void should_execute_deplacement_by_filepath() {
        String filePath = "src/test/resources/lines.txt";
        List<PositionTondeuse> positions = controleTondeuseService.realiserDeplacementParFichier(filePath);
        assertEquals(2, positions.size());
        assertEquals(1, positions.get(0).getCoordonnees().getX());
        assertEquals(3, positions.get(0).getCoordonnees().getY());
        assertEquals(Orientation.N, positions.get(0).getOrientation());
        assertEquals(5, positions.get(1).getCoordonnees().getX());
        assertEquals(1, positions.get(1).getCoordonnees().getY());
        assertEquals(Orientation.E, positions.get(1).getOrientation());
    }

    @Test
    void should_retrieve_exception_when_illegal_deplacement() {
        String filePath = "src/test/resources/badlinesdeplacement.txt";
        assertThrows(IllegalStateException.class, () -> controleTondeuseService.realiserDeplacementParFichier(filePath));
    }

    @Test
    void should_retrieve_exception_when_illegal_orientation() {
        String filePath = "src/test/resources/badlinesorientation.txt";
        assertThrows(IllegalArgumentException.class, () -> controleTondeuseService.realiserDeplacementParFichier(filePath));
    }
}