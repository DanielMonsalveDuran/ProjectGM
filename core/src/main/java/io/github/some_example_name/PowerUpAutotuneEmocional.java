package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Power-up Autotune que da bonus inmediato + autoestima gradual
 * Bonus: +20 autoestima inmediata + movimiento mejorado
 * Gradual: +1 autoestima cada 5 segundos durante 20 segundos
 */
public class PowerUpAutotuneEmocional extends PowerUp {
    
    private float tiempoAcumulado = 0f;
    private static final float INTERVALO_AUTOESTIMA = 5.0f; // Cada 5 segundos
    private int velocidadOriginal; // Para restaurar después
    private Carlos targetCarlos;
    
    public PowerUpAutotuneEmocional(Texture textura, float x, float y) {
        super(textura, x, y, 
              20f,                                    // 20 segundos de duración total
              "Autotune Emocional Plus",             // nombre
              "+20 autoestima +1 cada 5s +movimiento", // descripción
              false,                                 // no instantáneo
              20);                                   // autoestima base (bonus inmediato)
        
    }
    
    @Override
    public void aplicarEfectoPowerUp(Carlos carlos) {
        // 🔥 BONUS INMEDIATO (se ejecuta una vez)
    	this.targetCarlos = carlos;
        // 1. Bonus de autoestima inmediato (usa el sistema existente)
        carlos.sumarAutoestima(20);
        
        // 2. Mejora de movimiento (usa parámetro velocidad existente)
        this.velocidadOriginal = 400; // Asumiendo que 400 es la velocidad normal
        carlos.setVelocidad(500); // 25% más rápido
        
        // 4. Activar flag de autotune (sistema existente)
        carlos.activarAutotune(20f);
        
        // 5. Inicializar contador para efecto gradual
        this.tiempoAcumulado = 0f;
    }
    
    private Carlos getCarlos() {
        return targetCarlos;
    }
    
    @Override
    protected void actualizarDuracion() {
        // Lógica de autoestima gradual
        tiempoAcumulado += Gdx.graphics.getDeltaTime();
        
        if (tiempoAcumulado >= INTERVALO_AUTOESTIMA) {
            aplicarAutoestimaGradual();
            tiempoAcumulado = 0f; // Reiniciar contador
        }
    }
    
    private void aplicarAutoestimaGradual() {
        // Necesitas una referencia a Carlos - aquí asumo que la tienes
        Carlos carlos = obtenerCarlos(); 
        if (carlos != null) {
            carlos.sumarAutoestima(1);
            
        }
    }
    
    @Override
    public void desactivarPowerUp(Carlos carlos) {
        // 🔄 RESTAURAR VALORES ORIGINALES al terminar
        
        // 1. Restaurar velocidad original
    	if (targetCarlos != null) { 
            targetCarlos.setVelocidad(this.velocidadOriginal);
        }
        
        super.desactivarPowerUp(carlos);
    }
    
    /**
     * Método para obtener referencia a Carlos
     * NECESITAS ADAPTAR ESTO según tu arquitectura
     */
    private Carlos obtenerCarlos() {
        return null;
    }
    
    @Override
    public String toString() {
        return String.format("AutotunePlus{duracion=%.1f, intervalo=%.1f, bonus=%d}", 
                           duracion, INTERVALO_AUTOESTIMA, autoestimaBase);
    }
    
    public float getTiempoAcumulado() { 
        return tiempoAcumulado; 
    }
    
    public void setTiempoAcumulado(float tiempo) { 
        this.tiempoAcumulado = tiempo; 
    }
    
    public int getVelocidadOriginal() { 
        return velocidadOriginal; 
    }
    
    public void setVelocidadOriginal(int velocidad) { 
        this.velocidadOriginal = velocidad; 
    }
    
    public Carlos getTargetCarlos() {
        return targetCarlos;
    }
    
    public void setTargetCarlos(Carlos carlos) {
        this.targetCarlos = carlos;
    }
    
    public static float getIntervaloAutoestima() {
        return INTERVALO_AUTOESTIMA;
    }
}