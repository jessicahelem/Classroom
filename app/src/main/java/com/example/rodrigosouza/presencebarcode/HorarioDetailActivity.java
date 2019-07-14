package com.example.rodrigosouza.presencebarcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigosouza.presencebarcode.model.AusenciaInteresse;
import com.example.rodrigosouza.presencebarcode.model.DeclaracaoAusencia;
import com.example.rodrigosouza.presencebarcode.model.Turma;
import com.example.rodrigosouza.presencebarcode.utils.ConnectionRetrofit;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorarioDetailActivity extends AppCompatActivity {
    private Button disp;
    private Button cancel, solicitar;
    private TextView hoIni, hoFim, tur,curso;
    private TextView prof, discp, cargaHo;
    private EditText pesoDisponibilizar;

    private final String professorLogado = "4";
    private String date, horaInicio, ministrante, turma, idHorario;
    private Turma turmaRes;
    private final Context context=this;
    private int disponibilizar = 0;
    private int Countsolicitar = 0;
    public TextView txt_horas_antecipadas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario_detail);
        bindView();
        txt_horas_antecipadas = findViewById(R.id.input_horas_antecipadas);

    }
    public void disponiblizar(View v){
        DeclaracaoAusencia ausencia = new DeclaracaoAusencia();
        disponibilizar+=ausencia.getPeso();
        ausencia.setData_falta(date);
        ausencia.setJustificativa("Pessoal");
        ausencia.setProfessor(professorLogado);
        ausencia.setTurma(turma);
        ausencia.setHorario(idHorario);
        SimpleDateFormat fDate = new SimpleDateFormat("yyyy-MM-dd");
        Date hoje = new Date();
        String sData = fDate.format(hoje);
        ausencia.setData_declaracao(sData);
        ausencia.setPeso(disponibilizar);
        pesoDisponibilizar.setText(disponibilizar);


        Call<DeclaracaoAusencia> ausenciaCall = new ConnectionRetrofit().service().declararAusencia(ausencia);
        ausenciaCall.enqueue(new Callback<DeclaracaoAusencia>() {
            @Override
            public void onResponse(Call<DeclaracaoAusencia> call, Response<DeclaracaoAusencia> response) {
                if(response.body() != null){
                    // Log.i("MyLOG", "DecAuse response: "+response.body().toString());
                    Toast.makeText(context, "O horário foi disponibilizado para outros professores!", Toast.LENGTH_SHORT).show();
                }else{
                    //Log.i("MyLOG", "Eita Porra!");
                }
            }

            @Override
            public void onFailure(Call<DeclaracaoAusencia> call, Throwable t) {
                Log.i("MyLOG", "Error decAuse: "+t.toString());
            }
        });

        disp.setVisibility(View.GONE);
        //cancel.setVisibility(View.VISIBLE);

    }
    public void SolicitarHorario(View v){
        solicitar.setVisibility(View.GONE);
        showAddItemDialog();


        cancel.setVisibility(View.VISIBLE);
    }
    public void cancelarDispon(View v){
        // Call<DeclaracaoAusencia> deleteDec = new ConnectionRetrofit().service().deleteDeclaracao()
        Toast.makeText(this, "A ação foi cancelada!", Toast.LENGTH_SHORT).show();
        if(professorLogado.equals(ministrante)){
            disp.setVisibility(View.VISIBLE);
        }else{
            solicitar.setVisibility(View.VISIBLE);

        }
        cancel.setVisibility(View.GONE);

    }

    public void getTurma(String idTurma){
        Call<Turma> turmaCall = new ConnectionRetrofit().horarioService().getTurma(idTurma);
        turmaCall.enqueue(new Callback<Turma>() {
            @Override
            public void onResponse(Call<Turma> call, Response<Turma> response) {
                turmaRes = response.body();
                showData(turmaRes);
            }

            @Override
            public void onFailure(Call<Turma> call, Throwable t) {

            }
        });
    }

    public void verificarDisponibilidade(String idHorario){
        Log.i("MyLOG","IdHorario do Verificar: "+idHorario);
        Call<DeclaracaoAusencia> decAuseCall = new ConnectionRetrofit().service().verficaDeclaracaoAusencia(idHorario);
        Log.i("MyLOG","Request verif: "+decAuseCall.request().toString());
        decAuseCall.enqueue(new Callback<DeclaracaoAusencia>() {
            @Override
            public void onResponse(Call<DeclaracaoAusencia> call, Response<DeclaracaoAusencia> response) {
                if(response.body() != null && !professorLogado.equals(ministrante)){
                    Log.i("MyLOG","Response do Verificar: "+response.body().getJustificativa());
                    solicitar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<DeclaracaoAusencia> call, Throwable t) {
                Log.i("MyLOG","Error: "+t.toString());
            }
        });
    }

    private void showAddItemDialog() {
        final EditText pesoSolicitar = new EditText(this);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Adicionar quantidade de aulas");
                dialog.setMessage("Quantidade de aulas que deseja pegar");
                dialog.setView(pesoSolicitar);
                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AusenciaInteresse interesse = new AusenciaInteresse();
                        int peso = Integer.valueOf(pesoSolicitar.getText().toString());
                        pesoSolicitar.setText(peso);
                        Countsolicitar+=peso;
                        interesse.setPeso(Countsolicitar);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
    public void showData(Turma turma){

        tur.setText(turma.getEspecificacao_disciplina());
        ministrante = turma.getMinistrante();
        prof.setText(ministrante);
        discp.setText(turma.getDisciplina());
        curso.setText(turma.getCurso());
        cargaHo.setText(turma.getCarga_horaria());
        if(professorLogado.equals(ministrante)){
            disp.setVisibility(View.VISIBLE);
        }
    }

    public void bindView(){
        Intent intent = getIntent();
        idHorario =  intent.getStringExtra("idHorario");
        verificarDisponibilidade(idHorario);
        turma =  intent.getStringExtra("turma");
        horaInicio =  intent.getStringExtra("horaInicio");
        String horaFim =  intent.getStringExtra("horaFim");
        date =  intent.getStringExtra("date");
        disp = (Button) findViewById(R.id.bt_disponibilizar);
        cancel = (Button)findViewById(R.id.bt_cancelar);
        solicitar = findViewById(R.id.bt_detail_solicitar);

        pesoDisponibilizar = findViewById(R.id.edit_peso_dispo);
        prof = (TextView)findViewById(R.id.txt_professor_nome);
        hoIni = findViewById(R.id.txt_horario_hora_inicio);
        hoIni.setText(horaInicio);
        hoFim = findViewById(R.id.txt_horario_hora_fim);
        hoFim.setText(horaFim);
        discp = findViewById(R.id.txt_disciplina_nome);
        tur = findViewById(R.id.txt_turma);
        curso = findViewById(R.id.txt_title_detail);
        cargaHo = findViewById(R.id.txt_carga_horaria);
        getTurma(turma);
    }
}