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
    
    //Se crean o carvezas baratas o whiskys
    @Override
    public Trago crearTrago(float x, float y) {
        int tipo = MathUtils.random(0, 1); // 0-1 (50% cada uno)
        
        if (tipo == 0) {
            TragoCervezaBarata cerveza = new TragoCervezaBarata(loader.getTragoCervezaTexture(), x, y);
            cerveza.setPotenciaAlcoholica(8);
            cerveza.setBoostAutoestima(3);
            cerveza.setPuntosScore(30);
            return cerveza;
        } else {
            TragoWhisky whisky = new TragoWhisky(loader.getTragoWhiskyTexture(), x, y);
            whisky.setPotenciaAlcoholica(15);
            whisky.setBoostAutoestima(8);
            whisky.setPuntosScore(45);
            return whisky;
        }
    }
    
    @Override
    public Recuerdo crearRecuerdo(float x, float y) {
        RecuerdoMensaje mensaje = new RecuerdoMensaje(loader.getRecuerdoMensajeTexture(), x, y);
        mensaje.setDanioEmocional(10);
        return mensaje;
    }
    
    // Power-ups normales por ahora
    @Override
    public PowerUp crearPowerUp(float x, float y) {
        int tipoPowerUp = MathUtils.random(0, 2);
        switch(tipoPowerUp) {
            case 0: return new PowerUpAmnesiaSelectiva(loader.getAmnesiaTexture(), x, y);
            case 1: return new PowerUpCorazaDeMacho(loader.getCorazaTexture(), x, y);
            default: return new PowerUpAutotuneEmocional(loader.getAutotuneTexture(), x, y);
        }
    }
    
}
