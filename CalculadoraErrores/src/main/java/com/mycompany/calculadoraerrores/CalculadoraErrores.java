/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.calculadoraerrores;

/**
 *
 * @author TEC-YOU
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class CalculadoraErrores {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            try {
                System.out.println("\n--- Calculadora con Manejo de Errores ---");
                System.out.println("1. Suma");
                System.out.println("2. Resta");
                System.out.println("3. Multiplicación");
                System.out.println("4. División");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = sc.nextInt();

                if (opcion == 5) {
                    continuar = false;
                    System.out.println("Programa finalizado.");
                    break;
                }

                System.out.print("Ingrese el primer número: ");
                double a = sc.nextDouble();
                System.out.print("Ingrese el segundo número: ");
                double b = sc.nextDouble();

                switch (opcion) {
                    case 1 -> System.out.println("Resultado: " + (a + b));
                    case 2 -> System.out.println("Resultado: " + (a - b));
                    case 3 -> System.out.println("Resultado: " + (a * b));
                    case 4 -> System.out.println("Resultado: " + (a / b));
                    default -> System.out.println("Opción inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número válido.");
                sc.nextLine(); // limpiar buffer
            } catch (ArithmeticException e) {
                System.out.println("Error: No se puede dividir entre cero.");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            } finally {
                System.out.println("Ejecución completada (bloque finally).");
            }
        }
        sc.close();
    }
}

