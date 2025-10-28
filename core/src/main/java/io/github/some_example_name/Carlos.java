package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Carlos {
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
    
    // Power-ups activos (privados)
    private boolean autotuneActivo;
    private boolean amnesiaActiva;
    private boolean corazaActiva;
    private float tiempoPowerUp = 0;
    
    // ✅ SIMPLIFICADO: Multiplicador solo por power-ups
    private float multiplicadorScore;
    
    private static final float INTERVALO_REDUCCION = 0.5f;
    private static final int CANTIDAD_REDUCCION = 1;
    private float tiempoDesdeUltimaReduccion = 0f;
    
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
        this.tiempoPowerUp = 0;
        this.multiplicadorScore = 1.0f; // ✅ Base 1.0x siempre
    }
    
    // MÉTODOS PÚBLICOS (Interfaz controlada)
    public void crear() {
        area = new Rectangle();
        area.x = 800 / 2 - 64 / 2;
        area.y = 20;
        area.width = 64;
        area.height = 64;
    }
    
    public void actualizarMovimiento() {
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
        
        // ✅ MANTENIDO: Sistema de reducción de ebriedad
        tiempoDesdeUltimaReduccion += Gdx.graphics.getDeltaTime();
        
        if (tiempoDesdeUltimaReduccion >= INTERVALO_REDUCCION) {
            if (ebriedad > 0) {
                ebriedad -= CANTIDAD_REDUCCION;
                if (ebriedad < 0) ebriedad = 0;
                System.out.println("🍺 Ebriedad reducida a: " + ebriedad);
            }
            tiempoDesdeUltimaReduccion = 0;
        }
        
        actualizarPowerUps();
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
        this.tiempoPowerUp = 0;
        this.multiplicadorScore = 1.0f; // ✅ Reiniciar a 1.0x
        
        if (area != null) {
            area.x = 800 / 2 - 64 / 2;
            area.y = 20;
        }
    }
    
    public void dibujar(SpriteBatch batch) {
        if (!deprimido) {
            batch.draw(textura, area.x, area.y);
        } else {
            batch.draw(textura, area.x, area.y + MathUtils.random(-3, 3));
            tiempoDeprimido--;
            if (tiempoDeprimido <= 0) deprimido = false;
        }
    }
    
    public void deprimir() {
        if (!corazaActiva) {
            autoestima -= 20;
            deprimido = true;
            tiempoDeprimido = tiempoDeprimidoMax;
            
            // ✅ CORREGIDO: Verificar si el sonido existe
            if (sonidoLlanto != null) {
                sonidoLlanto.play();
            }
            
            actualizarEstadoAnimo();
        }
    }
    
    // ✅ NUEVO: Método para aumentar score (sin dependencia emocional)
    public void aumentarScore(int puntos) {
        int puntosConMultiplicador = (int)(puntos * multiplicadorScore);
        this.score += puntosConMultiplicador;
        System.out.println("⭐ +" + puntosConMultiplicador + " puntos! (Multiplicador: " + multiplicadorScore + "x)");
    }
    
    // GETTERS Y SETTERS (Control de acceso)
    public Rectangle getArea() { return area; }
    public int getAutoestima() { return autoestima; }
    public int getEbriedad() { return ebriedad; }
    public int getScore() { return score; } // ✅ NUEVO GETTER
    public String getEstadoAnimo() { return estadoAnimo; }
    public boolean estaDeprimido() { return deprimido; }
    public float getMultiplicadorScore() { return multiplicadorScore; } // ✅ NUEVO GETTER
    
    public void sumarAutoestima(int puntos) { 
        autoestima = Math.min(100, autoestima + puntos);
    }
    
    public void aumentarEbriedad(int nivel) { 
        ebriedad += nivel;
        if (ebriedad > 100) ebriedad = 100;
        System.out.println("🍺 Ebriedad: " + ebriedad + "/100");
    }
    
 // 🟢 NUEVO: Método para reducir ebriedad (usado por Amnesia)
    public void reducirEbriedad(int nivel) {
        ebriedad -= nivel;
        if (ebriedad < 0) ebriedad = 0;
        System.out.println("🍺 Ebriedad reducida a: " + ebriedad + "/100");
    }

    // 🟢 NUEVO: Método para reducir score sin multiplicador (usado por Amnesia)
    public void reducirScore(int puntos) {
        this.score -= puntos;
        if (this.score < 0) this.score = 0;
        System.out.println("⭐ -" + puntos + " puntos! (Amnesia)");
    }

    // 🟢 NUEVO GETTER: Para que Recuerdo pueda chequear la protección
    public boolean isCorazaActiva() {
        return corazaActiva;
    }
    
    public void activarAutotune(float duracion) {
        autotuneActivo = true;
        tiempoPowerUp = duracion;
        recalcularMultiplicador(); // ✅ Recalcular multiplicador
    }
    
    public void activarAmnesia(float duracion) {
        amnesiaActiva = true;
        tiempoPowerUp = duracion;
        recalcularMultiplicador(); // ✅ Recalcular multiplicador
    }
    
    public void activarCoraza(float duracion) {
        corazaActiva = true;
        tiempoPowerUp = duracion;
        recalcularMultiplicador(); // ✅ Recalcular multiplicador
    }
    
    // MÉTODOS PRIVADOS (Encapsulamiento interno)
    private void actualizarPowerUps() {
        if (tiempoPowerUp > 0) {
            tiempoPowerUp -= Gdx.graphics.getDeltaTime();
            if (tiempoPowerUp <= 0) {
                autotuneActivo = false;
                amnesiaActiva = false;
                corazaActiva = false;
                // ✅ Solo recalcular multiplicador basado en power-ups
                recalcularMultiplicador();
            }
        }
    }
    
    private void actualizarEstadoAnimo() {
        // ✅ ESTE MÉTODO SE MANTIENE para el estado emocional visual
        // pero ya NO afecta el multiplicador de score
        
        if (autoestima >= 80) estadoAnimo = "Negación";
        else if (autoestima >= 60) estadoAnimo = "Ira";
        else if (autoestima >= 30) estadoAnimo = "Depresión";
        else estadoAnimo = "Aceptación";
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
}
