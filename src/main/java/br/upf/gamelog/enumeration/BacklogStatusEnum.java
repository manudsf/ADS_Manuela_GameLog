package br.upf.gamelog.enumeration;

public enum BacklogStatusEnum {
    POR_JOGAR("Por Jogar"),
    A_JOGAR("A Jogar"),
    CONCLUIDO("Concluido"),
    PLATINADO("Platinado");

    private final String descricao;

    BacklogStatusEnum(String descricao) {
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
