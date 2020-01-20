package com.example.sg_finalassign_sg;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class resouce extends Fragment {

    private OnFragmentInteractionListener mListener;
    public static RequestQueue data;

    //main activity populating
    public static TextView headerText;
    public static TextView[] atricleNames = new TextView[10];
    public static ImageView[] imgs = new ImageView[10];

    //populate search feature
    public static TextView[] S_Text = new TextView[10];
    public static ImageView[] S_imgs = new ImageView[10];
    public static TextView[] titles = new TextView[10];

    //article data API
    public static article[] news = new article[10];
    public static String topic = "*";
    public static String request1 = "https://newsapi.org/v2/everything?q=";
    public static String request3 = "&apiKey=491332b3c0e8402b9371b5f1377486ef&pageSize=8";
    public static boolean searchChangedPage = false;

    //user
    public static User user = null;

    //current article
    public static article Art;
    public static boolean loggedIn = true;

    //database
    public static ArticleDB_Access db;

    //notification
    public static NotificationManagerCompat notificationManager;
    public static final String CHANN = "chann";
    public static final String CHANN2 = "chann2";
    public static boolean messaged1 = false, messaged2 = false, messaged3 = false;


    public static void getNewsData(){

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET, request1 + topic + request3, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray arr = response.getJSONArray("articles");
                            news = new article[10];

                            for (int i=0; i <= 7; i++){
                                JSONObject arr2 = arr.getJSONObject(i);

                                String titlee = arr2.getString("title"),
                                        url = arr2.getString("urlToImage"),
                                        author = arr2.getString("author"),
                                        publishedAt = arr2.getString("publishedAt"),
                                        des = arr2.getString("description"),
                                        link = arr2.getString("url"),
                                        content = arr2.getString("content");

                                JSONObject source;
                                String sorr2 = null, sorr3 = null;

                                try {
                                    source = arr2.getJSONObject("source");
                                    sorr2 = source.getString("id");
                                    sorr3 = source.getString("name");
                                }catch(Exception e){System.out.println("--------------------------------------------");System.out.println(e);System.out.println("--------------------------------------------");}

                                if (sorr2 == null || sorr2.equals("null")){ sorr2 = sorr3; }


                                news[i] = new article(titlee, author, publishedAt, des, link, url, content, sorr2);
                            }

                        } catch (JSONException e) {
                            System.out.println("--------------------------------------------");System.out.println(e);System.out.println("--------------------------------------------");
                            e.printStackTrace();


                        } }
                        }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); System.out.println("--------------------------------------------");System.out.println(error);System.out.println("--------------------------------------------");} });

        try {
            data.add(obj);
        }catch(Exception e){}
    }

    //populating main feed
    public static void populate(){
        for(int i = 0; i <= 7; i++) {
            String lin = news[i].getUrl();

            if (lin != null)
                Picasso.get().load(news[i].getUrl()).into(imgs[i]);

            if (i == 1 || i == 2) {
                if (news[i].getTitle().length() > 30) {
                    String title = news[i].getTitle().substring(0, 30) + "...";
                    atricleNames[i].setText(title);
                }
            } else {
                atricleNames[i].setText(news[i].getTitle());
            }
        }
    }

    //populating search page
    public static void populate2(){
        for(int i = 0; i <= 7; i++) {
            Picasso.get().load(news[i].getUrl()).into(S_imgs[i]);
            titles[i].setText(news[i].getTitle());

            if(news[i].getDescriptiong().length() >= 100){
                S_Text[i].setText(news[i].getDescriptiong().substring(0, 100));
            }else{
                S_Text[i].setText(news[i].getDescriptiong());
            }
            S_Text[i].append("\n\n" + news[i].getPublishDate());
        }
    }

    public static void setUpData(Context con){
        db = new ArticleDB_Access(con);
        if(db.getAmountOfPlayers() <= 1) {
            db.addPlayer(new User("Mike", "pass", "mike@rogers.com", "519-431-4523", "tech,appleeconamics,bitcoin"));
            db.addPlayer(new User("jane", "pass", "jane@rogers.com", "314-547-7753", "apple,canada,econamics,bitcoin"));
            db.addPlayer(new User("mary", "pass", "mary@rogers.com", "223-34-4363", "tech,apple,bitcoin"));
            db.addPlayer(new User("samm", "pass", "samm@rogers.com", "452-532-4432", "canada,bitcoin"));
        }
    }

    public static void notification(String message, Context con){

        notificationManager = NotificationManagerCompat.from(con);
        Notification notification = new NotificationCompat.Builder(con, CHANN)
                .setSmallIcon(R.drawable.search_icon)
                .setContentTitle("Message:")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setColor(Color.rgb(0,0,0))
                .build();
        notificationManager.notify(1, notification);
    }























        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        return textView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
