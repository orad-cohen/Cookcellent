package com.project.cookcellent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(String item);
    }


    private OnItemClickListener listener;
    private List<String> dataSet;
    private List<String> toList = new ArrayList<>();
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final Button remove;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.ing);
            remove = (Button) view.findViewById(R.id.remove);
        }

        public TextView getTextView() {
            return textView;
        }
        public Button getButton() {
            return remove;
        }
        public void bind(final String item, final OnItemClickListener listener) {
            textView.setText(item);
            itemView.findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
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
    public IngredientAdapter(ArrayList<String> dataSet, OnItemClickListener listen) {
        this.listener = listen;
        this.toList = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.selected, viewGroup, false);

        return new ViewHolder(view);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.getTextView().setText(toList.get(position));
        viewHolder.bind(toList.get(position),listener);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return toList.size();
    }

    public void remove(String text){
        toList.remove(text);
        notifyDataSetChanged();
    }
    public List<String> getSelection(){
        return toList;
    }
    public void add(String text){
        toList.add(text);

        notifyDataSetChanged();
    }

}