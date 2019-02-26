package com.lyn.practice.service.spring.cloud.client;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Hello world!
 *
 */
public class App {

    public static String calculate(){
        EvaluationContext ctx = new StandardEvaluationContext();
        ExpressionParser parser=new SpelExpressionParser();

        ctx.setVariable("abc",99);

        Object value = parser.parseExpression("#abc+9").getValue(ctx);

        System.out.printf("value = " + value);
        return  null;
    }

    public static void main(String[] args) {
        calculate();
    }

}
