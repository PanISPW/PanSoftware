package logic.entity;

import logic.dao.AdviceGoalDao;
import logic.enumeration.ProductType;
import logic.exception.UserNotFoundException;

import java.time.LocalDate;

// @author Danilo D'Amico

public class AdviceGoal extends Goal {

    private ProductType type;
    private String productBarcode;
    private String advice;
    private User adviceActivist;

    public AdviceGoal(String name, String description, int numberOfSteps, int stepsCompleted, LocalDate deadline, int id, User user, ProductType type, String advice, User adviceActivist) {
        super(name, description, numberOfSteps, stepsCompleted, deadline, user, id);
        this.type = type;
        this.advice = advice;
        this.adviceActivist = adviceActivist;

    }

    public AdviceGoal(String name, String description, int numberOfSteps, int stepsCompleted, LocalDate deadline, User user, int id) {
        super(name, description, numberOfSteps, stepsCompleted, deadline, user, id);
    }

    public static AdviceGoal getAdviceGoal(String user, int id) throws UserNotFoundException, Exception {
        return AdviceGoalDao.getAdviceGoal(user, id);
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public User getAdviceActivist() {
        return adviceActivist;
    }

    public void setAdviceActivist(User adviceActivist) {
        this.adviceActivist = adviceActivist;
    }
}
