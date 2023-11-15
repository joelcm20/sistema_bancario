package org.informatorio.service.menu.sesion;

public interface IMenuSesion {
    void menuSesion();

    void realizarTareaMenuSesion(int opcion);

    void depositarSaldo();

    void retirarSaldo();

    void consultarSaldo();

    void cerrarSesion();
}
