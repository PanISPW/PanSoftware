package logic.bean;

public class AdviceGoalQueryBean extends GoalQueryBean{
    int productType = 4; // not specified

    public int getProductType() {
        return this.productType;
    }

    public void setProductType(final int productType) {
        this.productType = productType;
    }
}
