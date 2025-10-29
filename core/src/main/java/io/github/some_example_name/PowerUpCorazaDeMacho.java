package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

/**
 * PowerUpCoraza - Protección emocional temporal
 * Carlos se vuelve inmune a los efectos negativos de los recuerdos por un tiempo
 */
public class PowerUpCorazaDeMacho extends PowerUp{
    
    // CONSTANTES ESPECÍFICAS DE CORAZA
    private static final float DURACION_CORAZA = 5f; // 5 segundos de protección
    private static final int BONUS_AUTOESTIMA = 5;
    private static final int PUNTOS_SCORE = 100;
    
    // Estado interno de la coraza
    private boolean activa;
    private float tiempoRestante;
    
    public PowerUpCorazaDeMacho(Texture textura, float x, float y) {
        super(textura, x, y, 
              DURACION_CORAZA,                    // duración
              "Coraza de Macho",                  // nombre
              "Protección emocional temporal",    // descripción
              false,                              // NO es instantáneo (tiene duración)
              BONUS_AUTOESTIMA);                  // autoestima base
        
        this.activa = false;
        this.tiempoRestante = DURACION_CORAZA;
    }
    
    // ===== IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS DE POWERUP =====
    
    @Override
    public void aplicarEfectoPowerUp(Carlos carlos) {
        // Activar la protección en Carlos
        carlos.activarCoraza(getDuracion());
        
        // Dar puntos de score
        carlos.aumentarScore(PUNTOS_SCORE);
        
        // Estado interno
        this.activa = true;
        this.tiempoRestante = getDuracion();
        
        System.out.println("🛡️ Coraza activada - Inmune a recuerdos por " + getDuracion() + "s");
    }
    
    @Override
    public void activarEfectoVisual() {
        // Efectos visuales al activar la coraza
        spawnearParticulas("escudo_dorado");
        reproducirSonido("coraza_activada.wav");
        
        System.out.println("✨ Efecto visual: Escudo dorado alrededor de Carlos");
    }
    
    @Override
    public void desactivarEfectoVisual() {
        // Efectos visuales al desactivarse
        spawnearParticulas("escudo_desaparece");
        reproducirSonido("coraza_desactivada.wav");
        
        System.out.println("💫 Efecto visual: Escudo desaparece");
    }
    
    // ===== SOBREESCRITURA DE MÉTODOS DE POWERUP =====
    
    @Override
    public void actualizarDuracion() {
        if (activa && tiempoRestante > 0) {
            tiempoRestante -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
            
            if (tiempoRestante <= 0) {
                // La coraza se desactiva automáticamente
                desactivarPowerUpInterno();
            }
        }
    }
    
    @Override
    public void desactivarPowerUp(Carlos carlos) {
        desactivarEfectoVisual();
        this.activa = false;
        this.tiempoRestante = 0;
        
        System.out.println("🛡️ Coraza desactivada - Vulnerable nuevamente");
    }
    
    // ===== MÉTODOS PÚBLICOS ESPECÍFICOS DE CORAZA =====
    
    /**
     * Verifica si la coraza está actualmente activa
     */
    public boolean estaActiva() {
        return activa && tiempoRestante > 0;
    }
    
    /**
     * Obtiene el tiempo restante de protección
     */
    public float getTiempoRestante() {
        return tiempoRestante;
    }
    
    /**
     * Método para que Carlos verifique si está protegido
     */
    public boolean puedeSerAfectadoPorRecuerdos() {
        return !estaActiva();
    }
    
    // ===== MÉTODOS PRIVADOS =====
    
    private void desactivarPowerUpInterno() {
        this.activa = false;
        this.tiempoRestante = 0;
        desactivarEfectoVisual();
        
        System.out.println("⏰ Coraza expirada - Protección terminada");
    }
    
    // ===== MÉTODOS DE DEBUG =====
    
    @Override
    public String toString() {
        return String.format("PowerUpCoraza{activa=%s, tiempoRestante=%.1f}", 
                           activa, tiempoRestante);
    }
    
    @Override
    public String getInfoDetallada() {
        String estado = estaActiva() ? 
            String.format("ACTIVA (%.1fs restantes)", tiempoRestante) : "INACTIVA";
        
        // ✅ CORREGIDO: Usar getters en lugar de acceder directamente
        return String.format("%s: %s - %s", 
                           getNombre(),        // ✅ usar getter
                           getDescripcion(),   // ✅ usar getter
                           estado);
    }
}