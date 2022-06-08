package org.socgen;

import org.socgen.service.ControleTondeuseService;

public class TondeuseApp {
    private static final ControleTondeuseService CONTROLE_TONDEUSE_SERVICE = new ControleTondeuseService();


    public static void main(String[] args) {
        String fileName = "src/main/resources/lines.txt";

        CONTROLE_TONDEUSE_SERVICE.realiserDeplacementParFichier(fileName);
    }


}
