package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Clase abstracta base para todos los power-ups del juego
 * REQUISITO GM1.4: Clase abstracta padre de múltiples power-ups
 * BUENAS PRÁCTICAS: Encapsulamiento, principio de abierto/cerrado, polimorfismo
 */
public abstract class PowerUp extends ObjetoCaida{
    
    // ATRIBUTOS PROTEGIDOS (encapsulamiento para hijas)
    protected final float duracion;
    protected final String nombre;
    protected final String descripcion;
    protected final boolean esInstantaneo;
    protected final int autoestimaBase;
    
    // CONSTANTES (configuración centralizada)
    protected static final float DURACION_BASE = 10f;
    protected static final int AUTOESTIMA_BASE = 15;
    
    /**
     * Constructor protegido - solo hijas pueden instanciar
     */
    protected PowerUp(Texture textura, float x, float y, 
                     float duracion, String nombre, String descripcion, 
                     boolean esInstantaneo, int autoestimaBase) {
        super(textura, x, y);
        this.duracion = duracion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.esInstantaneo = esInstantaneo;
        this.autoestimaBase = autoestimaBase;
        
        // Velocidad de caída específica para power-ups
        this.velocidadCaida = 150f; // Más lento que objetos normales
    }
    
    // MÉTODOS ABSTRACTOS (contrato que todas las hijas deben cumplir)
    
    /**
     * Aplica el efecto específico del power-up
     * CADA subclase implementa su comportamiento único
     */
    public abstract void aplicarEfectoPowerUp(Carlos carlos);
    
    
    /**
     * Método template - las hijas pueden overridear partes específicas
     */
    public void desactivarPowerUp(Carlos carlos) {
        
    }
    
    // GETTERS PÚBLICOS (encapsulamiento - acceso controlado)
    
    public final float getDuracion() {
        return duracion;
    }
    
    public final String getNombre() {
        return nombre;
    }
    
    public final String getDescripcion() {
        return descripcion;
    }
    
    public final boolean esInstantaneo() {
        return esInstantaneo;
    }
    
    public final int getAutoestimaBase() {
        return autoestimaBase;
    }
    
    // MÉTODOS DE UTILIDAD PROTEGIDOS (para hijas) funciones para implementar más adelante
    
    /**
     * Método helper para hijas - aplicar efecto de sonido
     */
    /*protected final void reproducirSonido(String sonido) {
    }*/
    
    /**
     * Método helper para hijas - spawnear partículas
     */
    /*protected final void spawnearParticulas(String tipoParticula) {
    }*/
    
    // SOBREESCRITURA DE ObjetoCaida
    
    @Override
    public void aplicarEfecto(Carlos carlos) {
    	aplicarEfectoPowerUp(carlos);
    }
    
    @Override
    public void actualizar() {
        super.actualizar(); // Comportamiento base de caída
        
        // Comportamiento adicional específico de power-ups
        if (!esInstantaneo) {
            // Lógica para power-ups con duración
            actualizarDuracion();
        }
    }
    
    /**
     * Método protegido para actualizar duración (overrideable por hijas)
     */
    protected void actualizarDuracion() {
        // Lógica base de actualización de duración
        // Las hijas pueden extender este comportamiento
    }
    
    // MÉTODOS DE DEBUG Y DESARROLLO
    
    /**
     * Representación en string para debugging
     */
    @Override
    public String toString() {
        return String.format("PowerUp{nombre='%s', duracion=%.1f, instantaneo=%s}", 
                           nombre, duracion, esInstantaneo);
    }
    
    /**
     * Información detallada para UI
     */
    public String getInfoDetallada() {
        return String.format("%s: %s (%.1fs)", nombre, descripcion, duracion);
    }
    
    
    public float getVelocidadCaida() { 
        return velocidadCaida; 
    }
    
    public void setVelocidadCaida(float velocidad) { 
        this.velocidadCaida = velocidad; 
    }
    
    public Texture getTextura() {
        return textura;
    }
    
    public void setTextura(Texture textura) {
        this.textura = textura;
    }
    
    public static float getDuracionBase() {
        return DURACION_BASE;
    }
    
}
