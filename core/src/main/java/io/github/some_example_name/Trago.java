package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

/**
 * Clase Trago - Implementa interfaz y extiende clase abstracta
 * REQUISITO GM1.4 y GM1.5
 */
public class Trago extends ObjetoCaida implements AfectableEmocionalmente{
    private int potenciaAlcoholica;
    private int boostAutoestima;
    
    public Trago(Texture textura, float x, float y) {
        super(textura, x, y);
        this.potenciaAlcoholica = 10;
        this.boostAutoestima = 5;
    }
    
    // Implementación del método abstracto de ObjetoCaida
    @Override
    public void aplicarEfecto(Carlos carlos) {
        aplicarEfectoEmocional(carlos);
    }
    
    // Implementación de la interfaz AfectableEmocionalmente
    @Override
    public void aplicarEfectoEmocional(Carlos carlos) {
        carlos.aumentarEbriedad(potenciaAlcoholica);
        carlos.sumarAutoestima(boostAutoestima);
    }
    
    @Override
    public String getTipoEfecto() {
        return "Consuelo Alcohólico";
    }
    
    @Override
    public boolean esPositivo() {
        return true;
    }
}
