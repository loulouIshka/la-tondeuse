package org.socgen.model;

public class PositionTondeuse {
    private static final String SEPARATOR = " ";
    private Coordonnees coordonnees;
    private Orientation orientation;

    public PositionTondeuse(Coordonnees coordonnees, Orientation orientation) {
        this.coordonnees = coordonnees;
        this.orientation = orientation;
    }

    public Coordonnees getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(Coordonnees coordonnees) {
        this.coordonnees = coordonnees;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public String toString() {
        return coordonnees.getX() + SEPARATOR + coordonnees.getY() + SEPARATOR + orientation.toString() + SEPARATOR;
    }
}
