package com.project.cookcellent;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class recipesAdapter extends RecyclerView.Adapter<recipesAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(recipeClass item);
    }


    private OnItemClickListener listener;
    private List<recipeClass> toList= new ArrayList<>();;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView recipeName;
        private final Button getRecipe;
        private final TextView rating;
        private final ImageView foodImage;

        public ViewHolder(View view) {
            super(view);

            recipeName = (TextView) view.findViewById(R.id.foodname);
            getRecipe = (Button) view.findViewById(R.id.enter);
            rating = (TextView) view.findViewById(R.id.foodrating);
            foodImage = (ImageView) view.findViewById(R.id.foodImage);

        }


        public void bind( final recipeClass item, final OnItemClickListener listener) {
            recipeName.setText(item.name);
            itemView.findViewById(R.id.enter).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
            Drawable foodPic =networkTools.getImage(item.image.get("url"));
            foodImage.setImageDrawable(foodPic);

            rating.setText(item.rating.get("ratingValue")+"/5");



        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public recipesAdapter(ArrayList<recipeClass> dataSet, OnItemClickListener listen) {

        this.listener = listen;
        this.toList = dataSet;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipebar, viewGroup, false);

        return new ViewHolder(view);
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        //viewHolder.getTextView().setText(toList.get(position));
        viewHolder.bind(toList.get(position),listener);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return toList.size();
    }


    public void add(recipeClass item){
        toList.add(item);
        notifyDataSetChanged();
    }


}