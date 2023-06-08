package it_school.sumdu.edu.mycatalogue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    private ArrayList<CatalogueItem> mItemList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mName;
        public TextView mAmount;
        public TextView mPrice;
        public ItemsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_image);
            mName = itemView.findViewById(R.id.item_name);
            mAmount = itemView.findViewById(R.id.item_amount);
            mPrice = itemView.findViewById(R.id.item_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public ItemsAdapter(ArrayList<CatalogueItem> itemList) {
        mItemList = itemList;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_row, parent, false);
        return new ItemsViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        CatalogueItem currentItem = mItemList.get(position);
        byte[] imageByteArray = currentItem.getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        holder.mImageView.setImageBitmap(bmp);
        holder.mName.setText(currentItem.getName());
        holder.mAmount.setText(currentItem.getAmount());
        holder.mPrice.setText(currentItem.getPrice());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
