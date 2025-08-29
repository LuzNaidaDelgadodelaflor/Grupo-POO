/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package principiospoo;

import javax.swing.JOptionPane;

/**
 * * @author Estudiante
 */
public class PrincipiosPOO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Empleado prog = new Programador(50, 75,"",57);
        Empleado as = new AnalistaSistemas("Rosa Davila", 32, 15800);
        String nom = JOptionPane.showInputDialog("Ingrese el nombre del empleado");
        prog.setNombre(nom);
        //
        mostrarSueldo(prog);
        mostrarSueldo(as);
    }
    public static void mostrarSueldo(Empleado emp){
        System.out.println("Empleado: "+ emp.getNombre()+"  "+ emp.getEdad()+" años");
        System.out.println("Sueldo: S/."+emp.calcularSueldo());
    }
    
}
