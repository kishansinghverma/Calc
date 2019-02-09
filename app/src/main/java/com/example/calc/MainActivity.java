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

    Button c;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt = findViewById(R.id.edt);
        c=findViewById(R.id.buttonc);

        edt.setEnabled(false);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str.length() > 0) {
                    str.deleteCharAt(str.length() - 1);
                    edt.setText(str);
                }
            }
        });
        c.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                str=new StringBuffer();
                edt.setText(str);
                return false;
            }
        });
    }

    StringBuffer str=new StringBuffer();
    DecimalFormat formatter = new DecimalFormat("#####.######");

    public void response(View v) {
        String name=((Button)v).getText().toString();
        String exp=str.toString();
        String sign=getSign(exp);
        if((name.equals("+") || name.equals("-") || name.equals("*") || name.equals("/") || name.equals("=")  || name.equals("\u221a") || name.equals(" x\u00B2") || name.equals("%")) && exp.contains(sign))
            process(name);

        if (isVerify(name) && !(name.equals("=") || name.equals("\u221a") || name.equals(" x\u00B2") || name.equals("%") ))
            str.append(name);

        if(name.equals("%") && str.length()>0 && !(str.length()==1 && str.charAt(0)=='-') )
        {
            str=new StringBuffer(String.valueOf(formatter.format((Double.parseDouble(str.toString())/100)))+"*");
        }
        if(name.equals("\u221a") && str.length()>0 && str.charAt(0)!='-')
        {
            str=new StringBuffer(String.valueOf(formatter.format(Math.sqrt(Double.parseDouble(str.toString())))));
        }
        if(name.equals(" x\u00B2") && str.length()>0 && !(str.length()==1 && str.charAt(0)=='-'))
        {
            str=new StringBuffer(String.valueOf(formatter.format(Math.pow(Double.parseDouble(str.toString()),2))));
        }

        edt.setText(str);
    }
    public void process(String name)
    {
        String exp=str.toString();
        String sign=getSign(exp);

        String[] values=exp.substring(1).split("\\+|\\*|-|/");
        if(exp.charAt(0)=='-')
            values[0]="-"+values[0];
        else
            values[0]=exp.charAt(0)+values[0];
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
            try {
                str = new StringBuffer(values[0]);
            }
            catch (Exception e5){
                str = new StringBuffer("");
            }
        }
    }
    String getSign(String exp)
    {
        try {
            if (exp.substring(1).contains("+"))
                return "+";
            if (exp.substring(1).contains("-"))
                return "-";
            if (exp.substring(1).contains("*"))
                return "*";
            if (exp.substring(1).contains("/"))
                return "/";
        }catch (Exception e){}
        return "none";
    }

    Boolean isVerify(String name)
    {
        String exp=str.toString();
        String sign=getSign(exp);

        if(name.equals("0") || name.equals("."))
        {
            if(exp.contains(sign)) {
                if(name.equals(".") && exp.charAt(exp.length()-1)==sign.charAt(0))
                    str=new StringBuffer(exp+"0");
                String values[] = exp.split("\\+|\\*|-|/");
                try {
                    if (values[1].contains(".") && name.equals("."))
                        return false;
                    if (Double.parseDouble(values[1]) == 0 && name.equals("0") && !values[1].contains(".")) {
                        return false;
                    }
                } catch (Exception e) {}
            }
            else
            {
                if(name.equals(".") && str.length()==0)
                    str=new StringBuffer("0");

                try {
                    if (exp.contains(".") && name.equals("."))
                        return false;
                    if (Double.parseDouble(exp)==0 && name.equals("0") && !exp.contains("."))
                        return false;
                } catch (Exception e){}
            }
        }
        if((name.equals("+")||name.equals("*")||name.equals("/")) && str.length()==0)
            return false;
        if((name.equals("+")||name.equals("-")||name.equals("*")||name.equals("/")) && exp.equals("-"))
            return false;

        return true;
    }
}