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
            listaDeCuentas = this.obtenerTodasLasCuentas();

            // ordenar por numero
            ordenarPorNumero(listaDeCuentas);

            // ordenar por saldo
            this.ordenarPorSaldo(listaDeCuentas);

            // guardar cuentas en un csv
            this.guardarCuentasEnCSV(writer, listaDeCuentas);

            System.out.println("Cuentas exportadas (ruta: raiz del proyecto).\n");
        } catch (IOException e) {
            System.out.printf("Error: %s", e.getMessage());
        }
    }

    private List<Cuenta> obtenerTodasLasCuentas() {
        List<Cuenta> cuentas = new ArrayList<>();
        for (Cliente cliente : DB.getBanco().getClientes()) {
            for (Cuenta cuenta : cliente.getCuentas()) {
                cuentas.add(cuenta);
            }
        }
        return cuentas;
    }

    private void guardarCuentasEnCSV(CSVWriter writer, List<Cuenta> cuentas) {
        for (Cuenta cuenta : cuentas) {
            long numeroCuenta = cuenta.getNumeroCuenta();
            String titular = cuenta.getTitular().getNombre();
            double saldo = cuenta.getSaldo();
            String tipoCuenta = "ahorro";
            if (cuenta instanceof CuentaCorriente) tipoCuenta = "corriente";
            String[] fila = {String.valueOf(numeroCuenta), titular, String.valueOf(saldo), tipoCuenta};
            writer.writeNext(fila);
        }
    }

    private void ordenarPorSaldo(List<Cuenta> cuentas) {
        Comparator<Cuenta> ordenarPorSaldo = Comparator.comparing(Cuenta::getSaldo);
        cuentas.sort(ordenarPorSaldo);
    }

    private void ordenarPorNumero(List<Cuenta> cuentas) {
        Comparator<Cuenta> ordenarPorNumeroCuenta = Comparator.comparing(Cuenta::getNumeroCuenta);
        cuentas.sort(ordenarPorNumeroCuenta);
    }
}