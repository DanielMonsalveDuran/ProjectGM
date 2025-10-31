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
        //desactivarEfectoVisual();
        this.activa = false;
        this.tiempoRestante = 0;
        
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
        //desactivarEfectoVisual();
        
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
        return String.format("%s: %s - %s", 
                           getNombre(),        
                           getDescripcion(),   
                           estado);
    }
    
    public void setActiva(boolean activa) { 
        this.activa = activa; 
    }
    
    public void setTiempoRestante(float tiempo) { 
        this.tiempoRestante = tiempo; 
    }
    
    public static float getDuracionCoraza() {
        return DURACION_CORAZA;
    }
    
    public static int getBonusAutoestima() {
        return BONUS_AUTOESTIMA;
    }
    
    public static int getPuntosScore() {
        return PUNTOS_SCORE;
    }
}