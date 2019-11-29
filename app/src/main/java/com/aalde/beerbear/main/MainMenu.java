package com.aalde.beerbear.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aalde.beerbear.R;
import com.aalde.beerbear.games.buses.rounds.BusesMain;
import com.aalde.beerbear.games.mexxen.MexxenMain;
import com.aalde.beerbear.games.poker.PokerMain;
import com.google.android.gms.ads.MobileAds;

/**
 * @author Jordy Aaldering
 * @author Daan de Grauw
 * @author Elianne Heuer
 * @author Rick Röttjers
 * @author Jelte Smits
 * @author Jesper Somers
 */
public class MainMenu extends AppCompatActivity {
    private Button btnBuses, btnPoker, btnPannen, btnMexxen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MobileAds.initialize(this, "ca-app-pub-3997916477055946~6255368111");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setView();
        setButtons();
    }

    private void setView() {
        btnBuses  = findViewById(R.id.buttonBuses);
        btnPoker  = findViewById(R.id.buttonPoker);
        btnPannen = findViewById(R.id.buttonPannen);
        btnMexxen = findViewById(R.id.buttonMexxen);
    }

    private void setButtons() {
        btnBuses.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), BusesMain.class);
                startActivity(intent);
            }
        });
        btnPoker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PokerMain.class);
                startActivity(intent);
            }
        });
        btnPannen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Not yet implemented", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        btnMexxen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MexxenMain.class);
                startActivity(intent);
            }
        });
    }
}
