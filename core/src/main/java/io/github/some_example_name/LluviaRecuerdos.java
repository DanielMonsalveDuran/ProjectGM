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
    private Array<ObjetoCaida> objetosCaida;
    private long ultimoObjetoTiempo;
    private Texture tragoTexture;
    private Texture recuerdoFotoTexture;
    private Texture recuerdoCartaTexture;
    private Texture recuerdoMensajeTexture;
    private Texture powerupAutotuneTexture;
    private Texture powerupAmnesiaTexture;
    private Texture powerupCorazaTexture;
    private Sound tragoSound;
    private Music musicaKaraoke;
    
    public LluviaRecuerdos(Texture tragoTex, 
                          Texture fotoTex, Texture cartaTex, Texture mensajeTex,
                          Texture autotuneTex, Texture amnesiaTex, Texture corazaTex,
                          Sound ts, Music mm) {
        musicaKaraoke = mm;
        tragoSound = ts;
        this.tragoTexture = tragoTex;
        this.recuerdoFotoTexture = fotoTex;
        this.recuerdoCartaTexture = cartaTex;
        this.recuerdoMensajeTexture = mensajeTex;
        this.powerupAutotuneTexture = autotuneTex;
        this.powerupAmnesiaTexture = amnesiaTex;
        this.powerupCorazaTexture = corazaTex;
    }
    
    public void crear() {
        objetosCaida = new Array<ObjetoCaida>();
        crearObjeto();
        
        // Solo reproducir música si existe
        if (musicaKaraoke != null) {
            musicaKaraoke.setLooping(true);
            musicaKaraoke.play();
        }
    }
    
    private void crearObjeto() {
        float x = MathUtils.random(0, 800 - 64);
        float y = 480;
        
        int tipo = MathUtils.random(1, 10);
        ObjetoCaida nuevoObjeto;
        
        if (tipo <= 6) {
            // 60% probabilidad - Tragos
            nuevoObjeto = new Trago(tragoTexture, x, y);
        } else if (tipo <= 9) {
            // 30% probabilidad - Recuerdos (Instanciar subclases)
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
            // 10% probabilidad - Power-ups
            int tipoPowerUp = MathUtils.random(0, 2);
            String tipoString;
            Texture texturaPowerUp;
            
            if (tipoPowerUp == 0) {
                // 🟢 CORREGIDO: Amnesia Selectiva usa powerupAmnesiaTexture
                nuevoObjeto = new PowerUpAmnesiaSelectiva(powerupAmnesiaTexture, x, y);
            } else if (tipoPowerUp == 1) {
                // 🟢 CORREGIDO: Autotune Emocional usa powerupAutotuneTexture
                nuevoObjeto = new PowerUpAutotuneEmocional(powerupAutotuneTexture, x, y);
            } else {
                nuevoObjeto = new PowerUpCorazaDeMacho(powerupCorazaTexture, x, y);
            }
        }
        
        objetosCaida.add(nuevoObjeto);
        ultimoObjetoTiempo = TimeUtils.nanoTime();
    }
    
    public void actualizarMovimiento(Carlos carlos) {
        if (TimeUtils.nanoTime() - ultimoObjetoTiempo > 100000000) {
            crearObjeto();
        }
        
        for (int i = objetosCaida.size - 1; i >= 0; i--) {
            ObjetoCaida objeto = objetosCaida.get(i);
            objeto.actualizar();
            
            if (objeto.estaFueraDePantalla()) {
                objetosCaida.removeIndex(i);
            } else if (objeto.getArea().overlaps(carlos.getArea())) {
                objeto.aplicarEfecto(carlos);
                
                // ✅ CORREGIDO: Verificar si el sonido existe
                if (objeto instanceof Trago && tragoSound != null) {
                    tragoSound.play();
                }
                
                objetosCaida.removeIndex(i);
            }
        }
    }
    
    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (ObjetoCaida objeto : objetosCaida) {
            objeto.dibujar(batch);
        }
    }
    
    public void destruir() {
        // Liberar sonidos si existen
        if (tragoSound != null) {
            tragoSound.dispose();
        }
        if (musicaKaraoke != null) {
            musicaKaraoke.dispose();
        }
        
        // Liberar texturas
        tragoTexture.dispose();
        recuerdoFotoTexture.dispose();
        recuerdoCartaTexture.dispose();
        recuerdoMensajeTexture.dispose();
        powerupAutotuneTexture.dispose();
        powerupAmnesiaTexture.dispose();
        powerupCorazaTexture.dispose();
    }
    
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
    
    public Texture getTragoTexture() { 
        return tragoTexture; 
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
    
    public void setTragoTexture(Texture texture) { 
        this.tragoTexture = texture; 
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