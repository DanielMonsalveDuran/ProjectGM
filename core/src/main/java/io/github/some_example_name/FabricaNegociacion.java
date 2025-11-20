package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * FÁBRICA CONCRETA - Estado de NEGOCIACIÓN
 * Mundo táctico donde el riesgo y recompensa se balancean
 * ENFOQUE SIMPLIFICADO: Valores balanceados estratégicamente
 */
public class FabricaNegociacion implements FabricaDeTragedias {
    
    private CargarArchivos loader;
    
    public FabricaNegociacion() {
        this.loader = CargarArchivos.getInstance();
    }
    
    @Override
    public Trago crearTrago(float x, float y) {
        // Tragos con efectos balanceados para NEGOCIACIÓN
        int tipoTrago = MathUtils.random(0, 2);
        Trago trago;
        
        switch(tipoTrago) {
            case 0: 
                trago = new TragoCervezaBarata(loader.getTragoCervezaTexture(), x, y);
                // Valores cercanos a originales pero ligeramente ajustados
                trago.setPotenciaAlcoholica(7);
                trago.setBoostAutoestima(3);
                break;
            case 1:
                trago = new TragoWhisky(loader.getTragoWhiskyTexture(), x, y);
                trago.setPotenciaAlcoholica(16);
                trago.setBoostAutoestima(7);
                break;
            default:
                trago = new TragoTequila(loader.getTragoTequilaTexture(), x, y);
                trago.setPotenciaAlcoholica(26);
                trago.setBoostAutoestima(11);
                break;
        }
        
        return trago;
    }
    
    @Override
    public Recuerdo crearRecuerdo(float x, float y) {
        // Recuerdos con daño moderado para NEGOCIACIÓN
        int tipoRecuerdo = MathUtils.random(0, 2);
        Recuerdo recuerdo;
        
        switch(tipoRecuerdo) {
            case 0: 
                recuerdo = new RecuerdoFoto(loader.getRecuerdoFotoTexture(), x, y);
                recuerdo.setDanioEmocional(14); // Ligeramente reducido
                break;
            case 1:
                recuerdo = new RecuerdoCarta(loader.getRecuerdoCartaTexture(), x, y);
                recuerdo.setDanioEmocional(24); // Ligeramente reducido
                break;
            default:
                recuerdo = new RecuerdoMensaje(loader.getRecuerdoMensajeTexture(), x, y);
                recuerdo.setDanioEmocional(9);  // Ligeramente reducido
                break;
        }
        
        return recuerdo;
    }
    
    @Override
    public PowerUp crearPowerUp(float x, float y) {
        // Power-ups normales
        int tipoPowerUp = MathUtils.random(0, 2);
        switch(tipoPowerUp) {
            case 0: return new PowerUpAmnesiaSelectiva(loader.getAmnesiaTexture(), x, y);
            case 1: return new PowerUpCorazaDeMacho(loader.getCorazaTexture(), x, y);
            default: return new PowerUpAutotuneEmocional(loader.getAutotuneTexture(), x, y);
        }
    }
    
    @Override
    public String getDescripcionAmbientacion() {
        return "Cada movimiento es una apuesta calculada. El riesgo y la recompensa se balancean.";
    }
    
    @Override
    public float getMultiplicadorVelocidad() {
        return 1.0f; // Velocidad normal - mundo "equilibrado" de la negociación
    }
}
