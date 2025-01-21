package cl.bch.technique.test.test.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class InvalidNextStepException extends RuntimeException {

    private static final long serialVersionUID = -1500970948244655842L;
    private int errorCode;
    private String errorMessage;

    public InvalidNextStepException(Throwable throwable) {
        super(throwable);
    }

    public InvalidNextStepException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public InvalidNextStepException(String msg) {
        super(msg);
    }

}

