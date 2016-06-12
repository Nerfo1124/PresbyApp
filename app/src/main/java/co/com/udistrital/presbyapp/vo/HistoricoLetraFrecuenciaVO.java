package co.com.udistrital.presbyapp.vo;

import java.util.Date;

/**
 * Created by Usuario on 31/05/2016.
 */
public class HistoricoLetraFrecuenciaVO {

    private int id;
    private int idUsuario;
    private String frecuencia;
    private String tamaño;
    private Date fechaHistorico;

    public HistoricoLetraFrecuenciaVO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public Date getFechaHistorico() {
        return fechaHistorico;
    }

    public void setFechaHistorico(Date fechaHistorico) {
        this.fechaHistorico = fechaHistorico;
    }

}
