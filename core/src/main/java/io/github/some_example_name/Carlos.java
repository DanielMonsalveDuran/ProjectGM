package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Carlos implements ElementoJuego{
    // ATRIBUTOS PRIVADOS (Encapsulamiento)
    private Rectangle area;
    private Texture textura;
    private Sound sonidoLlanto;
    private int autoestima;
    private int ebriedad;
    private int score; // ✅ Score infinito
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
    
    // ✅ SIMPLIFICADO: Multiplicador solo por power-ups
    private float multiplicadorScore;
    
    private static final float INTERVALO_REDUCCION = 0.5f;
    private static final int CANTIDAD_REDUCCION = 1;
    private float tiempoDesdeUltimaReduccion = 0f;
    
    // 🟢 NUEVO: Atributos para el estado temporal de Recuerdo
    private float tiempoEstadoTemporal;
    private String estadoAnimoTemporal;
    private boolean estadoTemporalActivo;
    
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
        this.multiplicadorScore = 1.0f; // ✅ Base 1.0x siempre
        
        // 🟢 Inicializar nuevo estado temporal
        this.tiempoEstadoTemporal = 0f;
        this.estadoAnimoTemporal = "Negación";
        this.estadoTemporalActivo = false;
    }
    
    // MÉTODOS PÚBLICOS (Interfaz controlada)
    public void crear() {
        area = new Rectangle();
        area.x = 800 / 2 - 64 / 2;
        area.y = 20;
        area.width = 64;
        area.height = 64;
    }
    
    
    public boolean estaDerrotado() {
        return autoestima <= 0;
    }
    
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
        this.multiplicadorScore = 1.0f; // ✅ Reiniciar a 1.0x
        
        // 🟢 Reiniciar estado temporal
        this.tiempoEstadoTemporal = 0f;
        this.estadoAnimoTemporal = "Negación";
        this.estadoTemporalActivo = false;
        
        if (area != null) {
            area.x = 800 / 2 - 64 / 2;
            area.y = 20;
        }
    }
    
    public void deprimir() {
        if (!corazaActiva) {
            // Nota: Se elimina la lógica de reducción de autoestima aquí, 
            // ya que se delega a las clases Recuerdo hijas.
            deprimido = true;
            tiempoDeprimido = tiempoDeprimidoMax;
            
            // ✅ CORREGIDO: Verificar si el sonido existe
            if (sonidoLlanto != null) {
                sonidoLlanto.play();
            }
            
        }
    }
    
    // 🟢 NUEVO: Método para setear un estado de ánimo temporal
    public void setEstadoTemporal(String estado, float duracion) {
        if (!corazaActiva) {
            this.estadoAnimoTemporal = estado;
            this.tiempoEstadoTemporal = duracion;
            this.estadoTemporalActivo = true;
            this.estadoAnimo = estado; // Actualizar de inmediato para el HUD
        }
    }
    
    // ✅ NUEVO: Método para aumentar score (sin dependencia emocional)
    public void aumentarScore(int puntos) {
        int puntosConMultiplicador = (int)(puntos * multiplicadorScore);
        this.score += puntosConMultiplicador;
    }
    
    // GETTERS Y SETTERS (Control de acceso)
    public int getAutoestima() { return autoestima; }
    public int getEbriedad() { return ebriedad; }
    public int getScore() { return score; } // ✅ NUEVO GETTER
    
    // 🟢 Modificado: Retorna el estado temporal si está activo
    public String getEstadoAnimo() { 
        if (estadoTemporalActivo) {
            return estadoAnimoTemporal;
        }
        return estadoAnimo; 
    }
    
    public boolean estaDeprimido() { return deprimido; }
    public float getMultiplicadorScore() { return multiplicadorScore; } // ✅ NUEVO GETTER
    
    public void sumarAutoestima(int puntos) { 
        autoestima = Math.min(100, autoestima + puntos);
        autoestima = Math.max(0, autoestima); // Asegurar que no sea negativo
        actualizarEstadoAnimo(); // ✅ Actualizar estado base
    }
    
    public void aumentarEbriedad(int nivel) { 
        ebriedad += nivel;
        if (ebriedad > 100) ebriedad = 100;
    }
    
 // 🟢 NUEVO: Método para reducir ebriedad (usado por Amnesia)
    public void reducirEbriedad(int nivel) {
        ebriedad -= nivel;
        if (ebriedad < 0) ebriedad = 0;
    }

    // 🟢 NUEVO: Método para reducir score sin multiplicador (usado por Amnesia)
    public void reducirScore(int puntos) {
        this.score -= puntos;
        if (this.score < 0) this.score = 0;
    }

    // 🟢 NUEVO GETTER: Para que Recuerdo pueda chequear la protección
    public boolean isCorazaActiva() {
        return tiempoCoraza > 0;
    }
    
    public boolean isAutotuneActivo() {
        return tiempoAutotune > 0;
    }
    
    public boolean isAmnesiaActiva() {
        return tiempoAmnesia > 0;
    }
    
    public void activarAutotune(float duracion) {
        this.tiempoAutotune = duracion; // ✅ RESETEA tiempo (no acumula)
        this.autotuneActivo = true;
        recalcularMultiplicador();
    }
    
    public void activarAmnesia(float duracion) {
        this.tiempoAmnesia = duracion; // ✅ RESETEA tiempo (no acumula)  
        this.amnesiaActiva = true;
        recalcularMultiplicador();
    }
    
    public void activarCoraza(float duracion) {
        this.tiempoCoraza = duracion; // ✅ RESETEA tiempo (no acumula)
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
    
// ✅ IMPLEMENTACIÓN DIRECTA DE LA INTERFAZ
    
    @Override
    public void actualizar() {
        // ✅ MOVER aquí la lógica de actualizarMovimiento()
        actualizarMovimientoInterno();
        actualizarPowerUps();
        // 🟢 NUEVO: Actualizar el temporizador del estado de ánimo temporal
        actualizarEstadoTemporal();
    }
    
    @Override
    public void dibujar(SpriteBatch batch) {
        // ✅ MOVER aquí la lógica de dibujar()
        if (!deprimido) {
            batch.draw(textura, area.x, area.y);
        } else {
            batch.draw(textura, area.x, area.y + MathUtils.random(-3, 3));
            tiempoDeprimido--;
            if (tiempoDeprimido <= 0) deprimido = false;
        }
    }
    
    @Override
    public Rectangle getArea() {
        return area;
    }
    
    @Override
    public boolean estaFueraDePantalla() {
        return false; // Carlos nunca sale de pantalla
    }
    
    // ✅ MÉTODO PRIVADO para la lógica interna de movimiento
    private void actualizarMovimientoInterno() {
        // Movimiento base
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            area.x -= (velocidad - ebriedad) * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            area.x += (velocidad - ebriedad) * Gdx.graphics.getDeltaTime();
        }
        
        // Efectos de ebriedad
        if (ebriedad > 30) {
            area.x += MathUtils.random(-2, 2) * (ebriedad / 20);
        }
        
        // Limites
        if (area.x < 0) area.x = 0;
        if (area.x > 800 - 64) area.x = 800 - 64;
        
        // Sistema de reducción de ebriedad
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
    private void actualizarPowerUps() {
        boolean cambio = false;
        
        // Actualizar coraza
        if (tiempoCoraza > 0) {
            tiempoCoraza -= Gdx.graphics.getDeltaTime();
            if (tiempoCoraza <= 0) {
                tiempoCoraza = 0;
                corazaActiva = false;
                cambio = true;
            }
        }
        
        // Actualizar autotune
        if (tiempoAutotune > 0) {
            tiempoAutotune -= Gdx.graphics.getDeltaTime();
            if (tiempoAutotune <= 0) {
                tiempoAutotune = 0;
                autotuneActivo = false;
                cambio = true;
            }
        }
        
        // Actualizar amnesia
        if (tiempoAmnesia > 0) {
            tiempoAmnesia -= Gdx.graphics.getDeltaTime();
            if (tiempoAmnesia <= 0) {
                tiempoAmnesia = 0;
                amnesiaActiva = false;
                cambio = true;
            }
        }
        
        // Recalcular solo si hubo cambio
        if (cambio) {
            recalcularMultiplicador();
        }
    }
    
    // 🟢 NUEVO: Manejar el temporizador del estado de ánimo temporal
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
    
    private void actualizarEstadoAnimo() {
        // ✅ SOLO aplicar estado base si NO hay estado temporal activo
        if (estadoTemporalActivo) return; 
        
        // 🟢 Lógica de estado base simplificada, como pide el usuario
        if (autoestima > 30) {
            estadoAnimo = "Negación";
        } else {
            estadoAnimo = "Aceptación";
        }
    }
    
    // ✅ NUEVO: Recalcular multiplicador solo basado en power-ups
    private void recalcularMultiplicador() {
        multiplicadorScore = 1.0f; // Base
        
        // ✅ Solo power-ups afectan el multiplicador
        if (autotuneActivo) multiplicadorScore += 0.5f;
        if (amnesiaActiva) multiplicadorScore += 0.3f;
        if (corazaActiva) multiplicadorScore += 0.2f;
    }
    
    public void destruir() {
        textura.dispose();
    }
    
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

