package com.example.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    final Button[] btn=new Button[6];
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt = findViewById(R.id.edt);
        btn[0] = findViewById(R.id.buttonc);
        btn[1] = findViewById(R.id.equal);

        edt.addTextChangedListener(tw);

        btn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str.length() > 0) {
                    str.deleteCharAt(str.length() - 1);
                    edt.setText(str);
                }
            }
        });
    }

    StringBuffer str=new StringBuffer();
    public void response(View v) {
        String name=((Button)v).getText().toString();
        String exp=str.toString();
        String sign=getSign(exp);
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        if((name.equals("+") || name.equals("-") || name.equals("*") || name.equals("/")) && exp.contains(sign))
            process();

        if (isVerify(name)){
            str.append(name);
            edt.setText(str);
        }
    }
    public void process()
    {
        DecimalFormat formatter = new DecimalFormat("#####.####");
        String exp=str.toString();
        String sign=getSign(exp);

        String[] values=str.toString().split("\\+|\\*|-|/");
        try {
            Double num1 = Double.parseDouble(values[0]);
            Double num2 = Double.parseDouble(values[1]);

            if (sign.equals("+"))
                str=new StringBuffer(String.valueOf(formatter.format(num1+num2)));
            else if (sign.equals("-"))
                str=new StringBuffer(String.valueOf(formatter.format(num1-num2)));
            else if (sign.equals("*"))
                str=new StringBuffer(String.valueOf(formatter.format(num1*num2)));
            else if (sign.equals("/") && num2>0)
                str=new StringBuffer(String.valueOf(formatter.format(num1/num2)));
            else {
                str=new StringBuffer(values[0]);
                Toast.makeText(this, "Can't Divide By Zero!", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            str=new StringBuffer(values[0]);
        }

    }
    String getSign(String exp)
    {
        if(exp.contains("+"))
            return new String("+");
        if(exp.contains("-"))
            return new String("-");
        if(exp.contains("*"))
            return new String("*");
        if(exp.contains("/"))
            return new String("/");
        return new String("none");
    }

    Boolean isVerify(String name)
    {
        String exp=str.toString();
        String sign=getSign(exp);

        if(name.equals("0") || name.equals("."))
        {
            if(exp.contains(sign)) {
                String values[] = (String[]) exp.split("\\+|\\*|-|/");
                try {
                    if (values[1].contains(".") && name.equals("."))
                        return false;
                    if (Float.parseFloat(values[1]) == 0 && name.equals("0") && !values[1].contains(".")) {
                        return false;
                    }
                } catch (Exception e) {}
            }
            else
            {
                try {
                    if (exp.contains(".") && name.equals("."))
                        return false;
                    if (Float.parseFloat(exp)==0 && name.equals("0") && !exp.contains("."))
                        return false;
                } catch (Exception e){}
            }
        }

        return true;
    }
    TextWatcher tw=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            str=new StringBuffer(edt.getText().toString());
        }
    };
}