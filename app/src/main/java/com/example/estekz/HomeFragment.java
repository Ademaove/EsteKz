package com.example.estekz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.estekz.models.AllProducts;
import com.example.estekz.models.Json;
import com.example.estekz.models.OnlyNeededData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    public HomeFragment() {
        // Required empty public constructor
        setHasOptionsMenu(false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private List<OnlyNeededData> productData = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AllProductsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Json getDataService;
    private NestedScrollView nestedScrollView;
    protected Handler handler;
    private boolean isLoading;
    private Button previous;
    private Button next;
    private int page_num=1;
    private EditText editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=(View)inflater.inflate(R.layout.fragment_home, container, false);
        getDataService = RetrofitClient.getRetrofitClient().create(Json.class);
        handler = new Handler();
        isLoading = false;
        nestedScrollView = rootView.findViewById(R.id.nestedScrollView);
        editText= rootView.findViewById(R.id.search_button);
        /* To refresh the news feed upon swiping. */
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        /* Set up the Recycler view.*/
        layoutManager = new LinearLayoutManager(getContext()); //MainActivity.this
        recyclerView = rootView.findViewById(R.id.recyclerView);
        previous= rootView.findViewById(R.id.previous_page);
        next= rootView.findViewById(R.id.next_page);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // As RecyclerView is within the NestedScrollView
        recyclerView.setNestedScrollingEnabled(false);

        onLoadingSwipeRefresh();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return rootView;
    }

    @Override
    public void onRefresh() {
        generateData();
    }

    private void onLoadingSwipeRefresh() {
        swipeRefreshLayout.post(this::generateData);
    }

    /**
     * Initially loads product information by making a GET request to the Woocommerce REST API.
     */

    public void generateData() {
        swipeRefreshLayout.setRefreshing(true);

        Call<List<AllProducts>> call;
        call = getDataService.getMore(page_num);
        call.enqueue(new Callback<List<AllProducts>>() {
            @Override
            public void onResponse(Call<List<AllProducts>> call, Response<List<AllProducts>> response) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body() != null) {
                    if (!productData.isEmpty()) {
                        productData.clear();
                    }
                    List<AllProducts> posts = response.body();
                    for (AllProducts post : posts) {
                        OnlyNeededData a=new OnlyNeededData(post.getId(),post.getName(), post.getPrice(), post.getImages().get(0).getSrc());
                        productData.add(a);
                    }

                    adapter = new AllProductsAdapter(productData, getContext()); //MainActivity.this
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),
                            2,
                            GridLayoutManager.VERTICAL,
                            false); //MainActivity.this
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    initListener();

                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getContext(), "Error. No internet connection", Toast.LENGTH_SHORT).show(); //MainActivity.this
                }
            }

            @Override
            public void onFailure(Call<List<AllProducts>> call, Throwable t) { }
        });
    }

    /**
     * Sets an OnItemClickListener to navigate to the individual single product page.
     */
    private void initListener() {
        adapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent( getActivity(), SingleProductPage.class);
            OnlyNeededData touched= productData.get(position);
            intent.putExtra("id", touched.getId());
            startActivity(intent);
        });
    }

    /**
     * Sets an OnScrollChangedListener() to load additional product.
     * Need to implement this, since I had a problem when doing this
     * Better to make it infinite scrolling
     */

    private void initLoader() {
        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);

            int diff = (view.getBottom() - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
            if (!isLoading && diff == 0) {
                isLoading = true;
                productData.add(null);
//                adapter.notifyItemInserted(articles.size() - 1);
//                adapter.notifyItemInserted(10);

                handler.postDelayed(() -> {
                    int end = productData.size() + 10;

                    Call<List<AllProducts>> call;
                    call = getDataService.getMore(page_num);

                    call.enqueue(new Callback<List<AllProducts>>() {
                        @Override
                        public void onResponse(Call<List<AllProducts>> call, Response<List<AllProducts>> response) {
                            assert response.body() != null;
                            if (response.isSuccessful() && response.body() != null) {
                                productData.remove(productData.size() - 1);
//                                adapter.notifyItemRemoved(articles.size());
                                int size= productData.size();
                                productData.clear();

                                List<AllProducts> responseProduct = response.body();
                                if (end < responseProduct.size()) {
//                                    responseArticles = responseArticles.subList(0, end);
                                }
                                List<OnlyNeededData> passa=new ArrayList<>();
                                for (AllProducts post : responseProduct) {
                                    OnlyNeededData a=new OnlyNeededData(post.getId(), post.getName(), post.getPrice(), post.getImages().get(0).getSrc());
                                    passa.add(a);
//
                                }
                                productData.addAll(passa);
                                adapter.notifyDataSetChanged();
//                                adapter.notifyItemRangeRemoved(0, size);
//                                adapter.notifyItemRangeInserted(0, articles.size());
                                isLoading = false;
                            } else {
                                Toast.makeText(getContext(), "Error. No Internet!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<AllProducts>> call, Throwable t) { }
                    });
                }, 2000);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous_page:
//                populateNextData();
//                Intent intentpr = new Intent(MainActivity.this, NewDataLoader.class);
//                page_num-=1;
//                intentpr.putExtra("Page Number", page_num);
                break;
            case R.id.next_page:
                Intent intent = new Intent( getContext(), SingleProductPage.class);
                startActivity(intent);
//                populatePreviousData();
//                Intent intent = new Intent(MainActivity.this, NewDataLoader.class);
//                page_num+=1;
//                intent.putExtra("Page Number", page_num);
                break;
        }
    }


}