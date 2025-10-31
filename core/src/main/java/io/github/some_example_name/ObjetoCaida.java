package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase abstracta base para todos los objetos que caen.
 * Proporciona la lógica compartida de movimiento, dibujado y comprobación de límites
 * e implementa la interfaz ElementoJuego.
 */
public abstract class ObjetoCaida implements ElementoJuego{
    protected Texture textura;
    protected Rectangle area;
    protected float velocidadCaida;
    
    /**
     * Constructor que inicializa el área de colisión y la velocidad de caída base.
     */
    public ObjetoCaida(Texture textura, float x, float y) {
        this.textura = textura;
        this.area = new Rectangle(x, y, 64, 64);
        this.velocidadCaida = 200f;
    }
    
    // Método concreto compartido
    /**
     * Actualiza la posición del objeto, moviéndolo hacia abajo según la velocidad
     * y el tiempo delta.
     */
    @Override
    public void actualizar() {
        area.y -= velocidadCaida * Gdx.graphics.getDeltaTime();
    }
    
    // Método concreto compartido
    /**
     * Dibuja la textura del objeto en su posición actual.
     * @param batch El SpriteBatch activo.
     */
    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }
    
    // Método concreto compartido
    /**
     * Verifica si el objeto ha caído por debajo del borde inferior de la pantalla (y < -64).
     * @return true si el objeto está fuera de pantalla, false en caso contrario.
     */
    @Override
    public boolean estaFueraDePantalla() {
        return area.y + 64 < 0;	
    }
    
    /**
     * Obtiene el área de colisión del objeto.
     * @return El Rectangle del área de colisión.
     */
    @Override
    public Rectangle getArea() {
        return area;
    }
    
    // Método abstracto que las subclases deben implementar
    /**
     * Método abstracto que define el efecto específico que ocurre cuando el objeto
     * colisiona con el personaje Carlos.
     * @param carlos La instancia de Carlos que recibió el efecto.
     */
    public abstract void aplicarEfecto(Carlos carlos);
    
    // Métodos Getters y Setters...
    public float getVelocidadCaida() { 
        return velocidadCaida; 
    }
    
    public void setVelocidadCaida(float velocidad) { 
        this.velocidadCaida = velocidad; 
    }
    
    public Texture getTextura() {
        return textura;
    }
    
    public void setTextura(Texture textura) {
        this.textura = textura;
    }
    
    public float getX() {
        return area.x;
    }
    
    public void setX(float x) {
        this.area.x = x;
    }
    
    public float getY() {
        return area.y;
    }
    
    public void setY(float y) {
        this.area.y = y;
    }
    
    public float getWidth() {
        return area.width;
    }
    
    public void setWidth(float width) {
        this.area.width = width;
    }
    
    public float getHeight() {
        return area.height;
    }
    
    public void setHeight(float height) {
        this.area.height = height;
    }
}