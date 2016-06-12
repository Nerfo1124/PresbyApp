package co.com.udistrital.presbyapp.vo;

import java.util.Date;

/**
 * Created by Fernando Hernandez on 16/01/2016.
 */
public class UsuarioVO {

    private int idUsuario;
    private String nombreUsuario;
    private String apellido1Usuario;
    private String apellido2Usuario;
    private String fechaNacimiento;
    private String sexo;
    private SesionVO sesionUsuario;
    private FormulaVO formulaUsuario;
    private SistemaVO configUsuario;
    private ReestablecerVO restablecerUsuario;

    // Bloque Getter
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getApellido1Usuario() {
        return apellido1Usuario;
    }

    public String getApellido2Usuario() {
        return apellido2Usuario;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public SesionVO getSesionUsuario() {
        return sesionUsuario;
    }

    public FormulaVO getFormulaUsuario() {
        return formulaUsuario;
    }

    public SistemaVO getConfigUsuario() {
        return configUsuario;
    }

    public ReestablecerVO getRestablecerUsuario() {
        return restablecerUsuario;
    }

    //Bloque Setter
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setApellido1Usuario(String apellido1Usuario) {
        this.apellido1Usuario = apellido1Usuario;
    }

    public void setApellido2Usuario(String apellido2Usuario) {
        this.apellido2Usuario = apellido2Usuario;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setSesionUsuario(SesionVO sesionUsuario) {
        this.sesionUsuario = sesionUsuario;
    }

    public void setFormulaUsuario(FormulaVO formulaUsuario) {
        this.formulaUsuario = formulaUsuario;
    }

    public void setConfigUsuario(SistemaVO configUsuario) {
        this.configUsuario = configUsuario;
    }

    public void setRestablecerUsuario(ReestablecerVO restablecerUsuario) {
        this.restablecerUsuario = restablecerUsuario;
    }
}
