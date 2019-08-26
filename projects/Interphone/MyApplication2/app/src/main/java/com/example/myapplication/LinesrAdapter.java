package com.example.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LinesrAdapter extends RecyclerView.Adapter<LinesrAdapter.LinearViewHolder>{
    private Context context;
    private List<String>list;

   public LinesrAdapter(Context context, List<String> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public LinesrAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        initId();
        return new LinearViewHolder( LayoutInflater.from( context ).inflate( R.layout.layout_linear_item,viewGroup,false) );
    }

    @Override
    public void onBindViewHolder(@NonNull LinesrAdapter.LinearViewHolder viewHolder, int i) {
     viewHolder.textView.setText(list.get( i ));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class LinearViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public LinearViewHolder(@NonNull View itemView) {
            super( itemView );
            textView=(TextView)itemView.findViewById( R.id.tv_context );
        }
    }
//    private void initId(){
//        list= new ArrayList<>( );
//    }
}
