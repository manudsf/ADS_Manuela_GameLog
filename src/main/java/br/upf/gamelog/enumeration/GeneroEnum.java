package br.upf.gamelog.enumeration;

public enum GeneroEnum {
    ACAO("Ação"),
    ACAO_AVENTURA("Ação-Aventura"),
    RPG("RPG"),
    JRPG("JRPG"),
    ESTRATEGIA("Estratégia"),
    SIMULACAO("Simulação"),
    ESPORTES("Esportes"),
    CORRIDA("Corrida"),
    TERROR("Terror"),
    PLATAFORMA("Plataforma"),
    METROIDVANIA("Metroidvania"),
    PUZZLE("Puzzle"),
    LUTA("Luta"),
    TIRO("Tiro"),
    MUNDO_ABERTO("Mundo Aberto"),
    OUTRO("Outro");

    private final String descricao;

    GeneroEnum(String descricao) {
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
