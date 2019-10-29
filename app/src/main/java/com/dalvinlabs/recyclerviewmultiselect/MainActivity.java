package com.dalvinlabs.recyclerviewmultiselect;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import com.dalvinlabs.recyclerviewmultiselect.databinding.ActivityMainBinding;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private SelectionTracker<Long> selectionTracker;
  private List<Product> mProduct;
  private  Adapter adapter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    mProduct =Product.getProducts();
    adapter = new Adapter(mProduct);
    binding.recyclerView.setAdapter(adapter);
    selectionTracker = new SelectionTracker.Builder<>("my_selection",
        binding.recyclerView,
        new Adapter.KeyProvider(binding.recyclerView.getAdapter()),
        new Adapter.DetailsLookup(binding.recyclerView),
        StorageStrategy.createLongStorage())
        .withSelectionPredicate(new Adapter.Predicate())
        .build();
    adapter.setSelectionTracker(selectionTracker);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_selections) {
      Toast.makeText(this, selectionTracker.getSelection().toString(), Toast.LENGTH_LONG).show();
      Iterator iterator = selectionTracker.getSelection().iterator();
     /* while(iterator.hasNext()){
       Long index = (Long)iterator.next();*/
        //mProduct.remove(index.intValue());
        mProduct.removeIf(
                x -> selectionTracker.getSelection().contains(x));
        adapter.notifyDataSetChanged();
        //}
      selectionTracker.clearSelection();

    }
    return super.onOptionsItemSelected(item);
  }
}
