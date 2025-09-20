/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.estudiante;

/**
 *
 * @author TEC-YOU
 */
public class Estudiante {
    private String nombre;
    private double nota1;
    private double nota2;
    private double nota3;

    public Estudiante(String nombre, double nota1, double nota2, double nota3) {
        this.nombre = nombre;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
    }

    // Getters para acceder a los atributos
    public String getNombre() {
        return nombre;
    }

    public double getNota1() {
        return nota1;
    }

    public double getNota2() {
        return nota2;
    }

    public double getNota3() {
        return nota3;
    }

    /**
     * Calcula y devuelve el promedio de las tres notas.
     * @return El promedio de notas del estudiante.
     */
    public double calcularPromedio() {
        return (nota1 + nota2 + nota3) / 3.0;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "nombre='" + nombre + '\'' +
                ", nota1=" + nota1 +
                ", nota2=" + nota2 +
                ", nota3=" + nota3 +
                '}';
    }
}
