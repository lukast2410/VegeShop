package id.ac.binus.vegetarianshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateData();

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    private void generateData() {
        Vector<Vege> veges = Helper.veges;
        veges.add(new Vege("Kale N' Bean Lasagna", 75000, "layers of lasagna pasta, bean bolognese, vegan besciamella, with brocolli and kale filling, served with vegan caesar salad.", R.drawable.vege1));
        veges.add(new Vege("Beefless Bean Burrito", 80000, "tex-mex seasoned pea-based 'beefless' meat, guacamole, mixed greens, brown rice, and spiced curry dressing wrapped in our flaxseed tortilla, served with chips", R.drawable.vege2));
        veges.add(new Vege("Cool Kids 4", 120000, "Vegan Lasagna + Sweet Potato Fries + Superfood Smoothie", R.drawable.vege3));
        veges.add(new Vege("Vegan Bakso", 70000, "Homemade mushroom balls, gluten-free noodles, steamed tofu, bokchoy and hioko mushrooms, served in veggie stock soup & served with sambal.", R.drawable.vege4));
        veges.add(new Vege("Mighty Mushroom", 85000, "mushroom patty on a wholewheat bun, served with chips and salad (vegetarian)", R.drawable.vege5));
        veges.add(new Vege("Mini Trio", 78000, "3 mini veggie burgers consisting of Mighty Mushroom, Beans 'N Cheese, & Spinach Chickpeas, served with chips & organic salad (please request for Gluten-Free bun for GF option)", R.drawable.vege6));
        veges.add(new Vege("Green Punk", 42000, "pineapple, kale, bokchoy, banana, coconut water", R.drawable.vege7));
        veges.add(new Vege("Mixed-Grain Fried Rice", 73000, "Burgreens style mixed-grain fried rice, 3 Burgreens satay with taichan dressing", R.drawable.vege8));
        veges.add(new Vege("Gluten-Free Holy", 66000, "3 onion-free mini veggie burgers consisting of Mighty Mushroom (vegetarian), Beans 'N Cheese, & Spinach Chickpeas, served with chips & organic salad", R.drawable.vege9));
        veges.add(new Vege("Barley Quinoa with Yakitori", 82000, "Aceh's Barley, three-color quinoa, organic greens & seeds, edamame, tomatoes, served with roasted sesame dressing", R.drawable.vege10));
        veges.add(new Vege("Vegan Fudge Brownie Bar", 30000, "Gooey brownie handcrafted with organic cacao powder and cassava flour, topped with chocolate chunks and almonds.", R.drawable.vege11));
        veges.add(new Vege("Vegan Hotdog", 55000, "protein-packed seitan & chickpea sausage and organic greens on wholewheat bun served with mustard, vegan mayo, & BBQ sauce, served with chips and salad (V)", R.drawable.vege12));

        HashMap<String, Vege> mapVege = Helper.mapVege;
        for (Vege vege: veges) {
            mapVege.put(vege.getName(), vege);
        }
    }
}