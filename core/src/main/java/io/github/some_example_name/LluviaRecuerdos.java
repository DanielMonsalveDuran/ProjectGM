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
    private FabricaDeTragedias fabricaActual;
    private RegistroFabricasEstados registroFabricas;
    private ComportamientoEstado comportamientoActual;
    
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
        
        // === INICIALIZACIÓN ABSTRACT FACTORY ===
        this.registroFabricas = RegistroFabricasEstados.getInstance();
        // Por defecto empieza con Negación (estado inicial del juego)
        this.fabricaActual = registroFabricas.getFabricaPorDefecto();
        this.comportamientoActual = registroFabricas.getComportamiento(EstadoDuelo.NEGACION);;
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
        ObjetoCaida nuevoObjeto = null;
        
        try {
        	if (tipo <= 6) {
        		// 60% probabilidad: Trago (usando fábrica actual)
                nuevoObjeto = fabricaActual.crearTrago(x, y);
            } else if (tipo <= 9) {
                // 30% probabilidad: Recuerdo (usando fábrica actual)
                nuevoObjeto = fabricaActual.crearRecuerdo(x, y);
            } else {
                // 10% probabilidad: Power-up (usando fábrica actual)
                nuevoObjeto = fabricaActual.crearPowerUp(x, y);
            }
        
    	} catch (Exception e) {
    		System.out.println("❌ Error creando objeto con fábrica: " + e.getMessage());
    		CargarArchivos loader =  CargarArchivos.getInstance();
    		// Fallback: crear objeto básico para evitar crash
    		nuevoObjeto = new TragoCervezaBarata(loader.getTragoCervezaTexture(), x, y);
    	}
        
        if (nuevoObjeto != null) {
            // Aplicar modificador de velocidad del estado actual a TODOS los objetos
        	aplicarComportamientoAObjeto(nuevoObjeto);
            objetosCaida.add(nuevoObjeto);
            ultimoObjetoTiempo = TimeUtils.nanoTime();
        }
    }
    
    
    /**
     * NUEVO MÉTODO - Determina velocidad base según tipo de objeto
     * Mantiene coherencia con el diseño original del juego
     */
    private float obtenerVelocidadBasePorTipo(ObjetoCaida objeto) {
        if (objeto instanceof Trago) return 200f;      // Velocidad base tragos
        if (objeto instanceof Recuerdo) return 180f;   // Velocidad base recuerdos  
        if (objeto instanceof PowerUp) return 150f;    // Velocidad base power-ups
        return 200f; // Default
    }
    
    private int getPotenciaBase(Trago trago) {
        if (trago instanceof TragoCervezaBarata) return 8;
        if (trago instanceof TragoWhisky) return 15;
        if (trago instanceof TragoTequila) return 25;
        return 8;
    }

    private int getBoostBase(Trago trago) {
        if (trago instanceof TragoCervezaBarata) return 3;
        if (trago instanceof TragoWhisky) return 8;
        if (trago instanceof TragoTequila) return 12;
        return 3;
    }

    private int getPuntosBase(Trago trago) {
        if (trago instanceof TragoCervezaBarata) return 30;
        if (trago instanceof TragoWhisky) return 75;
        if (trago instanceof TragoTequila) return 100;
        return 30;
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
                objeto.efectoColision(carlos);
                
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
    
    /**
     * NUEVO MÉTODO - Cambiar fábrica cuando el estado emocional cambia
     * Se llama desde ManejadorEstadosDuelo durante las transiciones
     */
    public void actualizarFabrica(EstadoDuelo nuevoEstado) {
        FabricaDeTragedias nuevaFabrica = registroFabricas.getFabrica(nuevoEstado);
        ComportamientoEstado nuevoComportamiento = registroFabricas.getComportamiento(nuevoEstado); 
        
        if (nuevaFabrica != fabricaActual) {
            this.fabricaActual = nuevaFabrica;
            System.out.println("🔄 Cambio de fábrica: " + nuevaFabrica.getClass().getSimpleName());
        }
        
        if (nuevoComportamiento != comportamientoActual) {
            this.comportamientoActual = nuevoComportamiento;
            System.out.println("🎯 Cambio de comportamiento: " + nuevoComportamiento.getClass().getSimpleName());
            
            // Aplicar a objetos existentes
            aplicarComportamientoAObjetosExistentes();
        }
    }
    
    private void aplicarComportamientoAObjetosExistentes() {
        for (ObjetoCaida objeto : objetosCaida) {
            aplicarComportamientoAObjeto(objeto);
        }
    }
    
    
    private void aplicarComportamientoAObjeto(ObjetoCaida objeto) {
        if (comportamientoActual == null) return;
        
        // Aplicar velocidad de caída
        float velocidadBase = obtenerVelocidadBasePorTipo(objeto);
        float nuevaVelocidad = velocidadBase * comportamientoActual.getMultiplicadorVelocidadCaida();
        objeto.setVelocidadCaida(nuevaVelocidad);
        
        // Modificar valores según el tipo de objeto
        if (objeto instanceof Trago) {
            Trago trago = (Trago) objeto;
            int nuevaPotencia = (int)(trago.getPotenciaAlcoholica() * comportamientoActual.getMultiplicadorPotenciaAlcoholica());
            int nuevoBoost = (int)(trago.getBoostAutoestima() * comportamientoActual.getMultiplicadorBoostAutoestima());
            int nuevosPuntos = (int)(trago.getPuntosScore() * comportamientoActual.getMultiplicadorPuntosScore());
            
            trago.setPotenciaAlcoholica(nuevaPotencia);
            trago.setBoostAutoestima(nuevoBoost);
            trago.setPuntosScore(nuevosPuntos);
        }
        
        if (objeto instanceof Recuerdo) {
            Recuerdo recuerdo = (Recuerdo) objeto;
            int nuevoDanio = (int)(recuerdo.getDanioEmocional() * comportamientoActual.getMultiplicadorDanioRecuerdo());
            recuerdo.setDanioEmocional(nuevoDanio);
        }
        
        if (objeto instanceof PowerUp) {
            PowerUp powerUp = (PowerUp) objeto;
            float nuevaDuracion = powerUp.getDuracion() * comportamientoActual.getMultiplicadorDuracionPowerUp();
            powerUp.setDuracion(nuevaDuracion);
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
    
    /**
     * NUEVO GETTER - Para acceso a la fábrica actual (debugging/testing)
     */
    public FabricaDeTragedias getFabricaActual() {
        return fabricaActual;
    }
    
    /**
     * NUEVO GETTER - Para acceso al registro de fábricas
     */
    public RegistroFabricasEstados getRegistroFabricas() {
        return registroFabricas;
    }
}