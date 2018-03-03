package com.branchlocator.branchlocator.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.branchlocator.branchlocator.Activity.MainDrawerActivity;
import com.branchlocator.branchlocator.Adapter.BranchListAdapter;
import com.branchlocator.branchlocator.HttpParser.HttpHandler;
import com.branchlocator.branchlocator.Pojo.Dataset;
import com.branchlocator.branchlocator.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PRINCE on 17/02/2018.
 */

public class BranchListFragment extends Fragment
{
    View rootView;
    RecyclerView rv_branchlist;
    ProgressDialog pDialog;
    List<Dataset> data;
    BranchListAdapter mAdapter;
    String Bankname;
    Bundle bundle;


    public static BranchListFragment newInstance() {
        BranchListFragment fragment = new BranchListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_branchlist, container, false);

        MainDrawerActivity mainActivity = (MainDrawerActivity) getActivity();
        mainActivity.iv_toolbar_back.setVisibility(View.VISIBLE);
        mainActivity.tv_toolbar_backtxt.setText("Bank List");
        mainActivity.tv_toolbar_backtxt.setVisibility(View.VISIBLE);
        mainActivity.tv_toolbar_title.setText("Branch List");

        rv_branchlist = (RecyclerView) rootView.findViewById(R.id.rv_branchlist);
        data = new ArrayList<>();

        //get the values out by key
        //the bundle we added to the intent

        Bankname = getArguments().getString("bank");

        if (isNetworkAvailable(getContext())) {
            new GetData().execute();
        } else {
            Toast.makeText(getContext(), "Please Check your internet connection!", Toast.LENGTH_SHORT).show();
        }


        return  rootView;
    }


    public class GetData extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait.....");
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {


            //domain_url = getResources().getString(R.string.domain_url);
            String urlreg = "https://api.techm.co.in/api/listbranches/"+Bankname;
            data.clear();

            // Creating new JSON Parser
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlreg);

            if (jsonStr != null)
            {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    if (jsonObj.getString("status").equalsIgnoreCase("success"))
                    {
                        // Getting JSON Array node
                        JSONArray contacts = jsonObj.getJSONArray("data");

                        String branch;
                        // looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++)
                        {
                            Dataset linkData = new Dataset();
                            branch = contacts.getString(i);
                            linkData.setBankname(Bankname);
                            linkData.setBranchname(branch);
                            data.add(linkData);
                        }
                    }
                } catch (final JSONException e) {
                    Log.e("Message :", "Json parsing error: " + e.getMessage());
                    Toast.makeText(getActivity(), "Something is wrong.", Toast.LENGTH_SHORT).show();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (data.equals("null") || data.size() <= 0)
            {
                //if data is available, don't show the empty text
                rv_branchlist.setVisibility(View.GONE);
            }
            else
            {

                mAdapter = new BranchListAdapter(getActivity(), data);
                rv_branchlist.setAdapter(mAdapter);
                rv_branchlist.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAdapter.notifyDataSetChanged();
            }
            pDialog.dismiss();
        }
    }


    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
