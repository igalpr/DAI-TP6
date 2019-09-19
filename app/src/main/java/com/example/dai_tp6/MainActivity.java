package com.example.dai_tp6;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    int OpcionSeleccionada;
    boolean Hair,Glasses,Makeup,Smile;
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
        Hair=false;
        Glasses=false;
        Makeup=false;
        Smile=false;
        mensaje.setTitle("Cosas para evaluar en las imagenes ademas de genero, edad y pelo facial");
        String[] opciones={"Hair","Glasses","MakeUp","Smile"};
        boolean[] OpcionesRespuestas={false,false,false,false};
        mensaje.setMultiChoiceItems(opciones, OpcionesRespuestas, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Log.d("Elegida "+which," "+isChecked);
                switch (which)
                {
                    case 0:
                        if(isChecked)
                        {
                            Hair=true;
                        }
                        else
                        {
                            Hair=false;
                        }
                        break;
                    case 1:
                        if(isChecked)
                        {
                            Glasses=true;
                        }
                        else
                        {
                            Glasses=false;
                        }
                        break;
                    case 2:
                        if(isChecked)
                        {
                            Makeup=true;
                        }
                        else
                        {
                            Makeup=false;
                        }
                        break;
                    case 3:
                        if(isChecked)
                        {
                            Smile=true;
                        }
                        else
                        {
                            Smile=false;
                        }
                        break;
                    default:
                        break;
                }
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
    public void Volver() {
        FragmentHome home=new FragmentHome();
        fragmentManager=getFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentARemplazar,home);
        fragmentTransaction.commit();
    }

    public void Reemplazar()
    {
        Fragment result=new FragmentResultados();
        fragmentManager=getFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FragmentARemplazar,result);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        Volver();
    }

    DialogInterface.OnClickListener Escucha=new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int CualSera) {

            /*if(OpcionSeleccionada==0)
            {
                Log.d("Opcionelegida","Hair");
                Hair=true;
            }
            if(OpcionSeleccionada==1)
            {
                Log.d("Opcion elegida","Glasses");
                Glasses=true;
            }
            if(OpcionSeleccionada==2)
            {
                Log.d("Opcion elegida","Makeup");
            Makeup=true;
            }
            if(OpcionSeleccionada==3)
            {
                Log.d("Opcion elegida","Smile");
                Smile=true;
            }*/
        }
    };

}
