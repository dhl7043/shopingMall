package shopingmall.project.exception;

public class NotFoundMemberException extends ShopException {

    private static final String MESSAGE = "회원을 조회할 수 없습니다.";

    public NotFoundMemberException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
