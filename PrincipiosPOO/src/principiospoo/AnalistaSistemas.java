/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principiospoo;

/**
 * * @author Prof. Jose Luis Cuya
 */
public class AnalistaSistemas extends Empleado{
    private double sueldoMensual;

    public AnalistaSistemas(String nombre, int edad, double sm) {
        super(nombre, edad);
        this.sueldoMensual=sm;
    }
    @Override
    public double calcularSueldo() {
        return sueldoMensual;
    }
    public double getSueldoMensual() { return sueldoMensual;}
    public void setSueldoMensual(double sueldoMensual) {
        this.sueldoMensual = sueldoMensual;
    }
}
