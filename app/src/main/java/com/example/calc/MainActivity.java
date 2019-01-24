package com.example.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    double first, second;

    void run(String str) {
        try {
            first = Double.parseDouble(((EditText) findViewById(R.id.editText)).getText().toString());
            second = Double.parseDouble(((EditText) findViewById(R.id.editText2)).getText().toString());
            if(str.equals("add"))
                Toast.makeText(getApplicationContext(), String.valueOf(first+second), Toast.LENGTH_SHORT).show();
            else if(str.equals("subtract"))
                Toast.makeText(getApplicationContext(), String.valueOf(first-second), Toast.LENGTH_SHORT).show();
            else if(str.equals("multiply"))
                Toast.makeText(getApplicationContext(), String.valueOf(first*second), Toast.LENGTH_SHORT).show();
            else
                try {
                    Toast.makeText(getApplicationContext(), String.valueOf(first / second), Toast.LENGTH_SHORT).show();
                }
                catch (Exception x) {
                    Toast.makeText(getApplicationContext(), "Can't Divide By Zero", Toast.LENGTH_SHORT).show();
                }

        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Invalid Inputs!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.button);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run("add");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run("subtract");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run("multiply");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                run("divide");
            }
        });
    }
}