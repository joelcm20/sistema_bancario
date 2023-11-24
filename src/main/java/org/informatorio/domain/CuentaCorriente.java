package org.informatorio.domain;

public class CuentaCorriente extends Cuenta {
    private double sobregiro = -1000;

    public double getSobregiro() {
        return sobregiro;
    }

    public void setSobregiro(double sobregiro) {
        this.sobregiro = sobregiro;
    }
}
