package org.informatorio.domain;

import org.informatorio.service.cuenta.CuentaService;

public class CuentaAhorro extends Cuenta {
    private double TNA = 10;

    public double getTNA() {
        return TNA;
    }

    public void setTNA(double TNA) {
        this.TNA = TNA;
    }

    public void calcularTNA() {
        new CuentaService().calcularTNA();
    }
}
