package org.informatorio.utils;

import java.math.BigInteger;
import java.util.Random;

public class GenerarNumeroCuenta {
    public static long numeroCuentaBancaria() {
        Random random = new Random();
        int longitud = 10;
        StringBuilder numero = new StringBuilder();

        for (int i = 0; i < longitud; i++) {
            int randomInt = random.nextInt(10);
            numero.append(randomInt);
        }

        return Long.parseLong(numero.toString());
    }
}
