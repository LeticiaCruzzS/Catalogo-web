package br.com.catalogo.model;

public abstract class Entidade {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
