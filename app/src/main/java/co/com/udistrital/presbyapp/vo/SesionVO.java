package co.com.udistrital.presbyapp.vo;

/**
 * Created by Fernando Hernandez on 16/01/2016.
 */
public class SesionVO {

    private int idSesion;
    private String usuario;
    private String contrasena;

    public SesionVO(){};

    // Bloque Getter

    public int getIdSesion() {
        return idSesion;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    //Bloque Setter

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}


