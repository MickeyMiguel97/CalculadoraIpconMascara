package com.example.yanirayanes.calculadoraipconmascara;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText ip, mascara, idnet, partenet, partehost, broadcast, ipdisponibles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip = findViewById(R.id.ipcompleta);
        mascara = findViewById(R.id.mascara);
        ipdisponibles = findViewById(R.id.edit_text_canip);
        idnet = findViewById(R.id.netid);
        broadcast = findViewById(R.id.edit_text_broad);
        partenet = findViewById(R.id.edit_text_netp);
        partehost = findViewById(R.id.host);

    }

    // Esta fracción de código se usa para calcular
    public void calcular(View v){

        //Separamos la String por medio de puntos
        String[] ip_separada = ip.getText().toString().split("\\.");

        //variables para los calculos.
        long rip =0;
        long rmask = 0;
        long invertmask = 0;
        int mascaraint = Integer.parseInt(mascara.getText().toString());

        //validacion de todos los octetos
        if(ip_separada.length != 4) return;

        //ip en formato binario
        for(int i=3; i>=0; i--) {
            rip |= (Long.parseLong(ip_separada[3-i])) << (i*8);
        }

        //mascara en formato binario
        for(int i=1; i <= mascaraint; i++) {
            rmask |= 1L << (32-i);
        }

        //mascara invertida
        invertmask = ~rmask;

        idnet.setText(longToIP(rip & rmask));
        broadcast.setText(longToIP(rip | invertmask));

        ipdisponibles.setText((int)(Math.pow(2, (double)(32-mascaraint))) -2 + "");
        partenet.setText(mascaraint + "");
        partehost.setText((32-mascaraint) + "");

    }

    // Esta fracción de código pasa el numero binario a IP
    public String longToIP(long ip){
        String st="";
        for(int i=3; i>=0; i--) {
            st += (0b1111_1111 & (ip >> (i*8) )) + (i!=0? ".": "");
        }
        return st;
    }

    public void reset(View v){
        ip.setText("");
        ip.requestFocus();
        mascara.setText("");
        idnet.setText("");
        broadcast.setText("");
        ipdisponibles.setText("");
        partenet.setText("");
        partehost.setText("");
    }
}
