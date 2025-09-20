import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.estudiante;

/**
 *
 * @author TEC-YOU
 */
public class GestionNotas {
     private List<Estudiante> estudiantes;

    public GestionNotas() {
        this.estudiantes = new ArrayList<>();
    }

    /**
     * Agrega un nuevo estudiante a la lista. Los datos se obtienen a través de cuadros de diálogo.
     */
    public void agregarEstudiante() {
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del estudiante:");
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operación cancelada o nombre inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double nota1 = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la nota 1:"));
            double nota2 = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la nota 2:"));
            double nota3 = Double.parseDouble(JOptionPane.showInputDialog("Ingrese la nota 3:"));

            Estudiante nuevoEstudiante = new Estudiante(nombre, nota1, nota2, nota3);
            estudiantes.add(nuevoEstudiante);
            JOptionPane.showMessageDialog(null, "✅ Estudiante " + nombre + " agregado exitosamente.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "❌ Entrada de notas inválida. Por favor, ingrese un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Muestra la lista completa de estudiantes con sus notas y promedios en un cuadro de diálogo.
     */
    public void mostrarEstudiantes() {
        if (estudiantes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "⚠️ La lista de estudiantes está vacía.");
            return;
        }

        StringBuilder sb = new StringBuilder("--- Lista de Estudiantes ---\n\n");
        for (Estudiante estudiante : estudiantes) {
            sb.append("• Nombre: ").append(estudiante.getNombre())
              .append(", Promedio: ").append(String.format("%.2f", estudiante.calcularPromedio()))
              .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Lista de Estudiantes", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Busca un estudiante por nombre a través de un cuadro de diálogo y muestra sus detalles.
     */
    public void buscarEstudiante() {
        String nombreABuscar = JOptionPane.showInputDialog("Ingrese el nombre del estudiante a buscar:");
        if (nombreABuscar == null || nombreABuscar.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Operación cancelada o nombre inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Estudiante estudianteEncontrado = null;
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNombre().equalsIgnoreCase(nombreABuscar)) {
                estudianteEncontrado = estudiante;
                break;
            }
        }

        if (estudianteEncontrado != null) {
            String mensaje = "🔍 Estudiante encontrado:\n\n" +
                             "• Nombre: " + estudianteEncontrado.getNombre() + "\n" +
                             "• Notas: [" + estudianteEncontrado.getNota1() + ", " +
                                            estudianteEncontrado.getNota2() + ", " +
                                            estudianteEncontrado.getNota3() + "]\n" +
                             "• Promedio: " + String.format("%.2f", estudianteEncontrado.calcularPromedio());
            JOptionPane.showMessageDialog(null, mensaje, "Búsqueda de Estudiante", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "❌ Estudiante con nombre '" + nombreABuscar + "' no encontrado.", "Resultado de Búsqueda", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Método principal para ejecutar el programa con un menú interactivo.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        GestionNotas gestion = new GestionNotas();
        String opcion;
        do {
            opcion = JOptionPane.showInputDialog(null,
                    "Seleccione una opción:\n" +
                            "1. Agregar estudiante\n" +
                            "2. Mostrar todos los estudiantes\n" +
                            "3. Buscar estudiante por nombre\n" +
                            "4. Salir",
                    "Gestión de Notas de Estudiantes",
                    JOptionPane.PLAIN_MESSAGE);

            if (opcion == null) { // El usuario hizo clic en "Cancelar"
                opcion = "4";
            }

            switch (opcion) {
                case "1":
                    gestion.agregarEstudiante();
                    break;
                case "2":
                    gestion.mostrarEstudiantes();
                    break;
                case "3":
                    gestion.buscarEstudiante();
                    break;
                case "4":
                    JOptionPane.showMessageDialog(null, "¡Hasta la próxima!", "Saliendo", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida. Intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } while (!opcion.equals("4"));
    }
}
