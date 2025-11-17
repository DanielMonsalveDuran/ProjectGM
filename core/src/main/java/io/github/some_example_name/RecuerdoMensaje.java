package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

/**
 * RecuerdoMensaje - Provoca el estado de Negociación por 10s
 */
public class RecuerdoMensaje extends Recuerdo{

    private static final int DANIO_AUTOESTIMA = 10; // Mantenido el -10 original

    public RecuerdoMensaje(Texture textura, float x, float y) {
        super(textura, x, y, DANIO_AUTOESTIMA);
    }
    
    public static int getDanioAutoestima() {
        return DANIO_AUTOESTIMA;
    }
}