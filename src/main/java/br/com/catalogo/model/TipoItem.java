package br.com.catalogo.model;

public enum TipoItem {
    LIVRO("Livro"),
    SERIE("Série"),
    FILME("Filme");

    private String descricao;

    TipoItem(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    
    public static TipoItem converter(String valor) {
        if (valor == null) {
            return null;
        }
        for (TipoItem t : TipoItem.values()) {
            if (t.name().equalsIgnoreCase(valor)) {
                return t;
            }
        }
        return null;
    }
}
