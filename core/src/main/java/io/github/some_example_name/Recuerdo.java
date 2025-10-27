package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class Recuerdo extends ObjetoCaida implements AfectableEmocionalmente {
    private int danioEmocional;
    private String tipoRecuerdo;
    
    public Recuerdo(Texture textura, float x, float y, String tipo) {
        super(textura, x, y);
        this.tipoRecuerdo = tipo;
        this.danioEmocional = 20;
    }
    
    @Override
    public void aplicarEfecto(Carlos carlos) {
        aplicarEfectoEmocional(carlos);
    }
    
    // Implementación EXPLÍCITA de la interfaz
    @Override
    public void aplicarEfectoEmocional(Carlos carlos) {
        carlos.deprimir();
        switch(tipoRecuerdo) {
            case "foto": carlos.sumarAutoestima(-15); break;
            case "carta": carlos.sumarAutoestima(-25); break;
            case "mensaje": carlos.sumarAutoestima(-10); break;
        }
    }
    
    @Override
    public String getTipoEfecto() {
        return "Dolor del Pasado - " + tipoRecuerdo;
    }
    
    @Override
    public boolean esPositivo() {
        return false;
    }
}
