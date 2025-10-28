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
        
        // 1. Bonus de autoestima inmediato (usa el sistema existente)
        carlos.sumarAutoestima(20);
        
        // 2. Mejora de movimiento (usa parámetro velocidad existente)
        this.velocidadOriginal = 400; // Asumiendo que 400 es la velocidad normal
        carlos.setVelocidad(500); // 25% más rápido
        
        // 4. Activar flag de autotune (sistema existente)
        carlos.activarAutotune(20f);
        
        // 5. Inicializar contador para efecto gradual
        this.tiempoAcumulado = 0f;
        
        System.out.println("🎤 AUTOTUNE PLUS: +20 autoestima, +velocidad");
    }
    
    @Override
    public void activarEfectoVisual() {
        System.out.println("💫 Efectos visuales de Autotune Plus activados");
        // Aquí irían efectos de partículas, brillo, etc.
    }
    
    @Override
    public void desactivarEfectoVisual() {
        System.out.println("💫 Efectos visuales de Autotune Plus desactivados");
        // Restaurar efectos visuales normales
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
            System.out.println("💖 Autotune Plus: +1 autoestima gradual - Total: " + carlos.getAutoestima());
            
            // Efecto visual pequeño por cada incremento
            activarEfectoIncremental();
        }
    }
    
    private void activarEfectoIncremental() {
        // Efecto visual sano por cada +1 autoestima
        System.out.println("✨ Efecto incremental: Pequeño brillo por +1 autoestima");
        // spawnearParticulas("brillo_pequeno");
    }
    
    @Override
    public void desactivarPowerUp(Carlos carlos) {
        // 🔄 RESTAURAR VALORES ORIGINALES al terminar
        
        // 1. Restaurar velocidad original
        if (carlos != null) {
            carlos.setVelocidad(this.velocidadOriginal);
        }
        
        // 2. Efectos visuales de desactivación
        desactivarEfectoVisual();
        
        // 3. Mensaje de fin
        System.out.println("🎤 Autotune Plus terminado. Velocidad restaurada.");
        
        super.desactivarPowerUp(carlos);
    }
    
    /**
     * Método para obtener referencia a Carlos
     * NECESITAS ADAPTAR ESTO según tu arquitectura
     */
    private Carlos obtenerCarlos() {
        // OPCIÓN 1: Si tienes una referencia global
        // return GameManager.getInstance().getCarlos();
        
        // OPCIÓN 2: Si Carlos se pasa como parámetro
        // (necesitarías almacenarlo en el constructor)
        
        // OPCIÓN 3: Temporal - para testing
        System.out.println("⚠️ Implementar obtenerCarlos() según tu arquitectura");
        return null;
    }
    
    @Override
    public String toString() {
        return String.format("AutotunePlus{duracion=%.1f, intervalo=%.1f, bonus=%d}", 
                           duracion, INTERVALO_AUTOESTIMA, autoestimaBase);
    }
}