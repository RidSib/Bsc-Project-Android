package com.apps.ridvan.smartgatekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.ridvan.smartgatekeeper.model.Function;
import com.apps.ridvan.smartgatekeeper.model.FunctionListData;

import java.util.ArrayList;

public class FunctionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functions);

        GlobalValues gValues = (GlobalValues) getApplication();
        FunctionListData fList = gValues.getFunctionList();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_functions_form);



        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ArrayList<Button> buttonList = new ArrayList<>();
        for (final Function function : fList.getFunctions()) {
            Button b = new Button(this);
            b.setLayoutParams(params);
            b.setText(function.getName());
            linearLayout.addView(b);
            buttonList.add(b);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    functionCall(function);
                }
            });
        }
    }

    private boolean functionCall(Function function){
        Toast.makeText(this, "Function "+function.getName()+" has been pressed!", Toast.LENGTH_SHORT).show();
        return false;
    }
}
