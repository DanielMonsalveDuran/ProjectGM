package io.github.some_example_name;

import java.util.*;

/**
 * CONFIGURACIÓN MAESTRA - Todas las reglas del sistema de estados aquí
 * FÁCIL DE EDITAR: Modificar valores en esta clase afecta todo el juego
 * ENCAPSULADO: Todo está centralizado y organizado
 */
public class ConfiguracionEstados {
    
    // ==================== UMBRALES BASE ====================
    public static final Map<EstadoDuelo, Integer> UMBRALES_AUTOESTIMA = Map.of(
        EstadoDuelo.NEGACION, 70,
        EstadoDuelo.IRA, 50,
        EstadoDuelo.NEGOCIACION, 35,
        EstadoDuelo.DEPRESION, 20,
        EstadoDuelo.ACEPTACION, 15
    );
    
    // ==================== CONFIGURACIÓN POR ESTADO ====================
    public static final Map<EstadoDuelo, ConfigEstado> CONFIG_ESTADOS = Map.of(
        // NEGACIÓN - Mecanismo de defensa inicial
        EstadoDuelo.NEGACION, new ConfigEstado(
            /* velocidadMultiplicador */ 1.0f,
            /* scoreMultiplicador */ 1.0f,
            /* danioRecuerdosMultiplicador */ 1.0f,
            /* efectoTragoMultiplicador */ 1.0f,
            /* ebriedadReduccionMultiplicador */ 1.0f,
            /* mecanicaUnica */ "RESISTENCIA_INICIAL"
        ),
        
        // IRA - Externalización del dolor
        EstadoDuelo.IRA, new ConfigEstado(
            /* velocidadMultiplicador */ 1.3f,
            /* scoreMultiplicador */ 1.5f,
            /* danioRecuerdosMultiplicador */ 1.3f,
            /* efectoTragoMultiplicador */ 0.9f,
            /* ebriedadReduccionMultiplicador */ 0.8f,
            /* mecanicaUnica */ "ESTALLIDO_DESTRUCTIVO"
        ),
        
        // NEGOCIACIÓN - Búsqueda de control ilusorio
        EstadoDuelo.NEGOCIACION, new ConfigEstado(
            /* velocidadMultiplicador */ 0.9f,
            /* scoreMultiplicador */ 1.2f,
            /* danioRecuerdosMultiplicador */ 1.25f,
            /* efectoTragoMultiplicador */ 0.65f,
            /* ebriedadReduccionMultiplicador */ 1.1f,
            /* mecanicaUnica */ "PACTOS_DESESPERADOS"
        ),
        
        // DEPRESIÓN - Agotamiento emocional total
        EstadoDuelo.DEPRESION, new ConfigEstado(
            /* velocidadMultiplicador */ 0.55f,
            /* scoreMultiplicador */ 1.8f,
            /* danioRecuerdosMultiplicador */ 0.65f,
            /* efectoTragoMultiplicador */ 1.6f,
            /* ebriedadReduccionMultiplicador */ 0.5f,
            /* mecanicaUnica */ "LETARGO_PROTECTOR"
        ),
        
        // ACEPTACIÓN - Prueba final
        EstadoDuelo.ACEPTACION, new ConfigEstado(
            /* velocidadMultiplicador */ 1.0f,
            /* scoreMultiplicador */ 2.5f,
            /* danioRecuerdosMultiplicador */ 1.4f,
            /* efectoTragoMultiplicador */ 0.0f, // Los tragos no dan autoestima
            /* ebriedadReduccionMultiplicador */ 3.0f,
            /* mecanicaUnica */ "RESISTENCIA_FINAL"
        )
    );
    
