package com.branchlocator.branchlocator.Adapter;

/**
 * Created by PRINCE on 18/02/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.branchlocator.branchlocator.Activity.MainDrawerActivity;
import com.branchlocator.branchlocator.Fragment.BranchDetailsFragment;
import com.branchlocator.branchlocator.Pojo.Dataset;
import com.branchlocator.branchlocator.R;

import java.util.List;

/**
 * Created by m6 on 17/08/17.
 */

public class BranchListAdapter extends RecyclerView.Adapter<BranchListAdapter.MyViewHolder>
{
    BranchDetailsFragment mFragment;
    Fragment fragment = null;
    private Context context;
    private List<Dataset> list;
    Bundle mBundle;

    public BranchListAdapter(Context context, List<Dataset> List) {
        this.context = context;
        this.list = List;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.raw_list_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BranchListAdapter.MyViewHolder holder, int position) {
        final Dataset dItem = list.get(position);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.rl_list.getLayoutParams();

         holder.bank_text.setText(dItem.getBranchname());
         Typeface semi_Font = Typeface.createFromAsset(context.getAssets(), "font/SF-UI-Display-Semibold.ttf");

         holder.bank_text.setTypeface(semi_Font);

        holder.rl_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment = new BranchDetailsFragment();
                mBundle = new Bundle();
                mBundle.putString("bank", dItem.getBankname());
                mBundle.putString("branch", dItem.getBranchname());
                mFragment.setArguments(mBundle);
                switchContent(1, mFragment);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public RelativeLayout rl_list;
        public TextView bank_text;


        public MyViewHolder(View itemView) {
            super(itemView);

            rl_list = itemView.findViewById(R.id.rl_list);
            bank_text = itemView.findViewById(R.id.bank_text);

        }
    }

    @SuppressLint("NewApi")
    public void switchContent(int id, Fragment fragment) {
        if (context == null)
            return;
        if (context instanceof MainDrawerActivity) {
            MainDrawerActivity mainActivity = (MainDrawerActivity) context;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag);
        }

    }

}



