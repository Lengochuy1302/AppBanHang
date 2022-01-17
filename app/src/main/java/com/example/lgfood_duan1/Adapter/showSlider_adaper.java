package com.example.lgfood_duan1.Adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lgfood_duan1.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class showSlider_adaper extends SliderViewAdapter<showSlider_adaper.Holder> {

    int[] images;

    public showSlider_adaper(int[] images){
        this.images = images;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_slider_trangchu,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {

        viewHolder.imageView.setImageResource(images[position]);

    }

    @Override
    public int getCount() {

        return images.length;
    }

    public class Holder extends SliderViewAdapter.ViewHolder{

        ImageView imageView;

        public Holder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.image_view);
        }


    }

}
