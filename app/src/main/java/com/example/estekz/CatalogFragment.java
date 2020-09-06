package com.example.estekz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CatalogFragment#} factory method to
 * create an instance of this fragment.
 */
public class CatalogFragment extends Fragment   {

    public CatalogFragment() {
    }
    RecyclerView recyclerView;
    CatalogRecAdapter recyclerAdapter;

    List<String> category_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)   {
        View rootView=inflater.inflate(R.layout.fragment_catalog, container, false);
        category_name = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recyclerview_forCatalog);
        recyclerAdapter = new CatalogRecAdapter(category_name);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        category_name.add("Аксессуары");
        category_name.add("Бижутерия и аксессуары");
        category_name.add("Браслеты");
        category_name.add("Броши и значки");
        category_name.add("Ванная и красота");
        category_name.add("Все для дома");
        category_name.add("Декор для дома");
        category_name.add("Детская");
        category_name.add("Женская одежда и обувь");
        category_name.add("Игрушки");
        category_name.add("Искусство");
        category_name.add("Картины и фотографии");
        category_name.add("Кольца");
        category_name.add("Костюмы и наряды");
        category_name.add("Красота и уход");
        category_name.add("Кухня");
        category_name.add("Мужская одежда и обувь");
        category_name.add("Одежда и обувь");
        category_name.add("Офисные принадлежности");
        category_name.add("Праздники");
        category_name.add("Праздничное оформление");
        category_name.add("Развлечения");
        category_name.add("Свадебные аксессуары");
        category_name.add("Серьги");
        category_name.add("Спорт и активный отдых");
        category_name.add("Сумки и кошельки");
        category_name.add("Товары для вечеринок");
        category_name.add("Украшения для тела");
        category_name.add("Украшения на шею");
        return rootView ;
    }

}