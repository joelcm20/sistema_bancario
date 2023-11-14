package org.informatorio;

import org.informatorio.db.DB;
import org.informatorio.domain.Banco;
import org.informatorio.entrada.InputConsoleService;
import org.informatorio.service.menu.principal.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco("BanCoopProspera");
        DB.setBanco(banco);

        Boolean seguir; // seguir en la aplicacion
        do {
            seguir = new MenuPrincipal().iniciar();
        } while (seguir);

        InputConsoleService.closeScanner();
    }
}