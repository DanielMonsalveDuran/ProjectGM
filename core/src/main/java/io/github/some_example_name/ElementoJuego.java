package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Interfaz fundamental para cualquier elemento visual y lógico del juego.
 * Define el contrato básico que deben cumplir todos los objetos actualizables
 * e interactuables, como el personaje Carlos y los ObjetoCaida.
 */
public interface ElementoJuego {
	/**
	 * Método para actualizar la lógica interna del elemento (movimiento, estados, temporizadores).
	 * Debe ser llamado en el ciclo de renderizado principal.
	 */
	void actualizar();
    
    /**
     * Método para dibujar el elemento en la pantalla.
     * @param batch El SpriteBatch activo para dibujar la textura.
     */
    void dibujar(SpriteBatch batch);
    
    /**
     * Obtiene el área de colisión del elemento (hitbox).
     * @return Un objeto Rectangle que representa la posición y dimensiones del elemento.
     */
    Rectangle getArea();
    
    /**
     * Verifica si el elemento se encuentra fuera de los límites visibles de la pantalla.
     * @return true si está fuera de pantalla, false en caso contrario.
     */
    boolean estaFueraDePantalla();
}