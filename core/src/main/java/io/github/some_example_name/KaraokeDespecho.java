package io.github.some_example_name;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;



public class KaraokeDespecho extends ApplicationAdapter {
       // Campos de la clase...
       private OrthographicCamera camera;
	   private SpriteBatch batch;	   
	   private BitmapFont font;
	   
	   private Carlos carlos;
	   private LluviaRecuerdos lluviaRecuerdos;
	   
	   private PantallaGameOver pantallaGameOver;
	   private PantallaVictoria pantallaVictoria; // Nueva pantalla
	   private boolean juegoActivo;
	   private boolean victoriaAlcanzada; // Nuevo flag
	   
	   private static final int TAMANIO_OBJETO = 64;
	   
	   
    /**
     * Método de inicialización llamado una sola vez al iniciar la aplicación.
     * Carga recursos (sonidos, texturas), inicializa la cámara, el batch,
     * el personaje Carlos y el gestor de objetos (LluviaRecuerdos).
     */
	@Override
	public void create() {
	    font = new BitmapFont();

	    // === USO DEL SINGLETON ===
	    CargarArchivos loader = CargarArchivos.getInstance();

	    Sound sonidoLlanto = loader.getSonidoLlanto();
	    Sound tragoSound = loader.getTragoSound();
	    Music musicaKaraoke = loader.getMusicaKaraoke();

	    Texture carlosTexture = loader.getCarlosTexture();
	    Texture tragoCervezaTexture = loader.getTragoCervezaTexture();
	    Texture tragoWhiskyTexture = loader.getTragoWhiskyTexture();
	    Texture tragoTequilaTexture = loader.getTragoTequilaTexture();
	    Texture recuerdoFotoTexture = loader.getRecuerdoFotoTexture();
	    Texture recuerdoCartaTexture = loader.getRecuerdoCartaTexture();
	    Texture recuerdoMensajeTexture = loader.getRecuerdoMensajeTexture();
	    Texture autotuneTexture = loader.getAutotuneTexture();
	    Texture amnesiaTexture = loader.getAmnesiaTexture();
	    Texture corazaTexture = loader.getCorazaTexture();

	    // === INICIALIZACIÓN DEL JUEGO ===
	    carlos = new Carlos(loader.getCarlosTexture(), loader.getSonidoLlanto());
	    lluviaRecuerdos = new LluviaRecuerdos(
	            loader.getTragoCervezaTexture(), loader.getTragoWhiskyTexture(), loader.getTragoTequilaTexture(),
	            loader.getRecuerdoFotoTexture(), loader.getRecuerdoCartaTexture(), loader.getRecuerdoMensajeTexture(),
	            loader.getAutotuneTexture(), loader.getAmnesiaTexture(), loader.getCorazaTexture(),
	            loader.getTragoSound(), loader.getMusicaKaraoke()
	    );
	    
	    
	    // 3. FINALMENTE crear ManejadorEstados con AMBAS referencias
        ManejadorEstadosDuelo manejadorEstados = new ManejadorEstadosDuelo(carlos, lluviaRecuerdos, this);
        
        // 4. Inyectar ManejadorEstados en Carlos
        carlos.setManejadorEstados(manejadorEstados);

	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, 800, 480);
	    batch = new SpriteBatch();

	    carlos.crear();
	    lluviaRecuerdos.crear();

