package com.example.docomomyapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.docomomyapplication.R;
import com.example.docomomyapplication.activity.FirstAccountLoginActivity;

public class TutorialFragment extends Fragment{
     private static final String TUTORIAL_PAGE="tutorial";
    private View mMainView;
    private int mNum;
    public static TutorialFragment newInstance(int position){
     TutorialFragment fragment=new TutorialFragment();
        Bundle bundle = new Bundle( );
        bundle.putInt( TUTORIAL_PAGE,position);
        fragment.setArguments( bundle );
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mNum=getArguments().getInt( TUTORIAL_PAGE );
        mMainView=null;
        switch (mNum){
            case 0:
                mMainView = inflater.inflate( R.layout.activity_zhinan, container, false);
                break;
            case 1:
                mMainView = inflater.inflate(R.layout.activity_zhinan2, container, false);
                break;
            case 2:
                mMainView = inflater.inflate(R.layout.activity_zhinan3, container, false);
                setMydocomoViewAction(mMainView);
                break;
        }
        return mMainView;
    }

    private void setMydocomoViewAction(View mMainView) {
        Button button= mMainView.findViewById( R.id.tutorial_3_mydocomo_btn);
        button.setOnClickListener( new View.OnClickListener( ){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), FirstAccountLoginActivity.class);
                startActivity( intent );
                getActivity().finish();
            }
        } );

    }
}
