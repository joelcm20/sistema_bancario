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

    public Banco() {
    }

    public Banco(String nombre) {
        this.nombre = nombre;
    }
}
