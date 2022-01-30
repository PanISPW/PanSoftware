package com.pansoftware.logic.bean;

import com.pansoftware.logic.enumeration.ProductType;
import com.pansoftware.logic.exception.InvalidDataException;
import com.pansoftware.logic.util.DaoUtils;

public class AdviceGoalBean extends GoalBean{

    private ProductType type = ProductType.NOTSPECIFIED;
    private String advice = "";
    private String adviceActivist = "";

    public ProductType getType() {
        return this.type;
    }

    public void setType(final ProductType type) throws InvalidDataException {
        if (type.equals(ProductType.FOOD) | type.equals(ProductType.LIFESTYLE) | type.equals(ProductType.MAKEUP) | type.equals(ProductType.OTHER))
            this.type = type;
    }

    public void setTypeInt(final int typeInt) {
        type = DaoUtils.intToProductType(typeInt);
    }

    public String getAdvice() {
        return this.advice;
    }

    public void setAdvice(final String advice) throws InvalidDataException {
        if(advice.length() >= 45)
            throw new InvalidDataException("The Advice should be under 45 characters");
        this.advice = advice;
    }

    public String getAdviceActivist() {
        return this.adviceActivist;
    }

    public void setAdviceActivist(final String adviceActivist) {
        this.adviceActivist = adviceActivist;
    }

}
