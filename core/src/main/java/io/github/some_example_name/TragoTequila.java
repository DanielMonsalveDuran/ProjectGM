package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

/**
 * TragoTequila - Bebida muy fuerte, efecto potente
 */
public class TragoTequila extends Trago {
    private static final int POTENCIA_ALCOHOLICA = 25;   // Máxima ebriedad
    private static final int BOOST_AUTOESTIMA = 12;      // Máxima autoestima
    private static final int PUNTOS_SCORE = 100;         // Máximos puntos
    
    public TragoTequila(Texture textura, float x, float y) {
        super(textura, x, y, POTENCIA_ALCOHOLICA, BOOST_AUTOESTIMA, PUNTOS_SCORE);
    }
}
