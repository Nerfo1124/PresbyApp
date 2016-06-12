package co.com.udistrital.presbyapp.vo;

/**
 * Created by Fernando Hernandez on 16/01/2016.
 */
public class SistemaVO {

    private int idSistema;
    private float tamanoFuente;
    private int frecuencia;

    // Bloque Getter
    public int getIdSistema() {
        return idSistema;
    }

    public float getTamanoFuente() {
        return tamanoFuente;
    }

    public int getFrecuencia() {
        return frecuencia;
    }



    // Bloque Setter
    public void setIdSistema(int idSistema) {
        this.idSistema = idSistema;
    }

    public void setTamanoFuente(float tamanoFuente) {
        this.tamanoFuente = tamanoFuente;
    }
    public void setFrecuencia(int frecuencia) {this.frecuencia = frecuencia;}


}
