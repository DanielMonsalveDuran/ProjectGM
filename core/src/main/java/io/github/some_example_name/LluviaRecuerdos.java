package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class LluviaRecuerdos {
    // Campos de la clase...
    private Array<ObjetoCaida> objetosCaida;
    private long ultimoObjetoTiempo;
    private Texture tragoCervezaTexture;
    private Texture tragoWhiskyTexture;
    private Texture tragoTequilaTexture;
    private Texture recuerdoFotoTexture;
    private Texture recuerdoCartaTexture;
    private Texture recuerdoMensajeTexture;
    private Texture powerupAutotuneTexture;
    private Texture powerupAmnesiaTexture;
    private Texture powerupCorazaTexture;
    private Sound tragoSound;
    private Music musicaKaraoke;
    
    /**
     * Constructor que inicializa las referencias a todas las texturas, sonidos y música
     * que serán utilizadas para crear los objetos que caen.
     */
    public LluviaRecuerdos(Texture tragoCervezaTex, Texture tragoWhiskyTex, Texture tragoTequilaTex, 
                          Texture fotoTex, Texture cartaTex, Texture mensajeTex,
                          Texture autotuneTex, Texture amnesiaTex, Texture corazaTex,
                          Sound ts, Music mm) {
        // Asignación de referencias a los campos de la clase
        musicaKaraoke = mm;
        tragoSound = ts;
        this.tragoCervezaTexture = tragoCervezaTex;
        this.tragoTequilaTexture = tragoTequilaTex;
        this.tragoWhiskyTexture = tragoWhiskyTex;
        this.recuerdoFotoTexture = fotoTex;
        this.recuerdoCartaTexture = cartaTex;
        this.recuerdoMensajeTexture = mensajeTex;
        this.powerupAutotuneTexture = autotuneTex;
        this.powerupAmnesiaTexture = amnesiaTex;
        this.powerupCorazaTexture = corazaTex;
    }
    
    /**
     * Inicializa el array de objetos que caen y comienza la música.
     * Se utiliza para iniciar o reiniciar el estado de la lluvia de objetos.
     */
    public void crear() {
        objetosCaida = new Array<ObjetoCaida>();
        crearObjeto();
        
        // Solo reproducir música si existe y configurar el loop
        if (musicaKaraoke != null) {
            musicaKaraoke.setLooping(true);
            musicaKaraoke.play();
        }
    }
    
    /**
     * Crea un nuevo objeto que cae (Trago, Recuerdo o Power-up)
     * basándose en una probabilidad definida (60% Tragos, 30% Recuerdos, 10% Power-ups).
     * El nuevo objeto se añade al array `objetosCaida`.
     */
    private void crearObjeto() {
        // Lógica de cálculo de posición x inicial y posición y superior
        float x = MathUtils.random(0, 800 - 64);
        float y = 480;
        
        // Generación de un número aleatorio para determinar el tipo de objeto
        int tipo = MathUtils.random(1, 10);
        ObjetoCaida nuevoObjeto;
        
        if (tipo <= 6) {
        	int tipoTrago = MathUtils.random(0, 2);
        	if (tipoTrago == 0) {
        	    nuevoObjeto = new TragoCervezaBarata(tragoCervezaTexture, x, y);
        	} else if (tipoTrago == 1) {
        	    nuevoObjeto = new TragoWhisky(tragoWhiskyTexture, x, y);
        	} else {
        	    nuevoObjeto = new TragoTequila(tragoTequilaTexture, x, y);
        	}

        
        	//========================================================
            // ESPACIO PARA DISPOSE DE LOS 3 TRAGOS
            // ========================================================
        	//if (tipoTrago == 0) {
        	    //nuevoObjeto = new TragoCervezaBarata(loader.getTragoCervezaTexture(), x, y);
        	//} else if (tipoTrago == 1) {
        	    //nuevoObjeto = new TragoWhisky(loader.getTragoWhiskyTexture(), x, y);
        	//} else {
        	    //nuevoObjeto = new TragoTequila(loader.getTragoTequilaTexture(), x, y);
        	//}
        	
        	
        } else if (tipo <= 9) {
            // 30% probabilidad: Instancia una de las subclases de Recuerdo
            int tipoRecuerdo = MathUtils.random(0, 2);
            
            if (tipoRecuerdo == 0) {
                // Foto: Ira
                nuevoObjeto = new RecuerdoFoto(recuerdoFotoTexture, x, y);
            } else if (tipoRecuerdo == 1) {
                // Carta: Depresión
                nuevoObjeto = new RecuerdoCarta(recuerdoCartaTexture, x, y);
            } else {
                // Mensaje: Negociación
                nuevoObjeto = new RecuerdoMensaje(recuerdoMensajeTexture, x, y);
            }
        } else {
            // 10% probabilidad: Instancia uno de los Power-ups
            int tipoPowerUp = MathUtils.random(0, 2);
            // Declaraciones String y Texture eliminadas por el compilador, pero no modificadas
            String tipoString;
            Texture texturaPowerUp;
            
            if (tipoPowerUp == 0) {
                // Amnesia Selectiva
                nuevoObjeto = new PowerUpAmnesiaSelectiva(powerupAmnesiaTexture, x, y);
            } else if (tipoPowerUp == 1) {
                // Autotune Emocional
                nuevoObjeto = new PowerUpAutotuneEmocional(powerupAutotuneTexture, x, y);
            } else {
                // Coraza de Macho
                nuevoObjeto = new PowerUpCorazaDeMacho(powerupCorazaTexture, x, y);
            }
        }
        
        // Añade el objeto al array y actualiza el tiempo del último objeto creado
        objetosCaida.add(nuevoObjeto);
        ultimoObjetoTiempo = TimeUtils.nanoTime();
    }
    
    /**
     * Actualiza la lógica de movimiento de todos los objetos que caen,
     * verifica las colisiones con Carlos, y elimina objetos fuera de pantalla.
     * @param carlos La instancia del personaje Carlos con la que se verifican las colisiones.
     */
    public void actualizarMovimiento(Carlos carlos) {
        if (TimeUtils.nanoTime() - ultimoObjetoTiempo > 100000000) {
            crearObjeto();
        }
        
        for (int i = objetosCaida.size - 1; i >= 0; i--) {
            ObjetoCaida objeto = objetosCaida.get(i);
            objeto.actualizar();
            
            if (objeto.estaFueraDePantalla()) {
                // === NUEVO: Registrar recuerdo evitado ===
                carlos.getManejadorEstados().registrarRecuerdoEvitado();
                objetosCaida.removeIndex(i);
            } else if (objeto.getArea().overlaps(carlos.getArea())) {
                objeto.aplicarEfecto(carlos);
                
                // === NUEVO: Registrar recuerdo tomado ===
                carlos.getManejadorEstados().registrarRecuestoTomado();
                
                if (objeto instanceof Trago && tragoSound != null) {
                    tragoSound.play();
                }
                
                objetosCaida.removeIndex(i);
            }
        }
    }
    
    /**
     * Dibuja todos los objetos que están actualmente en el array `objetosCaida`.
     * @param batch El SpriteBatch activo para dibujar.
     */
    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (ObjetoCaida objeto : objetosCaida) {
            objeto.dibujar(batch);
        }
    }
    
    /**
     * Libera (dispose) todos los recursos gráficos y de audio (texturas, sonidos, música)
     * asociados a esta clase para evitar fugas de memoria.
     */
    public void destruir() {
        // Solo detener música si existe (disposes ahora en CargarArchivos)
        if (musicaKaraoke != null) {
            musicaKaraoke.stop();  // Opcional: detener antes de dispose en Singleton
        }
    }
    
    // Métodos Getters y Setters de los campos de la clase...
    public Array<ObjetoCaida> getObjetosCaida() { 
        return objetosCaida; 
    }
    
    public void setObjetosCaida(Array<ObjetoCaida> objetos) { 
        this.objetosCaida = objetos; 
    }
    
    public long getUltimoObjetoTiempo() { 
        return ultimoObjetoTiempo; 
    }
    
    public void setUltimoObjetoTiempo(long tiempo) { 
        this.ultimoObjetoTiempo = tiempo; 
    }
    
    public Texture getTragoCervezaTexture() { 
    	return tragoCervezaTexture; 
    }
    public Texture getTragoWhiskyTexture() { 
    	return tragoWhiskyTexture; 
    }
    public Texture getTragoTequilaTexture() { 
    	return tragoTequilaTexture; 
    }
    
    public Texture getRecuerdoFotoTexture() { 
        return recuerdoFotoTexture; 
    }
    
    public Texture getRecuerdoCartaTexture() { 
        return recuerdoCartaTexture; 
    }
    
    public Texture getRecuerdoMensajeTexture() { 
        return recuerdoMensajeTexture; 
    }
    
    public Texture getPowerupAutotuneTexture() { 
        return powerupAutotuneTexture; 
    }
    
    public Texture getPowerupAmnesiaTexture() { 
        return powerupAmnesiaTexture; 
    }
    
    public Texture getPowerupCorazaTexture() { 
        return powerupCorazaTexture; 
    }
    
    public Sound getTragoSound() { 
        return tragoSound; 
    }
    
    public Music getMusicaKaraoke() { 
        return musicaKaraoke; 
    }
    
    public void setTragoCervezaTexture(Texture texture) { 
        this.tragoCervezaTexture = texture; 
    }
    
    public void setTragoWhiskyTexture(Texture texture) { 
        this.tragoWhiskyTexture = texture; 
    }
    
    public void setTragoTequilaTexture(Texture texture) { 
        this.tragoTequilaTexture = texture; 
    }
    
    public void setRecuerdoFotoTexture(Texture texture) { 
        this.recuerdoFotoTexture = texture; 
    }
    
    public void setRecuerdoCartaTexture(Texture texture) { 
        this.recuerdoCartaTexture = texture; 
    }
    
    public void setRecuerdoMensajeTexture(Texture texture) { 
        this.recuerdoMensajeTexture = texture; 
    }
    
    public void setPowerupAutotuneTexture(Texture texture) { 
        this.powerupAutotuneTexture = texture; 
    }
    
    public void setPowerupAmnesiaTexture(Texture texture) { 
        this.powerupAmnesiaTexture = texture; 
    }
    
    public void setPowerupCorazaTexture(Texture texture) { 
        this.powerupCorazaTexture = texture; 
    }
    
    public void setTragoSound(Sound sound) { 
        this.tragoSound = sound; 
    }
    
    public void setMusicaKaraoke(Music music) { 
        this.musicaKaraoke = music; 
    }
}