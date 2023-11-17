package org.informatorio.service.banco;

import org.informatorio.db.DB;
import org.informatorio.domain.Cliente;

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
}
