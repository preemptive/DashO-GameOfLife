package io.kimo.examples.gameoflifeview;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int DEFAULT_GOL_VIEW_CODE = 0;
    public static final int CUSTOM_COLORS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.configureToolbar(this, false);
        setTitle(getString(R.string.title));
        configureRecyclerView();
    }

    private void configureRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SimpleRecyclerAdapter());
    }

    private class SimpleRecyclerAdapter extends RecyclerView.Adapter<SimpleRecyclerAdapter.ViewHolder> {

        private List<String> examplesOptions;

        private SimpleRecyclerAdapter() {
            configureOptions();
        }

        @Override
        public @NonNull ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            View view = layoutInflater.inflate(R.layout.item_example, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.text.setText(examplesOptions.get(i));
        }

        @Override
        public int getItemCount() {
            return examplesOptions == null ? 0 : examplesOptions.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

            private final TextView text;

            private ViewHolder(View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.text);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch (getLayoutPosition()) {
                    default:
                    case DEFAULT_GOL_VIEW_CODE:
                        startActivity(new Intent(MainActivity.this, ThroughCodeActivity.class));
                        break;
                    case CUSTOM_COLORS:
                        startActivity(new Intent(MainActivity.this, CustomParamsActivity.class));
                        break;
                }
            }
        }

        private void configureOptions() {
            examplesOptions = new ArrayList<>();
            examplesOptions.add(getString(R.string.def_view_name));
            examplesOptions.add(getString(R.string.custom_view_name));
        }
    }
}
