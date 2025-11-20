package io.github.some_example_name;

import java.util.HashMap;
import java.util.Map;

/**
 * REGISTRY PATTERN - Centraliza el acceso a todas las fábricas de estados
 * SINGLETON para garantizar una única instancia
 * ENFOQUE SIMPLIFICADO: Registry minimalista y eficiente
 */
public class RegistroFabricasEstados {
    
    private static RegistroFabricasEstados instance;
    private final Map<EstadoDuelo, FabricaDeTragedias> fabricas;
    
    private RegistroFabricasEstados() {
        fabricas = new HashMap<>();
        inicializarFabricas();
    }
    
    public static RegistroFabricasEstados getInstance() {
        if (instance == null) {
            instance = new RegistroFabricasEstados();
        }
        return instance;
    }
    
    private void inicializarFabricas() {
        // Registrar todas las fábricas concretas
        fabricas.put(EstadoDuelo.NEGACION, new FabricaNegacion());
        fabricas.put(EstadoDuelo.IRA, new FabricaIra());
        fabricas.put(EstadoDuelo.NEGOCIACION, new FabricaNegociacion());
        fabricas.put(EstadoDuelo.DEPRESION, new FabricaDepresion());
        fabricas.put(EstadoDuelo.ACEPTACION, new FabricaAceptacion());
        
        System.out.println("🏭 Registro de Fábricas inicializado con " + fabricas.size() + " fábricas");
    }
    
    /**
     * Obtiene la fábrica correspondiente al estado emocional
     * @param estado Estado del duelo
     * @return Fábrica concreta para ese estado
     */
    public FabricaDeTragedias getFabrica(EstadoDuelo estado) {
        FabricaDeTragedias fabrica = fabricas.get(estado);
        if (fabrica == null) {
            // Fallback a Negación si no se encuentra la fábrica
            System.out.println("⚠️  Fábrica no encontrada para estado: " + estado + ", usando Negación");
            return fabricas.get(EstadoDuelo.NEGACION);
        }
        return fabrica;
    }
    
    /**
     * @return Fábrica por defecto (Negación - estado inicial)
     */
    public FabricaDeTragedias getFabricaPorDefecto() {
        return fabricas.get(EstadoDuelo.NEGACION);
    }
    
    /**
     * @return Número de fábricas registradas (para debugging)
     */
    public int getTotalFabricas() {
        return fabricas.size();
    }
}
