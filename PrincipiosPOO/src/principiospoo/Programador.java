/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principiospoo;

/**
 *
 * @author Estudiante
 */
public class Programador extends Empleado{ //(subclase - Herencia)
    private int horasTrabajadas;
    private double tarifaHora;

    public Programador(int horasTrabajadas, double tarifaHora, String nombre, int edad) {
        super(nombre, edad); //Herencia
        this.horasTrabajadas = horasTrabajadas;
        this.tarifaHora = tarifaHora;
    }
    //Polimorfismo
    @Override
    public double calcularSueldo() {
        return horasTrabajadas * tarifaHora;
    }
    public int getHorasTrabajadas() {  return horasTrabajadas;}
    public void setHorasTrabajadas(int horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }
    public double getTarifaHora() {return tarifaHora; }
    public void setTarifaHora(double tarifaHora) {
        this.tarifaHora = tarifaHora;
    }
    
}
