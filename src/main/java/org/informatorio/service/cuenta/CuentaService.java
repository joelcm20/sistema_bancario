package org.informatorio.service.cuenta;

import org.informatorio.db.DB;
import org.informatorio.domain.Cuenta;
import org.informatorio.domain.CuentaAhorro;
import org.informatorio.domain.CuentaCorriente;
import org.informatorio.entrada.InputConsoleService;
import org.informatorio.utils.GenerarNumeroCuenta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CuentaService implements ICuentaService {
    @Override
    public void depositarSaldo() {
        // obtener alias de la cuenta a depositar del cliente conectado
        System.out.print("Alias de la cuenta a depositar: ");
        String aliasADepositar = InputConsoleService.getScanner().nextLine().trim();

        // obtener cuenta a depositar del cliente conectado
        Optional<Cuenta> cuentaADepositar = this.seleccionarCuenta(aliasADepositar);

        // mostrar por pantalla en caso de que no exista la cuenta
        if (cuentaADepositar.isEmpty()) {
            System.out.println("Error: la cuenta no existe o no fue encontrada.\n");
        } else {
            // realizar la tarea en caso de que si exista la cuenta
            System.out.print("Monto a depositar: ");
            double monto = Double.parseDouble(InputConsoleService.getScanner().nextLine());
            this.depositar(cuentaADepositar.get(), monto);
            System.out.println("Monto depositado.\n");
        }
    }

    @Override
    public void depositar(Cuenta cuenta, double monto) {
        cuenta.setSaldo(cuenta.getSaldo() + monto);
    }

    private Optional<Cuenta> seleccionarCuenta(String alias) {
        Optional<Cuenta> cuenta;
        for (Cuenta c : DB.getBanco().getClienteConectado().getCuentas()) {
            if (c.getAlias().equals(alias)) {
                cuenta = Optional.of(c);
                return cuenta;
            }
        }
        return Optional.empty();
    }

    @Override
    public void retirarSaldo() {
        // obtener alias de la cuenta a retirar saldo del cliente conectado
        System.out.print("Alias de la cuenta: ");
        String alias = InputConsoleService.getScanner().nextLine().trim();

        // obtener cuenta a retirar saldo del cliente conectado
        Optional<Cuenta> cuentaARetirarSaldo = this.seleccionarCuenta(alias);

        // mostrar por pantalla en caso de que la cuena no exista
        if (cuentaARetirarSaldo.isEmpty()) {
            System.out.println("Error: la cuenta no existe o no fue encontrada.\n");
        } else {
            // realizar la tarea si existe la cuenta
            System.out.print("Monto a retirar: ");
            double monto = Double.parseDouble(InputConsoleService.getScanner().nextLine());

            // tener en cuenta el sobregiro si la cueta es corriente
            if (cuentaARetirarSaldo.get() instanceof CuentaCorriente) {
                // mostrar por pantalla si se llego al limite de sobregiro
                if (cuentaARetirarSaldo.get().getSaldo() - monto < ((CuentaCorriente) cuentaARetirarSaldo.get()).getSobregiro()) {
                    System.out.println("Error: sobregiro excedido.\n");
                } else {
                    // si todo esta bien, remirar el monto
                    this.retirar(cuentaARetirarSaldo.get(), monto);
                    System.out.println("Monto retirado.\n");
                }
            } else {
                // si la cuenta es de ahorro
                if (cuentaARetirarSaldo.get().getSaldo() - monto >= 0) {
                    retirar(cuentaARetirarSaldo.get(), monto);
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
            if (!this.clienteConectadoTieneCuentas()) {
                System.out.println("No hay cuentas todabia.\n");
                return;
            }

            // mostrar todas las cuentas
            System.out.println("- INFORMACION DE LAS CUENTAS:");
            this.mostrarInfoCuentas(DB.getBanco().getClienteConectado().getCuentas());
            System.out.print("\n");
        } else if (opcion == 2) { // mostrar cuenta elegida
            // obtener alias de la cuenta
            System.out.print("Alias de la cuenta: ");
            String alias = InputConsoleService.getScanner().nextLine().trim();

            // obtener cuenta a consultar del cliente conectado
            Optional<Cuenta> cuentaAConsultar = this.seleccionarCuenta(alias);

            // mostrar por pantalla si no existe
            if (cuentaAConsultar.isEmpty()) {
                System.out.println("Error: cuenta no existe o no fue encontrada.\n");
            } else {
                // mostrar consulta de la cuenta
                List<Cuenta> cuenta = List.of(cuentaAConsultar.get());
                this.mostrarInfoCuentas(cuenta);
                System.out.print("\n");
            }
        } else {
            System.out.println("Error: opcion invalida.\n");
        }
    }

    private Boolean clienteConectadoTieneCuentas() {
        return !DB.getBanco().getClienteConectado().getCuentas().isEmpty();
    }

    private void mostrarInfoCuentas(List<Cuenta> cuentas) {
        for (Cuenta c : cuentas) {
            System.out.printf("Nº %s, Alias: %s, Saldo: %s.\n", c.getNumeroCuenta(), c.getAlias(), c.getSaldo());
        }
    }

    public void eliminarCuenta() {
        // obtener alias de la cuenta a eliminar
        System.out.print("Alias de la cienta a eliminar: ");
        String alias = InputConsoleService.getScanner().nextLine().trim();

        // verificar si la cuenta existe
        Optional<Cuenta> cuentaAEliminar = this.seleccionarCuenta(alias);

        // mostrar por pantalla si la cuenta a eliminar no existe
        if (cuentaAEliminar.isEmpty()) {
            System.out.println("Error: la cuenta no existe o no fue encontrada.\n");
        } else { // eliminar la cuenta si existe
            // guardar la nueva lista de cuentas al cliente conectado
            List<Cuenta> nuevaListaCuentas = this.nuevaListaCuentasExceptuando(alias);
            DB.getBanco().getClienteConectado().setCuentas(nuevaListaCuentas);
            System.out.println("Cuenta eliminada.\n");
        }
    }

    private List<Cuenta> nuevaListaCuentasExceptuando(String alias) {
        List<Cuenta> nuevaListaCuentas = new ArrayList<>();
        for (Cuenta c : DB.getBanco().getClienteConectado().getCuentas()) {
            if (!c.getAlias().equals(alias)) {
                nuevaListaCuentas.add(c);
            }
        }
        return nuevaListaCuentas;
    }

    @Override
    public void abrirCuenta() {
        Cuenta cuenta;
        int tipoDeCuenta;

        // solicitar tipo de cuenta
        tipoDeCuenta = this.solicitarTipoDeCuentaACrear();

        // crear tipo de cuenta solicitada
        cuenta = this.crearCuenta(tipoDeCuenta);

        // crear numero de cuenta
        long numeroDeCuenta = GenerarNumeroCuenta.numeroCuentaBancaria();
        cuenta.setNumeroCuenta(numeroDeCuenta);

        // agregar el titular de la cuenta (el cliente conectado)
        cuenta.setTitular(DB.getBanco().getClienteConectado());

        // crear alias
        String alias = this.crearAlias();
        cuenta.setAlias(alias);

        // guardar cuenta nueva en la lista del cliente conectado
        DB.getBanco().getClienteConectado().setCuenta(cuenta);

        System.out.println("Cuenta creada correctamente.\n");
    }

    private int solicitarTipoDeCuentaACrear() {
        int tipoDeCuenta = -1;
        Boolean ok;
        do {
            try {
                System.out.println("Seleccionar tipo de cuenta:\n1. Cuenta ahorro.\n2. Cuenta corriente.");
                System.out.print("Seleccionar tipo: ");
                tipoDeCuenta = Integer.parseInt(InputConsoleService.getScanner().nextLine().trim());
                ok = Boolean.FALSE;
            } catch (NumberFormatException e) {
                ok = Boolean.TRUE;
                System.out.println("Error: formato invalido.\n");
            }
        } while (ok);
        return tipoDeCuenta;
    }

    private Cuenta crearCuenta(int tipo) {
        Cuenta cuenta = null;
        Boolean ok;
        do {
            if (tipo == 1) {
                ok = Boolean.FALSE;
                cuenta = new CuentaAhorro();
            } else if (tipo == 2) {
                ok = Boolean.FALSE;
                cuenta = new CuentaCorriente();
            } else {
                ok = Boolean.TRUE;
                System.out.println("Error: tipo de cuenta invalido.\n");
            }
        } while (ok);
        return cuenta;
    }

    private String crearAlias() {
        String alias;
        Boolean ok;
        do {
            System.out.print("Escribe un alias: ");
            alias = InputConsoleService.getScanner().nextLine().trim();
            // validar alias
            if (!ICuentaService.validarAlias(alias)) {
                ok = Boolean.TRUE;
                System.out.println("Error: alias invalido.\n");
            } else {
                ok = Boolean.FALSE;
            }
        } while (ok);
        return alias;
    }
}
