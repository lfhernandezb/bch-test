package cl.bch.technique.test.test.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class MissingConfigurationValueException extends RuntimeException {

    private static final long serialVersionUID = -1500970951344655842L;
    private int errorCode;
    private String errorMessage;

    public MissingConfigurationValueException(Throwable throwable) {
        super(throwable);
    }

    public MissingConfigurationValueException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public MissingConfigurationValueException(String msg) {
        super(msg);
    }

}
