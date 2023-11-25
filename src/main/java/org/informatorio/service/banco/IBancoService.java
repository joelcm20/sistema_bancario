package org.informatorio.service.banco;

import org.informatorio.domain.Cliente;
import org.informatorio.domain.Cuenta;

import java.util.Optional;

public interface IBancoService {
    void registrarCliente(Cliente cliente);

    Optional<Cliente> buscarClientePorCredenciales(String usuario, String contrasena);

    Boolean usuarioYaRegistrado(String usuario);

    void validarCuenta(Cuenta cuenta);

    void exportarCuentasACSV();
}
