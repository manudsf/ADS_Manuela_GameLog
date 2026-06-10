package br.upf.gamelog.enumeration;

public enum FormatoEnum {
    FISICO("Físico"),
    DIGITAL("Digital");

    private final String descricao;

    FormatoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
