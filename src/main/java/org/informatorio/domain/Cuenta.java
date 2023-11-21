package org.informatorio.domain;

import java.util.UUID;

public abstract class Cuenta {
    private UUID id = UUID.randomUUID();
    private long numeroCuenta;
    private Cliente titular;
    private String alias;
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

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


}