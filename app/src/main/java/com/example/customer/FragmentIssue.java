package com.example.customer;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentIssue extends Fragment {
        ImageButton call;
    RecyclerView recyclerView;
    ProgressBar progressBar ;
    TextView nocon,nodata;
    CoordinatorLayout mylayout;
    List<MongoData> list;
    String pr,iss,ds,cn,cp,ca;
    EditText product_name,Issue,Description_ed,Customer_name,Customer_Phno,Customer_addr;
    ApiInterface apiCall;
    Call<List<MongoData>> datacall;


    public FragmentIssue() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_issue, container, false);

        recyclerView =v.findViewById(R.id.recycle_id1);
        recyclerView.setHasFixedSize(true );
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        nocon=v.findViewById(R.id.nocon2);
        nodata=v.findViewById(R.id.nodata2);
        nodata.setVisibility(View.INVISIBLE);
        mylayout=v.findViewById(R.id.homefrag);
        progressBar=new ProgressBar(getContext());
        //progress bar
        progressBar=v.findViewById(R.id.progresss);
        progressBar.setVisibility(View.VISIBLE);
        ConnectivityManager cm =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected){
            progressBar.setVisibility(View.GONE);
            nocon.setVisibility(View.VISIBLE);
        }
        SharedPreferences sp = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        final String p = sp.getString("username","error");

        //swiperefresh
        final  String q = "{\"customerName\":\""+p+"\"}";
        final SwipeRefreshLayout refreshLayout=v.findViewById(R.id.swipid);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(list.isEmpty()){
                    nodata.setVisibility(View.VISIBLE);
                }
                if (list != null) {

        nodata.setVisibility(View.INVISIBLE);
        apiCall = ApiService.getService().create(ApiInterface.class);
                    String q = "{\"customerName\":\""+ p +"\"}";
                    datacall = apiCall.getData(q);

                    datacall.enqueue(new Callback<List<MongoData>>() {
                        @Override
                        public void onResponse(Call<List<MongoData>> call, Response<List<MongoData>> response) {
                            list = response.body();
                            if(list.isEmpty()){
                                nodata.setVisibility(View.VISIBLE);
                            }
                            recyclerView.setAdapter(new DataAdapter(list, R.layout.view_layout, getContext()));


                        }

                        @Override
                        public void onFailure(Call<List<MongoData>> call, Throwable t) {
                            Snackbar snackbar= Snackbar.make(mylayout,"Check Your Connection",Snackbar.LENGTH_SHORT);
                            View mView = snackbar.getView();

                            TextView mTextView =  mView.findViewById(android.support.design.R.id.snackbar_text);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                                mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            else
                                mTextView.setGravity(Gravity.CENTER_HORIZONTAL);

                            snackbar.show();
                        }


                    });

                    refreshLayout.setRefreshing(false);
                }
            }
        });


        //Retrofit connection
        
        apiCall = ApiService.getService().create(ApiInterface.class);
        datacall = apiCall.getData(q);
        datacall.enqueue(new Callback<List<MongoData>>() {
            @Override
            public void onResponse(Call<List<MongoData>> call, Response<List<MongoData>> response) {
                list = response.body();

                if(list.isEmpty()){
                    nodata.setVisibility(View.VISIBLE);
                }
                if (list != null) {
                    progressBar.setVisibility(View.GONE);
                }
                //   Toast.makeText(getContext(),"hi"+list.get(0).getCustomername(),Toast.LENGTH_LONG).show();

                recyclerView.setAdapter(new DataAdapter(list, R.layout.view_layout, getContext()));

            }

            @Override
            public void onFailure(Call<List<MongoData>> call, Throwable t) {
                Snackbar snackbar= Snackbar.make(mylayout,"Check Your Connection",Snackbar.LENGTH_SHORT);
                View mView = snackbar.getView();

                TextView mTextView =  mView.findViewById(android.support.design.R.id.snackbar_text);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                    mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                else
                    mTextView.setGravity(Gravity.CENTER_HORIZONTAL);

                snackbar.show();            }
        });
        return v;
    }

}
