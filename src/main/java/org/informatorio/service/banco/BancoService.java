package org.informatorio.service.banco;

import org.informatorio.db.DB;
import org.informatorio.domain.*;
import org.informatorio.entrada.InputConsoleService;
import org.informatorio.service.cuenta.ICuentaService;
import org.informatorio.utils.GenerarNumeroCuenta;

import java.util.List;

public class BancoService implements IBancoService {
    @Override
    public void registrarCliente(Cliente cliente) {
        Banco banco = DB.getBanco();
        List<Cliente> clientes = banco.getClientes();
        clientes.add(cliente);
        banco.setClientes(clientes);
    }

    @Override
    public void abrirCuenta() {
        Cuenta cuenta = null;
        int tipoDeCuenta = 0;
        Boolean ok;

        // crear tipo de cuenta
        ok = Boolean.FALSE;
        do {
            try {
                System.out.println("Seleccionar tipo de cuenta:\n1. Cuenta ahorro.\n2. Cuenta corriente.");
                System.out.print("Seleccionar tipo: ");
                tipoDeCuenta = Integer.parseInt(InputConsoleService.getScanner().nextLine());
                ok = Boolean.FALSE;
            } catch (NumberFormatException e) {
                ok = Boolean.TRUE;
                System.out.println("Error: formato invalido.\n");
            }
        } while (ok);

        ok = Boolean.FALSE;
        do {
            if (tipoDeCuenta == 1) {
                ok = Boolean.FALSE;
                cuenta = new CuentaAhorro();
            } else if (tipoDeCuenta == 2) {
                ok = Boolean.FALSE;
                cuenta = new CuentaCorriente();
            } else {
                ok = Boolean.TRUE;
                System.out.println("Error: tipo de cuenta invalido.\n");
            }
        } while (ok);

        // crear numero de cuenta
        long numeroDeCuenta = GenerarNumeroCuenta.numeroCuentaBancaria();
        cuenta.setNumeroCuenta(numeroDeCuenta);

        // agregar el titular de la cuenta (el cliente conectado)
        cuenta.setTitular(DB.getBanco().getClienteConectado());

        // crear alias
        ok = Boolean.FALSE;
        do {
            System.out.print("Escribe un alias: ");
            String alias = InputConsoleService.getScanner().nextLine().trim();
            // validar alias
            if (!ICuentaService.validarAlias(alias)) {
                ok = Boolean.TRUE;
                System.out.println("Error: alias invalido.\n");
            } else {
                ok = Boolean.FALSE;
                cuenta.setAlias(alias);
                DB.getBanco().getClienteConectado().setCuenta(cuenta);
                System.out.println("Cuenta creada correctamente.\n");
            }
        } while (ok);
    }
}
