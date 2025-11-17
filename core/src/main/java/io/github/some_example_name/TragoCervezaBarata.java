package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

/**
 * TragoCervezaBarata - Bebida suave, efecto moderado
 */
public class TragoCervezaBarata extends Trago {
    private static final int POTENCIA_ALCOHOLICA = 8;    // Menos ebriedad
    private static final int BOOST_AUTOESTIMA = 3;       // Menos autoestima
    private static final int PUNTOS_SCORE = 30;          // Menos puntos
    
    public TragoCervezaBarata(Texture textura, float x, float y) {
        super(textura, x, y, POTENCIA_ALCOHOLICA, BOOST_AUTOESTIMA, PUNTOS_SCORE);
    }
}
