package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

/**
 * Clase Trago - Implementa interfaz y extiende clase abstracta
 * REQUISITO GM1.4 y GM1.5
 */
public abstract class Trago extends ObjetoCaida {
    protected int potenciaAlcoholica;
    protected int boostAutoestima;
    protected int puntosScore;
    
    public Trago(Texture textura, float x, float y, int potencia, int boost, int puntos) {
        super(textura, x, y);
        this.potenciaAlcoholica = potencia;
        this.boostAutoestima = boost;
        this.puntosScore = puntos;
    }
    
    // Implementación del método abstracto de ObjetoCaida
    @Override
    public void aplicarEfecto(Carlos carlos) {
    	carlos.aumentarEbriedad(potenciaAlcoholica);
        carlos.sumarAutoestima(boostAutoestima);
        carlos.aumentarScore(puntosScore);
    }
    
    public int getPotenciaAlcoholica() { return potenciaAlcoholica; }
    public void setPotenciaAlcoholica(int potencia) { this.potenciaAlcoholica = potencia; }
    public int getBoostAutoestima() { return boostAutoestima; }
    public void setBoostAutoestima(int boost) { this.boostAutoestima = boost; }
    public int getPuntosScore() { return puntosScore; }
    public void setPuntosScore(int puntos) { this.puntosScore = puntos; }
}
