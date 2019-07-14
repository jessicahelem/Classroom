package com.example.rodrigosouza.presencebarcode.model;

import android.widget.TextView;

public class Turma {
    private String especificacao_disciplina;
    private int carga_horaria;
    private String ministrante;
    private String disciplina;
    private String curso;
    private int aulas_ministradas;
    public Turma(){

    }

    public String getEspecificacao_disciplina() {
        return especificacao_disciplina;
    }

    public void setEspecificacao_disciplina(String especificacao_disciplina) {
        this.especificacao_disciplina = especificacao_disciplina;
    }

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public void setCarga_horaria(int carga_horaria) {
        this.carga_horaria = carga_horaria;
    }

    public String getMinistrante() {
        return ministrante;
    }

    public void setMinistrante(String ministrante) {
        this.ministrante = ministrante;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getAulas_ministradas() {
        return aulas_ministradas;
    }

    public void setAulas_ministradas(int aulas_ministradas) {
        this.aulas_ministradas = aulas_ministradas;
    }

}