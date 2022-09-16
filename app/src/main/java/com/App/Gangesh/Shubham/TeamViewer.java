package com.App.Gangesh.Shubham;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TeamViewer extends AppCompatActivity {
    private static final String TAG = "TeamFragment";
    int batno,bolno,allno,batsel,bolsel,wktsel,allsel,money,total,foreign = 0,k=0;
    float price=0;
    Long hasSubmmited,cont;
    DatabaseReference dref,ddref,cref;
    TextView tmMotto,tmName,point,rank;
    ArrayList<Players> bats,bowls,alls,wkts,plys;
    ListView batL,bolL,allL,wktL;
    String userId,teamName,teamMotto,t1,t2,mid;
    Button edit,stats,popularPlayers,submit; ValueEventListener mListener;
    ProgressDialog dialog;
    public ProgressDialog mProgressDialog,progresdialoglistview,progressDialog;
    ProgressBar progressBar;

    public class background extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }

        @Override
        protected Void doInBackground(Void... voids) {


               /* ddref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot data) {
                        DataSnapshot dataSnapshot = data.child("MATCHES").child(mid);
                       // DataSnapshot dataSnapshots=data.child("PLAYERS");
                        dataSnapshot.getRef().child("contestants").setValue((Long)dataSnapshot.child("contestants").getValue()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                hideProgressDialog();
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Write failed
                                        // ...
                                    }
                                });;

                        //hasSubmmited=Long.valueOf(1);
                        // mProgressDialog.dismiss();
                       // ddref.child("USERS").child(userId).child("HasSubmitted").setValue(hasSubmmited);
                        new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                hideProgressDialog();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        };

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }


                });*/

            //}
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            showProgressDialog();

        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamviewer);
        total = 0;
        foreign = 0;
        userId=getIntent().getStringExtra("userId");
        t1=getIntent().getStringExtra("Team1");
        t2=getIntent().getStringExtra("Team2");
        mid=getIntent().getStringExtra("mid");
        Log.i("mid",mid);
        //mid="0";

        //showProgressDialog();
        bats = new ArrayList<>();
        bowls = new ArrayList<>();
        wkts = new ArrayList<>();
        alls = new ArrayList<>();
        plys= new ArrayList<>();
        batL = (ListView) findViewById(R.id.batList1);
        bolL = (ListView)findViewById(R.id.bowlList1);
        allL = (ListView)findViewById(R.id.allList1);
        wktL = (ListView)findViewById(R.id.wktList1);
        popularPlayers=(Button)findViewById(R.id.popularPlayers);
        submit=(Button)findViewById(R.id.button4);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
       /* dref = FirebaseDatabase.getInstance().getReference();
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {

                cont=(Long) data.child("MATCHES").child(mid).child("contestants").getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                Log.i("Loop","Loop");
                if(hasSubmmited==0){

                    ddref= FirebaseDatabase.getInstance().getReference();
                    hasSubmmited=Long.valueOf(1);
                    ddref.child("USERS").child(userId).child("HasSubmitted").setValue(hasSubmmited);
                    for(Players player:plys){
                        final int pid=player.getId();
                        ddref.child("PLAYERS").child(Integer.toString(pid)).child("count").setValue(player.getCount()+1);
                    }
                    Log.i("Loop1","Loop1");
                    ddref.child("MATCHES").child(mid).child("contestants").setValue(cont+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            hideProgressDialog();
                            Log.i("Loop2","Loop2");
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Write failed
                                    // ...
                                }
                            });

                }
                //if(hasSubmmited==0)
                //new background().execute();
                Log.i("Loop3","Loop3");
                Toast.makeText(TeamViewer.this, "Team Submission Successful",
                        Toast.LENGTH_SHORT).show();




            }
        });

        popularPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(TeamViewer.this,PopularPlayers.class);
                startIntent.putExtra("userId",userId);
                startIntent.putExtra("Team1",t1);
                startIntent.putExtra("Team2",t2);
                startIntent.putExtra("mid",mid);
                startActivity(startIntent);
            }
        });

        edit = (Button)findViewById(R.id.editTeam);

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //signIn.setBackgroundColor(Color.GRAY);
                Intent startIntent = new Intent(TeamViewer.this,Fixture.class);
                startIntent.putExtra("userId",userId);
                startIntent.putExtra("Team1",t1);
                startIntent.putExtra("Team2",t2);

                startActivity(startIntent);
            }
        });

        getData();
    }

    void getData(){
        Log.i("loop","load");
        dref = FirebaseDatabase.getInstance().getReference();
        dref.child("USERS").child(userId).child("Score").setValue(0);
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot data) {
                Log.i("DAta","data");
                cont=(Long) data.child("MATCHES").child(mid).child("contestants").getValue();
                DataSnapshot dataSnapshot = data.child("USERS").child(userId);
                hasSubmmited= (Long) dataSnapshot.child("HasSubmitted").getValue();
                batno = Integer.parseInt(dataSnapshot.child("Batsmen").getValue().toString());
                bolno  = Integer.parseInt(dataSnapshot.child("Bowler").getValue().toString());
                allno = Integer.parseInt(dataSnapshot.child("Allrounder").getValue().toString());

                int teamScore = 0;
                for(int i=0;i<batno;i++){

                    Players player = new Players();
                    DataSnapshot ds = dataSnapshot.child("Bat").child(Integer.toString(i));
                    //Log.d(TAG,ds.getValue().toString());
                    String pid = ds.child("PID").getValue().toString();
                    player.setTotScore(((Long) ds.child("Score").getValue()).intValue());
                    teamScore += player.getTotScore();
                    ds = data.child("PLAYERS").child(pid);
                    player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                    player.setCountry((String)ds.child("Country").getValue());
                    player.setName((String)ds.child("Name").getValue());
                    player.setRole((String)ds.child("Role").getValue());
                    player.setTeam((String)ds.child("Team").getValue());
                    player.setUrl((String)ds.child("ImageURL").getValue());
                    player.setPrice((Float.parseFloat(String.valueOf( ds.child("Price").getValue()))));
                    player.setCount(((Long) ds.child("count").getValue()));
                    player.setId(Integer.parseInt(pid));

                    bats.add(player);
                    plys.add(player);

                }


                for(int i=0;i<bolno;i++){

                    DataSnapshot ds = dataSnapshot.child("Bowl").child(Integer.toString(i));
                    Players player = new Players();
                    String pid = ds.child("PID").getValue().toString();
                    player.setTotScore(((Long) ds.child("Score").getValue()).intValue());
                    teamScore += player.getTotScore();
                    ds = data.child("PLAYERS").child(pid);

                    player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                    player.setCountry((String)ds.child("Country").getValue());
                    player.setName((String)ds.child("Name").getValue());
                    player.setRole((String)ds.child("Role").getValue());
                    player.setTeam((String)ds.child("Team").getValue());
                    player.setUrl((String)ds.child("ImageURL").getValue());
                    player.setPrice((Float.parseFloat(String.valueOf( ds.child("Price").getValue()))));
                    player.setCount(((Long) ds.child("count").getValue()));
                    player.setId(Integer.parseInt(pid));

                    bowls.add(player);
                    plys.add(player);

                }

                for(int i=0;i<1;i++){
                    DataSnapshot ds = dataSnapshot.child("Wkt").child(Integer.toString(i));
                    Players player = new Players();
                    String pid = ds.child("PID").getValue().toString();
                    player.setTotScore(((Long) ds.child("Score").getValue()).intValue());
                    teamScore += player.getTotScore();
                    ds = data.child("PLAYERS").child(pid);
                    player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                    player.setCountry((String)ds.child("Country").getValue());
                    player.setName((String)ds.child("Name").getValue());
                    player.setRole((String)ds.child("Role").getValue());
                    player.setTeam((String)ds.child("Team").getValue());
                    player.setUrl((String)ds.child("ImageURL").getValue());
                    player.setPrice((Float.parseFloat(String.valueOf( ds.child("Price").getValue()))));
                    player.setCount(((Long) ds.child("count").getValue()));
                    player.setId(Integer.parseInt(pid));
                    //if(!player.getCountry().startsWith("Bangla")) foreign++;
                    // price += player.getPrice();
                    wkts.add(player);
                    plys.add(player);

                }

                for(int i=0;i<allno;i++){
                    DataSnapshot ds = dataSnapshot.child("All").child(Integer.toString(i));
                    Players player = new Players();
                    String pid = ds.child("PID").getValue().toString();
                    player.setTotScore(((Long) ds.child("Score").getValue()).intValue());
                    teamScore += player.getTotScore();
                    ds = data.child("PLAYERS").child(pid);
                    player.setAge(Integer.parseInt((String) ds.child("Age").getValue()));
                    player.setCountry((String)ds.child("Country").getValue());
                    player.setName((String)ds.child("Name").getValue());
                    player.setRole((String)ds.child("Role").getValue());
                    player.setTeam((String)ds.child("Team").getValue());
                    player.setUrl((String)ds.child("ImageURL").getValue());
                    player.setPrice((Float.parseFloat(String.valueOf( ds.child("Price").getValue()))));
                    player.setCount(((Long) ds.child("count").getValue()));
                    player.setId(Integer.parseInt(pid));
                    alls.add(player);
                    plys.add(player);

                }






                generateLists();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void generateLists(){
        Log.i("loop","load player");
        CustomAdapter batAdapter = new CustomAdapter(bats,batno);
        CustomAdapter bolAdapter = new CustomAdapter(bowls,bolno);
        CustomAdapter wktAdapter = new CustomAdapter(wkts,1);
        CustomAdapter allAdapter = new CustomAdapter(alls,allno);

        batL.setAdapter(batAdapter);
        bolL.setAdapter(bolAdapter);
        wktL.setAdapter(wktAdapter);
        allL.setAdapter(allAdapter);

        Log.d(TAG,"Ekhane");
        Utility.setListViewHeightBasedOnChildren(batL);
        Utility.setListViewHeightBasedOnChildren(wktL);
        Utility.setListViewHeightBasedOnChildren(allL);
        Utility.setListViewHeightBasedOnChildren(bolL);


    }


    class CustomAdapter extends BaseAdapter {

        ArrayList<Players> pplayers;
        int count;
        CustomAdapter(ArrayList<Players> players, int no){
            count = no;
            pplayers = players;
        }
        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_layout,null);
            ImageView image = view.findViewById(R.id.listIm);
            TextView name = view.findViewById(R.id.pName);
            TextView roll= view.findViewById(R.id.pRole);
            TextView price = view.findViewById(R.id.pPrice);
            TextView team = view.findViewById(R.id.pTeam);
            TextView prices = view.findViewById(R.id.price);

            String url = pplayers.get(i).getUrl();

            loadimage(url,image);
            price.setText("Price");
            name.setText(pplayers.get(i).getName());
            roll.setText(pplayers.get(i).getRole());
            team.setText(pplayers.get(i).getTeam());
            prices.setText(Float.toString(pplayers.get(i).getPrice()));

            return view;
        }
    }



    public void showProgressDialog() {

        if (mProgressDialog == null) {

            mProgressDialog = new ProgressDialog(this);
            // mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

            mProgressDialog.setMessage("Please wait this dialog box will close automatically");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);

            mProgressDialog.setIndeterminate(true);

        }


    }
    public void hideProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {

            mProgressDialog.dismiss();

        }

    }void loadimage(String url,ImageView imageView){
        Picasso.with(this).load(url).placeholder(R.drawable.anon).error(R.drawable.anon)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }


    @Override
    public void onPause() {
        if (mListener != null && dref!=null) {
            dref.removeEventListener(mListener);
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (mListener != null && dref!=null) {
            dref.removeEventListener(mListener);
        }
        super.onStop();
    }

}
