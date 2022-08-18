package com.project.cookcellent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IngredientHistoryAdapter extends RecyclerView.Adapter<IngredientHistoryAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(ArrayList<String> item);
    }


    private IngredientHistoryAdapter.OnItemClickListener listener;
    private ArrayList<ArrayList<String>> dataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;


        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.searchhistorybar);

        }

        public TextView getTextView() {
            return textView;
        }

        public void bind(final ArrayList<String> item, final IngredientHistoryAdapter.OnItemClickListener listener) {
            textView.setText(item.get(item.size()-1));
            itemView.findViewById(R.id.searchhistorybar).setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public IngredientHistoryAdapter(ArrayList<ArrayList<String>> dataSet, IngredientHistoryAdapter.OnItemClickListener listen) {
        this.listener = listen;
        this.dataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public IngredientHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.searchhistorybar, viewGroup, false);

        return new IngredientHistoryAdapter.ViewHolder(view);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(IngredientHistoryAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element


        viewHolder.bind(dataSet.get(position),listener);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