	    juegoActivo = true;
	    victoriaAlcanzada = false;
	    pantallaGameOver = new PantallaGameOver(this);
	    pantallaVictoria = new PantallaVictoria(this);
	}
    
    /**
     * Reinicia el estado del juego para comenzar una nueva partida.
     * Reinicia el estado de Carlos y la generación de objetos.
     */
    public void reiniciarJuego() {
        // Reinicia todos los atributos de Carlos
        carlos.reiniciar();
        
        // Reinicia la generación de objetos que caen
        lluviaRecuerdos.crear(); 
        
        // Activa el juego
        juegoActivo = true;
        victoriaAlcanzada = false;
    }
    
    /**
     * El bucle principal del juego, llamado continuamente.
     * Contiene la lógica de Game Over, la actualización del estado del juego
     * y el dibujado de todos los elementos.
     */
    @Override
    public void render() {
        // 1. Verificar condición de derrota
        if (carlos.estaDerrotado()) {
            // Si Carlos está derrotado y el juego estaba activo, pasa el score a la pantalla de GO
            if (juegoActivo) { 
                pantallaGameOver.setScoreFinal(carlos.getScore()); 
            }
            juegoActivo = false;
        }
        
        // 2. Control de estado de juego
        if (!juegoActivo) {
            // Si hubo victoria, mostrar pantalla de victoria
            if (victoriaAlcanzada) {
                pantallaVictoria.render(Gdx.graphics.getDeltaTime());
            } else {
                // Si no, es Game Over
                pantallaGameOver.render(Gdx.graphics.getDeltaTime());
            }
            return;
        }
        
        // 3. Fase de Dibujado (HUD)
        ScreenUtils.clear(0.1f, 0.1f, 0.2f, 1); // Limpia la pantalla con un color de fondo
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        
        // Dibuja la información del HUD (Autoestima, Ebriedad, Estado, Score, Multiplicador)
        font.draw(batch, "Autoestima: " + carlos.getAutoestima(), 5, 475);
        font.draw(batch, "Ebrio: " + carlos.getEbriedad() + "%", 5, 450);
        font.draw(batch, "Estado Duelo: " + carlos.getManejadorEstados().getEstadoActual().getNombre(), 5, 425);
        font.draw(batch, "Score: " + carlos.getScore(), 650, 475);
        font.draw(batch, String.format("Multiplicador: %.1fx", carlos.getMultiplicadorScore()), 650, 450);
        
        if (carlos.getManejadorEstados().isAceptacionActiva()) {
            float tiempoRestante = ConfiguracionEstados.TIEMPO_ACEPTACION_VICTORIA - 
                                 carlos.getManejadorEstados().getTiempoEnAceptacion();
            font.draw(batch, String.format("Aceptación: %.1fs", tiempoRestante), 300, 475);
        }
        
        // Dibuja el estado de los Power-ups activos con su tiempo restante
        font.draw(batch, "Power-ups activos:", 5, 400);
        
        int yPos = 380;
        
        // Muestra Coraza
        if (carlos.isCorazaActiva()) {
            String texto = String.format("🛡️ Coraza: %.1fs", carlos.getTiempoCoraza());
            font.draw(batch, texto, 10, yPos);
            yPos -= 20;
        }
        
        // Muestra Autotune
        if (carlos.isAutotuneActivo()) {
            String texto = String.format("🎤 Autotune: %.1fs", carlos.getTiempoAutotune());
            font.draw(batch, texto, 10, yPos);
            yPos -= 20;
        }
        
        // Muestra Amnesia
        if (carlos.isAmnesiaActiva()) {
            String texto = String.format("🧠 Amnesia: %.1fs", carlos.getTiempoAmnesia());
            font.draw(batch, texto, 10, yPos);
            yPos -= 20;
        }
        
        // Muestra advertencia de autoestima crítica
        if (carlos.getAutoestima() <= 30) {
            font.draw(batch, "¡PELIGRO! Autoestima crítica", 300, 50);
        }
        
        batch.end();
        
        // 4. Fase de Actualización de Lógica
        if (!carlos.estaDeprimido()) {
            carlos.actualizar(); // Actualiza movimiento, power-ups y estados de Carlos
            lluviaRecuerdos.actualizarMovimiento(carlos); // Actualiza la caída de objetos y colisiones
        }
        
        // 5. Fase de Dibujado (Elementos de juego)
        batch.begin();
        carlos.dibujar(batch); // Dibuja a Carlos
        lluviaRecuerdos.actualizarDibujoLluvia(batch); // Dibuja los objetos que caen
        batch.end();
    }
    
    /**
     * Método llamado al cerrar la aplicación.
     * Libera todos los recursos gráficos y de audio cargados.
     */
    @Override
    public void dispose() {
        // Liberar recursos centralizados
        CargarArchivos.getInstance().dispose();

        // Liberar otros recursos locales
        batch.dispose();
        font.dispose();
    }
    
    // Métodos Getters y Setters...
    public Carlos getCarlos() {
        return carlos;
    }
    
    public LluviaRecuerdos getLluviaRecuerdos() {
        return lluviaRecuerdos;
    }
    
    public boolean isJuegoActivo() {
        return juegoActivo;
    }
    
    public void setJuegoActivo(boolean activo) {
        this.juegoActivo = activo;
    }
    
    public OrthographicCamera getCamera() {
        return camera;
    }
    
    public SpriteBatch getBatch() {
        return batch;
    }
    
    public BitmapFont getFont() {
        return font;
    }
    
    public PantallaGameOver getPantallaGameOver() {
        return pantallaGameOver;
    }
    
    public static int getTamanioObjeto() {
        return TAMANIO_OBJETO;
    }
    public void victoria() {
        juegoActivo = false;
        victoriaAlcanzada = true;
        pantallaVictoria.setScoreFinal(carlos.getScore());
    }
}
