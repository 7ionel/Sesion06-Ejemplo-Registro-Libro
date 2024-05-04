package com.example.semana06.entity;

import java.util.Date;

public class libro {
    private int idLibro;
    private String titulo;
    private int anio;
    private String serie;
    private String fechaRegistro;

    private int estado;
    private categoria categoria;
    private pais pais;

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public com.example.semana06.entity.categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(com.example.semana06.entity.categoria categoria) {
        this.categoria = categoria;
    }

    public com.example.semana06.entity.pais getPais() {
        return pais;
    }

    public void setPais(com.example.semana06.entity.pais pais) {
        this.pais = pais;
    }
}
