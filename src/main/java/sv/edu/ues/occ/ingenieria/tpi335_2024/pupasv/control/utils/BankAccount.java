/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ues.occ.ingenieria.tpi335_2024.pupasv.control.utils;

/**
 *
 * @author morales
 */
public class BankAccount {
    private double balance;
    
    //Constructor que inicializa la cuenta con un saldo inicial
    public BankAccount(double initialBalance){
        this.balance = initialBalance;
    }
    
    //Método para despositar dinero en la cuenta
    public void deposit(double amount){
        if(amount > 0 ){
            balance =+ amount;
        }else{
            throw new IllegalStateException("El monto a depositar debe se positivo");
        }
    }
    
    //Método  para retirar dinero de la cuenta
    public void withdraw(double amount) throws InsufficientFundsException {//Implementar la excepción personalisada
         if(amount > balance){
             //Lanzar la expecion personalizada
             throw new InsufficientFundsException("Fondos Insuficientes");
         }
       
         if(amount > 0){
             balance -= amount;
         }else{
             throw new IllegalStateException("El monto a retirar debe ser positivo");
         }
    }
    
    //Método para obtener el saldo actual de la cuenta
    public double getBalance(){
        return balance;
    }
}
