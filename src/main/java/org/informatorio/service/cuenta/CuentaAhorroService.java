package org.informatorio.service.cuenta;

import org.informatorio.db.DB;
import org.informatorio.domain.Cuenta;
import org.informatorio.domain.CuentaAhorro;
import org.informatorio.entrada.InputConsoleService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class CuentaAhorroService implements ICuentaAhorroService {
    @Override
    public void calcularTNA() {
        CuentaAhorro cuenta = null;
        // obtener cuenta a calcular tna
        System.out.print("Alias de la cuenta: ");
        String alias = InputConsoleService.getScanner().nextLine();

        // buscar cuenta con el alias
        for (Cuenta c : DB.getBanco().getClienteConectado().getCuentas()) {
            if (alias.equals(c.getAlias()) && c instanceof CuentaAhorro) {
                cuenta = (CuentaAhorro) c;
            }
        }

        if (Objects.isNull(cuenta)) {
            System.out.println("Error: la cuenta no existe o no fue encontrada.\n");
        } else {
            double tasaDiaria = cuenta.getTNA() / 365;
            long diasTranscurridos = ChronoUnit.DAYS.between(cuenta.getFechaApertura(), LocalDate.now());

            double saldoFinal = cuenta.getSaldo() * Math.pow(1 + tasaDiaria, diasTranscurridos);
            double interesesGenerados = saldoFinal - cuenta.getSaldo();
            System.out.printf("Intereses generados %s.\n\n", interesesGenerados);
        }
    }
}
