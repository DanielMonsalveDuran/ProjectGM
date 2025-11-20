package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * FÁBRICA CONCRETA - Estado de NEGACIÓN
 * Mundo ilusorio donde todo parece más fácil de lo que realmente es
 * ENFOQUE SIMPLIFICADO: Reutiliza clases existentes con valores modificados
 */
public class FabricaNegacion implements FabricaDeTragedias {
    
    private CargarArchivos loader;
    
    public FabricaNegacion() {
        this.loader = CargarArchivos.getInstance();
    }
    
    @Override
    public Trago crearTrago(float x, float y) {
        // Reutiliza TragoCervezaBarata pero con valores de NEGACIÓN
        TragoCervezaBarata trago = new TragoCervezaBarata(loader.getTragoCervezaTexture(), x, y);
        trago.setPotenciaAlcoholica(6);  // Reducida de 8
        trago.setBoostAutoestima(4);     // Aumentada de 3  
        trago.setPuntosScore(35);        // Aumentada de 30
        return trago;
    }
    
    @Override
    public Recuerdo crearRecuerdo(float x, float y) {
        // Reutiliza RecuerdoFoto existente pero con valores de NEGACIÓN
        RecuerdoFoto recuerdo = new RecuerdoFoto(loader.getRecuerdoFotoTexture(), x, y);
        recuerdo.setDanioEmocional(10);  // Reducido de 15
        return recuerdo;
    }
    
    @Override
    public PowerUp crearPowerUp(float x, float y) {
        // Power-ups normales por ahora - puede expandirse después
        int tipoPowerUp = MathUtils.random(0, 2);
        switch(tipoPowerUp) {
            case 0: return new PowerUpAmnesiaSelectiva(loader.getAmnesiaTexture(), x, y);
            case 1: return new PowerUpCorazaDeMacho(loader.getCorazaTexture(), x, y);
            default: return new PowerUpAutotuneEmocional(loader.getAutotuneTexture(), x, y);
        }
    }
    
    @Override
    public String getDescripcionAmbientacion() {
        return "Todo parece normal... demasiado normal. Los recuerdos duelen menos aquí.";
    }
    
    @Override
    public float getMultiplicadorVelocidad() {
        return 0.8f; // 20% más lento - mundo "amigable" de la negación
    }
}
