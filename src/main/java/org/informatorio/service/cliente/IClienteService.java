package org.informatorio.service.cliente;

import org.informatorio.domain.Cliente;

import java.util.Optional;

public interface IClienteService {
    void registrarme();

    void iniciarSesion();

    Boolean usuarioYaRegistrado(String usuario);

    Optional<Cliente> buscarClientePorCredenciales(String usuario, String contrasena);
}
