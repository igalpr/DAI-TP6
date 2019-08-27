package com.example.dai_tp6;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dai_tp6.R;

public class FragmentHome extends Fragment {
    View VistaParaTrabajar;
    Button IrAGaleria,TomarFoto,VerEstadistica;
    TextView TXTMostrarResultados;
    ImageView imageViewAnalizar;
    ProgressDialog progressDialog;
    public View onCreateView(LayoutInflater inflador, ViewGroup parent, Bundle datosRecibidos) {
        Log.d("onCreateView", "Entro");
        VistaParaTrabajar = inflador.inflate(R.layout.pantalla_principal, parent, false);
        IrAGaleria=VistaParaTrabajar.findViewById(R.id.BotonElegirGaleria);
        TomarFoto=VistaParaTrabajar.findViewById(R.id.BotonTomarFoto);
        VerEstadistica=VistaParaTrabajar.findViewById(R.id.BotonMostrarEstadistica);
        TXTMostrarResultados=VistaParaTrabajar.findViewById(R.id.EstadisticaParcial);
        imageViewAnalizar=VistaParaTrabajar.findViewById(R.id.ImageViewImagenAEscanear);
        progressDialog=new ProgressDialog(this.getContext());


        String ApiEndPoint="https://brazilsouth.api.cognitive.microsoft.com/face/v1.0";
        String APIKey="29105693a23347ba9409bd930df29464";

        return VistaParaTrabajar;
    }

}
