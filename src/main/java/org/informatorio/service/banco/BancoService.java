package org.informatorio.service.banco;

import org.informatorio.db.DB;
import org.informatorio.domain.*;

import java.util.List;

public class BancoService implements IBancoService {
    @Override
    public void registrarCliente(Cliente cliente) {
        Banco banco = DB.getBanco();
        List<Cliente> clientes = banco.getClientes();
        clientes.add(cliente);
        banco.setClientes(clientes);
    }
}
