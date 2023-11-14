package org.informatorio.service.cliente;

import org.informatorio.domain.Cliente;
import org.informatorio.domain.Direccion;
import org.informatorio.entrada.InputConsoleService;
import org.informatorio.service.banco.BancoService;

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
        System.out.print("- Nombre de usuario: ");
        cliente.setUsuario(InputConsoleService.getScanner().nextLine());

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

        System.out.println("Usuario registrado correctamente.");
    }
}
