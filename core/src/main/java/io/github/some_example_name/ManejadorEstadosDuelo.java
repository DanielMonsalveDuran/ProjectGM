package io.github.some_example_name;

import java.util.*;

/**
 * MANEJADOR PRINCIPAL - Controla toda la lógica de estados del duelo
 * ENCAPSULADO: Toda la complejidad está aquí, el resto del juego solo usa la interfaz simple
 */
public class ManejadorEstadosDuelo {
    
    // Referencia al jugador
    private final Carlos carlos;
    
    // Estado actual
    private EstadoDuelo estadoActual;
    
    // Métricas para transiciones
    private float tiempoEnEstadoActual;
    private float tiempoTotalJuego;
    private int recuerdosEvitados;
    private int recuerdosTomados;
    private int powerupsUsados;
    private int tragosConsumidos;
    
    // Estado específico de Aceptación
    private float tiempoEnAceptacion;
    private boolean aceptacionActiva;
    
    // Temporizadores
    private float tiempoDesdeUltimaMetrica;
    private static final float INTERVALO_ACTUALIZACION = 0.1f; // 10 veces por segundo
    
    public ManejadorEstadosDuelo(Carlos carlos) {
        this.carlos = carlos;
        this.estadoActual = EstadoDuelo.NEGACION;
        this.tiempoEnEstadoActual = 0f;
        this.tiempoTotalJuego = 0f;
        this.aceptacionActiva = false;
        
        // Inicializar métricas
        this.recuerdosEvitados = 0;
        this.recuerdosTomados = 0;
        this.powerupsUsados = 0;
        this.tragosConsumidos = 0;
    }
    
    /**
     * Método principal llamado en cada frame
     */
    public void actualizarEstado() {
        float delta = com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        
        // Actualizar temporizadores
        tiempoEnEstadoActual += delta;
        tiempoTotalJuego += delta;
        tiempoDesdeUltimaMetrica += delta;
        
        // Verificar transiciones cada cierto intervalo (optimización)
        if (tiempoDesdeUltimaMetrica >= INTERVALO_ACTUALIZACION) {
            verificarTransiciones();
            tiempoDesdeUltimaMetrica = 0f;
        }
        
        // Lógica específica de Aceptación
        if (estadoActual == EstadoDuelo.ACEPTACION) {
            actualizarAceptacion(delta);
        }
        
        // Aplicar efectos del estado actual
        aplicarEfectosEstadoActual();
    }
    
    /**
     * Verifica todas las posibles transiciones desde el estado actual
     */
    private void verificarTransiciones() {
        List<ReglaTransicion> reglas = ConfiguracionEstados.getReglasParaEstado(estadoActual);
        
        for (ReglaTransicion regla : reglas) {
            if (evaluarReglaTransicion(regla)) {
                ejecutarTransicion(regla.getEstadoDestino());
                break; // Solo una transición por frame
            }
        }
    }
    
    /**
     * Evalúa si una regla de transición se cumple
     */
    private boolean evaluarReglaTransicion(ReglaTransicion regla) {
        return regla.esAplicable(
            carlos.getAutoestima(),
            carlos.getEbriedad(),
            carlos.getScore(),
            tiempoTotalJuego,
            tiempoEnEstadoActual,
            recuerdosEvitados,
            recuerdosTomados,
            powerupsUsados,
            getPowerupsActivos(),
            tragosConsumidos
        );
    }
    
    /**
     * Ejecuta una transición entre estados
     */
    private void ejecutarTransicion(EstadoDuelo nuevoEstado) {
        System.out.println("TRANSICIÓN: " + estadoActual + " → " + nuevoEstado);
        
        // Lógica especial para Aceptación
        if (nuevoEstado == EstadoDuelo.ACEPTACION) {
            activarAceptacion();
        }
        
        EstadoDuelo estadoAnterior = estadoActual;
        estadoActual = nuevoEstado;
        tiempoEnEstadoActual = 0f;
        
        // Aplicar efectos inmediatos del nuevo estado
        aplicarEfectosInmediatos(estadoAnterior, nuevoEstado);
    }
    
    /**
     * Lógica especial para activar Aceptación
     */
    private void activarAceptacion() {
        aceptacionActiva = true;
        tiempoEnAceptacion = 0f;
        // Reiniciar autoestima al entrar en Aceptación
        carlos.setAutoestima(ConfiguracionEstados.AUTOESTIMA_INICIAL_ACEPTACION);
        System.out.println("🎯 ACEPTACIÓN ACTIVADA - Tienes " + 
                          ConfiguracionEstados.TIEMPO_ACEPTACION_VICTORIA + " segundos para ganar!");
    }
    
