package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class PowerUpAutotuneEmocional extends PowerUp {

    private static final float DURACION_TOTAL = 20f;
    private static final float INTERVALO_TICK = 5f; // +1 autoestima cada 5s
    private float tiempoAcumulado = 0f;
    private int velocidadOriginal = 400;

    public PowerUpAutotuneEmocional(Texture textura, float x, float y) {
        super(textura, x, y,
              DURACION_TOTAL,
              "Autotune Emocional Plus",
              "+20 autoestima inmediata + 25% velocidad + +1 cada 5s x20s",
              false,
              20);
    }

    @Override
    protected void aplicarEfectosEspeciales(Carlos carlos) {
        // Guardar velocidad original y aumentar
        velocidadOriginal = 400;
        carlos.setVelocidad((int)(velocidadOriginal * 1.25f));

        // Activar efecto visual/sonoro del autotune
        carlos.activarAutotune(DURACION_TOTAL);

        // Reiniciar contador
        tiempoAcumulado = 0f;
    }

    // Este método se llama desde Carlos o desde un gestor si lo prefieres
    // Pero lo más fácil: que Carlos tenga un método actualizarPowerUps()
    public void actualizar(float delta, Carlos carlos) {
        if (duracion <= 0) return;

        tiempoAcumulado += delta;
        if (tiempoAcumulado >= INTERVALO_TICK) {
            carlos.sumarAutoestima(1);
            tiempoAcumulado -= INTERVALO_TICK;
        }
    }

    // Llamado cuando termina la duración (puedes conectarlo desde Carlos)
    public void finalizar(Carlos carlos) {
        carlos.setVelocidad(velocidadOriginal);
    }
}