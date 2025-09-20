/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.conversiones;

import javax.swing.JOptionPane;

/**
 *
 * @author TEC-YOU
 */
public class Conversiones {
    
    public static void main(String[] args) {
        // Crear una instancia de la clase Conversor
        Conversor conversor = new Conversor();

        // Prueba 1: Celsius a Fahrenheit
        double celsius = 25;
        double fahrenheit = conversor.convertir(celsius);
        JOptionPane.showMessageDialog(null,celsius+ " Grado Celsius son "+ fahrenheit + " grados Fahrenheit");

        // Prueba 2: Kilómetros a millas
        double kilometros = 100;
        double millas = conversor.convertir(kilometros, true);
        JOptionPane.showMessageDialog(null, kilometros + " kilometros son " + millas + " millas. ");

        // Prueba 3: Segundos a minutos y segundos
        int totalSegundos = 150;
        String tiempoConvertido = conversor.convertir(totalSegundos);
        JOptionPane.showMessageDialog(null, totalSegundos + " segundos son " + tiempoConvertido);
    } 
}
