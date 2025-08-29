/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principiospoo;

/**
 *
 * @author Estudiante
 */
public abstract class Empleado { //(abstraccion)
    private String nombre; //(Encapsulamiento / get y set)
    private int edad;

    //Metodo abstracto
    public abstract double calcularSueldo();
        
    
    public Empleado(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }
    //Encapsulamiento
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    
    
}
