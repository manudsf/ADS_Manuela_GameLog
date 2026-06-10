package br.upf.gamelog.enumeration;

public enum PlataformaEnum {
    PLAYSTATION_4("PlayStation 4"),
    PLAYSTATION_5("PlayStation 5"),
    XBOX_ONE("Xbox One"),
    XBOX_SERIES("Xbox Series X/S"),
    NINTENDO_SWITCH("Nintendo Switch"),
    PC("PC"),
    MOBILE("Mobile"),
    OUTRO("Outro");

    private final String descricao;

    PlataformaEnum(String descricao) {
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
