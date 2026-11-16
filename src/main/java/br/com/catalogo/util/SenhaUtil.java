package br.com.catalogo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SenhaUtil {

    public static String gerarHash(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(senha.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Erro ao gerar hash", e);
        }
    }

    public static boolean confere(String senha, String hash) {
        if (senha == null || hash == null) {
            return false;
        }
        return gerarHash(senha).equals(hash);
    }
}
