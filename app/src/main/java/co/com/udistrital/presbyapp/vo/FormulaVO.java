package co.com.udistrital.presbyapp.vo;

import java.util.Date;

/**
 * Created by Fernando Hernandez on 16/01/2016.
 */
public class FormulaVO {

    private int idFormula;
    private float aVisualOD;
    private float aVisualOI;
    private String tamanioFuente;

    // Bloque Getter
    public int getIdFormula() {
        return idFormula;
    }

    public float getaVisualOD() {
        return aVisualOD;
    }

    public float getaVisualOI() {
        return aVisualOI;
    }

    public String getTamanioFuente() {
        return tamanioFuente;
    }

    // Bloque Setter
    public void setIdFormula(int idFormula) {
        this.idFormula = idFormula;
    }

    public void setaVisualOD(float aVisualOD) {
        this.aVisualOD = aVisualOD;
    }

    public void setaVisualOI(float aVisualOI) {
        this.aVisualOI = aVisualOI;
    }

    public void setTamanioFuente(String tamanioFuente) {
        this.tamanioFuente = tamanioFuente;
    }
}
