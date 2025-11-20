package io.github.some_example_name;

public class ComportamientoDepresion implements ComportamientoEstado {
    
    @Override
    public float getMultiplicadorDanioRecuerdo() {
        return 0.9f; // -10% daño
    }
    
    @Override
    public float getMultiplicadorPotenciaAlcoholica() {
        return 1.2f; // +20% alcohol
    }
    
    @Override
    public float getMultiplicadorBoostAutoestima() {
        return 0.6f; // -40% autoestima
    }
    
    @Override
    public float getMultiplicadorPuntosScore() {
        return 1.5f; // +50% puntos
    }
    
    @Override
    public float getMultiplicadorVelocidadCaida() {
        return 0.6f; // -40% velocidad
    }
    
    @Override
    public float getMultiplicadorDuracionPowerUp() {
        return 1.3f; // +30% duración power-ups
    }
}
