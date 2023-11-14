package org.informatorio.service.banco;

import org.informatorio.domain.Cliente;

import java.util.Optional;

public interface IBancoService {
    void registrarCliente(Cliente cliente);

    Optional<Cliente> buscarClientePorCredenciales(String usuario, String contrasena);
}
