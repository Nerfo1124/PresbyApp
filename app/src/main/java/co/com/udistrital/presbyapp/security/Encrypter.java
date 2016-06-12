package co.com.udistrital.presbyapp.security;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Fernando on 21/03/2016.
 */
public class Encrypter {

    private static String TAG_LOG = "[Sin_nombre]";

    public static String MD5 = "MD2";
    public static String SHA256 = "SHA-256";

    /**
     * <b>Descripcion: </b> Convierte un arreglo de bytes a String usando valores
     * hexadecimales.
     *
     * @param digest
     * @return
     */
    private static String toHexadecimal(byte[] digest) {
        String hash = "";
        for (byte aux : digest) {
            int b = aux & 0xfff;
            if (Integer.toHexString(b).length() == 1)
                hash += 0;
            hash += Integer.toHexString(b);
        }
        return hash;
    }

    /**
     * <b>Descripcion: </b> Encripta un mensaje de texto mediante algoritmo de
     * resumen de mensaje.
     *
     * @param msg       Texto a encriptar.
     * @param algorithm Algoritmo de encriptacion.
     * @return Mensaje encriptado.
     */
    public static String getStringMessageDigest(String msg, String algorithm) {
        byte[] digest = null;
        byte[] buffer = msg.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ex) {
            Log.e(TAG_LOG, "Error al crear digest a encriptar.", ex);
        }
        return toHexadecimal(digest);
    }
}
