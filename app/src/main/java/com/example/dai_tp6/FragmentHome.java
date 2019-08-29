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
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.AddPersistedFaceResult;
import com.microsoft.projectoxford.face.contract.CreatePersonResult;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceList;
import com.microsoft.projectoxford.face.contract.FaceListMetadata;
import com.microsoft.projectoxford.face.contract.FaceMetadata;
import com.microsoft.projectoxford.face.contract.FaceRectangle;
import com.microsoft.projectoxford.face.contract.GroupResult;
import com.microsoft.projectoxford.face.contract.IdentifyResult;
import com.microsoft.projectoxford.face.contract.LargeFaceList;
import com.microsoft.projectoxford.face.contract.LargePersonGroup;
import com.microsoft.projectoxford.face.contract.Person;
import com.microsoft.projectoxford.face.contract.PersonFace;
import com.microsoft.projectoxford.face.contract.PersonGroup;
import com.microsoft.projectoxford.face.contract.SimilarFace;
import com.microsoft.projectoxford.face.contract.SimilarPersistedFace;
import com.microsoft.projectoxford.face.contract.TrainingStatus;
import com.microsoft.projectoxford.face.contract.VerifyResult;
import com.microsoft.projectoxford.face.rest.ClientException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class FragmentHome extends Fragment {
    View VistaParaTrabajar;
    Button IrAGaleria,TomarFoto,VerEstadistica;
    TextView TXTMostrarResultados;
    ImageView imageViewAnalizar;
    ProgressDialog progressDialog;
    FaceServiceClient serviceClient;
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

        try {
        serviceClient=new FaceServiceRestClient(ApiEndPoint,APIKey);
        }
        }catch(Exception e)
        {
            Log.d("Error",""+e.getLocalizedMessage());
        }
        return VistaParaTrabajar;
    }

}
