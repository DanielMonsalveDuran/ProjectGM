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
	   private boolean juegoActivo;
	   
	   private static final int TAMANIO_OBJETO = 64;
	   
	   
    /**
     * Método de inicialización llamado una sola vez al iniciar la aplicación.
     * Carga recursos (sonidos, texturas), inicializa la cámara, el batch,
     * el personaje Carlos y el gestor de objetos (LluviaRecuerdos).
     */
	@Override
    public void create() {
        // Inicializa la fuente para el HUD
        font = new BitmapFont();
        
        // Inicialización de recursos de audio con manejo de errores (try-catch)
        Sound sonidoLlanto = null;
        Sound tragoSound = null;
        Music musicaKaraoke = null;
        
        // Intenta cargar el sonido de llanto
        try {
            sonidoLlanto = Gdx.audio.newSound(Gdx.files.internal("llanto.wav"));
        } catch (Exception e) {
            System.out.println("❌ No se pudo cargar llanto.wav - Continuando sin sonido");
        }
        
        // Intenta cargar el sonido del trago
        try {
            tragoSound = Gdx.audio.newSound(Gdx.files.internal("trago.wav"));
        } catch (Exception e) {
            System.out.println("❌ No se pudo cargar trago.wav - Continuando sin sonido");
        }
        
        // Intenta cargar la música de karaoke
        try {
            musicaKaraoke = Gdx.audio.newMusic(Gdx.files.internal("karaoke.mp3"));
        } catch (Exception e) {
            System.out.println("❌ No se pudo cargar karaoke.mp3 - Continuando sin música");
        }
        
        // Cargar texturas y escalarlas a un tamaño fijo
        Texture carlosTexture = cargarTexturaEscalada("carlos.png");
        Texture tragoTexture = cargarTexturaEscalada("trago.png");
        Texture recuerdoFotoTexture = cargarTexturaEscalada("recuerdo_foto.png");
        Texture recuerdoCartaTexture = cargarTexturaEscalada("recuerdo_carta.png");
        Texture recuerdoMensajeTexture = cargarTexturaEscalada("recuerdo_mensaje.png");
        Texture autotuneTexture = cargarTexturaEscalada("powerup_autotune.png");
        Texture amnesiaTexture = cargarTexturaEscalada("powerup_amnesia.png");
        Texture corazaTexture = cargarTexturaEscalada("powerup_coraza.png");
        
        // Inicializa el personaje principal
        carlos = new Carlos(carlosTexture, sonidoLlanto);
        
        // Inicializa el gestor de objetos que caen, pasando todas las texturas y audios
        lluviaRecuerdos = new LluviaRecuerdos(
            tragoTexture, 
            recuerdoFotoTexture, 
            recuerdoCartaTexture, 
            recuerdoMensajeTexture,
            autotuneTexture, 
            amnesiaTexture, 
            corazaTexture,
            tragoSound, 
            musicaKaraoke
        );
        
        // Configura la cámara ortográfica para la vista del juego (800x480)
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        
        // Llama a los métodos de creación inicial de los elementos del juego
        carlos.crear();
        lluviaRecuerdos.crear();
        
        // Inicializa el estado de la partida y la pantalla de Game Over
        juegoActivo = true;
        pantallaGameOver = new PantallaGameOver(this);
        
    }
    
    /**
     * Método auxiliar que carga una textura de un archivo y la escala
     * a un tamaño fijo definido por TAMANIO_OBJETO (64x64).
     * @param archivo El nombre del archivo de la textura a cargar.
     * @return La textura cargada y escalada, o lanza una excepción si falla críticamente.
     */
    private Texture cargarTexturaEscalada(String archivo) {
        // Intenta cargar y escalar la textura
        try {
            // Cargar el Pixmap original
            Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal(archivo));
            
            // Crear nuevo Pixmap con el tamaño deseado
            Pixmap pixmapEscalado = new Pixmap(TAMANIO_OBJETO, TAMANIO_OBJETO, Format.RGBA8888);
            
            // Dibujar el original escalado en el nuevo
            pixmapEscalado.drawPixmap(pixmapOriginal,
                0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), // área origen
                0, 0, TAMANIO_OBJETO, TAMANIO_OBJETO // área destino
            );
            
            // Crear textura desde el Pixmap escalado
            Texture textura = new Texture(pixmapEscalado);
            
            // Liberar memoria de los pixmaps
            pixmapOriginal.dispose();
            pixmapEscalado.dispose();
            
            return textura;
            
        } catch (Exception e) {
            System.out.println("   ❌ Error cargando " + archivo + ": " + e.getMessage());
            // Si falla el escalado, intenta cargar la textura normalmente
            try {
                Texture textura = new Texture(Gdx.files.internal(archivo));
                return textura;
            } catch (Exception e2) {
                System.out.println("   💥 Error crítico con " + archivo);
                // Si el archivo no existe, lanza una excepción de runtime
                throw new RuntimeException("No se pudo cargar: " + archivo);
            }
        }
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
            // Si el juego no está activo, solo renderiza la pantalla de Game Over y retorna
            pantallaGameOver.render(Gdx.graphics.getDeltaTime());
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
        font.draw(batch, "Estado: " + carlos.getEstadoAnimo(), 5, 425);
        font.draw(batch, "Score: " + carlos.getScore(), 650, 475);
        font.draw(batch, String.format("Multiplicador: %.1fx", carlos.getMultiplicadorScore()), 650, 450);
        
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
        carlos.destruir();
        lluviaRecuerdos.destruir();
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
}

