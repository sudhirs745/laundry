package com.sud.laundry.presentation.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.grocery.R;

import java.util.List;

public class WordAdapter extends ArrayAdapter<String> {

    SearchClickInterface  searchClickInterface;

    public WordAdapter(Context context, List<String> words) {
        super(context, 0, words);
        searchClickInterface=(SearchClickInterface)context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.word_list_item, parent, false);
        }

        final String currentWord = getItem(position);

        TextView name = listItemView.findViewById(R.id.txtWord);
        name.setText(currentWord);

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //searchClickInterface.onItemClick(currentWord, position);
            }
        });

        listItemView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                       // searchClickInterface.onItemLongClick(currentWord,position);
                        return false;

                    }
                }
        );


        return listItemView;
    }
}
