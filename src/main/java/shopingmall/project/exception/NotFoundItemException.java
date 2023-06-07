package shopingmall.project.exception;

public class NotFoundItemException extends shopException {

    private static final String MESSAGE = "상품을 조회할 수 없습니다.";

    public NotFoundItemException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
