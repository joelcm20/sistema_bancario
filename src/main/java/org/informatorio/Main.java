package org.informatorio;

import org.informatorio.db.db;
import org.informatorio.domain.Banco;
import org.informatorio.entrada.InputConsoleService;
import org.informatorio.service.menu.principal.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco("BanCoopProspera");
        db.setBanco(banco);

        Boolean seguir; // seguir en la aplicacion
        do {
            seguir = new MenuPrincipal().iniciar();
        } while (seguir);

        InputConsoleService.closeScanner();
    }
}