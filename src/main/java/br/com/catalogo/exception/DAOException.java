package br.com.catalogo.exception;

public class DAOException extends RuntimeException {

    public DAOException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
