package io.github.some_example_name;

public class ComportamientoIra implements ComportamientoEstado{
	@Override
    public float getMultiplicadorDanioRecuerdo() {
        return 1.4f; // +40% daño
    }
    
    @Override
    public float getMultiplicadorPotenciaAlcoholica() {
        return 1.3f; // +30% alcohol
    }
    
    @Override
    public float getMultiplicadorBoostAutoestima() {
        return 0.7f; // -30% autoestima
    }
    
    @Override
    public float getMultiplicadorPuntosScore() {
        return 1.2f; // +20% puntos
    }
    
    @Override
    public float getMultiplicadorVelocidadCaida() {
        return 1.4f; // +40% velocidad
    }
    
    @Override
    public float getMultiplicadorDuracionPowerUp() {
        return 0.6f; // -40% duración power-ups
    }

}
