package br.upf.gamelog.enumeration;

public enum PrioridadeEnum {
    ALTA("Alta"),
    MEDIA("Média"),
    BAIXA("Baixa");

    private final String descricao;

    PrioridadeEnum(String descricao) {
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
