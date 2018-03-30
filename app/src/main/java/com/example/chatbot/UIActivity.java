package com.example.chatbot;

import android.app.Activity;
import android.content.Intent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbot.MainActivity;
import com.example.chatbot.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UIActivity extends AppCompatActivity {
    Button subscriptions, summary;
    Button expense, budget, income;
    TextView expense_text_view, income_text_view, budget_text_view;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String expense_value, income_value, budget_value;
    int updated_expense = 0, updated_income = 0, updated_budget = 0, balance = 0;
    String TAG = "UIActivity";
    // CharSequence expense_char_value, budget_char_value, income_char_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("details");

        // int balance;
        expense = (Button) findViewById(R.id.add_expense);
        budget = (Button) findViewById(R.id.add_budget);
        income = (Button) findViewById(R.id.add_income);
        summary = (Button) findViewById(R.id.show_summary);

        expense_text_view = (TextView) findViewById(R.id.expense_edit_text);
        income_text_view = (TextView) findViewById(R.id.income_edit_text);
        budget_text_view = (TextView) findViewById(R.id.budget_edit_text);

        budget_value = expense_text_view.getText().toString();

        // Log.v(TAG, expense_value);
//        expense_char_value = expense_text_view.getText();
//        income_char_value = expense_text_view.getText();
//        budget_char_value = expense_text_view.getText();


        expense.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                expense_value = expense_text_view.getText().toString();
                try {
                    updated_expense = updated_expense + Integer.parseInt(expense_value);
                } catch (NumberFormatException e) {
                    e.printStackTrace();

                }
                myRef.child("expense_value").setValue(updated_expense);
                Toast toast = Toast.makeText(UIActivity.this, "Added to database. The value is : " + updated_expense, Toast.LENGTH_SHORT);
                toast.show();

            }
        });


        budget.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                budget_value = budget_text_view.getText().toString();
                try {
                    updated_budget = Integer.parseInt(budget_value);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                myRef.child("budget_value").setValue(updated_budget);
                Toast toast = Toast.makeText(UIActivity.this, "Added to database. The value is : " + updated_budget, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        income.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                income_value = income_text_view.getText().toString();
                try {
                    updated_income = updated_income + Integer.parseInt(income_value);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                myRef.child("income_value").setValue(updated_income);
                Toast toast = Toast.makeText(UIActivity.this, "Added to database. The value is : " + updated_income, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        summary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                balance = updated_budget - updated_expense;
                Toast toast = Toast.makeText(UIActivity.this, "Balance is : " + balance, Toast.LENGTH_SHORT);
                toast.show();
                String summary_message =  createSummary(updated_expense,updated_income,balance);
                displayMessage(summary_message);

            }
        });

    }


    private String createSummary(int updated_expense,int updated_income,  int balance)
    {    String summary_message = "Your Balance is: "+balance;
        summary_message = summary_message +"\nYour Expense is: "+updated_expense;
        summary_message = summary_message +"\nYour Income is: "+updated_income;
        return summary_message;

    }

    private void displayMessage(String summary_message) {


        TextView quantityTextView = (TextView) findViewById(R.id.summary_text_view);
        quantityTextView.setVisibility(View.VISIBLE);
        quantityTextView.setText("" + summary_message);
    }


    }
