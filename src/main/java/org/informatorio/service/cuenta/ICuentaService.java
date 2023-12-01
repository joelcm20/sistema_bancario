package org.informatorio.service.cuenta;

import org.informatorio.db.DB;
import org.informatorio.domain.Cliente;
import org.informatorio.domain.Cuenta;

public interface ICuentaService {
    void depositarSaldo();

    void depositar(Cuenta cuenta, double monto);

    void retirarSaldo();

    void retirar(Cuenta cuenta, double monto);

    void consultarSaldo();

    static Boolean validarAlias(String alias) {
        Boolean esValido = Boolean.TRUE;
        for (Cliente cliente : DB.getBanco().getClientes()) {
            for (Cuenta cuenta : cliente.getCuentas()) {
                if (alias.equals(cuenta.getAlias())) {
                    esValido = Boolean.FALSE;
                    return esValido;
                }
            }
        }
        return esValido;
    }

    void eliminarCuenta();
}
