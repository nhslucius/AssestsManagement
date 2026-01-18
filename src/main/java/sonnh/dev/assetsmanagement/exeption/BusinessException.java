package sonnh.dev.assetsmanagement.exeption;

import lombok.Getter;
import sonnh.dev.assetsmanagement.constant.ErrorCode;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }
}
