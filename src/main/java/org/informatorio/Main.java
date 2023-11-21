package org.informatorio;

import org.informatorio.db.DB;
import org.informatorio.domain.Banco;
import org.informatorio.entrada.InputConsoleService;
import org.informatorio.service.menu.principal.MenuPrincipalService;

public class Main {
    public static void main(String[] args) {
        // crear banco
        Banco banco = new Banco("BanCoopProspera");
        // gardar el banco en la db
        DB.setBanco(banco);

        Boolean seguir; // seguir en la aplicacion
        do {
            seguir = new MenuPrincipalService().iniciar();
        } while (seguir);

        InputConsoleService.closeScanner();
    }
}