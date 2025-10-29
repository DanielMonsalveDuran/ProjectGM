package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase abstracta base para todos los objetos que caen
 * REQUISITO GM1.4: Clase abstracta con al menos 2 clases hijas
 */
public abstract class ObjetoCaida implements ElementoJuego{
    protected Texture textura;
    protected Rectangle area;
    protected float velocidadCaida;
    
    public ObjetoCaida(Texture textura, float x, float y) {
        this.textura = textura;
        this.area = new Rectangle(x, y, 64, 64);
        this.velocidadCaida = 200f;
    }
    
    @Override
    public String getTipoElemento() {
        return "ObjetoCaida";
    }
    
    // Método concreto compartido
    @Override
    public void actualizar() {
        area.y -= velocidadCaida * Gdx.graphics.getDeltaTime();
    }
    
    // Método concreto compartido
    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }
    
    // Método concreto compartido
    @Override
    public boolean estaFueraDePantalla() {
        return area.y + 64 < 0;	
    }
    
    @Override
    public Rectangle getArea() {
        return area;
    }
    
    // Método abstracto que las subclases deben implementar
    public abstract void aplicarEfecto(Carlos carlos);
}