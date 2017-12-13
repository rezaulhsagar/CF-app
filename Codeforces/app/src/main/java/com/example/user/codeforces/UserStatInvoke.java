package com.example.user.codeforces;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/*done*/

public class UserStatInvoke extends AppCompatActivity {
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_user_stat_invoke);

        final EditText UsernameBox = (EditText) findViewById(R.id.UsernameBox);
        final Button UserStatButton = (Button) findViewById(R.id.UserStatButton);
        final Button MainMenu = (Button) findViewById(R.id.MainMenu);

        UserStatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = UsernameBox.getText().toString();
                String url = ("http://codeforces.com/api/user.info?handles=" + username);
                UsernameBox.setText("");
                new GetUserStat().execute(url);
            }
        });

        MainMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });
    }

    private class GetUserStat extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            pDialog = new ProgressDialog(UserStatInvoke.this);
            pDialog.setMessage("Downloading Information");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params){
            final HttpHandler sh = new HttpHandler();

            String jsonStr = "INITIAL";
            Log.d("p0", params[0]);

            try{
                jsonStr = sh.makeServiceCall(params[0]);
            } catch (Exception e){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (pDialog.isShowing()) pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to load JSON from the server!", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            if(!result.equals("INITIAL")){
                if(pDialog.isShowing()) pDialog.dismiss();
                Intent gotoUserStat = new Intent(UserStatInvoke.this, UserStat.class);
                gotoUserStat.putExtra("JsonStr",result);
                startActivity(gotoUserStat);
            }
        }
    }

}