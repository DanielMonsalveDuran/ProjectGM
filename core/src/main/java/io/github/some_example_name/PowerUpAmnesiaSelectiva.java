package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class PowerUpAmnesiaSelectiva extends PowerUp {

    private static final float SCORE_REDUCCION_PORCENTAJE = 0.07f; // 7%
    private static final int EBRIEDAD_REDUCCION = 12;

    public PowerUpAmnesiaSelectiva(Texture textura, float x, float y) {
        super(textura, x, y,
              0.1f,
              "Amnesia Selectiva",
              "Borra 7% del score, autoestima al 100 y baja ebriedad",
              true,
              0);
    }

    @Override
    protected void aplicarEfectosEspeciales(Carlos carlos) {
        // Reducir score
        int scoreActual = carlos.getScore();
        int reduccion = (int) (scoreActual * SCORE_REDUCCION_PORCENTAJE);
        carlos.reducirScore(reduccion);

        // Reset autoestima a 100
        int faltante = 100 - carlos.getAutoestima();
        if (faltante > 0) carlos.sumarAutoestima(faltante);

        // Bajar ebriedad
        carlos.reducirEbriedad(EBRIEDAD_REDUCCION);
    }
}