package com.example.lgfood_duan1.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class paginationScrollListenerKhoHang extends RecyclerView.OnScrollListener {

    private GridLayoutManager linearLayoutManager;

    public paginationScrollListenerKhoHang(GridLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount=linearLayoutManager.getChildCount();
        int totalItemCount=linearLayoutManager.getItemCount();
        int firstVisibleItemPosition=linearLayoutManager.findFirstVisibleItemPosition();


        if (isLoading() || isLastPage()){
            return;
        }
        if (firstVisibleItemPosition>=0 && (visibleItemCount + firstVisibleItemPosition) >=totalItemCount){
            loadMoreItem();
        }
    }

    public abstract void loadMoreItem();
    public  abstract boolean isLoading();
    public  abstract boolean isLastPage();

}
