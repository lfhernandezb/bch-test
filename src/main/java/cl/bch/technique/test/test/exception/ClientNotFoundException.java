package cl.bch.technique.test.test.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ClientNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1500970921344655842L;
    private int errorCode;
    private String errorMessage;

    public ClientNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public ClientNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public ClientNotFoundException(String msg) {
        super(msg);
    }

}
