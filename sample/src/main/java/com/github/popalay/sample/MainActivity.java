package com.github.popalay.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.github.popalay.supermultipartfactory.SuperMultipartFactory;

import okhttp3.MultipartBody;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sample();
    }

    private void sample() {
        final NestedModel order = new NestedModel(21.34, "$");
        final Model model = new Model(42, "Denis", "Nikiforov", "http://exaple.com/exaple.jpeg", order);

        MultipartBody multipartBody = SuperMultipartFactory.create(model).getMultipartBody();

        // Result:
        // id = 42
        // first_name = Denis
        // last_name = Nikiforov
        // avatar = http://exaple.com/exaple.jpeg
        // order[amount] = 21.34
        // order[currency_symbol] = $

    }
}
