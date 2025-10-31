package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Carlos implements ElementoJuego{
    // ATRIBUTOS PRIVADOS (Encapsulamiento)...
    private Rectangle area;
    private Texture textura;
    private Sound sonidoLlanto;
    private int autoestima;
    private int ebriedad;
    private int score; // Score infinito
    private String estadoAnimo;
    private int velocidad;
    private boolean deprimido;
    private int tiempoDeprimidoMax;
    private int tiempoDeprimido;
    
    private float tiempoCoraza;
    private float tiempoAutotune; 
    private float tiempoAmnesia;
    
    // Power-ups activos (privados)
    private boolean autotuneActivo;
    private boolean amnesiaActiva;
    private boolean corazaActiva;
    
    // Multiplicador solo por power-ups
    private float multiplicadorScore;
    
    private static final float INTERVALO_REDUCCION = 0.5f;
    private static final int CANTIDAD_REDUCCION = 1;
    private float tiempoDesdeUltimaReduccion = 0f;
    
    // Atributos para el estado temporal de Recuerdo
    private float tiempoEstadoTemporal;
    private String estadoAnimoTemporal;
    private boolean estadoTemporalActivo;
    
    /**
     * Constructor que inicializa los atributos de estado de Carlos,
     * incluyendo texturas, sonidos y valores iniciales (autoestima=100, ebriedad=0).
     */
    public Carlos(Texture tex, Sound ss) {
        this.textura = tex;
        this.sonidoLlanto = ss;
        this.autoestima = 100;
        this.ebriedad = 0;
        this.score = 0;
        this.estadoAnimo = "Negación";
        this.velocidad = 400;
        this.deprimido = false;
        this.tiempoDeprimidoMax = 50;
        this.tiempoDeprimido = 0;
        this.autotuneActivo = false;
        this.amnesiaActiva = false;
        this.corazaActiva = false;
        this.tiempoCoraza = 0f;
        this.tiempoAutotune = 0f;
        this.tiempoAmnesia = 0f;
        this.multiplicadorScore = 1.0f; // Base 1.0x siempre
        
        // Inicializa nuevo estado temporal
        this.tiempoEstadoTemporal = 0f;
        this.estadoAnimoTemporal = "Negación";
        this.estadoTemporalActivo = false;
    }
    
    // MÉTODOS PÚBLICOS (Interfaz controlada)
    /**
     * Inicializa el área de colisión (Rectangle) y posiciona a Carlos en el centro inferior de la pantalla.
     */
    public void crear() {
        area = new Rectangle();
        area.x = 800 / 2 - 64 / 2;
        area.y = 20;
        area.width = 64;
        area.height = 64;
    }
    
    /**
     * Verifica si la autoestima ha llegado a cero, indicando la condición de derrota.
     * @return true si autoestima <= 0, false en caso contrario.
     */
    public boolean estaDerrotado() {
        return autoestima <= 0;
    }
    
    /**
     * Reinicia todos los atributos de estado de Carlos a sus valores iniciales
     * para empezar una nueva partida.
     */
    public void reiniciar() {
        this.autoestima = 100;
        this.ebriedad = 0;
        this.score = 0;
        this.estadoAnimo = "Negación";
        this.deprimido = false;
        this.autotuneActivo = false;
        this.amnesiaActiva = false;
        this.corazaActiva = false;
        this.tiempoCoraza = 0f;
        this.tiempoAutotune = 0f;
        this.tiempoAmnesia = 0f;
        this.multiplicadorScore = 1.0f; // Reiniciar a 1.0x
        
        // Reiniciar estado temporal
        this.tiempoEstadoTemporal = 0f;
        this.estadoAnimoTemporal = "Negación";
        this.estadoTemporalActivo = false;
        
        // Reposiciona a Carlos
        if (area != null) {
            area.x = 800 / 2 - 64 / 2;
            area.y = 20;
        }
    }
    
    /**
     * Activa el estado de 'deprimido' (inmovilidad temporal) y reproduce el sonido de llanto.
     * El efecto solo se aplica si la coraza no está activa.
     */
    public void deprimir() {
        if (!corazaActiva) {
            // Activa el flag deprimido y establece el temporizador
            deprimido = true;
            tiempoDeprimido = tiempoDeprimidoMax;
            
            // Verifica y reproduce el sonido de llanto
            if (sonidoLlanto != null) {
                sonidoLlanto.play();
            }
        }
    }
    
    /**
     * Establece un estado de ánimo temporal para Carlos (por ejemplo, 'Ira' de un recuerdo).
     * @param estado El nombre del estado de ánimo.
     * @param duracion La duración en segundos del estado temporal.
     */
    public void setEstadoTemporal(String estado, float duracion) {
        if (!corazaActiva) {
            this.estadoAnimoTemporal = estado;
            this.tiempoEstadoTemporal = duracion;
            this.estadoTemporalActivo = true;
            this.estadoAnimo = estado; // Actualizar de inmediato para el HUD
        }
    }
    
    /**
     * Aumenta el score de Carlos, aplicando el multiplicador actual.
     * @param puntos Los puntos base a sumar.
     */
    public void aumentarScore(int puntos) {
        int puntosConMultiplicador = (int)(puntos * multiplicadorScore);
        this.score += puntosConMultiplicador;
    }
    
    // GETTERS Y SETTERS (Control de acceso)
    public int getAutoestima() { return autoestima; }
    public int getEbriedad() { return ebriedad; }
    public int getScore() { return score; }
    
    /**
     * Obtiene el estado de ánimo actual. Si el estado temporal está activo, devuelve ese estado.
     */
    public String getEstadoAnimo() { 
        if (estadoTemporalActivo) {
            return estadoAnimoTemporal;
        }
        return estadoAnimo; 
    }
    
    public boolean estaDeprimido() { return deprimido; }
    public float getMultiplicadorScore() { return multiplicadorScore; } 
    
    /**
     * Modifica la autoestima, clamped entre 0 y 100, y actualiza el estado de ánimo base.
     * @param puntos Los puntos a sumar (pueden ser negativos).
     */
    public void sumarAutoestima(int puntos) { 
        autoestima = Math.min(100, autoestima + puntos);
        autoestima = Math.max(0, autoestima); // Asegurar que no sea negativo
        actualizarEstadoAnimo(); // Actualiza estado base
    }
    
    /**
     * Aumenta el nivel de ebriedad, clamped al máximo de 100.
     * @param nivel El nivel a sumar.
     */
    public void aumentarEbriedad(int nivel) { 
        ebriedad += nivel;
        if (ebriedad > 100) ebriedad = 100;
    }
    
    /**
     * Reduce el nivel de ebriedad, clamped al mínimo de 0.
     * @param nivel El nivel a restar.
     */
    public void reducirEbriedad(int nivel) {
        ebriedad -= nivel;
        if (ebriedad < 0) ebriedad = 0;
    }

    /**
     * Reduce el score directamente, clamped al mínimo de 0.
     * @param puntos Los puntos a restar.
     */
    public void reducirScore(int puntos) {
        this.score -= puntos;
        if (this.score < 0) this.score = 0;
    }

    /**
     * Verifica si el power-up Coraza de Macho está activo.
     * @return true si `tiempoCoraza` es mayor a cero.
     */
    public boolean isCorazaActiva() {
        return tiempoCoraza > 0;
    }
    
    /**
     * Verifica si el power-up Autotune Emocional está activo.
     * @return true si `tiempoAutotune` es mayor a cero.
     */
    public boolean isAutotuneActivo() {
        return tiempoAutotune > 0;
    }
    
    /**
     * Verifica si el power-up Amnesia Selectiva está activo.
     * @return true si `tiempoAmnesia` es mayor a cero.
     */
    public boolean isAmnesiaActiva() {
        return tiempoAmnesia > 0;
    }
    
    /**
     * Activa el power-up Autotune, estableciendo su duración y recalculando el multiplicador.
     * @param duracion La duración en segundos.
     */
    public void activarAutotune(float duracion) {
        this.tiempoAutotune = duracion; 
        this.autotuneActivo = true;
        recalcularMultiplicador();
    }
    
    /**
     * Activa el power-up Amnesia, estableciendo su duración y recalculando el multiplicador.
     * @param duracion La duración en segundos.
     */
    public void activarAmnesia(float duracion) {
        this.tiempoAmnesia = duracion; 
        this.amnesiaActiva = true;
        recalcularMultiplicador();
    }
    
    /**
     * Activa el power-up Coraza, estableciendo su duración y recalculando el multiplicador.
     * @param duracion La duración en segundos.
     */
    public void activarCoraza(float duracion) {
        this.tiempoCoraza = duracion; 
        this.corazaActiva = true;
        recalcularMultiplicador();
    }
    
    public float getTiempoCoraza() {
        return tiempoCoraza;
    }
    
    public float getTiempoAutotune() {
        return tiempoAutotune;
    }
    
    public float getTiempoAmnesia() {
        return tiempoAmnesia;
    }
    
    // IMPLEMENTACIÓN DIRECTA DE LA INTERFAZ ELEMENTOJUEGO
    
    /**
     * Implementación del método actualizar de ElementoJuego.
     * Ejecuta la lógica de movimiento, la actualización de temporizadores de power-ups
     * y el estado de ánimo temporal.
     */
    @Override
    public void actualizar() {
        actualizarMovimientoInterno();
        actualizarPowerUps();
        actualizarEstadoTemporal();
    }
    
    /**
     * Implementación del método dibujar de ElementoJuego.
     * Dibuja la textura de Carlos. Si está deprimido, aplica un pequeño temblor visual.
     * @param batch El SpriteBatch activo.
     */
    @Override
    public void dibujar(SpriteBatch batch) {
        if (!deprimido) {
            batch.draw(textura, area.x, area.y);
        } else {
            // Aplica un desplazamiento aleatorio (temblor)
            batch.draw(textura, area.x, area.y + MathUtils.random(-3, 3));
            
            // Decrementa el temporizador de depresión y desactiva si llega a cero
            tiempoDeprimido--;
            if (tiempoDeprimido <= 0) deprimido = false;
        }
    }
    
    /**
     * Implementación del método getArea de ElementoJuego.
     * @return El Rectangle que representa el área de colisión.
     */
    @Override
    public Rectangle getArea() {
        return area;
    }
    
    /**
     * Implementación del método estaFueraDePantalla de ElementoJuego.
     * Siempre devuelve false, ya que Carlos no debe salir de la pantalla.
     */
    @Override
    public boolean estaFueraDePantalla() {
        return false; 
    }
    
    // MÉTODO PRIVADO para la lógica interna de movimiento
    /**
     * Contiene la lógica de movimiento del personaje (teclado, límites)
     * y la reducción gradual de la ebriedad.
     */
    private void actualizarMovimientoInterno() {
        // Movimiento horizontal basado en las teclas de dirección
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            // La velocidad se reduce por el nivel de ebriedad
            area.x -= (velocidad - ebriedad) * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            area.x += (velocidad - ebriedad) * Gdx.graphics.getDeltaTime();
        }
        
        // Efectos de ebriedad: añade un pequeño movimiento aleatorio si el nivel es alto
        if (ebriedad > 30) {
            area.x += MathUtils.random(-2, 2) * (ebriedad / 20);
        }
        
        // Limites de pantalla
        if (area.x < 0) area.x = 0;
        if (area.x > 800 - 64) area.x = 800 - 64;
        
        // Sistema de reducción gradual de ebriedad a lo largo del tiempo
        tiempoDesdeUltimaReduccion += Gdx.graphics.getDeltaTime();
        if (tiempoDesdeUltimaReduccion >= INTERVALO_REDUCCION) {
            if (ebriedad > 0) {
                ebriedad -= CANTIDAD_REDUCCION;
                if (ebriedad < 0) ebriedad = 0;
            }
            tiempoDesdeUltimaReduccion = 0;
        }
    }
    
    
    // MÉTODOS PRIVADOS (Encapsulamiento interno)
    /**
     * Gestiona los temporizadores de los power-ups activos.
     * Si un temporizador llega a cero, desactiva el power-up y recalcula el multiplicador.
     */
    private void actualizarPowerUps() {
        boolean cambio = false;
        
        // Actualizar temporizador de coraza
        if (tiempoCoraza > 0) {
            tiempoCoraza -= Gdx.graphics.getDeltaTime();
            if (tiempoCoraza <= 0) {
                tiempoCoraza = 0;
                corazaActiva = false;
                cambio = true;
            }
        }
        
        // Actualizar temporizador de autotune
        if (tiempoAutotune > 0) {
            tiempoAutotune -= Gdx.graphics.getDeltaTime();
            if (tiempoAutotune <= 0) {
                tiempoAutotune = 0;
                autotuneActivo = false;
                cambio = true;
            }
        }
        
        // Actualizar temporizador de amnesia
        if (tiempoAmnesia > 0) {
            tiempoAmnesia -= Gdx.graphics.getDeltaTime();
            if (tiempoAmnesia <= 0) {
                tiempoAmnesia = 0;
                amnesiaActiva = false;
                cambio = true;
            }
        }
        
        // Recalcular solo si hubo cambio en el estado de algún power-up
        if (cambio) {
            recalcularMultiplicador();
        }
    }
    
    /**
     * Gestiona el temporizador del estado de ánimo temporal (ej. Ira, Depresión).
     * Si el tiempo expira, revierte el estado de ánimo al estado base.
     */
    private void actualizarEstadoTemporal() {
        if (estadoTemporalActivo) {
            tiempoEstadoTemporal -= Gdx.graphics.getDeltaTime();
            
            if (tiempoEstadoTemporal <= 0) {
                tiempoEstadoTemporal = 0;
                estadoTemporalActivo = false;
                actualizarEstadoAnimo(); // Revertir al estado base
            }
        }
    }
    
    /**
     * Establece el estado de ánimo base (Negación/Aceptación) basado en el nivel de autoestima.
     * Solo se ejecuta si NO hay un estado de ánimo temporal activo.
     */
    private void actualizarEstadoAnimo() {
        if (estadoTemporalActivo) return; 
        
        if (autoestima > 30) {
            estadoAnimo = "Negación";
        } else {
            estadoAnimo = "Aceptación";
        }
    }
    
    /**
     * Recalcula el multiplicador de score basado en los power-ups activos.
     * La base es 1.0x, y cada power-up activo añade un bonus fijo.
     */
    private void recalcularMultiplicador() {
        multiplicadorScore = 1.0f; // Base
        
        if (autotuneActivo) multiplicadorScore += 0.5f;
        if (amnesiaActiva) multiplicadorScore += 0.3f;
        if (corazaActiva) multiplicadorScore += 0.2f;
    }
    
    /**
     * Libera la textura para evitar fugas de memoria.
     */
    public void destruir() {
        textura.dispose();
    }
    
    // Métodos Getters y Setters adicionales...
    public int getVelocidad() {
    	return velocidad;
    }
    public void setVelocidad(int velocidad) {
    	this.velocidad = velocidad;
    }
    
    public void setAutoestima(int autoestima) { 
        this.autoestima = Math.min(100, Math.max(0, autoestima));
        actualizarEstadoAnimo();
    }
    
    public void setEbriedad(int ebriedad) { 
        this.ebriedad = Math.min(100, Math.max(0, ebriedad));
    }
    
    public void setScore(int score) { 
        this.score = Math.max(0, score); 
    }
    
    public void setEstadoAnimo(String estadoAnimo) { 
        this.estadoAnimo = estadoAnimo; 
    }
    
    public void setEstadoTemporalActivo(boolean activo) { 
        this.estadoTemporalActivo = activo; 
    }
    
    public void setTiempoEstadoTemporal(float tiempo) { 
        this.tiempoEstadoTemporal = tiempo; 
    }
    
    public void setEstadoAnimoTemporal(String estado) {
        this.estadoAnimoTemporal = estado;
    }
    
    public void setTiempoCoraza(float tiempo) { 
        this.tiempoCoraza = tiempo; 
        this.corazaActiva = tiempo > 0;
        recalcularMultiplicador();
    }
    
    public void setTiempoAutotune(float tiempo) { 
        this.tiempoAutotune = tiempo; 
        this.autotuneActivo = tiempo > 0;
        recalcularMultiplicador();
    }
    
    public void setTiempoAmnesia(float tiempo) { 
        this.tiempoAmnesia = tiempo; 
        this.amnesiaActiva = tiempo > 0;
        recalcularMultiplicador();
    }
    
    public void setMultiplicadorScore(float multiplicador) { 
        this.multiplicadorScore = Math.max(1.0f, multiplicador); 
    }
    
    public void setDeprimido(boolean deprimido) {
        this.deprimido = deprimido;
        if (!deprimido) this.tiempoDeprimido = 0;
    }
    
    public void setTiempoDeprimido(int tiempo) {
        this.tiempoDeprimido = tiempo;
        this.deprimido = tiempo > 0;
    }
    
    public void setTiempoDeprimidoMax(int tiempo) {
        this.tiempoDeprimidoMax = tiempo;
    }
    
    public boolean isEstadoTemporalActivo() { return estadoTemporalActivo; }
    public float getTiempoEstadoTemporal() { return tiempoEstadoTemporal; }
    public String getEstadoAnimoTemporal() { return estadoAnimoTemporal; }
    public int getTiempoDeprimido() { return tiempoDeprimido; }
    public int getTiempoDeprimidoMax() { return tiempoDeprimidoMax; }
    public Texture getTextura() { return textura; }
    public Sound getSonidoLlanto() { return sonidoLlanto; }
}
