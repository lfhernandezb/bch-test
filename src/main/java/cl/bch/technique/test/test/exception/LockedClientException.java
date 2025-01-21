package cl.bch.technique.test.test.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class LockedClientException extends RuntimeException {

    private static final long serialVersionUID = -1500970936744655842L;
    private int errorCode;
    private String errorMessage;

    public LockedClientException(Throwable throwable) {
        super(throwable);
    }

    public LockedClientException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public LockedClientException(String msg) {
        super(msg);
    }

}
