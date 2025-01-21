package cl.bch.technique.test.test.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class InvalidEmailException extends RuntimeException {

    private static final long serialVersionUID = -1500970941344655842L;
    private int errorCode;
    private String errorMessage;

    public InvalidEmailException(Throwable throwable) {
        super(throwable);
    }

    public InvalidEmailException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public InvalidEmailException(String msg) {
        super(msg);
    }

}
