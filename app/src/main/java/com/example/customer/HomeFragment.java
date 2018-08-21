package com.example.customer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {



    FloatingActionButton fab;
    RecyclerView recyclerView;
    ProgressBar progressBar ;
    List<MongoData> list;
    String pr,iss,ds,cn,ca,cp,as;
    String l;
    EditText product_name,Issue,Description_ed,Customer_name,Customer_Phno,Customer_addr;
    ApiInterface apiCall;
    Call<List<MongoData>> datacall;
    TextView nocon,nodata;
   CoordinatorLayout mylayout;


    public HomeFragment() {// Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {

        final View v =inflater.inflate(R.layout.fragment_fragment_home, container, false);
        mylayout=v.findViewById(R.id.layout);
        recyclerView =v.findViewById(R.id.homerv);
        recyclerView.setHasFixedSize(true );
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        nocon=v.findViewById(R.id.nocon);
        nodata=v.findViewById(R.id.nodata);
        progressBar=new ProgressBar(getContext());
        //progress bar
        progressBar=v.findViewById(R.id.progressBar);
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

        //swiperefresh
        final SharedPreferences sp = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        final String p = sp.getString("username","error");
        final SwipeRefreshLayout refreshLayout=v.findViewById(R.id.swipid);


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(list.isEmpty()){
                    nodata.setVisibility(View.VISIBLE);
                }
                if (list != null) {
                    nodata.setVisibility(View.INVISIBLE);
                    String q = "{\"customerName\":\""+ p +"\"}";
                    apiCall = ApiService.getService().create(ApiInterface.class);
                    datacall = apiCall.getData(q);
                    datacall.enqueue(new Callback<List<MongoData>>() {
                        @Override
                        public void onResponse(Call<List<MongoData>> call, Response<List<MongoData>> response) {
                            list = response.body();
                            if(list.isEmpty()){
                                nodata.setVisibility(View.VISIBLE);
                            }
                            recyclerView.setAdapter(new DataAdapter(list, R.layout.view_layout, v.getContext()));


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

                            snackbar.show();                        }


                    });

                    refreshLayout.setRefreshing(false);
                }
            }
        });


        //Retrofit connection
        apiCall = ApiService.getService().create(ApiInterface.class);

        String q = "{\"customerName\":\""+p+"\"}";
        datacall = apiCall.getData(q);
        datacall.enqueue(new Callback<List<MongoData>>() {
            @Override
            public void onResponse(Call<List<MongoData>> call, Response<List<MongoData>> response) {
                list = response.body();
                if (list.isEmpty()){
                    nodata.setVisibility(View.VISIBLE);
                }
                if (list != null) {

                    progressBar.setVisibility(View.GONE);
                }

                recyclerView.setAdapter(new DataAdapter(list, R.layout.view_layout, v.getContext()));

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



        //fab
        fab = v.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.my_dialog_box);
                dialog.setTitle("Add");
                dialog.setCancelable(true);
                //window

                String name=sp.getString("username","error");
                Customer_name=dialog.findViewById(R.id.customername_editbox);
                Customer_name.setText(name);

                Button ok, cancel;
                ok = dialog.findViewById(R.id.ok_id);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                        product_name = dialog.findViewById(R.id.product_editbox);
                        Issue = dialog.findViewById(R.id.issue_editbox);
                        Description_ed = dialog.findViewById(R.id.description_editbox);
                        Customer_Phno = dialog.findViewById(R.id.customerphone_editbox);
                        Customer_addr = dialog.findViewById(R.id.customeradd_editbox);

                        pr = product_name.getText().toString();
                        iss = Issue.getText().toString();
                        ds = Description_ed.getText().toString();
                        cn = Customer_name.getText().toString();
                        cp = Customer_Phno.getText().toString();
                        ca = Customer_addr.getText().toString();
                        if (pr.isEmpty() ||  cn.isEmpty() || ca.isEmpty() || iss.isEmpty() || cp.length() != 10) {
                            Snackbar snackbar= Snackbar.make(mylayout,"Please Fill All The Fields",Snackbar.LENGTH_SHORT);
                            View mView = snackbar.getView();

                            TextView mTextView =  mView.findViewById(android.support.design.R.id.snackbar_text);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                                mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            else
                                mTextView.setGravity(Gravity.CENTER_HORIZONTAL);

                            snackbar.show();
                        } else {
                            //put operation
                            JSONObject j = new JSONObject();
                            try {
                                j.put("productname", pr);
                                j.put("issue", iss);
                                j.put("description", ds);
                                j.put("customerName", cn);
                                j.put("customerPhno", cp);
                                j.put("address", ca);
                                ApiInterfacePut apiPut = ApiService.getService().create(ApiInterfacePut.class);
                                System.out.println("Hello" + j.toString());
                                Call<ResponseBody> body = apiPut.savePost(j.toString());
                                body.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        Snackbar snackbar= Snackbar.make(mylayout,"Issue Noted Successfully",Snackbar.LENGTH_SHORT);
                                        View mView = snackbar.getView();

                                        TextView mTextView =  mView.findViewById(android.support.design.R.id.snackbar_text);

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                                            mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                        else
                                            mTextView.setGravity(Gravity.CENTER_HORIZONTAL);

                                        snackbar.show();
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }
                    }
                });


                cancel = dialog.findViewById(R.id.cancel_id);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        return v;

    }

}
