package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

/**
 * RecuerdoCarta - Provoca el estado de Depresión por 10s
 */
public class RecuerdoCarta extends Recuerdo{

    private static final int DANIO_AUTOESTIMA = 25; // Mantenido el -25 original

    public RecuerdoCarta(Texture textura, float x, float y) {
        super(textura, x, y, DANIO_AUTOESTIMA);
    }
    
    @Override
    protected void aplicarEstadoTemporal(Carlos carlos) {
        // 💔 Estado Temporal: DEPRESIÓN
        carlos.setEstadoTemporal("Depresión", DURACION_ESTADO_TEMPORAL);
    }
}