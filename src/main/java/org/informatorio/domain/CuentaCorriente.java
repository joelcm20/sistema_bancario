package org.informatorio.domain;

public class CuentaCorriente extends Cuenta {
    private double sobregiro;

    public double getSobregiro() {
        return sobregiro;
    }

    public void setSobregiro(double sobregiro) {
        this.sobregiro = sobregiro;
    }
}
