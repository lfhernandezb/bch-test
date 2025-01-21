package cl.bch.technique.test.test.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class PasswordNotProvidedException extends RuntimeException {
    private static final long serialVersionUID = -1500974531544655932L;
    private int errorCode;
    private String errorMessage;

    public PasswordNotProvidedException(Throwable throwable) {
        super(throwable);
    }

    public PasswordNotProvidedException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public PasswordNotProvidedException(String msg) {
        super(msg);
    }
}
