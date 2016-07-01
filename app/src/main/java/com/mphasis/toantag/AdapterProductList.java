package com.mphasis.toantag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Sourav.Bhattacharya on 6/30/2016.
 */
public class AdapterProductList extends BaseAdapter {
    private final ProductBean[] mProducts;
   // String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public AdapterProductList(Context mContext, ProductBean[]  products) {
        // TODO Auto-generated constructor stub
         mProducts=products;
        context=mContext;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mProducts.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView name;
        TextView price;
        TextView discount;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.row_product, null);
        holder.name=(TextView) rowView.findViewById(R.id.txtProductName);
        holder.price=(TextView) rowView.findViewById(R.id.txtProductPrice);
        holder.discount=(TextView) rowView.findViewById(R.id.txtProductDiscount);

        holder.name.setText(mProducts[position].getProduct_name());
        holder.price.setText(mProducts[position].getProduct_price());
        holder.discount.setText(mProducts[position].getProduct_Discount());
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+mProducts[position], Toast.LENGTH_LONG).show();
            }
        });
        return rowView;
    }
}
