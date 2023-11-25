package org.informatorio.service.menu.principal;

import org.informatorio.db.DB;
import org.informatorio.domain.Cliente;
import org.informatorio.entrada.InputConsoleService;
import org.informatorio.service.banco.BancoService;
import org.informatorio.service.menu.sesion.MenuSesionService;

import java.util.Objects;

public class MenuPrincipalService implements IMenuPrincipalService {

    @Override
    public Boolean iniciar() {
        int opcion = -1;
        Boolean ok;
        int seguir = -1;
        do {
            try {
                mostrarMenu();
                System.out.print("Elegir opcion: ");
                opcion = Integer.parseInt(InputConsoleService.getScanner().nextLine());
                // realizar tarea
                ok = Boolean.FALSE;
                if (Objects.isNull(DB.getBanco().getClienteConectado())) {
                    this.realizarTareaMenuPrincipal(opcion);
                } else {
                    new MenuSesionService().realizarTareaMenuSesion(opcion);
                }
            } catch (NumberFormatException e) {
                ok = Boolean.TRUE;
                System.out.println("Error: formato invalido.\n");
            }
        } while (ok);
        return opcion == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    @Override
    public void mostrarMenu() {
        Cliente clienteConectado = DB.getBanco().getClienteConectado();
        if (Objects.isNull(clienteConectado)) {
            this.menuPrincipal();
        } else {
            new MenuSesionService().menuSesion();
        }
    }

    @Override
    public void menuPrincipal() {
        StringBuilder menu = new StringBuilder();
        menu.append(String.format("========== %s ==========\n", DB.getBanco().getNombre()));
        menu.append("1. Registrarse.\n");
        menu.append("2. Iniciar sesion.\n");
        menu.append("3. Exportar Cuentas a CSV.\n");
        menu.append("0. Salir.\n");
        menu.append("=".repeat(22 + DB.getBanco().getNombre().length()) + "\n");
        System.out.print(menu);
    }

    @Override
    public void realizarTareaMenuPrincipal(int opcion) {
        switch (opcion) {
            case 1:
                Cliente.registrarme();
                break;
            case 2:
                Cliente.iniciarSesion();
                break;
            case 3:
                new BancoService().exportarCuentasACSV();
                break;
            case 0:
                System.out.println("Adios.\n");
                break;
            default:
                System.out.println("Opcion invalida.\n");
        }
    }
}