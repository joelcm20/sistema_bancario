package org.informatorio.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cliente {
    private UUID id = UUID.randomUUID();
    private String nombre;
    private String usuario;
    private String contrasena;
    private Direccion direccion;
    private List<Cuenta> cuentas = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
    }
}