    // ==================== REGLAS DE TRANSICIÓN ====================
    public static final List<ReglaTransicion> REGLAS_TRANSICION = Arrays.asList(
        // NEGACIÓN → IRA
        new ReglaTransicion(
            EstadoDuelo.NEGACION, EstadoDuelo.IRA,
            Arrays.asList(
                new Condicion(Condicion.TipoParametro.AUTOESTIMA, Condicion.Operador.MAYOR_IGUAL, 70),
                new Condicion(Condicion.TipoParametro.EBRIEDAD, Condicion.Operador.MAYOR_IGUAL, 40),
                new Condicion(Condicion.TipoParametro.TIEMPO_JUEGO, Condicion.Operador.MAYOR_IGUAL, 30)
            )
        ),
        
        // IRA → NEGOCIACIÓN
        new ReglaTransicion(
            EstadoDuelo.IRA, EstadoDuelo.NEGOCIACION,
            Arrays.asList(
                new Condicion(Condicion.TipoParametro.AUTOESTIMA, Condicion.Operador.MAYOR_IGUAL, 50),
                new Condicion(Condicion.TipoParametro.TIEMPO_ESTADO_ACTUAL, Condicion.Operador.MAYOR_IGUAL, 20),
                new Condicion(Condicion.TipoParametro.RECUERDOS_EVITADOS, Condicion.Operador.MAYOR_IGUAL, 12),
                new Condicion(Condicion.TipoParametro.EBRIEDAD, Condicion.Operador.MAYOR_IGUAL, 60)
            )
        ),
        
        // NEGOCIACIÓN → DEPRESIÓN
        new ReglaTransicion(
            EstadoDuelo.NEGOCIACION, EstadoDuelo.DEPRESION,
            Arrays.asList(
                new Condicion(Condicion.TipoParametro.AUTOESTIMA, Condicion.Operador.MAYOR_IGUAL, 35),
                new Condicion(Condicion.TipoParametro.TIEMPO_ESTADO_ACTUAL, Condicion.Operador.MAYOR_IGUAL, 15),
                new Condicion(Condicion.TipoParametro.RECUERDOS_TOMADOS, Condicion.Operador.MAYOR_IGUAL, 5)
            )
        ),
        
        // DEPRESIÓN → ACEPTACIÓN
        new ReglaTransicion(
            EstadoDuelo.DEPRESION, EstadoDuelo.ACEPTACION,
            Arrays.asList(
                new Condicion(Condicion.TipoParametro.AUTOESTIMA, Condicion.Operador.MAYOR_IGUAL, 20),
                new Condicion(Condicion.TipoParametro.TIEMPO_ESTADO_ACTUAL, Condicion.Operador.MAYOR_IGUAL, 10),
                new Condicion(Condicion.TipoParametro.POWERUPS_ACTIVOS, Condicion.Operador.IGUAL, 0)
            )
        ),
        
        // ==================== REGRESOS ====================
        
        // IRA → NEGACIÓN (Regreso)
        new ReglaTransicion(
            EstadoDuelo.IRA, EstadoDuelo.NEGACION,
            Arrays.asList(
                new Condicion(Condicion.TipoParametro.AUTOESTIMA, Condicion.Operador.MENOR_IGUAL, 60),
                new Condicion(Condicion.TipoParametro.EBRIEDAD, Condicion.Operador.MENOR_IGUAL, 20),
                new Condicion(Condicion.TipoParametro.RECUERDOS_TOMADOS, Condicion.Operador.MENOR_IGUAL, 3)
            ),
            true
        ),
        
        // NEGOCIACIÓN → IRA (Regreso)
        new ReglaTransicion(
            EstadoDuelo.NEGOCIACION, EstadoDuelo.IRA,
            Arrays.asList(
                new Condicion(Condicion.TipoParametro.AUTOESTIMA, Condicion.Operador.MENOR_IGUAL, 55),
                new Condicion(Condicion.TipoParametro.POWERUPS_USADOS, Condicion.Operador.MAYOR_IGUAL, 2)
            ),
            true
        ),
        
        // DEPRESIÓN → NEGOCIACIÓN (Regreso)
        new ReglaTransicion(
            EstadoDuelo.DEPRESION, EstadoDuelo.NEGOCIACION,
            Arrays.asList(
                new Condicion(Condicion.TipoParametro.AUTOESTIMA, Condicion.Operador.MENOR_IGUAL, 40),
                new Condicion(Condicion.TipoParametro.POWERUPS_USADOS, Condicion.Operador.MAYOR_IGUAL, 1)
            ),
            true
        )
    );
    
    // ==================== CONFIGURACIÓN ACEPTACIÓN ====================
    public static final float TIEMPO_ACEPTACION_VICTORIA = 60.0f; // 60 segundos para ganar
    public static final int AUTOESTIMA_INICIAL_ACEPTACION = 100;
    public static final float INTERVALO_RECUERDOS_DORADOS = 15.0f; // Cada 15 segundos
    
    // ==================== MÉTODOS DE UTILIDAD ====================
    
    public static ConfigEstado getConfigEstado(EstadoDuelo estado) {
        return CONFIG_ESTADOS.get(estado);
    }
    
    public static List<ReglaTransicion> getReglasParaEstado(EstadoDuelo estadoOrigen) {
        List<ReglaTransicion> reglas = new ArrayList<>();
        for (ReglaTransicion regla : REGLAS_TRANSICION) {
            if (regla.getEstadoOrigen() == estadoOrigen) {
                reglas.add(regla);
            }
        }
        return reglas;
    }
    
    public static int getUmbralAutoestima(EstadoDuelo estado) {
        return UMBRALES_AUTOESTIMA.getOrDefault(estado, 100);
    }
}