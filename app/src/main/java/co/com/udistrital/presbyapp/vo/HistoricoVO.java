package co.com.udistrital.presbyapp.vo;

import java.util.Date;

/**
 * Created by Fernando on 07/03/2016.
 */
public class HistoricoVO {

    private int id;
    private int idUsuario;
    private String tiempo;
    private Date fechaHistorico;

    public HistoricoVO() {
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

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public Date getFechaHistorico() {
        return fechaHistorico;
    }

    public void setFechaHistorico(Date fechaHistorico) {
        this.fechaHistorico = fechaHistorico;
    }
}
