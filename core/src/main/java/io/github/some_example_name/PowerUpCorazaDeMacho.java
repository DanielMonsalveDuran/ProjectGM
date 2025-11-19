package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class PowerUpCorazaDeMacho extends PowerUp {

    private static final float DURACION_CORAZA = 6f;
    private static final int BONUS_AUTOESTIMA = 8;

    public PowerUpCorazaDeMacho(Texture textura, float x, float y) {
        super(textura, x, y,
              DURACION_CORAZA,
              "Coraza de Macho",
              "Inmune a recuerdos por " + DURACION_CORAZA + "s",
              false,
              BONUS_AUTOESTIMA);
    }

    @Override
    protected void aplicarEfectosEspeciales(Carlos carlos) {
        carlos.activarCoraza(getDuracion());
    }
}