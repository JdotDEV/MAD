package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView equationTextView;
    private final StringBuilder equationBuilder = new StringBuilder();
    private double lastResult = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        equationTextView = findViewById(R.id.equationTextView);
        setNumericButtonClickListener(R.id.button_0, "0");
        setNumericButtonClickListener(R.id.button_1, "1");
        setNumericButtonClickListener(R.id.button_2, "2");
        setNumericButtonClickListener(R.id.button_3, "3");
        setNumericButtonClickListener(R.id.button_4, "4");
        setNumericButtonClickListener(R.id.button_5, "5");
        setNumericButtonClickListener(R.id.button_6, "6");
        setNumericButtonClickListener(R.id.button_7, "7");
        setNumericButtonClickListener(R.id.button_8, "8");
        setNumericButtonClickListener(R.id.button_9, "9");
        setOperatorButtonClickListener(R.id.button_add, "+");
        setOperatorButtonClickListener(R.id.button_sub, "-");
        setOperatorButtonClickListener(R.id.button_mul, "*");
        setOperatorButtonClickListener(R.id.button_divide, "/");
        setOperatorButtonClickListener(R.id.button_point, ".");
        setOperatorButtonClickListener(R.id.button_percent, "%");

        findViewById(R.id.button_ac).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equationBuilder.setLength(0);
                updateEquation();
            }
        });

        findViewById(R.id.button_equal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
            }
        });
    }

    private void setNumericButtonClickListener(int buttonId, final String value) {
        findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equationBuilder.append(value);
                updateEquation();
            }
        });
    }

    private void setOperatorButtonClickListener(int buttonId, final String operator) {
        findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operator.equals(".")) {
                    handleDecimalPoint();
                } else if (operator.equals("%")) {
                    calculatePercentage();
                } else {
                    equationBuilder.append(" ").append(operator).append(" ");
                    updateEquation();
                }
            }
        });
    }

    private void handleDecimalPoint() {
        String lastPart = getLastPartOfEquation();
        if (!lastPart.contains(".")) {
            equationBuilder.append(".");
            updateEquation();
        }
    }

    private String getLastPartOfEquation() {
        String equation = equationBuilder.toString();
        if (equation.contains(" ")) {
            return equation.substring(equation.lastIndexOf(" ") + 1);
        } else {
            return equation;
        }
    }

    private void updateEquation() {
        equationTextView.setText(equationBuilder.toString());
    }

    private void calculateResult() {
        String equation = equationBuilder.toString();

        try {
            Expression expression = new ExpressionBuilder(equation).build();
            double result = expression.evaluate();
            lastResult = result;

            if (result == (int) result) {
                equationTextView.setText(String.valueOf((int) result));
            } else {
                String resultText = String.format("%.2f", result);
                equationTextView.setText(resultText);
            }

            equationBuilder.setLength(0);
        } catch (ArithmeticException | IllegalArgumentException e) {
            String errorText = "Error: " + e.getMessage();
            equationTextView.setText(errorText);
            equationBuilder.setLength(0);
        }
    }

    private void calculatePercentage() {
        try {
            double numericValue = Double.parseDouble(String.valueOf(evaluateExpression(equationBuilder.toString())));
            double result = numericValue / 100.0;
            lastResult = result;
            String resultText = String.format("%.2f", result);
            equationTextView.setText(resultText);

            equationBuilder.setLength(0);
        } catch (ArithmeticException | IllegalArgumentException e) {
            String errorText = "Error: " + e.getMessage();
            equationTextView.setText(errorText);
            equationBuilder.setLength(0);
        }
    }

    private double evaluateExpression(String expression) {
        try {
            Expression e = new ExpressionBuilder(expression).build();
            return e.evaluate();
        } catch (Exception e) {
            e.printStackTrace();
            return Double.NaN;
        }
    }
}