package io.github.some_example_name;

import java.util.List;

/**
 * Encapsula una regla completa de transición entre estados
 * FÁCIL DE CONFIGURAR: Agregar/remover condiciones sin afectar lógica
 */
public class ReglaTransicion {
    private final EstadoDuelo estadoOrigen;
    private final EstadoDuelo estadoDestino;
    private final List<Condicion> condiciones;
    private final boolean esRegreso; // true si es regreso a estado anterior
    
    public ReglaTransicion(EstadoDuelo estadoOrigen, EstadoDuelo estadoDestino, 
                          List<Condicion> condiciones, boolean esRegreso) {
        this.estadoOrigen = estadoOrigen;
        this.estadoDestino = estadoDestino;
        this.condiciones = condiciones;
        this.esRegreso = esRegreso;
    }
    
    public ReglaTransicion(EstadoDuelo estadoOrigen, EstadoDuelo estadoDestino, 
                          List<Condicion> condiciones) {
        this(estadoOrigen, estadoDestino, condiciones, false);
    }
    
    // Evalúa si se cumplen TODAS las condiciones
    public boolean esAplicable(int autoestima, int ebriedad, int score,
                              float tiempoJuego, float tiempoEstadoActual,
                              int recuerdosEvitados, int recuerdosTomados,
                              int powerupsUsados, int powerupsActivos, int tragosConsumidos) {
        
        for (Condicion condicion : condiciones) {
            if (!condicion.evaluar(autoestima, ebriedad, score, tiempoJuego,
                                 tiempoEstadoActual, recuerdosEvitados, recuerdosTomados,
                                 powerupsUsados, powerupsActivos, tragosConsumidos)) {
                return false;
            }
        }
        return true;
    }
    
    // GETTERS
    public EstadoDuelo getEstadoOrigen() { return estadoOrigen; }
    public EstadoDuelo getEstadoDestino() { return estadoDestino; }
    public List<Condicion> getCondiciones() { return condiciones; }
    public boolean isEsRegreso() { return esRegreso; }
    
    @Override
    public String toString() {
        return estadoOrigen + " → " + estadoDestino + " [" + condiciones.size() + " condiciones]";
    }
}
