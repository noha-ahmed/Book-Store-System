package Models;

import lombok.Getter;

public enum ErrorCodes {

    WRONG_PASSWORD(-1),
    WRONG_USERNAME(-2),
    CORRECT_USER(0),
    USERNAME_EXISTS(-3),
    EMAIL_EXISTS(-4),
    SQL_ERROR(-5);

    @Getter
    private final int code;
    @org.jetbrains.annotations.Contract(pure = true)
    ErrorCodes(int code){
        this.code = code;
    }

}
