package io.github.some_example_name;

/**
 * Enum que representa los 5 estados del duelo según el modelo de Kübler-Ross
 * Fácil de extender en el futuro
 */
public enum EstadoDuelo {
    NEGACION("Negación", "Mecanismo de defensa inicial"),
    IRA("Ira", "Externalización del dolor"), 
    NEGOCIACION("Negociación", "Búsqueda de control ilusorio"),
    DEPRESION("Depresión", "Agotamiento emocional total"),
    ACEPTACION("Aceptación", "Prueba final - Renacimiento doloroso");
    
    private final String nombre;
    private final String descripcion;
    
    EstadoDuelo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    
    @Override
    public String toString() { return nombre; }
}
