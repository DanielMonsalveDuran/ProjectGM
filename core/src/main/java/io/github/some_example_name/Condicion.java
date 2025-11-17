package io.github.some_example_name;

/**
 * Representa una condición genérica para transiciones entre estados
 * EXTENSIBLE: Fácil agregar nuevos tipos de condiciones
 */
public class Condicion {
    public enum TipoParametro {
        AUTOESTIMA, EBRIEDAD, SCORE, TIEMPO_JUEGO, TIEMPO_ESTADO_ACTUAL,
        RECUERDOS_EVITADOS, RECUERDOS_TOMADOS, POWERUPS_USADOS, 
        POWERUPS_ACTIVOS, TRAGOS_CONSUMIDOS
    }
    
    public enum Operador {
        MENOR_IGUAL("<="), MAYOR_IGUAL(">="), IGUAL("=="), 
        MENOR("<"), MAYOR(">"), DIFERENTE("!=");
        
        private final String simbolo;
        Operador(String simbolo) { this.simbolo = simbolo; }
        public String getSimbolo() { return simbolo; }
    }
    
    private final TipoParametro parametro;
    private final Operador operador;
    private final Number valor;
    
    public Condicion(TipoParametro parametro, Operador operador, Number valor) {
        this.parametro = parametro;
        this.operador = operador;
        this.valor = valor;
    }
    
    public boolean evaluar(int autoestima, int ebriedad, int score, 
                          float tiempoJuego, float tiempoEstadoActual,
                          int recuerdosEvitados, int recuerdosTomados,
                          int powerupsUsados, int powerupsActivos, int tragosConsumidos) {
        
        int valorActual = obtenerValorActual(autoestima, ebriedad, score, tiempoJuego,
                                           tiempoEstadoActual, recuerdosEvitados, 
                                           recuerdosTomados, powerupsUsados, 
                                           powerupsActivos, tragosConsumidos);
        
        return comparar(valorActual, valor.intValue());
    }
    
    private int obtenerValorActual(int autoestima, int ebriedad, int score,
                                 float tiempoJuego, float tiempoEstadoActual,
                                 int recuerdosEvitados, int recuerdosTomados,
                                 int powerupsUsados, int powerupsActivos, int tragosConsumidos) {
        switch (parametro) {
            case AUTOESTIMA: return autoestima;
            case EBRIEDAD: return ebriedad;
            case SCORE: return score;
            case TIEMPO_JUEGO: return (int) tiempoJuego;
            case TIEMPO_ESTADO_ACTUAL: return (int) tiempoEstadoActual;
            case RECUERDOS_EVITADOS: return recuerdosEvitados;
            case RECUERDOS_TOMADOS: return recuerdosTomados;
            case POWERUPS_USADOS: return powerupsUsados;
            case POWERUPS_ACTIVOS: return powerupsActivos;
            case TRAGOS_CONSUMIDOS: return tragosConsumidos;
            default: return 0;
        }
    }
    
    private boolean comparar(int valorActual, int valorObjetivo) {
        switch (operador) {
            case MENOR_IGUAL: return valorActual <= valorObjetivo;
            case MAYOR_IGUAL: return valorActual >= valorObjetivo;
            case IGUAL: return valorActual == valorObjetivo;
            case MENOR: return valorActual < valorObjetivo;
            case MAYOR: return valorActual > valorObjetivo;
            case DIFERENTE: return valorActual != valorObjetivo;
            default: return false;
        }
    }
    
    // GETTERS
    public TipoParametro getParametro() { return parametro; }
    public Operador getOperador() { return operador; }
    public Number getValor() { return valor; }
    
    @Override
    public String toString() {
        return parametro + " " + operador.getSimbolo() + " " + valor;
    }
}
