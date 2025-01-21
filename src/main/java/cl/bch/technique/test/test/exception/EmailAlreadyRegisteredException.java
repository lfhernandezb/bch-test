package cl.bch.technique.test.test.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class EmailAlreadyRegisteredException extends RuntimeException {

    private static final long serialVersionUID = -1500970911344655842L;
    private int errorCode;
    private String errorMessage;

    public EmailAlreadyRegisteredException(Throwable throwable) {
        super(throwable);
    }

    public EmailAlreadyRegisteredException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public EmailAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
