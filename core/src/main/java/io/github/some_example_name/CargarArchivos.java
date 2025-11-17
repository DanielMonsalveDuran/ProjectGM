package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

public class CargarArchivos {
    private static CargarArchivos instance;

    // Recursos privados
    private Sound sonidoLlanto;
    private Sound tragoSound;
    private Music musicaKaraoke;
    private Texture carlosTexture;
    private Texture tragoCervezaTexture;
    private Texture tragoWhiskyTexture;
    private Texture tragoTequilaTexture;
    private Texture recuerdoFotoTexture;
    private Texture recuerdoCartaTexture;
    private Texture recuerdoMensajeTexture;
    private Texture autotuneTexture;
    private Texture amnesiaTexture;
    private Texture corazaTexture;
 // ========================================================
    // ESPACIO PARA IMPLEMENTAR LOS 3 TRAGOS 
    // ========================================================
    //private Texture tragoCervezaTexture;
    //private Texture tragoWhiskyTexture;
    //private Texture tragoTequilaTexture;
    // ========================================================

    private static final int TAMANIO_OBJETO = 64;

    // Constructor privado: carga recursos
    private CargarArchivos() {
        cargarRecursos();
    }

    public static CargarArchivos getInstance() {
        if (instance == null) {
            instance = new CargarArchivos();
        }
        return instance;
    }

    private void cargarRecursos() {
        // Cargar audio con try-catch
        try {
            sonidoLlanto = Gdx.audio.newSound(Gdx.files.internal("llanto.wav"));
        } catch (Exception e) {
            System.out.println("❌ No se pudo cargar llanto.wav - Continuando sin sonido");
        }

        try {
            tragoSound = Gdx.audio.newSound(Gdx.files.internal("trago.wav"));
        } catch (Exception e) {
            System.out.println("❌ No se pudo cargar trago.wav - Continuando sin sonido");
        }

        try {
            musicaKaraoke = Gdx.audio.newMusic(Gdx.files.internal("karaoke.mp3"));
        } catch (Exception e) {
            System.out.println("❌ No se pudo cargar karaoke.mp3 - Continuando sin música");
        }

        // Cargar y escalar texturas
        carlosTexture = cargarTexturaEscalada("carlos.png");
        tragoCervezaTexture = cargarTexturaEscalada("trago_cerveza.png");
        tragoWhiskyTexture = cargarTexturaEscalada("trago_whisky.png");
        tragoTequilaTexture = cargarTexturaEscalada("trago_tequila.png");
        recuerdoFotoTexture = cargarTexturaEscalada("recuerdo_foto.png");
        recuerdoCartaTexture = cargarTexturaEscalada("recuerdo_carta.png");
        recuerdoMensajeTexture = cargarTexturaEscalada("recuerdo_mensaje.png");
        autotuneTexture = cargarTexturaEscalada("powerup_autotune.png");
        amnesiaTexture = cargarTexturaEscalada("powerup_amnesia.png");
        corazaTexture = cargarTexturaEscalada("powerup_coraza.png");
        
     // ========================================================
        // ESPACIO PARA IMPLEMENTAR LOS 3 TRAGOS 
        // ========================================================
        // Texture tragoCervezaTexture = cargarTexturaEscalada("trago_cerveza_barata.png");
        // Texture tragoWhiskyTexture = cargarTexturaEscalada("trago_whisky.png");
        // Texture tragoTequilaTexture = cargarTexturaEscalada("trago_tequila.png");
        // ========================================================

    }

    private Texture cargarTexturaEscalada(String archivo) {
        try {
            Pixmap pixmapOriginal = new Pixmap(Gdx.files.internal(archivo));
            Pixmap pixmapEscalado = new Pixmap(TAMANIO_OBJETO, TAMANIO_OBJETO, Format.RGBA8888);
            pixmapEscalado.drawPixmap(pixmapOriginal,
                    0, 0, pixmapOriginal.getWidth(), pixmapOriginal.getHeight(),
                    0, 0, TAMANIO_OBJETO, TAMANIO_OBJETO);
            Texture textura = new Texture(pixmapEscalado);
            pixmapOriginal.dispose();
            pixmapEscalado.dispose();
            return textura;
        } catch (Exception e) {
            System.out.println("💥 Error crítico con " + archivo);
            throw new RuntimeException("No se pudo cargar: " + archivo);
        }
    }

    // Método para liberar todos los recursos
    public void dispose() {
        if (sonidoLlanto != null) sonidoLlanto.dispose();
        if (tragoSound != null) tragoSound.dispose();
        if (musicaKaraoke != null) musicaKaraoke.dispose();
        if (carlosTexture != null) carlosTexture.dispose();
        if (tragoCervezaTexture != null) tragoCervezaTexture.dispose();
        if (tragoWhiskyTexture != null) tragoWhiskyTexture.dispose();
        if (tragoTequilaTexture != null) tragoTequilaTexture.dispose();
        if (recuerdoFotoTexture != null) recuerdoFotoTexture.dispose();
        if (recuerdoCartaTexture != null) recuerdoCartaTexture.dispose();
        if (recuerdoMensajeTexture != null) recuerdoMensajeTexture.dispose();
        if (autotuneTexture != null) autotuneTexture.dispose();
        if (amnesiaTexture != null) amnesiaTexture.dispose();
        if (corazaTexture != null) corazaTexture.dispose();
        
     // ========================================================
        // ESPACIO PARA DISPOSE DE LOS 3 TRAGOS
        // ========================================================
        // if (tragoCervezaTexture != null) tragoCervezaTexture.dispose();
        // if (tragoWhiskyTexture != null) tragoWhiskyTexture.dispose();
        // if (tragoTequilaTexture != null) tragoTequilaTexture.dispose();
        // ========================================================
    }

    // Getters para recursos
    public Sound getSonidoLlanto() { return sonidoLlanto; }
    public Sound getTragoSound() { return tragoSound; }
    public Music getMusicaKaraoke() { return musicaKaraoke; }
    public Texture getCarlosTexture() { return carlosTexture; }
    public Texture getRecuerdoFotoTexture() { return recuerdoFotoTexture; }
    public Texture getRecuerdoCartaTexture() { return recuerdoCartaTexture; }
    public Texture getRecuerdoMensajeTexture() { return recuerdoMensajeTexture; }
    public Texture getAutotuneTexture() { return autotuneTexture; }
    public Texture getAmnesiaTexture() { return amnesiaTexture; }
    public Texture getCorazaTexture() { return corazaTexture; }
    public Texture getTragoCervezaTexture() { return tragoCervezaTexture; }
    public Texture getTragoWhiskyTexture() { return tragoWhiskyTexture; }
    public Texture getTragoTequilaTexture() { return tragoTequilaTexture; }
 // ========================================================
    //  ESPACIO PARA GETTERS DE LOS 3 TRAGOS
    // ========================================================
    // public Texture getTragoCervezaTexture() { return tragoCervezaTexture; }
    // public Texture getTragoWhiskyTexture() { return tragoWhiskyTexture; }
    // public Texture getTragoTequilaTexture() { return tragoTequilaTexture; }
    // ========================================================
}