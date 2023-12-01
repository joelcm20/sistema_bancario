package org.informatorio.service.banco;

import org.informatorio.domain.Cliente;

public interface IBancoService {
    void registrarCliente(Cliente cliente);

    void abrirCuenta();
}
