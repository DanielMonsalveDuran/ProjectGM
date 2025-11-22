package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class PantallaVictoria implements Screen {
    private final KaraokeDespecho juego;
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont fontTitulo;
    private int scoreFinal;
    
    public PantallaVictoria(KaraokeDespecho juego) {
        this.juego = juego;
        this.batch = new SpriteBatch();
        this.scoreFinal = 0;
        this.font = new BitmapFont();
        this.fontTitulo = new BitmapFont();
        
        // Configurar fuentes
        fontTitulo.getData().setScale(2.0f);
        fontTitulo.setColor(Color.GREEN); // Color verde para victoria
        font.getData().setScale(1.2f);
        font.setColor(Color.WHITE);
    }
    
    public void setScoreFinal(int score) {
        this.scoreFinal = score;
    }
    
    @Override
    public void show() {
        // Se llama cuando se muestra esta pantalla
    }
    
    @Override
    public void render(float delta) {
        // Limpiar pantalla
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        // Dibujar título
        fontTitulo.draw(batch, "¡VICTORIA!", 0, 400, 800, Align.center, false);
        
        // Dibujar mensaje
        font.draw(batch, "¡Carlos ha superado el duelo!", 0, 300, 800, Align.center, false);
        font.draw(batch, "Ha encontrado la paz interior", 0, 250, 800, Align.center, false);
        font.draw(batch, "Score Final: " + scoreFinal, 0, 200, 800, Align.center, false);
        
        // Dibujar opciones
        font.draw(batch, "Presiona ESC para Salir", 0, 100, 800, Align.center, false);
        
        batch.end();
        
        // Manejar input
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
    
    @Override
    public void resize(int width, int height) {}
    
    @Override
    public void pause() {}
    
    @Override
    public void resume() {}
    
    @Override
    public void hide() {}
    
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        fontTitulo.dispose();
    }
}