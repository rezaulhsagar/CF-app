package com.example.user.codeforces;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*done*/

public class UserStat extends AppCompatActivity {
    ImageView UserImage;
    String handle, firstName, lastName, organization, city, country, rating, maxRating, rank, maxRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_user_stat);

        UserImage = (ImageView) findViewById(R.id.UserImage);

        String JsonStr = getIntent().getExtras().getString("JsonStr");
        final Button SearchAgain = (Button) findViewById(R.id.SearchAgain);
        new GetUserStat().execute(JsonStr);

        SearchAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class GetUserStat extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params){
            String url = null;
            Log.d("url",params[0]);
            try{
                JSONObject jsonObj = new JSONObject(params[0]);
                JSONArray Result = jsonObj.getJSONArray("result");
                JSONObject User = Result.getJSONObject(0);

                String Status = jsonObj.getString("status");

                if(Status.equals("OK")){
                    try{
                        url = User.getString("titlePhoto");
                    } catch(Exception e){/**/}

                    try{
                        handle = User.getString("handle");
                    } catch(Exception E){
                        handle = "Not found!";
                    }
                    try{
                        firstName = User.getString("firstName");
                    }catch(Exception e){
                        firstName = "Not found!";
                    }
                    try{
                        lastName = User.getString("lastName");
                    }catch(Exception e){
                        lastName = "Not found!";
                    }
                    try{
                        organization = User.getString("organization");
                    } catch(Exception E){
                        organization = "Not found!";
                    }
                    try{
                        city = User.getString("city");
                    }catch(Exception e){
                        city = "Not found!";
                    }
                    try{
                        country = User.getString("country");
                    }catch(Exception e){
                        country = "Not found!";
                    }
                    try{
                        rating = Integer.toString(User.getInt("rating"));

                    }catch(Exception e){
                        rating = "Not found!";
                    }
                    try{
                        maxRating = Integer.toString(User.getInt("maxRating"));
                    }catch(Exception e){
                        maxRating = "Not found!";
                    }
                    try{
                        rank = User.getString("rank");
                    }catch(Exception e){
                        rank = "Not found!";
                    }
                    try{
                        maxRank = User.getString("maxRank");
                    }catch(Exception e){
                        maxRank = "Not found!";
                    }
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Failed to load data for this user!", Toast.LENGTH_LONG).show();
                        }
                    });
                }


            } catch(final JSONException e){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "JSON parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            return url;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            final TextView handleData = (TextView) findViewById(R.id.handleData);
            final TextView firstNameData = (TextView) findViewById(R.id.firstNameData);
            final TextView lastNameData = (TextView) findViewById(R.id.lastNameData);
            final TextView organizationData = (TextView) findViewById(R.id.organizationData);
            final TextView cityData = (TextView) findViewById(R.id.cityData);
            final TextView countryData = (TextView) findViewById(R.id.countryData);
            final TextView ratingData = (TextView) findViewById(R.id.ratingData);
            final TextView maxRatingData= (TextView) findViewById(R.id.maxRatingData);
            final TextView rankData = (TextView) findViewById(R.id.rankData);
            final TextView maxRankData = (TextView) findViewById(R.id.maxRankData);

            handleData.setText(handle);
            firstNameData.setText(firstName);
            lastNameData.setText(lastName);
            organizationData.setText(organization);
            cityData.setText(city);
            countryData.setText(country);
            ratingData.setText(rating);
            maxRatingData.setText(maxRating);
            rankData.setText(rank);
            maxRankData.setText(maxRank);

            if(rank.equals("newbie")){
                rankData.setTextColor(Color.parseColor("#808080"));
            }
            else if(rank.equals("pupil")){
                rankData.setTextColor(Color.parseColor("#006400"));
            }
            else if(rank.equals("specialist")){
                rankData.setTextColor(Color.parseColor("#00FFFF"));
            }
            else if(rank.equals("expert")){
                rankData.setTextColor(Color.parseColor("#0000FF"));
            }
            else if(rank.equals("candidate master")){
                rankData.setTextColor(Color.parseColor("#800080"));
            }
            else if(rank.equals("master")){
                rankData.setTextColor(Color.parseColor("#FFA500"));
            }
            else{
                rankData.setTextColor(Color.parseColor("#FF0000"));
            }

            if(maxRank.equals("newbie")){
                maxRankData.setTextColor(Color.parseColor("#808080"));
            }
            else if(maxRank.equals("pupil")){
                maxRankData.setTextColor(Color.parseColor("#006400"));
            }
            else if(maxRank.equals("specialist")){
                maxRankData.setTextColor(Color.parseColor("#00FFFF"));
            }
            else if(maxRank.equals("expert")){
                maxRankData.setTextColor(Color.parseColor("#0000FF"));
            }
            else if(maxRank.equals("candidate master")){
                maxRankData.setTextColor(Color.parseColor("#800080"));
            }
            else if(maxRank.equals("master")){
                maxRankData.setTextColor(Color.parseColor("#FFA500"));
            }
            else{
                maxRankData.setTextColor(Color.parseColor("#FF0000"));
            }

            Log.d("try","ok");
            new GetImage().execute(result);
        }
    }

    private class GetImage extends  AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            return sh.downloadImage(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            UserImage.setImageBitmap(result);
        }
    }

}