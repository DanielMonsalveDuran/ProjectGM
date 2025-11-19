package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

/**
 * Clase Trago - Implementa interfaz y extiende clase abstracta
 * REQUISITO GM1.4 y GM1.5
 */
public abstract class Trago extends ObjetoCaida {
    protected final int potenciaAlcoholica;
    protected final int boostAutoestima;
    protected final int puntosScore;

    public Trago(Texture textura, float x, float y, int potencia, int boost, int puntos) {
        super(textura, x, y);
        this.potenciaAlcoholica = potencia;
        this.boostAutoestima = boost;
        this.puntosScore = puntos;
    }

    @Override protected int getCambioAutoestima(Carlos carlos) { return boostAutoestima; }
    @Override protected int getCambioEbriedad() { return potenciaAlcoholica; }
    @Override protected int getCambioScore() { return puntosScore; }

    @Override
    protected void aplicarEfectosEspeciales(Carlos carlos) {
        // Nada especial
    }

    @Override
    protected void registrarEvento(Carlos carlos) {
        carlos.getManejadorEstados().registrarTragoConsumido();
    }
}
