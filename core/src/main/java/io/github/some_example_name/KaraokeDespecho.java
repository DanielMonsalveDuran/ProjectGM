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
       private OrthographicCamera camera;
	   private SpriteBatch batch;	   
	   private BitmapFont font;
	   
	   private Carlos carlos;
	   private LluviaRecuerdos lluviaRecuerdos;
	   
	   private PantallaGameOver pantallaGameOver;
	   private boolean juegoActivo;
	   
	   private static final int TAMANIO_OBJETO = 64;
	   
	   
	@Override
    public void create() {
        font = new BitmapFont();
        
        // ✅ CÓDIGO CON TRY-CATCH PARA SONIDOS
        Sound sonidoLlanto = null;
        Sound tragoSound = null;
        Music musicaKaraoke = null;
        
        try {
            sonidoLlanto = Gdx.audio.newSound(Gdx.files.internal("llanto.wav"));
            System.out.println("✅ Sonido llanto cargado");
        } catch (Exception e) {
            System.out.println("❌ No se pudo cargar llanto.wav - Continuando sin sonido");
        }
        
        try {
            tragoSound = Gdx.audio.newSound(Gdx.files.internal("trago.wav"));
            System.out.println("✅ Sonido trago cargado");
        } catch (Exception e) {
            System.out.println("❌ No se pudo cargar trago.wav - Continuando sin sonido");
        }
        
        try {
            musicaKaraoke = Gdx.audio.newMusic(Gdx.files.internal("karaoke.mp3"));
            System.out.println("✅ Música cargada");
        } catch (Exception e) {
            System.out.println("❌ No se pudo cargar karaoke.mp3 - Continuando sin música");
        }
        
        // Cargar texturas (estas SÍ deben existir)
        Texture carlosTexture = cargarTexturaEscalada("carlos.png");
        Texture tragoTexture = cargarTexturaEscalada("trago.png");
        Texture recuerdoFotoTexture = cargarTexturaEscalada("recuerdo_foto.png");
        Texture recuerdoCartaTexture = cargarTexturaEscalada("recuerdo_carta.png");
        Texture recuerdoMensajeTexture = cargarTexturaEscalada("recuerdo_mensaje.png");
        Texture autotuneTexture = cargarTexturaEscalada("powerup_autotune.png");
        Texture amnesiaTexture = cargarTexturaEscalada("powerup_amnesia.png");
        Texture corazaTexture = cargarTexturaEscalada("powerup_coraza.png");
        
        carlos = new Carlos(carlosTexture, sonidoLlanto);
        
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
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        
        carlos.crear();
        lluviaRecuerdos.crear();
        
        juegoActivo = true;
        pantallaGameOver = new PantallaGameOver(this);
        
        System.out.println("🎮 Juego iniciado correctamente");
    }
    
    private Texture cargarTexturaEscalada(String archivo) {
        try {
            // Cargar el Pixmap original
            Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal(archivo));
            
            // Mostrar info del original
            System.out.println("   📐 " + archivo + " original: " + 
                pixmapOriginal.getWidth() + "x" + pixmapOriginal.getHeight());
            
            // Crear nuevo Pixmap con el tamaño deseado
            Pixmap pixmapEscalado = new Pixmap(TAMANIO_OBJETO, TAMANIO_OBJETO, Format.RGBA8888);
            
            // Dibujar el original escalado en el nuevo
            pixmapEscalado.drawPixmap(pixmapOriginal,
                0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(), // área origen
                0, 0, TAMANIO_OBJETO, TAMANIO_OBJETO // área destino
            );
            
            // Crear textura desde el Pixmap escalado
            Texture textura = new Texture(pixmapEscalado);
            
            // Liberar memoria
            pixmapOriginal.dispose();
            pixmapEscalado.dispose();
            
            System.out.println("   ✅ " + archivo + " escalado a " + TAMANIO_OBJETO + "x" + TAMANIO_OBJETO);
            return textura;
            
        } catch (Exception e) {
            System.out.println("   ❌ Error cargando " + archivo + ": " + e.getMessage());
            // Si falla, cargar normalmente (sin escalar)
            try {
                Texture textura = new Texture(Gdx.files.internal(archivo));
                System.out.println("   ⚠️ " + archivo + " cargado sin escalar");
                return textura;
            } catch (Exception e2) {
                System.out.println("   💥 Error crítico con " + archivo);
                throw new RuntimeException("No se pudo cargar: " + archivo);
            }
        }
    }
    
    public void reiniciarJuego() {
        // Opción simple: solo reiniciar el estado de los objetos existentes
        carlos.reiniciar();
        
        // Limpiar la lluvia de objetos actual
        lluviaRecuerdos.crear(); // Esto debería reiniciar el array de objetos
        
        juegoActivo = true;
        System.out.println("🔄 Juego reiniciado - Autoestima: " + carlos.getAutoestima());
    }
    
    @Override
    public void render() {
        // Verificar condición de derrota
        if (carlos.estaDerrotado()) {
        	if (juegoActivo) { // Solo si el juego estaba activo antes de la derrota
                pantallaGameOver.setScoreFinal(carlos.getScore()); 
            }
            juegoActivo = false;
        }
        
        // Si el juego no está activo, mostrar pantalla de Game Over
        if (!juegoActivo) {
            pantallaGameOver.render(Gdx.graphics.getDeltaTime());
            return;
        }
        
        // Limpiar pantalla
        ScreenUtils.clear(0.1f, 0.1f, 0.2f, 1);
        
        // Actualizar cámara y batch
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        // Comenzar dibujado
        batch.begin();
        
        // ✅ HUD ACTUALIZADO: Ebriedad izquierda, Score derecha
        font.draw(batch, "Autoestima: " + carlos.getAutoestima(), 5, 475);
        font.draw(batch, "Ebrio: " + carlos.getEbriedad() + "%", 5, 450);
        font.draw(batch, "Estado: " + carlos.getEstadoAnimo(), 5, 425);
        
        // ✅ NUEVO: Score arriba a la derecha
        font.draw(batch, "Score: " + carlos.getScore(), 650, 475);
        font.draw(batch, String.format("Multiplicador: %.1fx", carlos.getMultiplicadorScore()), 650, 450);
        
        // Mostrar advertencia cuando la autoestima es crítica
        if (carlos.getAutoestima() <= 30) {
            font.draw(batch, "¡PELIGRO! Autoestima crítica", 300, 50);
        }
        
        // Actualizar y dibujar elementos del juego si Carlos no está deprimido
        if (!carlos.estaDeprimido()) {
            carlos.actualizarMovimiento();
            lluviaRecuerdos.actualizarMovimiento(carlos);
        }
        
        // Dibujar personaje y lluvia de objetos
        carlos.dibujar(batch);
        lluviaRecuerdos.actualizarDibujoLluvia(batch);
        
        // Finalizar dibujado
        batch.end();
    }
    
    @Override
    public void dispose() {
        carlos.destruir();
        lluviaRecuerdos.destruir();
        batch.dispose();
        font.dispose();
    }
}

