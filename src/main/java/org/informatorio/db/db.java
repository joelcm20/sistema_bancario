package org.informatorio.db;

import org.informatorio.domain.Banco;

public class db {
    public static Banco banco;

    public static Banco getBanco() {
        return banco;
    }

    public static void setBanco(Banco b) {
        banco = b;
    }
}
