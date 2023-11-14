package org.informatorio.domain;

import java.util.ArrayList;
import java.util.List;

public class Banco {
    private String nombre;
    private List<Cliente> clientes = new ArrayList<>();

    private Cliente clienteConectado;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Cliente getClienteConectado() {
        return clienteConectado;
    }

    public void setClienteConectado(Cliente clienteConectado) {
        this.clienteConectado = clienteConectado;
    }

    public Banco(String nombre) {
        this.nombre = nombre;
    }

    public void agregarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public void abrirCuenta(Cuenta cuenta) {
        boolean clienteExiste = Boolean.FALSE;
        for (Cliente c : this.getClientes()) {
            if (c.equals(cuenta.getTitular())) {
                clienteExiste = Boolean.TRUE;
                break;
            }
        }
        if (!clienteExiste) {
            System.out.println("Error al abrir cuenta, cliente no encontrado.");
            return;
        }
        cuenta.getTitular().agregarCuenta(cuenta);
    }
}
