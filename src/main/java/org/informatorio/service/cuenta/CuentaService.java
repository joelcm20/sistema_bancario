package org.informatorio.service.cuenta;

import org.informatorio.db.DB;
import org.informatorio.domain.Cuenta;
import org.informatorio.domain.CuentaCorriente;
import org.informatorio.entrada.InputConsoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            this.depositar(cuentaADepositar, monto);
            System.out.println("Monto depositado.\n");
        }
    }

    @Override
    public void depositar(Cuenta cuenta, double monto) {
        cuenta.setSaldo(cuenta.getSaldo() + monto);
    }

    @Override
    public void retirarSaldo() {
        Cuenta cuentaARetirarSaldo = null;
        // obtener alias de la cuenta a retirar saldo
        System.out.print("Alias de la cuenta: ");
        String alias = InputConsoleService.getScanner().nextLine().trim();

        // validar si la cuenta existe
        Boolean existeCuenta = Boolean.FALSE;
        for (Cuenta c : DB.getBanco().getClienteConectado().getCuentas()) {
            if (alias.equals(c.getAlias())) {
                existeCuenta = Boolean.TRUE;
                cuentaARetirarSaldo = c;
            }
        }

        // mostrar por pantalla en caso de que la cuena no exista
        if (!existeCuenta) {
            System.out.println("Error: la cuenta no existe o no fue encontrada.\n");
        } else {
            // realizar la tarea si existe la cuenta
            System.out.print("Monto a retirar: ");
            double monto = Double.parseDouble(InputConsoleService.getScanner().nextLine());

            // tener en cuenta el sobregiro si la cueta es corriente
            if (cuentaARetirarSaldo instanceof CuentaCorriente) {
                // mostrar por pantalla si se llego al limite de sobregiro
                if (cuentaARetirarSaldo.getSaldo() - monto < ((CuentaCorriente) cuentaARetirarSaldo).getSobregiro()) {
                    System.out.println("Error: sobregiro excedido.\n");
                } else {
                    // si todo esta bien, remirar el monto
                    this.retirar(cuentaARetirarSaldo, monto);
                    System.out.println("Monto retirado.\n");
                }
            } else {
                // si la cuenta es de ahorro
                if (cuentaARetirarSaldo.getSaldo() - monto >= 0) {
                    retirar(cuentaARetirarSaldo, monto);
                    System.out.println("Monto retirado.\n");
                } else {
                    // mostrar por pantalla si el saldo es insuficiente
                    System.out.println("Saldo insuficiente.\n");
                }

            }
        }
    }

    @Override
    public void retirar(Cuenta cuenta, double monto) {
        cuenta.setSaldo(cuenta.getSaldo() - monto);
    }

    @Override
    public void consultarSaldo() {
        // elegir como mostrar los saldos de las cuentas
        System.out.print("1. Todas las cuentas.\n2. Elegir cuenta.\n");
        System.out.print("Opcion: ");
        int opcion = Integer.parseInt(InputConsoleService.getScanner().nextLine().trim());

        // consultando saldo
        if (opcion == 1) {
            // mostrar por pantalla si todavia no hay cuentas.
            if (DB.getBanco().getClienteConectado().getCuentas().isEmpty()) {
                System.out.println("No hay cuentas todabia.\n");
                return;
            }

            // mostrar todas las cuentas
            System.out.println("- INFORMACION DE LAS CUENTAS:");
            for (Cuenta c : DB.getBanco().getClienteConectado().getCuentas()) {
                System.out.printf("Nº %s, Alias: %s, Saldo: %s.\n", c.getNumeroCuenta(), c.getAlias(), c.getSaldo());
            }
            System.out.print("\n");
        } else if (opcion == 2) {
            // mostrar cuenta elegida
            Cuenta cuentaAConsultar = null;

            // obtener alias de la cuenta
            System.out.print("Alias de la cuenta: ");
            String alias = InputConsoleService.getScanner().nextLine().trim();

            // buscar si la cuenta existe
            for (Cuenta c : DB.getBanco().getClienteConectado().getCuentas()) {
                if (alias.equals(c.getAlias())) {
                    cuentaAConsultar = c;
                    break;
                }
            }

            // mostrar por pantalla si no existe
            if (Objects.isNull(cuentaAConsultar)) {
                System.out.println("Error: cuenta no existe o no fue encontrada.\n");
            } else {
                // mostrar consulta de la cuenta
                System.out.printf("Nº %s, Alias: %s, Saldo: %s.\n", cuentaAConsultar.getNumeroCuenta(), cuentaAConsultar.getAlias(), cuentaAConsultar.getSaldo());
                System.out.print("\n");
            }
        } else {
            System.out.println("Error: opcion invalida.\n");
        }
    }

    public void eliminarCuenta() {
        Cuenta cuentaAEliminar = null;
        // obtener alias de la cuenta a eliminar
        System.out.print("Alias de la cienta a eliminar: ");
        String alias = InputConsoleService.getScanner().nextLine().trim();

        // verificar si la cuenta existe
        Boolean existeCuenta = Boolean.FALSE;
        for (Cuenta c : DB.getBanco().getClienteConectado().getCuentas()) {
            if (alias.equals(c.getAlias())) {
                existeCuenta = Boolean.TRUE;
                cuentaAEliminar = c;
            }
        }

        // mostrar por pantalla si la cuenta a eliminar no existe
        if (Objects.isNull(cuentaAEliminar)) {
            System.out.println("Error: la cuenta no existe o no fue encontrada.\n");
        } else {
            // eliminar la cuenta si existe
            List<Cuenta> nuevaListaCuentas = new ArrayList<>();
            for (Cuenta c : DB.getBanco().getClienteConectado().getCuentas()) {
                if (!c.getAlias().equals(cuentaAEliminar.getAlias())) {
                    nuevaListaCuentas.add(c);
                }
            }
            // guardar la nueva lista de cuentas al cliente conectado
            DB.getBanco().getClienteConectado().setCuentas(nuevaListaCuentas);
            System.out.println("Cuenta eliminada.\n");
        }
    }
}
