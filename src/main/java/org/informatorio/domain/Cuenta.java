package org.informatorio.domain;

import java.util.UUID;

public abstract class Cuenta {
    private UUID id = UUID.randomUUID();
    private int numeroCuenta;
    private Cliente titular;
    private double saldo;

    public void depositarSaldo(double monto) {
        this.saldo = this.saldo + monto;
    }

    public void retirarSaldo(double monto) {
        this.saldo = this.saldo - monto;
    }

    public void consultarSaldo() {
        System.out.printf("Saldo: %s.\n", this.saldo);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


}