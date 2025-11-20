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
        int tipo = MathUtils.random(0, 1);
        if (tipo == 0) {
            return new TragoCervezaBarata(loader.getTragoCervezaTexture(), x, y);
        } else {
            return new TragoWhisky(loader.getTragoWhiskyTexture(), x, y);
        }
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
