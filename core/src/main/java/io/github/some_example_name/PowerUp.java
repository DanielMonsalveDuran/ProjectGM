package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class PowerUp extends ObjetoCaida implements AfectableEmocionalmente {
    private String tipoPowerUp;
    private int duracion;
    
    public PowerUp(Texture textura, float x, float y, String tipo) {
        super(textura, x, y);
        this.tipoPowerUp = tipo;
        this.duracion = 10;
    }
    
    @Override
    public void aplicarEfecto(Carlos carlos) {
        aplicarEfectoEmocional(carlos);
    }
    
    // Implementación EXPLÍCITA de la interfaz
    @Override
    public void aplicarEfectoEmocional(Carlos carlos) {
        switch(tipoPowerUp) {
            case "autotune":
                carlos.activarAutotune(duracion);
                carlos.sumarAutoestima(15);
                break;
            case "amnesia":
                carlos.activarAmnesia(duracion);
                carlos.sumarAutoestima(10);
                break;
            case "coraza":
                carlos.activarCoraza(duracion);
                carlos.sumarAutoestima(5);
                break;
        }
    }
    
    @Override
    public String getTipoEfecto() {
        switch(tipoPowerUp) {
            case "autotune": return "Confianza Musical";
            case "amnesia": return "Olvido Terapéutico";
            case "coraza": return "Protección Emocional";
            default: return "PowerUp Desconocido";
        }
    }
    
    @Override
    public boolean esPositivo() {
        return true;
    }
}
