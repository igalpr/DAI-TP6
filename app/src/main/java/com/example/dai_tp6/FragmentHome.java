package com.example.dai_tp6;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.*;
import com.microsoft.projectoxford.face.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class FragmentHome extends Fragment implements View.OnClickListener {
    View VistaParaTrabajar;
    Button IrAGaleria,TomarFoto,VerEstadistica;
    TextView TXTMostrarResultados;
    ImageView imageViewAnalizar;
    ProgressDialog progressDialog;
    FaceServiceClient serviceClient;
    SharedPreferences preferencias;
    int CodigoPedirPermisos=31,codigoLlamadaCamara=32,codigoIrAGaleria=33;
    Context contexto=this.getContext();
    public View onCreateView(LayoutInflater inflador, ViewGroup parent, Bundle datosRecibidos) {
        Log.d("onCreateView", "Entro");
        VistaParaTrabajar = inflador.inflate(R.layout.pantalla_principal, parent, false);
        IrAGaleria=VistaParaTrabajar.findViewById(R.id.BotonElegirGaleria);
        TomarFoto=VistaParaTrabajar.findViewById(R.id.BotonTomarFoto);
        VerEstadistica=VistaParaTrabajar.findViewById(R.id.BotonMostrarEstadistica);
        TXTMostrarResultados=VistaParaTrabajar.findViewById(R.id.EstadisticaParcial);
        imageViewAnalizar=VistaParaTrabajar.findViewById(R.id.ImageViewImagenAEscanear);
        progressDialog=new ProgressDialog(this.getContext());
        preferencias=this.getActivity().getSharedPreferences("DAI-TP6",Context.MODE_PRIVATE);

        TomarFoto.setOnClickListener(this);
        IrAGaleria.setOnClickListener(this);
        VerEstadistica.setOnClickListener(this);
        String ApiEndPoint="https://brazilsouth.api.cognitive.microsoft.com/face/v1.0";
        String APIKey="29105693a23347ba9409bd930df29464";

        try {
        serviceClient=new FaceServiceRestClient(ApiEndPoint,APIKey);
        if(ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
        {
            TomarFoto.setEnabled(false);
            ActivityCompat.requestPermissions(this.getActivity(),new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},CodigoPedirPermisos);
        }
        else
        {
            TomarFoto.setEnabled(true);
        }
        }catch(Exception e)
        {
            Log.d("Error",""+e.getLocalizedMessage());
        }
        return VistaParaTrabajar;
    }

    @Override
    public void onRequestPermissionsResult(int CodRespuesta, @NonNull String[] permissions, @NonNull int[] ResultadosPermisos) {
        super.onRequestPermissionsResult(CodRespuesta, permissions, ResultadosPermisos);

        Log.d("onRequestPermissionsResult","entro "+CodRespuesta);
        if(CodRespuesta==CodigoPedirPermisos)
            {
            for (int puntero=0;puntero<permissions.length;puntero++)
            {
                Log.d("PermisionGranted:","Permisos: "+puntero+" -Nombre: "+permissions[puntero]+" - "+(ResultadosPermisos[puntero]==PackageManager.PERMISSION_GRANTED));
            }
            boolean TengoPermisos=true;
            for (int puntero=0;puntero<ResultadosPermisos.length;puntero++)
            {
                if(ResultadosPermisos[puntero]!=PackageManager.PERMISSION_GRANTED)
                {
                    TengoPermisos=false;
                }
            }
            if(TengoPermisos)
            {
                Log.d("Permisos","Tengo los 151");
                TomarFoto.setEnabled(true);
            }
            else
            {
                Log.d("Permisos","No los atrape a todos");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==TomarFoto.getId())
        {
            PresionarTomarFoto(v);
        }
        if(v.getId()==IrAGaleria.getId())
        {
            PresionarElegirFoto(v);
        }
        if(v.getId()==VerEstadistica.getId())
        {
            MainActivity main=(MainActivity)getActivity();

        }
    }
    public void PresionarTomarFoto(View vista)
    {
        Intent TomarFoto;
        TomarFoto=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(TomarFoto,codigoLlamadaCamara);
    }
    public void PresionarElegirFoto(View vista)
    {
        Intent ObtenerFoto=new Intent(Intent.ACTION_GET_CONTENT);
        ObtenerFoto.setType("image/*");
        startActivityForResult(ObtenerFoto,codigoIrAGaleria);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==codigoLlamadaCamara && resultCode== Activity.RESULT_OK)
        {
            Bitmap fotoRecibida=(Bitmap)data.getExtras().get("data");
            imageViewAnalizar.setImageBitmap(fotoRecibida);
            procesarImagenObtenida(fotoRecibida);
        }
        if (requestCode==codigoIrAGaleria&&resultCode==Activity.RESULT_OK && data!=null)
        {
            Uri Ubicacion=data.getData();
            Bitmap imagenFoto=null;
            try{
                imagenFoto=MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(),Ubicacion);
            }catch (Exception e){
                Log.d("Error"," "+e.getLocalizedMessage());
            }
            if(imagenFoto!=null)
            {
                imageViewAnalizar.setImageBitmap(imagenFoto);
                procesarImagenObtenida(imagenFoto);
            }
        }
    }
    public void procesarImagenObtenida(final Bitmap imagenAPrcesar)
    {
        ByteArrayOutputStream streamSalida=new ByteArrayOutputStream();
        imagenAPrcesar.compress(Bitmap.CompressFormat.JPEG,100,streamSalida);
        ByteArrayInputStream streamEntrada=new ByteArrayInputStream(streamSalida.toByteArray());

        class procesarImagen extends AsyncTask<InputStream,String,Face[]>
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.show();
            }

            @Override
            protected Face[] doInBackground(InputStream... imagenAProcesar) {
                publishProgress("Estamos procesando Leo...");

                Face[] resultado=null;
                try{
                    FaceServiceClient.FaceAttributeType[] atributos;
                    atributos=new FaceServiceClient.FaceAttributeType[]{
                            FaceServiceClient.FaceAttributeType.Age,
                            FaceServiceClient.FaceAttributeType.Gender,
                            FaceServiceClient.FaceAttributeType.FacialHair,
                            FaceServiceClient.FaceAttributeType.Hair,
                            FaceServiceClient.FaceAttributeType.Smile,
                            FaceServiceClient.FaceAttributeType.Makeup,
                            FaceServiceClient.FaceAttributeType.Glasses
                    };
                    MainActivity main=(MainActivity) getActivity();
                    resultado=serviceClient.detect(imagenAProcesar[0],true,false,atributos);
                }catch(Exception error)
                {
                    Log.d("error","Error en SC: "+error.getLocalizedMessage());

                }
                return resultado;
            }


            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);
                progressDialog.setMessage(values[0]);
            }

            @Override
            protected void onPostExecute(Face[] result) {
                super.onPostExecute(result);
                progressDialog.dismiss();
                if(result==null)
                {
                    TXTMostrarResultados.setText("Error en el procesado");
                } else
                {
                    if(result.length>0)
                    {
                        recuadrarCaras(imagenAPrcesar,result);
                        procesarResultadosDeCaras(result);
                    } else{
                        TXTMostrarResultados.setText("No habia ninguna cara en la imagen");
                    }
                }
            }
        }
        procesarImagen MiTask=new procesarImagen();
        MiTask.execute(streamEntrada);
    }
    public void recuadrarCaras(Bitmap imagenOriginal,Face[] carasARecuadrar)
    {
        Bitmap imagenADibujar=imagenOriginal.copy(Bitmap.Config.ARGB_8888,true);
        Canvas LienzoQueTuPintaras=new Canvas(imagenADibujar);
        Paint pincel=new Paint();
        pincel.setAntiAlias(true);
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setColor(Color.BLUE);
        pincel.setStrokeWidth(5);
        for (Face unacarita:carasARecuadrar)
        {
            FaceRectangle faceRectangle=unacarita.faceRectangle;
            LienzoQueTuPintaras.drawRect(faceRectangle.left,
                    faceRectangle.top,
                    faceRectangle.left+faceRectangle.width,
                    faceRectangle.top+faceRectangle.height,pincel);
        }
        imageViewAnalizar.setImageBitmap(imagenADibujar);
    }
    public void procesarResultadosDeCaras(Face[] carasAProcesar)
    {
        int cantidadHombres,CantidadMujeres;
        cantidadHombres=preferencias.getInt("cantidadHombres",0);
        CantidadMujeres=preferencias.getInt("cantidadMujeres",0);
        String Mensaje="";
        MainActivity main=(MainActivity)getActivity();
        for(int puntero=0;puntero<carasAProcesar.length;puntero++)
        {
            Mensaje+="Edad: "+carasAProcesar[puntero].faceAttributes.age;
            Mensaje+="- Género: "+carasAProcesar[puntero].faceAttributes.gender;
            Mensaje+="- Barba: "+carasAProcesar[puntero].faceAttributes.facialHair.beard;
            if(main.Hair)
            {
                Mensaje+="-Pelo: "+carasAProcesar[puntero].faceAttributes.hair;
            }
            if(main.Smile)
            {
                Mensaje+="-Sonrisa: "+carasAProcesar[puntero].faceAttributes.smile;
            }
            if(main.Glasses)
            {
                Mensaje+="-Anteojos: "+carasAProcesar[puntero].faceAttributes.glasses;
            }
            if(main.Makeup)
            {
                Mensaje+="-Maquillaje: "+carasAProcesar[puntero].faceAttributes.makeup;
            }

            if(carasAProcesar[puntero].faceAttributes.gender.equals("male"))
            {
                cantidadHombres++;
            }
            else
            {
                CantidadMujeres++;
            }
            SharedPreferences.Editor editorPreferencias;
            editorPreferencias=preferencias.edit();
            editorPreferencias.putInt("cantidadHombres",cantidadHombres);
            editorPreferencias.putInt("cantidadMujeres",CantidadMujeres);
            editorPreferencias.commit();
            if(puntero<carasAProcesar.length-1)
            {
                Mensaje+="\n";
            }
        }
        Mensaje+=" - H:"+cantidadHombres+" - M:"+CantidadMujeres;
        TXTMostrarResultados.setText(Mensaje);
    }
}
