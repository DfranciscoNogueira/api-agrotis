package br.com.agrotis.enums;

public enum ErrorMessages {

    ALREADY_USED_KEY("%s = %s já existe"),
    INVALID_INPUT("Entrada inválida fornecida: %s"),
    ENTITY_NOT_FOUND_GENERIC("%s com %s = %s não encontrado");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }

    public String getMessage() {
        return message;
    }

}


