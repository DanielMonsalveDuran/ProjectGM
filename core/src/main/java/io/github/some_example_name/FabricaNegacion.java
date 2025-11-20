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
    
    
    //Solo se crean Cervezas Baratas
    @Override
    public Trago crearTrago(float x, float y) {
        TragoCervezaBarata cerveza = new TragoCervezaBarata(loader.getTragoCervezaTexture(), x, y);
        cerveza.setPotenciaAlcoholica(8);
        cerveza.setBoostAutoestima(3);
        cerveza.setPuntosScore(30);
        return cerveza;
    }

    
    //Solo se crean mensajes
    @Override
    public Recuerdo crearRecuerdo(float x, float y) {
        RecuerdoMensaje mensaje = new RecuerdoMensaje(loader.getRecuerdoMensajeTexture(), x, y);
        mensaje.setDanioEmocional(10);
        return mensaje;
    }
    
    
    //Siempre se crean todos los power ups
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
