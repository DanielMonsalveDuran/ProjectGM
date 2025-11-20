package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * FÁBRICA CONCRETA - Estado de DEPRESIÓN
 * Mundo lento y pesado donde la inevitabilidad reina
 * ENFOQUE SIMPLIFICADO: Valores que reflejan lentitud y pesadez
 */
public class FabricaDepresion implements FabricaDeTragedias {
    
    private CargarArchivos loader;
    
    public FabricaDepresion() {
        this.loader = CargarArchivos.getInstance();
    }
    
    @Override
    public Trago crearTrago(float x, float y) {
        // Tragos con efectos prolongados para DEPRESIÓN
        int tipoTrago = MathUtils.random(0, 2);
        Trago trago;
        
        switch(tipoTrago) {
            case 0: 
                trago = new TragoCervezaBarata(loader.getTragoCervezaTexture(), x, y);
                trago.setPotenciaAlcoholica(8);  // Aumentada - busca escape
                trago.setBoostAutoestima(3);     // Reducida - poco efecto
                trago.setPuntosScore(30);
                break;
            case 1:
                trago = new TragoWhisky(loader.getTragoWhiskyTexture(), x, y);
                trago.setPotenciaAlcoholica(15); // Aumentada
                trago.setBoostAutoestima(8);     // Reducida
                trago.setPuntosScore(45);
                break;
            default:
                trago = new TragoTequila(loader.getTragoTequilaTexture(), x, y);
                trago.setPotenciaAlcoholica(25); // Aumentada
                trago.setBoostAutoestima(12);     // Reducida
                trago.setPuntosScore(60);
                break;
        }
        
        return trago;
    }
    
    @Override
    public Recuerdo crearRecuerdo(float x, float y) {
        int tipo = MathUtils.random(0, 1); // 50% cada uno
        
        if (tipo == 0) {
            RecuerdoMensaje mensaje = new RecuerdoMensaje(loader.getRecuerdoMensajeTexture(), x, y);
            mensaje.setDanioEmocional(10);
            return mensaje;
        } else {
            RecuerdoFoto foto = new RecuerdoFoto(loader.getRecuerdoFotoTexture(), x, y);
            foto.setDanioEmocional(15);
            return foto;
        }
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
}
