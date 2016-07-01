package com.mphasis.toantag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ListView;

import com.google.gson.Gson;

public class ProductListActtivity extends AppCompatActivity {

    private ListView listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_acttivity);
AdapterProductList productAdapter;
        listData= (ListView) findViewById(R.id.listView);
        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("productData")!= null)
        {

            Gson gson = new Gson();
            ProductBean[] products= gson.fromJson(bundle.getString("productData"),ProductBean[].class);
            productAdapter = new AdapterProductList(ProductListActtivity.this,products);
            listData.setAdapter(productAdapter);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:

                moveTaskToBack(true);

                return true;
        }
        return false;
    }
}
