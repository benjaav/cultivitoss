package com.example.cultivitoss;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cultivitoss.db.dbCategorias;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private DatePicker datePicker;
    private Button btnRegistrar;
    private ListView listView;
    private ArrayList<String> cultivosList;
    private ArrayAdapter<String> cultivosAdapter;
    private dbCategorias db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        datePicker = findViewById(R.id.datePicker);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        listView = findViewById(R.id.listView);

        db = new dbCategorias(this);
        cultivosList = new ArrayList<>();
        cultivosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cultivosList);
        listView.setAdapter(cultivosAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cultivos_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarCultivo();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Aquí puedes implementar la funcionalidad para editar o eliminar cultivos
                // Por ejemplo, mostrar un diálogo para confirmar la eliminación
                eliminarCultivo(position);
            }
        });

        cargarCultivos();
    }

    private void registrarCultivo() {
        String tipoCultivo = spinner.getSelectedItem().toString();
        if (tipoCultivo.isEmpty()) {
            Toast.makeText(MainActivity.this, "Por favor, selecciona un tipo de cultivo", Toast.LENGTH_SHORT).show();
            return;
        }

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date fechaCultivo = calendar.getTime();
        long diasHastaCosecha;

        switch (tipoCultivo) {
            case "Tomates":
                diasHastaCosecha = 80;
                break;
            case "Cebollas":
                diasHastaCosecha = 120;
                break;
            case "Lechugas":
                diasHastaCosecha = 60;
                break;
            case "Apio":
                diasHastaCosecha = 85;
                break;
            case "Choclo":
                diasHastaCosecha = 90;
                break;
            default:
                diasHastaCosecha = 0;
                break;
        }

        Date fechaCosecha = new Date(fechaCultivo.getTime() + diasHastaCosecha * 24L * 60L * 60L * 1000L);
        db.insertarCultivo(tipoCultivo, fechaCultivo, fechaCosecha);
        cultivosList.add(tipoCultivo + " - Cosecha: " + fechaCosecha.toString());
        cultivosAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Cultivo registrado exitosamente. Fecha de cosecha: " + fechaCosecha.toString(), Toast.LENGTH_LONG).show();
    }

    private void eliminarCultivo(int position) {
        String cultivo = cultivosList.get(position);
        // Aquí puedes implementar la lógica para eliminar el cultivo de la base de datos
        // Por ejemplo, db.eliminarCultivo(id);
        cultivosList.remove(position);
        cultivosAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Cultivo eliminado: " + cultivo, Toast.LENGTH_SHORT).show();
    }

    private void cargarCultivos() {
        // Aquí puedes cargar los cultivos desde la base de datos y añadirlos a cultivosList
        // Por ejemplo:
        // List<Cultivo> cultivos = db.obtenerTodosLosCultivos();
        // for (Cultivo cultivo : cultivos) {
        //     cultivosList.add(cultivo.toString());
        // }
        // cultivosAdapter.notifyDataSetChanged();
    }
}
