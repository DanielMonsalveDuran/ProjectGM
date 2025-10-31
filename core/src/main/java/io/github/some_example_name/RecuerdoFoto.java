package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

/**
 * RecuerdoFoto - Provoca el estado de Ira por 10s
 */
public class RecuerdoFoto extends Recuerdo{

    private static final int DANIO_AUTOESTIMA = 15; // Mantenido el -15 original

    public RecuerdoFoto(Texture textura, float x, float y) {
        super(textura, x, y, DANIO_AUTOESTIMA);
    }
    
    @Override
    protected void aplicarEstadoTemporal(Carlos carlos) {
        // 💔 Estado Temporal: IRA
        carlos.setEstadoTemporal("Ira", DURACION_ESTADO_TEMPORAL);
    }
    
    public static int getDanioAutoestima() {
        return DANIO_AUTOESTIMA;
    }
}