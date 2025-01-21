package cl.bch.technique.test.test.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class EmailNotProvidedException extends RuntimeException {

    private static final long serialVersionUID = -1500970931544655842L;
    private int errorCode;
    private String errorMessage;

    public EmailNotProvidedException(Throwable throwable) {
        super(throwable);
    }

    public EmailNotProvidedException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public EmailNotProvidedException(String msg) {
        super(msg);
    }

}
