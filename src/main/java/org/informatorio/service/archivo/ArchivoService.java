package org.informatorio.service.archivo;

import com.opencsv.CSVWriter;
import org.informatorio.db.DB;
import org.informatorio.domain.Cliente;
import org.informatorio.domain.Cuenta;
import org.informatorio.domain.CuentaCorriente;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArchivoService implements IArchivoService {
    @Override
    public void exportarCuentasACSV() {
        String[] header = {"Numero", "Titular", "Saldo", "Tipo"};
        String ruta = "./cuentas.csv"; // raiz del proyecto

        try (CSVWriter writer = new CSVWriter(new FileWriter(ruta))) {
            writer.writeNext(header);
            List<Cuenta> listaDeCuentas = new ArrayList<>();

            // obtener todas las cuentas de todos los clientes
            for (Cliente cliente : DB.getBanco().getClientes()) {
                for (Cuenta cuenta : cliente.getCuentas()) {
                    listaDeCuentas.add(cuenta);
                }
            }

            // ordenar por numero
            Comparator<Cuenta> ordenarPorNumeroCuenta = Comparator.comparing(Cuenta::getNumeroCuenta);
            listaDeCuentas.sort(ordenarPorNumeroCuenta);

            // ordenar por saldo
            Comparator<Cuenta> ordenarPorSaldo = Comparator.comparing(Cuenta::getSaldo);
            listaDeCuentas.sort(ordenarPorSaldo);

            // guardar cuentas en un csv
            for (Cuenta cuenta : listaDeCuentas) {
                long numeroCuenta = cuenta.getNumeroCuenta();
                String titular = cuenta.getTitular().getNombre();
                double saldo = cuenta.getSaldo();
                String tipoCuenta = "ahorro";
                if (cuenta instanceof CuentaCorriente) tipoCuenta = "corriente";
                String[] fila = {String.valueOf(numeroCuenta), titular, String.valueOf(saldo), tipoCuenta};
                writer.writeNext(fila);
            }
            System.out.println("Cuentas exportadas (ruta: raiz del proyecto).\n");
        } catch (IOException e) {
            System.out.printf("Error: %s", e.getMessage());
        }
    }
}