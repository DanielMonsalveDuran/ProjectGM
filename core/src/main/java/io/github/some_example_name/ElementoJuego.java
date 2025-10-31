package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface ElementoJuego {
	void actualizar();
    void dibujar(SpriteBatch batch);
    Rectangle getArea();
    boolean estaFueraDePantalla();
}
