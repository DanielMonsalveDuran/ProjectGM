package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Clase abstracta base para todos los power-ups del juego
 * REQUISITO GM1.4: Clase abstracta padre de múltiples power-ups
 * BUENAS PRÁCTICAS: Encapsulamiento, principio de abierto/cerrado, polimorfismo
 */
public abstract class PowerUp extends ObjetoCaida {
    protected final float duracion;
    protected final String nombre;
    protected final String descripcion;
    protected final boolean esInstantaneo;
    protected final int autoestimaBase;

    protected PowerUp(Texture textura, float x, float y, float duracion,
                     String nombre, String descripcion, boolean esInstantaneo, int autoestimaBase) {
        super(textura, x, y);
        this.duracion = duracion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esInstantaneo = esInstantaneo;
        this.autoestimaBase = autoestimaBase;
        this.velocidadCaida = 150f;
    }

    @Override protected int getCambioAutoestima(Carlos carlos) { return autoestimaBase; }
    @Override protected int getCambioEbriedad() { return 0; }
    @Override protected int getCambioScore() { return 0; }

    
    protected void registrarEvento(Carlos carlos) {
        carlos.getManejadorEstados().registrarPowerupUsado();
    }

    // Cada power-up implementa su efecto especial aquí
    @Override
    protected abstract void aplicarEfectosEspeciales(Carlos carlos);

    // Métodos de duración (mantenidos como antes)
    public float getDuracion() { return duracion; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public boolean esInstantaneo() { return esInstantaneo; }
}
