package com.rebootrebels.savdhaan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class Help extends AppCompatActivity {

    ListView l1;
    ArrayList<String> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        l1=(ListView) findViewById(R.id.list);
        items=new ArrayList<>();
        items.add("\n\n"+"Andhra Pradesh\n"+
                "\n" +
                "Hyderabad/Secundrabad Police station\n" +

                "040-27853508\n" +
                "\n" +
                "Andhra Pradesh Women Protection cell\n" +

                "040-23320539\n" +
                "\n" +
                "Andhra Pradesh Women Commission\n" +

                "0863-2329090\n" +
                "\n" +
                "Hyderabad Women Police Station\n" +

                "040-27852400/4852");
        items.add("Arunachal Pradesh\n"+
                "\n"+"Women Commission 'C' Sector, Ita Nagar\n"+
                "0360-2214567,0360-2290544");
        items.add("Assam\n"+"\n"+ "ASSAM WOMEN HELPLINE\n" +

                "181 , 9345215029,\n" +
                "0361-2521242\n"+"\nASSAM Women Commission\n" +
                "0361-2227888,2220150 ,0361-2220013");
        items.add("Bihar\n"+"\n"+"Bihar Women Helpline\n" +

                "18003456247 / 0612-2320047 / 2214318\n" +
                "\n" +
                "Bihar Women Commission( 1 South, Beily Road, Patna, Bihar )\n" +
                "0612- 2507800");
        items.add("Chhattisgarh\n"+"\n"+"Chandigarh Women Police\n" +
                "0172-2741900");
        items.add("Goa\n"+"\n"+"GOA Women Helpline\n" +
                "1091 ,0832-2421208\n" +
                "\n" +
                "GOA Women Commission \n" +
                "0832-2421080");
        items.add("Gujrat\n"+"\n"+" STATE WOMEN COMMMISION GUJRAT\n"+"18002331111 / 079-23251604 , 079-23251613");
        items.add("Haryana");
        items.add("Himanchal Pradesh");
        items.add("Jammu and kashmir");
        items.add("Jharkhand");
        items.add("karnataka");
        items.add("kerala");
        items.add("Madhya Predesh");
        items.add("Maharastra");
        items.add("Manipur");
        items.add("Meghalaya");
        items.add("Mizorum");
        items.add("Nagaland ");
         items.add("Odisha");
        items.add("Punjab");
        items.add("Rajasthan");
         items.add("Sikkim");
          items.add("Tamil Nadu");
          items.add("Telangana");
           items.add("Tripura");
           items.add("Uttar Pradesh");
            items.add("Uttarakhand");
             items.add("West Bengal");
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        l1.setAdapter(adapter);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.hel,menu);
        MenuItem searchitem=menu.findItem(R.id.search);
            SearchView searchView=(SearchView) MenuItemCompat.getActionView(searchitem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> temp=new ArrayList<>();
                for(String te:items){
                    if(te.toLowerCase().contains(newText.toLowerCase()))
                    {
                        temp.add(te);
                    }
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<>(Help.this,android.R.layout.simple_list_item_1,temp);

                l1.setAdapter(adapter);
                return  true;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }


        });
        return super.onCreateOptionsMenu(menu);

    }
}