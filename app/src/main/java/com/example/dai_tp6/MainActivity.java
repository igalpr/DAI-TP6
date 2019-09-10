package com.example.dai_tp6;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int OpcionSeleccionada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentHome home=new FragmentHome();
        fragmentManager=getFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentARemplazar,home);
        fragmentTransaction.commit();
        AlertDialog.Builder mensaje=new AlertDialog.Builder(this);
        mensaje.setTitle("Cosas para evaluar en las imagenes ademas de genero, edad y pelo facial");
        String[] opciones={"Hair","Glasses","MakeUp","Smile"};
        boolean[] OpcionesRespuestas={false,false,false,false};
        mensaje.setMultiChoiceItems(opciones, OpcionesRespuestas, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        });/*opciones,-1,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        OpcionSeleccionada=item;
                    }
                }
        );*/
        mensaje.setPositiveButton("Continuar",Escucha);
        mensaje.setIcon(R.drawable.emoji);
        mensaje.create();
        mensaje.show();
    }
    DialogInterface.OnClickListener Escucha=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int CualSera) {

            if(OpcionSeleccionada==0)
            {

            }
            if(OpcionSeleccionada==1)
            {

            }
            if(OpcionSeleccionada==2)
            {

            }
            if(OpcionSeleccionada==3)
            {

            }
        }
    };

}
