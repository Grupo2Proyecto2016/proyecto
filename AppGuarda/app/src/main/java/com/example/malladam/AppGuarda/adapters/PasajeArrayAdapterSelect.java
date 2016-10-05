package com.example.malladam.AppGuarda.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.malladam.AppGuarda.R;
import com.example.malladam.AppGuarda.models.AsientoActivo;
import com.example.malladam.AppGuarda.models.GroupPasajeDT;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by malladam on 11/09/2016.
 */
public class PasajeArrayAdapterSelect<T> extends ArrayAdapter<T>
{
    private final Lock lock = new ReentrantLock();
    public double totalValue;
    private double seatValue;
    public List<Integer> selectedSeats = new ArrayList<>();
    List<ImageView> seatsImages = new ArrayList<>();

    public PasajeArrayAdapterSelect(Context context, List<T> objects, double seatValue) {
        super(context, 0, objects);
        this.seatValue = seatValue;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        lock.lock();
        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo con columna_asientos.xml
            listItemView = inflater.inflate(R.layout.columna_asientos, parent,false);
        }

        //Obteniendo instancias de los text views
        TextView numero1 = (TextView) listItemView.findViewById(R.id.numero1);
        TextView numero2 = (TextView) listItemView.findViewById(R.id.numero2);
        TextView numero3 = (TextView) listItemView.findViewById(R.id.numero3);
        TextView numero4 = (TextView) listItemView.findViewById(R.id.numero4);

        ImageView asiento1 = (ImageView) listItemView.findViewById(R.id.asiento1);
        ImageView asiento2 = (ImageView) listItemView.findViewById(R.id.asiento2);
        ImageView asiento3 = (ImageView) listItemView.findViewById(R.id.asiento3);
        ImageView asiento4 = (ImageView) listItemView.findViewById(R.id.asiento4);

        //Obteniendo instancia de la Tarea en la posición actual
        T item = (T)getItem(position);
        GroupPasajeDT grupo = ((GroupPasajeDT) item);
        AsientoActivo pasaje1 = grupo.getPasaje1();
        AsientoActivo pasaje2 = grupo.getPasaje2();
        AsientoActivo pasaje3 = grupo.getPasaje3();
        AsientoActivo pasaje4 = grupo.getPasaje4();

        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////
        ///////////////////EIDTAR ACA LOS ICONOS////////////////////////////////////


        if(pasaje1 != null)
        {
            asiento1.setTag(GenerateTag(seatsImages.size(), pasaje1.getId_asiento()));
            if (!pasaje1.getReservado())
            {
                numero1.setText(String.valueOf((position * 4) + 1));
                asiento1.setImageResource(R.drawable.icon_seat_libre);
                asiento1.setColorFilter(Color.GREEN);

                asiento1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        SelectSeat(v.getTag().toString());
                    }
                });
            }
            else
            {
                asiento1.setImageResource(R.drawable.icon_seat_ocupado);
                asiento1.setColorFilter(Color.RED);
            }

            seatsImages.add(asiento1);
        }

        if(pasaje2 != null) {
            asiento2.setTag(GenerateTag(seatsImages.size(), pasaje2.getId_asiento()));
            if (!pasaje2.getReservado()) {
                numero2.setText(String.valueOf((position * 4) + 2));
                asiento2.setImageResource(R.drawable.icon_seat_libre);
                asiento2.setColorFilter(Color.GREEN);

                asiento2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        SelectSeat(v.getTag().toString());
                    }
                });
            }
            else
            {
                asiento2.setImageResource(R.drawable.icon_seat_ocupado);
                asiento2.setColorFilter(Color.RED);
            }

            seatsImages.add(asiento2);
        }

        if(pasaje3 != null)
        {
            asiento3.setTag(GenerateTag(seatsImages.size(), pasaje3.getId_asiento()));
            if (!pasaje3.getReservado()) {
                numero3.setText(String.valueOf((position * 4) + 3));
                asiento3.setImageResource(R.drawable.icon_seat_libre);
                asiento3.setColorFilter(Color.GREEN);

                asiento3.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        SelectSeat(v.getTag().toString());
                    }
                });
            }
            else
            {
                asiento3.setImageResource(R.drawable.icon_seat_ocupado);
                asiento3.setColorFilter(Color.RED);
            }

            seatsImages.add(asiento3);
        }

        if(pasaje4 != null)
        {
            asiento4.setTag(GenerateTag(seatsImages.size(), pasaje4.getId_asiento()));
            if (!pasaje4.getReservado()) {
                numero4.setText(String.valueOf((position * 4) + 4));
                asiento4.setImageResource(R.drawable.icon_seat_libre);
                asiento4.setColorFilter(Color.GREEN);

                asiento4.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        SelectSeat(v.getTag().toString());
                    }
                });
            }
            else
            {
                asiento4.setImageResource(R.drawable.icon_seat_ocupado);
                asiento4.setColorFilter(Color.RED);
            }

            seatsImages.add(asiento4);
        }
        lock.unlock();
        return listItemView;

    }

    private void SelectSeat(String tag)
    {
        String[] tags = tag.split("-");
        int imageIndex = Integer.parseInt(tags[0]);
        int seatId = Integer.parseInt(tags[1]);

        if(selectedSeats.contains(seatId))
        {
            Object seatObj = seatId;
            selectedSeats.remove(seatObj);
            seatsImages.get(imageIndex).setImageResource(R.drawable.icon_seat_libre);
            seatsImages.get(imageIndex).setColorFilter(Color.GREEN);
            totalValue -= seatValue;
        }
        else
        {
            selectedSeats.add(seatId);
            seatsImages.get(imageIndex).setImageResource(R.drawable.icon_seat_ocupado);
            seatsImages.get(imageIndex).setColorFilter(Color.RED);
            totalValue += seatValue;
        }
    }

    private String GenerateTag(int imgIndex, long seatId)
    {
        return String.format("%s-%s", imgIndex, seatId);
    }
}