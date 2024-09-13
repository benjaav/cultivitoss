package com.example.cultivitoss.db;

import java.util.Date;

public class Cultivo {
    private int id;
    private String tipoCultivo;
    private Date fechaCultivo;
    private Date fechaCosecha;

    public Cultivo(int id, String tipoCultivo, Date fechaCultivo, Date fechaCosecha) {
        this.id = id;
        this.tipoCultivo = tipoCultivo;
        this.fechaCultivo = fechaCultivo;
        this.fechaCosecha = fechaCosecha;
    }

    public int getId() {
        return id;
    }

    public String getTipoCultivo() {
        return tipoCultivo;
    }

    public Date getFechaCultivo() {
        return fechaCultivo;
    }

    public Date getFechaCosecha() {
        return fechaCosecha;
    }

    @Override
    public String toString() {
        return tipoCultivo + " - Cosecha: " + fechaCosecha.toString();
    }
}