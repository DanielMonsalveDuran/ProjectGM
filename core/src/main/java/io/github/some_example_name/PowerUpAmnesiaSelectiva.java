package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

/**
 * Power-up Amnesia Selectiva - Efecto instantáneo
 * Extiende de PowerUp e implementa la interfaz AfectableEmocionalmente.
 */
public class PowerUpAmnesiaSelectiva extends PowerUp{

    private static final float SCORE_REDUCCION_PORCENTAJE = 0.05f; // 5%
    private static final int EBRIEDAD_REDUCCION = 10;
    
    public PowerUpAmnesiaSelectiva(Texture textura, float x, float y) {
        super(textura, x, y,
              0.1f,                                   // Duración mínima (es instantáneo)
              "Amnesia Selectiva",                    // Nombre
              "Borra el 5% del score, reinicia la autoestima y baja la ebriedad.", // Descripción
              true,                                   // ES INSTANTÁNEO
              0);                                     // Autoestima base (el efecto principal la pone a 100)
    }

    @Override
    public void aplicarEfectoPowerUp(Carlos carlos) {
        // 1. Reducir Score en un 5%
        int scoreActual = carlos.getScore();
        int scoreReduccion = (int) (scoreActual * SCORE_REDUCCION_PORCENTAJE);
        carlos.reducirScore(scoreReduccion); // Usa el nuevo método en Carlos
        
        // 2. Subir Autoestima a 100
        int aumentoAutoestima = 100 - carlos.getAutoestima();
        carlos.sumarAutoestima(aumentoAutoestima);
        
        // 3. Bajar Ebriedad en 10
        carlos.reducirEbriedad(EBRIEDAD_REDUCCION); // Usa el nuevo método en Carlos
    }
    
    // Sobreescritura de PowerUp para efectos instantáneos
    @Override
    protected void actualizarDuracion() {}
    
    @Override
    public void desactivarPowerUp(Carlos carlos) {
        // No hace nada porque es instantáneo
    }
    
    public static float getScoreReduccionPorcentaje() {
        return SCORE_REDUCCION_PORCENTAJE;
    }
    
    public static int getEbriedadReduccion() {
        return EBRIEDAD_REDUCCION;
    }
}