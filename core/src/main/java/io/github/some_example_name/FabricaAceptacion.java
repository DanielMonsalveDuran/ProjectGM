package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * FÁBRICA CONCRETA - Estado de ACEPTACIÓN
 * Mundo de claridad y tentación final - estado de victoria
 * ENFOQUE SIMPLIFICADO: Objetos especiales para la prueba final
 */
public class FabricaAceptacion implements FabricaDeTragedias {
    
    private CargarArchivos loader;
    
    public FabricaAceptacion() {
        this.loader = CargarArchivos.getInstance();
    }
    
    @Override
    public Trago crearTrago(float x, float y) {
        // En ACEPTACIÓN, los tragos se convierten en objetos de claridad
        // Reutilizamos TragoCervezaBarata pero con efectos completamente diferentes
        TragoCervezaBarata claridad = new TragoCervezaBarata(loader.getTragoCervezaTexture(), x, y);
        claridad.setPotenciaAlcoholica(0);    // Sin ebriedad - Carlos está sobrio
        claridad.setBoostAutoestima(8);       // Autoestima pura
        claridad.setPuntosScore(60);          // Puntos por crecimiento personal
        return claridad;
    }
    
    @Override
    public Recuerdo crearRecuerdo(float x, float y) {
        // En ACEPTACIÓN, los recuerdos son tentaciones doradas
        // Reutilizamos RecuerdoFoto pero con mecánica especial
        RecuerdoFoto tentacion = new RecuerdoFoto(loader.getRecuerdoFotoTexture(), x, y);
        tentacion.setDanioEmocional(25);      // Daño muy alto por recaer
        return tentacion;
    }
    
    @Override
    public PowerUp crearPowerUp(float x, float y) {
        // Power-ups de crecimiento en ACEPTACIÓN
        int tipoPowerUp = MathUtils.random(0, 2);
        switch(tipoPowerUp) {
            case 0: return new PowerUpAmnesiaSelectiva(loader.getAmnesiaTexture(), x, y);
            case 1: return new PowerUpCorazaDeMacho(loader.getCorazaTexture(), x, y);
            default: return new PowerUpAutotuneEmocional(loader.getAutotuneTexture(), x, y);
        }
    }
    
    @Override
    public String getDescripcionAmbientacion() {
        return "La claridad duele... pero libera. Resiste la tentación final para ganar.";
    }
    
    @Override
    public float getMultiplicadorVelocidad() {
        return 1.1f; // Ligero aumento - mundo "claro pero desafiante"
    }
}
