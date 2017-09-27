package me.lam.maidong.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;


import me.lam.maidong.R;
import me.lam.maidong.adapter.TieziItemAdapter;

public class TieziFragment extends Fragment  {

  //  private PullToRefreshListView tiezilistview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tiezi, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // tiezilistview = (PullToRefreshListView) view.findViewById(R.id.tiezilistview);
     //   tiezilistview.setAdapter(new TieziItemAdapter(getActivity()));


    }

}
