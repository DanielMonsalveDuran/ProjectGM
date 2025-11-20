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
                trago.setPotenciaAlcoholica(9);  // Aumentada - busca escape
                trago.setBoostAutoestima(2);     // Reducida - poco efecto
                break;
            case 1:
                trago = new TragoWhisky(loader.getTragoWhiskyTexture(), x, y);
                trago.setPotenciaAlcoholica(18); // Aumentada
                trago.setBoostAutoestima(5);     // Reducida
                break;
            default:
                trago = new TragoTequila(loader.getTragoTequilaTexture(), x, y);
                trago.setPotenciaAlcoholica(28); // Aumentada
                trago.setBoostAutoestima(9);     // Reducida
                break;
        }
        
        return trago;
    }
    
    @Override
    public Recuerdo crearRecuerdo(float x, float y) {
        // Recuerdos con daño moderado pero inevitables
        int tipoRecuerdo = MathUtils.random(0, 2);
        Recuerdo recuerdo;
        
        switch(tipoRecuerdo) {
            case 0: 
                recuerdo = new RecuerdoFoto(loader.getRecuerdoFotoTexture(), x, y);
                recuerdo.setDanioEmocional(16);
                break;
            case 1:
                recuerdo = new RecuerdoCarta(loader.getRecuerdoCartaTexture(), x, y);
                recuerdo.setDanioEmocional(27);
                break;
            default:
                recuerdo = new RecuerdoMensaje(loader.getRecuerdoMensajeTexture(), x, y);
                recuerdo.setDanioEmocional(11);
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
        return "El tiempo fluye como miel... pesada y lenta. Los recuerdos son inevitables.";
    }
    
    @Override
    public float getMultiplicadorVelocidad() {
        return 0.6f; // 40% más lento - mundo "pesado" de la depresión
    }
}
