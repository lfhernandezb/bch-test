package cl.bch.technique.test.test.exception;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class PhoneNotProvidedException extends RuntimeException {

    private static final long serialVersionUID = -1500970931544655842L;
    private int errorCode;
    private String errorMessage;

    public PhoneNotProvidedException(Throwable throwable) {
        super(throwable);
    }

    public PhoneNotProvidedException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public PhoneNotProvidedException(String msg) {
        super(msg);
    }

}
