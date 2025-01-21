package cl.bch.technique.test.test.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class NameNotProvidedException extends RuntimeException {

    private static final long serialVersionUID = -1500970931544655842L;
    private int errorCode;
    private String errorMessage;

    public NameNotProvidedException(Throwable throwable) {
        super(throwable);
    }

    public NameNotProvidedException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public NameNotProvidedException(String msg) {
        super(msg);
    }

}
