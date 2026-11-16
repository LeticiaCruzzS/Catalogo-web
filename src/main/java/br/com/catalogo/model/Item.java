package br.com.catalogo.model;

public class Item extends Entidade {

    private String titulo;
    private TipoItem tipo;
    private String criador; 
    private Integer ano;
    private String genero;
    private Integer nota; 
    private String descricao;
    private Long usuarioId;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoItem getTipo() {
        return tipo;
    }

    public void setTipo(TipoItem tipo) {
        this.tipo = tipo;
    }

    public String getCriador() {
        return criador;
    }

    public void setCriador(String criador) {
        this.criador = criador;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    
    public String getEstrelas() {
        if (nota == null) {
            return "";
        }
        String s = "";
        for (int i = 1; i <= 5; i++) {
            if (i <= nota) {
                s += "★";
            } else {
                s += "☆";
            }
        }
        return s;
    }
}
