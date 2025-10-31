package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

// 🟢 MODIFICADO: Clase abstracta base para recuerdos
public abstract class Recuerdo extends ObjetoCaida{
    
    // 🟢 NUEVO: Definir duración del estado temporal (10s como pidió el usuario)
    protected static final float DURACION_ESTADO_TEMPORAL = 10.0f; 
    
    protected int danioEmocional;
    
    public Recuerdo(Texture textura, float x, float y, int danio) {
        super(textura, x, y);
        this.danioEmocional = danio;
    }
    
    // 🟢 MODIFICADO: Implementación con chequeo de Coraza
    @Override
    public void aplicarEfecto(Carlos carlos) {
    	// 🛡️ VERIFICACIÓN: Si la coraza está activa, el recuerdo no tiene efecto
        if (carlos.isCorazaActiva()) {
            System.out.println("🛡️ Recuerdo bloqueado por Coraza de Macho.");
            return; // Detener el efecto negativo
        }
        
        // Aplicar daño genérico (deprimir) y el efecto específico
        carlos.deprimir(); // Activa el llanto y el temblor por un tiempo
        carlos.sumarAutoestima(-danioEmocional); // Aplica el daño específico de la hija
        
        aplicarEstadoTemporal(carlos); // Método abstracto para el estado de ánimo
    }
    
    // 🟢 NUEVO: Método abstracto para que las hijas definan su estado de ánimo
    protected abstract void aplicarEstadoTemporal(Carlos carlos);
    
    public int getDanioEmocional() { 
        return danioEmocional; 
    }
    
    public void setDanioEmocional(int danio) { 
        this.danioEmocional = danio; 
    }
    
    public static float getDuracionEstadoTemporal() {
        return DURACION_ESTADO_TEMPORAL;
    }
}