package com.example.dai_tp6;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentResultados extends Fragment implements View.OnClickListener {
    TextView textresult;
    SharedPreferences preferencias;
    Button boton;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View VistaADevolver=inflater.inflate(R.layout.resultados,container,false);
        textresult=VistaADevolver.findViewById(R.id.TextoResultados);
        preferencias=this.getActivity().getSharedPreferences("DAI-TP6", Context.MODE_PRIVATE);
        String Mensaje;
        boton=VistaADevolver.findViewById(R.id.BotonVaciar);
        boton.setOnClickListener(this);
        int hombres,mujeres,personas,intelectuales,edad, barba, laPelada, sonrisa, anteojos, maquillaje, cantHombresConBarba;
        hombres=preferencias.getInt("cantidadHombres",0);
        mujeres=preferencias.getInt("cantidadMujeres",0);
        personas=preferencias.getInt("contadorPersonas",0);
        edad=preferencias.getInt("edad",0);
        barba=preferencias.getInt("barba",0);
        laPelada=preferencias.getInt("laPelada",0);
        sonrisa=preferencias.getInt("sonrisa",0);
        anteojos=preferencias.getInt("anteojos",0);
        maquillaje=preferencias.getInt("maquillaje",0);
        cantHombresConBarba=preferencias.getInt("cantHombresConBarba",0);
        intelectuales=preferencias.getInt("cantidadIntelectuales",0);
        if(cantHombresConBarba==0)
        {
            cantHombresConBarba=1;
        }
        if(personas==0)
        {
            Mensaje="No hay datos para analizar";
        }
        else {
            Mensaje = "De las " + personas + ", la cantidad de hombres es" + hombres + " y la cantidad de mujeres es " + mujeres;
            Mensaje += ". El promedio de edad es de " + edad / personas;
            Mensaje += ". El porcentaje de hombre con barba es de " + (cantHombresConBarba / personas) * 100 + "% y el promedio de barba dentro de estos es " + barba / cantHombresConBarba;
            Mensaje += ". El promedio de pelada es de " + laPelada / personas;
            Mensaje += ". El promedio de personas con problemas de vista o locos que quieren ser cool es " + (anteojos*100 / personas)  + "%";
            Log.d("Valores", "anteojos: " + anteojos);
            Log.d("Valores", "personas: " + personas);
            Mensaje += ". Y por ultimo y mas importante la elite, los conocedores de la vida, los intelectuales son " + intelectuales + " personas";
        }
        textresult.setText(Mensaje);
        return VistaADevolver;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor editorPreferencias;
        editorPreferencias=preferencias.edit();
        editorPreferencias.putInt("cantidadHombres",0);
        editorPreferencias.putInt("cantidadMujeres",0);
        editorPreferencias.putInt("contadorPersonas",0);
        editorPreferencias.putInt("edad",0);
        editorPreferencias.putInt("barba",0);
        editorPreferencias.putInt("laPelada",0);
        editorPreferencias.putInt("sonrisa",0);
        editorPreferencias.putInt("anteojos",0);
        editorPreferencias.putInt("maquillaje",0);
        editorPreferencias.putInt("cantHombresConBarba",0);
        editorPreferencias.putInt("cantidadIntelectuales",0);
        editorPreferencias.commit();
        MainActivity main=(MainActivity)getActivity();
        main.Volver();
    }
}
