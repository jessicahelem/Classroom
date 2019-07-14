package com.example.rodrigosouza.presencebarcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.rodrigosouza.presencebarcode.model.AusenciaInteresse;
import com.example.rodrigosouza.presencebarcode.model.DeclaracaoAusencia;
import com.example.rodrigosouza.presencebarcode.model.Turma;

import java.util.ArrayList;
import java.util.List;

public class DetalhesCargaHorariaActivity extends AppCompatActivity {
    public int carga_horaria_h= 60;
    public int horas_restantes;
    public int horas_antecipadas = 2;
    public int horas_ministradas=20;
    public int horas_ausencias= 2;
    public TextView txt_carga_horaria;
    public TextView txt_horas_antecipadas;
    public TextView txt_horas_ausentes;
    public TextView txt_horas_ministradas;
    public TextView txt_horas_restantes;
    public TextView txt_hora_total;
    private int hora_total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_carga_horaria);


        txt_carga_horaria = findViewById(R.id.input_carg_horaria);
        txt_horas_antecipadas = findViewById(R.id.input_horas_antecipadas);
        txt_horas_ausentes = findViewById(R.id.input_ausencias);
        txt_horas_ministradas  = findViewById(R.id.input_horas_ministradas);
        txt_horas_restantes = findViewById(R.id.input_horas_restantes);
        txt_hora_total = findViewById(R.id.input_hora_total);


        //mostrarCargahoraria();

        Turma  turma = new Turma();
        DeclaracaoAusencia ausencia = new DeclaracaoAusencia();
        AusenciaInteresse antecipadas = new AusenciaInteresse();
        horas_antecipadas = antecipadas.getPeso();
        horas_ausencias = ausencia.getPeso();
        horas_ministradas = turma.getAulas_ministradas();
        carga_horaria_h = turma.getCarga_horaria();
        hora_total = horas_ministradas + horas_antecipadas;
        horas_restantes = (carga_horaria_h - hora_total);


        txt_carga_horaria.setText(String.valueOf(carga_horaria_h));
        txt_horas_ministradas.setText(String.valueOf(horas_ministradas));
        txt_horas_ausentes.setText(String.valueOf(horas_ausencias));
        txt_horas_antecipadas.setText(String.valueOf(horas_antecipadas));
        txt_horas_restantes.setText(String.valueOf(horas_restantes));
        txt_hora_total.setText(String.valueOf(hora_total));

    }

//    public void mostrarCargahoraria(){
//        Turma  turma = new Turma();
//        DeclaracaoAusencia ausencia = new DeclaracaoAusencia();
//        AusenciaInteresse antecipadas = new AusenciaInteresse();
//        horas_antecipadas = antecipadas.getPeso();
//        horas_ausencias = ausencia.getPeso();
//        horas_ministradas = turma.getAulas_ministradas();
//        carga_horaria_h = turma.getCarga_horaria();
//        hora_total = horas_ministradas + horas_antecipadas;
//        horas_restantes = (carga_horaria_h - hora_total);
//
//
//        txt_carga_horaria.setText(carga_horaria_h);
//        txt_horas_ministradas.setText(horas_ministradas);
//        txt_horas_ausentes.setText(horas_ausencias);
//        txt_horas_antecipadas.setText(horas_antecipadas);
//        txt_horas_restantes.setText(horas_restantes);
//        txt_hora_total.setText(hora_total);
//
//
//
//    }
//




    }

