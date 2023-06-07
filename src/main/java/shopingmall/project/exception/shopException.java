package shopingmall.project.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class shopException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();

    public shopException(String message) {
        super(message);
    }

    public shopException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode(); // 필수 반환할 코드

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
