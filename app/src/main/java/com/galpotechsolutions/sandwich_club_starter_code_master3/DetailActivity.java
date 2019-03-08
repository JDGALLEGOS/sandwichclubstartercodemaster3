package com.galpotechsolutions.sandwich_club_starter_code_master3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import com.galpotechsolutions.sandwich_club_starter_code_master3.model.Sandwich;
import com.galpotechsolutions.sandwich_club_starter_code_master3.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        try {
            String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
            String json = sandwiches[position];
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            if (sandwich == null) {
                // Sandwich data unavailable
                closeOnError();
                return;
            }

            populateUI(sandwich);

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param sandwich
     */
    private void populateUI(Sandwich sandwich) {

        ImageView ingredientsImageView = findViewById(R.id.image_iv);
        TextView alsoKnowTextView = findViewById(R.id.also_known_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView originTextView = findViewById(R.id.origin_tv);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsImageView);

        setTitle(sandwich.getMainName());

        List<String> nameList = sandwich.getAlsoKnownAs();
        for (int i=0; i<nameList.size(); i++){
            alsoKnowTextView.append(nameList.get(i) + ". ");
        }

        List<String> ingredientsList = sandwich.getIngredients();
        for (int i=0; i<ingredientsList.size(); i++){
            ingredientsTextView.append(ingredientsList.get(i) + ". ");
        }

        originTextView.append(sandwich.getPlaceOfOrigin());
        descriptionTextView.append(sandwich.getDescription());

    }
}
