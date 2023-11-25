package org.informatorio.service.banco;

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
import java.util.Optional;

public class BancoService implements IBancoService {
    @Override
    public void registrarCliente(Cliente cliente) {
        DB.getBanco().agregarCliente(cliente);
    }

    @Override
    public Optional<Cliente> buscarClientePorCredenciales(String usuario, String contrasena) {
        for (Cliente cliente : DB.getBanco().getClientes()) {
            if (cliente.getUsuario().equals(usuario) && cliente.getContrasena().equals(contrasena)) {
                return Optional.of(cliente);
            }
        }
        return Optional.empty();
    }

    @Override
    public Boolean usuarioYaRegistrado(String usuario) {
        for (Cliente c : DB.getBanco().getClientes()) {
            if (c.getUsuario().equals(usuario)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    @Override
    public void validarCuenta(Cuenta cuenta) {
        boolean clienteExiste = Boolean.FALSE;
        for (Cliente c : DB.getBanco().getClientes()) {
            if (c.equals(cuenta.getTitular())) {
                clienteExiste = Boolean.TRUE;
                break;
            }
        }
        if (!clienteExiste) {
            System.out.println("Error al abrir cuenta, cliente no encontrado.");
        }
    }

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
