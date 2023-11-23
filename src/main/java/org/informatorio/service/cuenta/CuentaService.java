package org.informatorio.service.cuenta;

import org.informatorio.db.DB;
import org.informatorio.domain.Cliente;
import org.informatorio.domain.Cuenta;
import org.informatorio.entrada.InputConsoleService;

public class CuentaService implements ICuentaService {
    @Override
    public void depositarSaldo() {
        Cuenta cuentaADepositar = null;
        // obtener alias de la cuenta a depositar del cliente conectado
        System.out.print("Alias de la cuenta a depositar: ");
        String aliasADepositar = InputConsoleService.getScanner().nextLine().trim();

        // verificar que esa cuenta exixta para el cliente conectado
        Boolean existeCuenta = Boolean.FALSE;
        for (Cuenta c : DB.getBanco().getClienteConectado().getCuentas()) {
            if (c.getAlias().equals(aliasADepositar)) {
                existeCuenta = Boolean.TRUE;
                cuentaADepositar = c;
            }
        }

        // mostrar por pantalla en caso de que no exista la cuenta
        if (!existeCuenta) {
            System.out.println("Error: la cuenta no existe o no fue encontrada.\n");
        } else {
            // realizar la tarea en caso de que si exista la cuenta
            System.out.print("Monto a depositar: ");
            double monto = Double.parseDouble(InputConsoleService.getScanner().nextLine());
            cuentaADepositar.depositarSaldo(monto);
            System.out.println("Monto depositado.\n");
        }
    }

    @Override
    public void retirarSaldo() {

    }

    @Override
    public void consultarSaldo() {

    }
}
