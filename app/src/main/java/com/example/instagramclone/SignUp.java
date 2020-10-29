package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave, btnGetAllData;
    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower;
    private TextView txtGetData;
    private String allKickBoxers;
    private Button btnTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSave = findViewById(R.id.btnSave);
        btnGetAllData = findViewById(R.id.btnGetAllData);
        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        txtGetData = findViewById(R.id.txtGetData);
        btnTransition = findViewById(R.id.btnNextActivity);

        btnSave.setOnClickListener(SignUp.this);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("TTSyoOM5Fd", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null && e == null) {
                            txtGetData.setText(object.get("name") + " - PunchPower: " + object.get("punchPower"));
                        }
                    }
                });
            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allKickBoxers = "";

                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");

                //queryAll.whereGreaterThan("punchPower", 100);
                queryAll.whereGreaterThanOrEqualTo("punchPower", 100);

                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null ) {
                            if (objects.size() > 0) {
                                for (ParseObject kickBoxer : objects) {
                                    allKickBoxers = allKickBoxers + kickBoxer.get("name") + "\n";
                                }
                                FancyToast.makeText(SignUp.this, allKickBoxers, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            } else {
                                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });

        btnTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        try {
            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", edtName.getText().toString());
            kickBoxer.put("punchSpeed", Integer.parseInt(edtPunchSpeed.getText().toString()));
            kickBoxer.put("punchPower", Integer.parseInt(edtPunchPower.getText().toString()));
            kickBoxer.put("kickSpeed", Integer.parseInt(edtKickSpeed.getText().toString()));
            kickBoxer.put("kickPower", Integer.parseInt(edtKickPower.getText().toString()));
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        //Toast.makeText(SignUp.this, kickBoxer.get("name") + " object saved", Toast.LENGTH_LONG).show();
                        FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " object saved", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    } else {
                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(SignUp.this, e + "", Toast.LENGTH_LONG).show();
        }
    }
}