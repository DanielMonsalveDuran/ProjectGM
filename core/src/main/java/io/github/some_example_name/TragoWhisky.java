package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

/**
 * TragoWhisky - Bebida fuerte, efecto considerable
 */
public class TragoWhisky extends Trago {
    private static final int POTENCIA_ALCOHOLICA = 15;   // Ebriedad media
    private static final int BOOST_AUTOESTIMA = 8;       // Autoestima media
    private static final int PUNTOS_SCORE = 75;          // Puntos medios
    
    public TragoWhisky(Texture textura, float x, float y) {
        super(textura, x, y, POTENCIA_ALCOHOLICA, BOOST_AUTOESTIMA, PUNTOS_SCORE);
    }
}
