/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.conversiones;

/**
 *
 * @author TEC-YOU
 */
public class Conversor {
    /**
     * Convierte grados Celsius a Fahrenheit.
     * @param celsius La temperatura en grados Celsius.
     * @return La temperatura en grados Fahrenheit.
     */
    public double convertir(double celsius) {
        return (celsius * 9/5) + 32;
    }
    
    /**
     * Convierte kilómetros a millas.
     * @param kilometros La distancia en kilómetros.
     * @param esDistancia Un parámetro para diferenciar este método.
     * @return La distancia en millas.
     */
    public double convertir(double kilometros, boolean esDistancia) {
        return kilometros * 0.621371;
    }

    /**
     * Convierte segundos a minutos y segundos restantes.
     * @param segundos El tiempo total en segundos.
     * @return Un string formateado como "M minutos y S segundos".
     */
    public String convertir(int segundos) {
        int minutos = segundos / 60;
        int segundosRestantes = segundos % 60;
        return String.format( minutos + " minutos y " + segundosRestantes +" segundos");
    }
}
