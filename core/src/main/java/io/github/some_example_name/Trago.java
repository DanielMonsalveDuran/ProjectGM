package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

/**
 * Clase Trago - Implementa interfaz y extiende clase abstracta
 * REQUISITO GM1.4 y GM1.5
 */
public class Trago extends ObjetoCaida{
    private int potenciaAlcoholica;
    private int boostAutoestima;
    private int puntosScore;
    
    public Trago(Texture textura, float x, float y) {
        super(textura, x, y);
        this.potenciaAlcoholica = 10;
        this.boostAutoestima = 5;
        this.puntosScore = 50;
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
