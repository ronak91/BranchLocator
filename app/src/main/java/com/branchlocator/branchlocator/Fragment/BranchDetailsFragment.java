package com.branchlocator.branchlocator.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.branchlocator.branchlocator.Activity.MainDrawerActivity;
import com.branchlocator.branchlocator.HttpParser.HttpHandler;
import com.branchlocator.branchlocator.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PRINCE on 17/02/2018.
 */

public class BranchDetailsFragment extends Fragment
{
    View rootView;
    ProgressDialog pDialog;
    String Bankname, Branchname;
    String bank, branch, address, ifsc, micr;
    TextView txt_bank_name, txt_bank_branch, txt_bank_address, txt_bank_ifsc, txt_bank_micr;

    public static BranchDetailsFragment newInstance() {
        BranchDetailsFragment fragment = new BranchDetailsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_branchdetails, container, false);

        MainDrawerActivity mainActivity = (MainDrawerActivity) getActivity();
        mainActivity.iv_toolbar_back.setVisibility(View.VISIBLE);
        mainActivity.tv_toolbar_backtxt.setText("Branch List");
        mainActivity.tv_toolbar_backtxt.setVisibility(View.VISIBLE);
        mainActivity.tv_toolbar_title.setText("Branch Detail");

        Bankname = getArguments().getString("bank");
        Branchname = getArguments().getString("branch");

        txt_bank_name = (TextView) rootView.findViewById(R.id.txt_bank_name);
        txt_bank_branch= (TextView) rootView.findViewById(R.id.txt_bank_branch);
        txt_bank_address = (TextView) rootView.findViewById(R.id.txt_bank_address);
        txt_bank_ifsc = (TextView) rootView.findViewById(R.id.txt_bank_ifsc);
        txt_bank_micr = (TextView) rootView.findViewById(R.id.txt_bank_micr);

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
            String urlreg = "https://api.techm.co.in/api/getbank/"+ Bankname +"/"+ Branchname;


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
                        // Getting JSON Object node
                        JSONObject alldata = jsonObj.getJSONObject("data");

                        bank = alldata.getString("BANK");
                        branch = alldata.getString("BRANCH");
                        address = alldata.getString("ADDRESS");
                        ifsc = alldata.getString("IFSC");
                        micr = alldata.getString("MICRCODE");


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

            txt_bank_name.setText(bank);
            txt_bank_branch.setText(branch);
            txt_bank_address.setText(address);
            txt_bank_ifsc.setText(ifsc);
            txt_bank_micr.setText(micr);

             pDialog.dismiss();
        }
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
