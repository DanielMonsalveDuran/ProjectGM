package io.github.some_example_name;

public class ComportamientoAceptacion implements ComportamientoEstado {
    
    @Override
    public float getMultiplicadorDanioRecuerdo() {
        return 1.6f; // +60% daño
    }
    
    @Override
    public float getMultiplicadorPotenciaAlcoholica() {
        return 0.0f; // 0% alcohol - Carlos está sobrio
    }
    
    @Override
    public float getMultiplicadorBoostAutoestima() {
        return 1.8f; // +80% autoestima
    }
    
    @Override
    public float getMultiplicadorPuntosScore() {
        return 2.0f; // +100% puntos
    }
    
    @Override
    public float getMultiplicadorVelocidadCaida() {
        return 1.1f; // +10% velocidad
    }
    
    @Override
    public float getMultiplicadorDuracionPowerUp() {
        return 0.8f; // -20% duración power-ups
    }
}
