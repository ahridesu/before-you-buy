package com.example.beforeyoubuy.ui.allProducts;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.beforeyoubuy.Handlers.DataBaseHandler;
import com.example.beforeyoubuy.R;
import com.example.beforeyoubuy.models.Produto;

import java.util.ArrayList;
import java.util.List;

public class AllProductsActivity extends AppCompatActivity {

    private DataBaseHandler dbHandler;
    private ListView listView;
    private ItemAdapter itAdap;

    /**
     * Creates a View of all products
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        listView = (ListView) findViewById(R.id.list_view);
        dbHandler = DataBaseHandler.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        itAdap = new ItemAdapter(dbHandler.getFavoritos(), this);
        Log.e("Item adapter", itAdap.toString());
        listView.setAdapter(itAdap);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem menuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();

        if(id==R.id.search)
            return true;
        return super.onOptionsItemSelected(menuItem);

    }

    public class ItemAdapter extends BaseAdapter implements Filterable {

        DataBaseHandler dataBaseHandler = DataBaseHandler.getInstance();
        private List<Produto> productsList;
        private List<Produto> filteredProductList;
        private Context context;

        public ItemAdapter(List<Produto> products, Context context) {
            this.productsList = products;
            this.filteredProductList = products;
            this.context = context;
        }

        @Override
        public int getCount() {
            return filteredProductList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_items, null);

            ImageView img = view.findViewById(R.id.item_img);
            TextView name = view.findViewById(R.id.item_name);
            TextView dsc = view.findViewById(R.id.item_dsc);

            img.setImageResource(productsList.get(position).getImage());//TODO: https://prnt.sc/zzx22p
            name.setText(productsList.get(position).getName());
            dsc.setText("IT DOES NOT MATTER");
            //dsc.setText("dsc");
            return view;
        }

        @Override
        public Filter getFilter() {
            Filter f = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults fr = new FilterResults();

                    if(constraint == null || constraint.length() == 0){
                        fr.count = productsList.size();
                        fr.values = productsList;
                    }else{
                        String searchStr =  constraint.toString();
                        List<Produto> results = new ArrayList<Produto>();
                        for(Produto product: filteredProductList){
                            results.add(product);
                        }

                        fr.count = results.size();
                        fr.values = results;
                    }
                    return fr;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    filteredProductList = (List<Produto>) results;

                    notifyDataSetChanged();

                }


            };
            return f;
        }
    }
}
