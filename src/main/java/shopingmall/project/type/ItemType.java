package shopingmall.project.type;

public enum ItemType {

    FOOD("Food"),
    CLOTHES("Clothes");

    private final String type;

    ItemType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
