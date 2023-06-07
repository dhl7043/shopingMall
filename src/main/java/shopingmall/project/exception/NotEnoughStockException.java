package shopingmall.project.exception;

public class NotEnoughStockException extends shopException {

    private static final String MESSAGE = "재고가 충분하지 않습니다.";

    public NotEnoughStockException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
