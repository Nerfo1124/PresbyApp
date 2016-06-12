package co.com.udistrital.presbyapp.vo;

/**
 * Created by Fernando Hernandez on 16/01/2016.
 */
public class ReestablecerVO {

    private int idReestablecer;
    private String pregunta1;
    private String Respuesta1;
    private String pregunta2;
    private String respuesta2;
    private String tamanoFuente;

    // Bloque Getter
    public int getIdReestablecer() {
        return idReestablecer;
    }

    public String getPregunta1() {
        return pregunta1;
    }

    public String getRespuesta1() {
        return Respuesta1;
    }

    public String getPregunta2() {
        return pregunta2;
    }

    public String getRespuesta2() {
        return respuesta2;
    }

    public String getTamanoFuente() {
        return tamanoFuente;
    }

    //Bloque Setter
    public void setIdReestablecer(int idReestablecer) {
        this.idReestablecer = idReestablecer;
    }

    public void setPregunta1(String pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public void setPregunta2(String pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public void setRespuesta1(String respuesta1) {
        Respuesta1 = respuesta1;
    }

    public void setRespuesta2(String respuesta2) {
        this.respuesta2 = respuesta2;
    }

    public void setTamanoFuente(String tamanoFuente) {
        this.tamanoFuente = tamanoFuente;
    }
}
