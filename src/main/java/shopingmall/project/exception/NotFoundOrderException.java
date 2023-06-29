package shopingmall.project.exception;

public class NotFoundOrderException extends ShopException {

    private static final String MESSAGE = "주문을 조회할 수 없습니다.";

    public NotFoundOrderException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
