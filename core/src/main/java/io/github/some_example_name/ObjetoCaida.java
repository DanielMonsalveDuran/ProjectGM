package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase abstracta base con Template Method para todos los objetos que caen.
 * Define el flujo exacto al colisionar con Carlos:
 * 1. Efectos básicos (autoestima, ebriedad, score)
 * 2. Efectos especiales (power-ups, depresión, etc.)
 * 3. Registrar evento en el sistema de duelo
 */
public abstract class ObjetoCaida implements ElementoJuego {

    protected Texture textura;
    protected Rectangle area;
    protected float velocidadCaida = 200f;

    public ObjetoCaida(Texture textura, float x, float y) {
        this.textura = textura;
        this.area = new Rectangle(x, y, 64, 64);
    }

    // ===================== TEMPLATE METHOD =====================
    // Método final: define el orden exacto de ejecución
    public final void efectoColision(Carlos carlos) {
        aplicarEfectos(carlos);
        registrarEvento(carlos);
    }

    // ------------------- PASOS DEL TEMPLATE -------------------

    /** 1. Cambios básicos de estadísticas (siempre se aplican) */
    protected void aplicarEfectos(Carlos carlos) {
        int deltaAutoestima = getCambioAutoestima(carlos);
        int deltaEbriedad = getCambioEbriedad();
        int deltaScore = getCambioScore();

        if (deltaAutoestima != 0) carlos.sumarAutoestima(deltaAutoestima);
        if (deltaEbriedad != 0) carlos.aumentarEbriedad(deltaEbriedad);
        if (deltaScore != 0) carlos.aumentarScore((int)(deltaScore * carlos.getMultiplicadorScore()));
        aplicarEfectosEspeciales(carlos);
    }

    /** 2. Efectos especiales únicos (deben implementar las hijas) */
    protected abstract void aplicarEfectosEspeciales(Carlos carlos);

    /** 3. Registrar en el sistema de duelo (trago, power-up, etc.) */
    protected void registrarEvento(Carlos carlos) {
        // Por defecto no hace nada. Las hijas sobrescriben si quieren
    }

    // ------------------- MÉTODOS ABSTRACTOS OBLIGATORIOS -------------------

    protected abstract int getCambioAutoestima(Carlos carlos);
    protected abstract int getCambioEbriedad();
    protected abstract int getCambioScore();

    // ------------------- COMPORTAMIENTO COMPARTIDO -------------------

    @Override
    public void actualizar() {
        area.y -= velocidadCaida * com.badlogic.gdx.Gdx.graphics.getDeltaTime();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }

    @Override
    public boolean estaFueraDePantalla() {
        return area.y + 64 < 0;
    }

    @Override
    public Rectangle getArea() {
        return area;
    }

    // ------------------- GETTERS Y SETTERS -------------------

    public float getVelocidadCaida() {return velocidadCaida;}
    public void setVelocidadCaida(float velocidad) {this.velocidadCaida = velocidad;}
    public Texture getTextura() {return textura;}
    public void setTextura(Texture textura){this.textura = textura;}
    public float getX() { return  area.x; }
    public void setX(float x) { area.x = x; }
    public float getY() { return area.y; }
    public void setY(float y) { area.y = y; }
}