    /**
     * Actualización específica del estado Aceptación
     */
    private void actualizarAceptacion(float delta) {
        tiempoEnAceptacion += delta;
        
        // Verificar victoria
        if (tiempoEnAceptacion >= ConfiguracionEstados.TIEMPO_ACEPTACION_VICTORIA) {
            victoriaAceptacion();
        }
        
        // Generar recuerdos dorados periódicamente
        if (tiempoEnAceptacion % ConfiguracionEstados.INTERVALO_RECUERDOS_DORADOS < delta) {
            generarRecuerdoDorado();
        }
    }
    
    /**
     * El jugador ganó al mantenerse en Aceptación
     */
    private void victoriaAceptacion() {
        System.out.println("🏆 VICTORIA - Carlos superó su duelo!");
        // Aquí iría la lógica para terminar el juego con victoria
        // Por ahora solo imprimimos el mensaje
        aceptacionActiva = false;
    }
    
    /**
     * Aplica los efectos del estado actual al jugador
     */
    private void aplicarEfectosEstadoActual() {
        ConfigEstado config = ConfiguracionEstados.getConfigEstado(estadoActual);
        
        if (config != null) {
            // Aplicar multiplicadores (esto se integraría con los sistemas existentes)
            aplicarMultiplicadores(config);
            
            // Aplicar mecánica única
            aplicarMecanicaUnica(config.getMecanicaUnica());
        }
    }
    
    /**
     * Aplica efectos inmediatos al cambiar de estado
     */
    private void aplicarEfectosInmediatos(EstadoDuelo anterior, EstadoDuelo nuevo) {
        // Efectos especiales al entrar/salir de estados
        switch (nuevo) {
            case IRA:
                // Efecto visual/sonoro de ira
                break;
            case DEPRESION:
                // Activar efecto de depresión
                break;
            case ACEPTACION:
                // Efecto de claridad
                break;
        }
    }
    
    /**
     * Aplica los multiplicadores del estado al jugador
     */
    private void aplicarMultiplicadores(ConfigEstado config) {
        // Esta lógica se integraría con los sistemas existentes de Carlos
        // Por ahora es un esqueleto para mostrar la estructura
    }
    
    /**
     * Ejecuta la mecánica única de cada estado
     */
    private void aplicarMecanicaUnica(String mecanica) {
        // Implementar las mecánicas únicas de cada estado
        switch (mecanica) {
            case "RESISTENCIA_INICIAL":
                // Lógica de resistencia inicial
                break;
            case "ESTALLIDO_DESTRUCTIVO":
                // Lógica de estallido destructivo
                break;
            case "PACTOS_DESESPERADOS":
                // Lógica de pactos desesperados
                break;
            case "LETARGO_PROTECTOR":
                // Lógica de letargo protector
                break;
            case "RESISTENCIA_FINAL":
                // Lógica de resistencia final
                break;
        }
    }
    
    // ==================== MÉTODOS PARA REGISTRAR EVENTOS ====================
    
    public void registrarRecuerdoEvitado() { recuerdosEvitados++; }
    public void registrarRecuestoTomado() { recuerdosTomados++; }
    public void registrarPowerupUsado() { powerupsUsados++; }
    public void registrarTragoConsumido() { tragosConsumidos++; }
    
    // ==================== GETTERS PÚBLICOS ====================
    
    public EstadoDuelo getEstadoActual() { return estadoActual; }
    public float getTiempoEnEstadoActual() { return tiempoEnEstadoActual; }
    public float getTiempoEnAceptacion() { return tiempoEnAceptacion; }
    public boolean isAceptacionActiva() { return aceptacionActiva; }
    public int getRecuerdosEvitados() { return recuerdosEvitados; }
    public int getRecuerdosTomados() { return recuerdosTomados; }
    
    // ==================== MÉTODOS PRIVADOS ====================
    
    private int getPowerupsActivos() {
        // Contar power-ups activos en Carlos
        int count = 0;
        if (carlos.isCorazaActiva()) count++;
        if (carlos.isAutotuneActivo()) count++;
        if (carlos.isAmnesiaActiva()) count++;
        return count;
    }
    
    private void generarRecuerdoDorado() {
        // Lógica para generar recuerdos dorados de tentación
        System.out.println("💛 Aparece Recuerdo Dorado - ¿Recaerás?");
    }
    
    /**
     * Para debugging - muestra el estado completo
     */
    public String getInfoEstado() {
        return String.format("Estado: %s | Tiempo: %.1fs | Autoestima: %d | Ebriedad: %d",
                           estadoActual, tiempoEnEstadoActual, 
                           carlos.getAutoestima(), carlos.getEbriedad());
    }
}
