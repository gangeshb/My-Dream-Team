package com.App.Gangesh.Shubham;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OurList extends AppCompatActivity {

    ArrayList<String> teams;
    ArrayList<Float> per;
    DatabaseReference dref;
    int noOfTeams;
    String t1,t2,mid,userId;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.our_list);
        t1=getIntent().getStringExtra("Team1");
        t2=getIntent().getStringExtra("Team2");
        mid=getIntent().getStringExtra("mid");
        userId=getIntent().getStringExtra("userId");
        listView=(ListView)findViewById(R.id.teamListView);

        teams = new ArrayList<String>();
        per=new ArrayList<Float>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(OurList.this,ViewTeam.class);
                intent.putExtra("Team1",t1);
                intent.putExtra("Team2",t2);
                intent.putExtra("mid",mid);
                intent.putExtra("teamId",String.valueOf(i+1));
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });


        getdata();


    }
    public void getdata(){
        dref= FirebaseDatabase.getInstance().getReference();
        dref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                noOfTeams=Integer.parseInt(dataSnapshot.child("MATCH").child(mid).child("tno").getValue().toString());
                Log.i("count",Integer.toString(noOfTeams));
                for(int i=1;i<=noOfTeams;i++){
                    teams.add("Team "+Integer.toString(i));
                    per.add(Float.parseFloat(dataSnapshot.child("MATCH").child(mid).child("Teams").child(Integer.toString(i)).child("count").getValue().toString())/Float.parseFloat(dataSnapshot.child("MATCH").child(mid).child("contestants").getValue().toString())*100);
                    Log.i("MSG",teams.get(i-1));
                }
                generateList();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void generateList(){
        OurList.CustopmAdapter custopmAdapter = new OurList.CustopmAdapter();


        listView.setAdapter(custopmAdapter);

    }
    class CustopmAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return noOfTeams;
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

            view = getLayoutInflater().inflate(R.layout.fixture_items,null);
            TextView Teams = view.findViewById(R.id.teams);
            TextView matchno= view.findViewById(R.id.matchNo);
            TextView match= view.findViewById(R.id.match);
            match.setText("%age");


            Teams.setText(teams.get(i));
            matchno.setText(String.format("%.2f",(per.get(i))));


            return view;
        }
    }
}
