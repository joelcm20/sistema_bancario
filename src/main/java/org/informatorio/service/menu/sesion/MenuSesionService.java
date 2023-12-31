package org.informatorio.service.menu.sesion;

import org.informatorio.db.DB;
import org.informatorio.domain.Cliente;
import org.informatorio.service.cuenta.CuentaAhorroService;
import org.informatorio.service.cuenta.CuentaService;

public class MenuSesionService implements IMenuSesionService {
    @Override
    public void menuSesion() {
        StringBuilder menu = new StringBuilder();
        menu.append(String.format("========== Bienvenido/a %s ==========\n", DB.getBanco().getClienteConectado().getUsuario()));
        menu.append("1. Depositar saldo.\n");
        menu.append("2. Retirar saldo.\n");
        menu.append("3. Consultar saldo.\n");
        menu.append("4. Agregar cuenta.\n");
        menu.append("5. Eliminar cuenta.\n");
        menu.append("6. Calcular TNA(solo cuentas de ahorro).\n");
        menu.append("7. Cerrar sesion.\n");
        menu.append("0. Salir.\n");
        menu.append("=".repeat(35 + DB.getBanco().getClienteConectado().getUsuario().length()) + "\n");
        System.out.print(menu);
    }

    @Override
    public void realizarTareaMenuSesion(int opcion) {
        switch (opcion) {
            case 1:
                new CuentaService().depositarSaldo();
                break;
            case 2:
                new CuentaService().retirarSaldo();
                break;
            case 3:
                new CuentaService().consultarSaldo();
                break;
            case 4:
                new CuentaService().abrirCuenta();
                break;
            case 5:
                new CuentaService().eliminarCuenta();
                break;
            case 6:
                new CuentaAhorroService().calcularTNA();
                break;
            case 7:
                this.cerrarSesion();
                break;
            case 0:
                System.out.println("Adios.\n");
                break;
            default:
                System.out.println("Opcion invalida.\n");
                break;
        }
    }

    @Override
    public void cerrarSesion() {
        Cliente clienteConectado = DB.getBanco().getClienteConectado();
        System.out.printf("Cerrando sesion de %s.\n\n", clienteConectado.getUsuario());
        DB.getBanco().setClienteConectado(null);
    }
}
