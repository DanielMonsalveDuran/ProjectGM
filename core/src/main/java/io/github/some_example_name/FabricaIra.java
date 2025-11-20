package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * FÁBRICA CONCRETA - Estado de IRA  
 * Mundo caótico y agresivo donde todo es intenso
 * ENFOQUE SIMPLIFICADO: Mismas clases, valores intensificados
 */
public class FabricaIra implements FabricaDeTragedias {
    
    private CargarArchivos loader;
    
    public FabricaIra() {
        this.loader = CargarArchivos.getInstance();
    }
    
    @Override
    public Trago crearTrago(float x, float y) {
        // Elige trago aleatorio pero con valores de IRA
        int tipoTrago = MathUtils.random(0, 2);
        Trago trago;
        
        switch(tipoTrago) {
            case 0: 
                trago = new TragoCervezaBarata(loader.getTragoCervezaTexture(), x, y);
                trago.setPotenciaAlcoholica(12); // Aumentada de 8
                trago.setBoostAutoestima(2);     // Reducida de 3
                break;
            case 1:
                trago = new TragoWhisky(loader.getTragoWhiskyTexture(), x, y);
                trago.setPotenciaAlcoholica(20); // Aumentada de 15
                trago.setBoostAutoestima(6);     // Reducida de 8
                break;
            default:
                trago = new TragoTequila(loader.getTragoTequilaTexture(), x, y);
                trago.setPotenciaAlcoholica(30); // Aumentada de 25
                trago.setBoostAutoestima(10);    // Reducida de 12
                break;
        }
        
        return trago;
    }
    
    @Override
    public Recuerdo crearRecuerdo(float x, float y) {
        // Recuerdos con daño aumentado para IRA
        int tipoRecuerdo = MathUtils.random(0, 2);
        Recuerdo recuerdo;
        
        switch(tipoRecuerdo) {
            case 0: 
                recuerdo = new RecuerdoFoto(loader.getRecuerdoFotoTexture(), x, y);
                recuerdo.setDanioEmocional(20); // Aumentado de 15
                break;
            case 1:
                recuerdo = new RecuerdoCarta(loader.getRecuerdoCartaTexture(), x, y);
                recuerdo.setDanioEmocional(35); // Aumentado de 25
                break;
            default:
                recuerdo = new RecuerdoMensaje(loader.getRecuerdoMensajeTexture(), x, y);
                recuerdo.setDanioEmocional(15); // Aumentado de 10
                break;
        }
        
        return recuerdo;
    }
    
    @Override
    public PowerUp crearPowerUp(float x, float y) {
        // Power-ups normales por ahora
        int tipoPowerUp = MathUtils.random(0, 2);
        switch(tipoPowerUp) {
            case 0: return new PowerUpAmnesiaSelectiva(loader.getAmnesiaTexture(), x, y);
            case 1: return new PowerUpCorazaDeMacho(loader.getCorazaTexture(), x, y);
            default: return new PowerUpAutotuneEmocional(loader.getAutotuneTexture(), x, y);
        }
    }
    
    @Override
    public String getDescripcionAmbientacion() {
        return "El mundo arde con tu furia interior. Todo es más intenso y peligroso.";
    }
    
    @Override
    public float getMultiplicadorVelocidad() {
        return 1.4f; // 40% más rápido - mundo "acelerado" de la ira
    }
}
