package com.pansoftware.test;

// @author Danilo D'Amico

import com.pansoftware.logic.bean.GoalBean;
import com.pansoftware.logic.exception.InvalidDataException;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestGoalBean {

    private static final String TEST_INPUT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut nec sagittis metus. Aliquam eget elementum ante, eget varius neque. Cras lobortis, lacus nec commodo commodo, nisi quam egestas quam, ut vestibulum elit justo et lacus. Ut a orci et ipsum molestie accumsan. Aliquam erat volutpat. Etiam interdum eget urna eu sollicitudin.";

    @Test
    public void testSetNameTooLong() {

        assertThrows(InvalidDataException.class, () -> {
            final GoalBean bean = new GoalBean();
            bean.setName(TEST_INPUT);
        });
    }

}
