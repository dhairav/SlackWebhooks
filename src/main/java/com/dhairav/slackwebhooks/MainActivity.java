package com.dhairav.slackwebhooks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = (EditText)findViewById(R.id.inputText);
        postButton = (Button)findViewById(R.id.button);

        //execute method on button press

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputString = inputText.getText().toString();
                if (!inputString.isEmpty()){
                    post(inputString);
                }else Toast.makeText(MainActivity.this, "Empty text field, nothing to post.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void post(String stringToPost){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =  "https://hooks.slack.com/services/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";

        //Create JSONObject here
        JSONObject jsonParam = new JSONObject();
        try {
            jsonParam.put("text", stringToPost);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
         *JSONObjectRequest using Android Volley lib
         */

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                Toast.makeText(MainActivity.this, "Posted Successfully", Toast.LENGTH_LONG).show();
                inputText.setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); //print error response in logs
            }
        });

        //add to request queue, if queue is empty, will be executed immediately
        //otherwise synchronous behaviour portrayed
        queue.add(request);
    }
}
