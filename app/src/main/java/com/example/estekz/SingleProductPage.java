package com.example.estekz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.estekz.models.AllProducts;
import com.example.estekz.models.Json;
import com.example.estekz.models.OnlyNeededData;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleProductPage extends AppCompatActivity {
    private Json getDataService;
    private List<OnlyNeededData> singleProduct = new ArrayList<>();
    Integer[] colors = null;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    private List<OnlyNeededData> recommendedProduct = new ArrayList<>();
    private AllProductsAdapter adapterOther;
    private int page_num=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_product);
        ImageSlider imageSlider=findViewById(R.id.slider);
        TextView title=findViewById(R.id.title_name);
        TextView price=findViewById(R.id.price_of_product);
        recyclerView=findViewById(R.id.other_products);
        HtmlTextView htmlTextView = findViewById(R.id.description);
        List<SlideModel> slideModels=new ArrayList<>();
        progressBar=findViewById(R.id.loading_specific_photos);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent=getIntent();
        long id=intent.getLongExtra("id", 10234);
        getDataService = RetrofitClient.getRetrofitClient().create(Json.class);
        Call<AllProducts> call;
        call = getDataService.getPost(id);
        call.enqueue(new Callback<AllProducts>() {
            @Override
            public void onResponse(Call<AllProducts> call, Response<AllProducts> response) {
                assert response.body() != null;
                if (response.isSuccessful() && response.body() != null) {
                    if (!singleProduct.isEmpty()) {
                        singleProduct.clear();
                    }

                    AllProducts post = response.body();
                    title.setText(post.getName());
                    htmlTextView.setHtml(post.getDescription());
                    price.setText("Цена: "+post.getPrice()+" Тг");


                    for(int i=0; i<post.getImages().size(); ++i){
                        slideModels.add(new SlideModel(post.getImages().get(i).getSrc(), ScaleTypes.CENTER_INSIDE));
                    }
                    progressBar.setVisibility(View.GONE);
                    imageSlider.setImageList(slideModels, ScaleTypes.CENTER_INSIDE);

                    Call<List<AllProducts>> callOther;
                    callOther = getDataService.getMore(page_num);
                    callOther.enqueue(new Callback<List<AllProducts>>() {
                        @Override
                        public void onResponse(Call<List<AllProducts>> call, Response<List<AllProducts>> response) {
                            assert response.body() != null;
                            if (response.isSuccessful() && response.body() != null) {
                                if (!recommendedProduct.isEmpty()) {
                                    recommendedProduct.clear();
                                }

                                List<AllProducts> posts = response.body();
                                for (AllProducts post : posts) {
                                    OnlyNeededData a=new OnlyNeededData(post.getId(),post.getName(), post.getPrice(), post.getImages().get(0).getSrc());
                                    recommendedProduct.add(a);
                                }

                                adapterOther = new AllProductsAdapter(recommendedProduct, SingleProductPage.this);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(SingleProductPage.this,
                                        2,
                                        GridLayoutManager.VERTICAL,
                                        false);
                                recyclerView.setLayoutManager(gridLayoutManager);
                                recyclerView.setAdapter(adapterOther);
                                adapterOther.notifyDataSetChanged();
                            } else {
                                Toast.makeText(SingleProductPage.this, "Error. No Internet!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<AllProducts>> call, Throwable t) { }
                    });




                } else {
                    Toast.makeText(SingleProductPage.this, "Error. No Internet!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AllProducts> call, Throwable t) {
                Toast.makeText(SingleProductPage.this, "REST API Failed", Toast.LENGTH_SHORT).show();

            }

        });

        Integer[] colors_temp = {
                getResources().getColor(R.color.mainColor),
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.whiteShade),
                getResources().getColor(R.color.mainColor)
        };

        colors = colors_temp;



    }
}
