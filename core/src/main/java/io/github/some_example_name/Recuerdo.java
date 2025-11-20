package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

// 🟢 MODIFICADO: Clase abstracta base para recuerdos
public abstract class Recuerdo extends ObjetoCaida {
    protected int danioEmocional;

    public Recuerdo(Texture textura, float x, float y, int danio) {
        super(textura, x, y);
        this.danioEmocional = danio;
    }

    @Override protected int getCambioAutoestima(Carlos carlos) {
    	if (!carlos.isCorazaActiva()) {return -danioEmocional; }
    	else return 0;}
    @Override protected int getCambioEbriedad() { return 0; }
    @Override protected int getCambioScore() { return -50; }

    @Override
    protected void aplicarEfectosEspeciales(Carlos carlos) {
        if (!carlos.isCorazaActiva()) {
            carlos.deprimir();
        }
    }
    
    /**
     * NUEVO SETTER - Permite a las fábricas modificar el daño dinámicamente
     * Esto es clave para el Abstract Factory simplificado
     */
    public void setDanioEmocional(int danio) {
        this.danioEmocional = danio;
    }
}