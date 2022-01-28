package id.ac.binus.vegetarianshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

import id.ac.binus.vegetarianshop.R;
import id.ac.binus.vegetarianshop.Vege;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private Vector<Vege> veges;
    private ProductClickListener listener;

    public ProductAdapter(Vector<Vege> veges, ProductClickListener listener){
        this.veges = veges;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ivProductImage.setImageResource(veges.get(position).getImageId());
        String price = "Rp. " + veges.get(position).getPrice();
        holder.tvProductPrice.setText(price);
        holder.tvProductName.setText(veges.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return veges.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvProductName, tvProductPrice;
        CardView cvContainer;

        public MyViewHolder(@NonNull View itemView, ProductClickListener listener) {
            super(itemView);
            cvContainer = itemView.findViewById(R.id.cvProductContainer);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);

            cvContainer.setOnClickListener(x -> {
                listener.productClicked(getAdapterPosition());
            });
        }
    }

    public interface ProductClickListener{
        void productClicked(int position);
    }
}
