package org.informatorio.service.cliente;

import org.informatorio.db.DB;
import org.informatorio.domain.*;
import org.informatorio.entrada.InputConsoleService;
import org.informatorio.service.banco.BancoService;
import org.informatorio.service.cuenta.CuentaService;
import org.informatorio.service.cuenta.ICuentaService;
import org.informatorio.utils.GenerarNumeroCuenta;

import java.util.Optional;

public class ClienteService implements IClienteService {
    @Override
    public void registrarme() {
        Cliente cliente = new Cliente();
        Direccion direccion = new Direccion();
        Boolean ok;

        // crear nombre
        System.out.print("- Nombre completo: ");
        cliente.setNombre(InputConsoleService.getScanner().nextLine());

        // crear nombre de usuario
        ok = Boolean.FALSE;
        do {
            System.out.print("- Nombre de usuario: ");
            cliente.setUsuario(InputConsoleService.getScanner().nextLine());
            // validar si el usuario ya existe
            if (new BancoService().usuarioYaRegistrado(cliente.getUsuario())) {
                System.out.println("Error: el usuario ya exixte. Elige otro.");
                ok = Boolean.TRUE;
            } else {
                ok = Boolean.FALSE;
            }
        } while (ok);

        // crear contraseña
        ok = Boolean.FALSE;
        do {
            System.out.print("- Contraseña: ");
            cliente.setContrasena(InputConsoleService.getScanner().nextLine());

            // confirmarContraseña
            System.out.print("- Confirmar contraseña: ");
            String contrasenaConfirmar = InputConsoleService.getScanner().nextLine();
            if (!contrasenaConfirmar.equals(cliente.getContrasena())) {
                ok = Boolean.TRUE;
                System.out.println("Las contraseñas no coinciden. Vuelve a intentar.");
            } else {
                ok = Boolean.FALSE;
            }
        } while (ok);

        // crear direccion | numero
        ok = Boolean.FALSE;
        do {
            try {
                System.out.println("- Completar datos de direccion.");
                System.out.print("- Numero: ");
                direccion.setNumero(Integer.parseInt(InputConsoleService.getScanner().nextLine()));
                ok = Boolean.FALSE;
            } catch (Exception e) {
                ok = Boolean.TRUE;
                System.out.println("Error de formato.");
            }
        } while (ok);

        // crear direccion | calle
        System.out.print("- Calle: ");
        direccion.setCalle(InputConsoleService.getScanner().nextLine());

        // crear direccion | ciudad
        System.out.print("- Ciudad: ");
        direccion.setCalle(InputConsoleService.getScanner().nextLine());

        // crear direccion | provincia
        System.out.print("- Provincia: ");
        direccion.setCalle(InputConsoleService.getScanner().nextLine());

        // asignando direccion al cliente
        cliente.setDireccion(direccion);

        // registrar el cliente en el banco
        new BancoService().registrarCliente(cliente);

        System.out.println("Usuario registrado correctamente.\n");
    }

    @Override
    public void iniciarSesion() {
        // obtener nombre de usuario
        System.out.print("- Nombre de usuario: ");
        String nombreUsuario = InputConsoleService.getScanner().nextLine();
        // obtener contraseña
        System.out.print("- Contraseña: ");
        String contrasena = InputConsoleService.getScanner().nextLine();

        // buscar cliente por sus credenciales
        Optional<Cliente> cliente = new BancoService().buscarClientePorCredenciales(nombreUsuario, contrasena);

        // gestionar en caso de que el cliente no exista
        if (cliente.isEmpty()) {
            System.out.println("Error: Usuario o contraseña invalidos.\n");
        } else {
            // agregar al usuario como conectado
            DB.getBanco().setClienteConectado(cliente.get());
            System.out.println("Sesion iniciada correctamente.\n");
        }
    }

    @Override
    public void agregarCuenta() {
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
