package io.github.some_example_name;

/**
 * Encapsula TODOS los efectos y parámetros de un estado específico
 * FÁCIL DE EDITAR: Modificar valores aquí afecta todo el juego automáticamente
 */
public class ConfigEstado {
    // Multiplicadores de atributos
    private final float velocidadMultiplicador;
    private final float scoreMultiplicador;
    private final float danioRecuerdosMultiplicador;
    private final float efectoTragoMultiplicador;
    private final float ebriedadReduccionMultiplicador;
    
    // Mecánica única identificador
    private final String mecanicaUnica;
    
    // Efectos visuales/sonoros (extensible)
    private final String efectoVisual;
    private final String efectoSonoro;
    
    public ConfigEstado(float velocidadMultiplicador, float scoreMultiplicador,
                       float danioRecuerdosMultiplicador, float efectoTragoMultiplicador,
                       float ebriedadReduccionMultiplicador, String mecanicaUnica) {
        this.velocidadMultiplicador = velocidadMultiplicador;
        this.scoreMultiplicador = scoreMultiplicador;
        this.danioRecuerdosMultiplicador = danioRecuerdosMultiplicador;
        this.efectoTragoMultiplicador = efectoTragoMultiplicador;
        this.ebriedadReduccionMultiplicador = ebriedadReduccionMultiplicador;
        this.mecanicaUnica = mecanicaUnica;
        this.efectoVisual = "";
        this.efectoSonoro = "";
    }
    
    // GETTERS - Encapsulamiento total
    public float getVelocidadMultiplicador() { return velocidadMultiplicador; }
    public float getScoreMultiplicador() { return scoreMultiplicador; }
    public float getDanioRecuerdosMultiplicador() { return danioRecuerdosMultiplicador; }
    public float getEfectoTragoMultiplicador() { return efectoTragoMultiplicador; }
    public float getEbriedadReduccionMultiplicador() { return ebriedadReduccionMultiplicador; }
    public String getMecanicaUnica() { return mecanicaUnica; }
    public String getEfectoVisual() { return efectoVisual; }
    public String getEfectoSonoro() { return efectoSonoro; }
}