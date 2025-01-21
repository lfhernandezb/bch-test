package cl.bch.technique.test.test.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class InvalidPasswordException extends RuntimeException {

    private static final long serialVersionUID = -1500970931344655842L;
    private int errorCode;
    private String errorMessage;

    public InvalidPasswordException(Throwable throwable) {
        super(throwable);
    }

    public InvalidPasswordException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public InvalidPasswordException(String msg) {
        super(msg);
    }

}
