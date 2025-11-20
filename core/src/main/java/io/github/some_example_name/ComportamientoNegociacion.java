package io.github.some_example_name;

public class ComportamientoNegociacion implements ComportamientoEstado {
    
    @Override
    public float getMultiplicadorDanioRecuerdo() {
        return 1.1f; // +10% daño
    }
    
    @Override
    public float getMultiplicadorPotenciaAlcoholica() {
        return 1.1f; // +10% alcohol
    }
    
    @Override
    public float getMultiplicadorBoostAutoestima() {
        return 0.9f; // -10% autoestima
    }
    
    @Override
    public float getMultiplicadorPuntosScore() {
        return 1.3f; // +30% puntos
    }
    
    @Override
    public float getMultiplicadorVelocidadCaida() {
        return 1.0f; // Velocidad normal
    }
    
    @Override
    public float getMultiplicadorDuracionPowerUp() {
        return 1.1f; // +10% duración power-ups
    }
}
