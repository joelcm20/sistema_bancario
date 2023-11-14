package org.informatorio.service.banco;

import org.informatorio.db.db;
import org.informatorio.domain.Cliente;

public class BancoService implements IBancoService {
    @Override
    public void registrarCliente(Cliente cliente) {
        db.getBanco().agregarCliente(cliente);
    }
}
