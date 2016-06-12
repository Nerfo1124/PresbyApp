package co.com.udistrital.presbyapp.util;

import android.util.Log;

/**
 * <b>Descripcion: </b>Clase creada con el fin de manipular datos espefcificamente de optometria y
 * oftalmologia, esto con el fin de asignar valores correctos al leer una formula de gafas.
 *
 * Created by Fernando on 09/05/2016.
 */
public class OptometriaUtil {

    /**
     * Variable de distancia de trabajo.
     */
    public static final int DISTANCIA_ENFOQUE = 30;

    /**
     * Variable que contiene los distintos niveles de acomodacion segun prescripcion medica.
     */
    private static final float[] adicionFormula = {0.25f,0.5f,0.75f,1.0f,1.25f,1.5f,1.75f,2.0f,2.25f,2.5f,
            2.75f,3.0f,3.25f,3.5f,3.75f,4.0f,4.25f,4.5f,4.75f, 5.0f};

    /**
     * Variable que contiene los valores estandar de acomodacion segun edad de una persona.
     */
    private static final double[] tablaAcomodacion = {12.0d, 10.0d, 8.5d, 7.0d, 5.5d, 4.5d, 3.5d,
            2.5d, 1.75d, 1.0d, 0.5d, 0.25d, 0.0d};

    /**
     * Variable con tamaño de fuente recomendado
     */
    private static final int[] tamanioFuente = {30, 35, 40, 45, 50, 55, 60, 65, 70};

    /**
     * <b>Descripcion: </b>Método que permite devolver un valor aceptable para la formula medica.
     *
     * @param progress Variable del SeekBar para asignar un valor de la Formula Medica.
     * @return String con el valor de la Formula Medica Elegida por el usuario.
     */
    public static String rangoFormulaMedica(int progress){
        String respuesta;
        switch (progress){
            case 1:
                respuesta = "" + adicionFormula[0];
                break;
            case 2:
                respuesta = "" + adicionFormula[1];
                break;
            case 3:
                respuesta = "" + adicionFormula[2];
                break;
            case 4:
                respuesta = "" + adicionFormula[3];
                break;
            case 5:
                respuesta = "" + adicionFormula[4];
                break;
            case 6:
                respuesta = "" + adicionFormula[5];
                break;
            case 7:
                respuesta = "" + adicionFormula[6];
                break;
            case 8:
                respuesta = "" + adicionFormula[7];
                break;
            case 9:
                respuesta = "" + adicionFormula[8];
                break;
            case 10:
                respuesta = "" + adicionFormula[9];
                break;
            case 11:
                respuesta = "" + adicionFormula[10];
                break;
            case 12:
                respuesta = "" + adicionFormula[11];
                break;
            case 13:
                respuesta = "" + adicionFormula[12];
                break;
            case 14:
                respuesta = "" + adicionFormula[13];
                break;
            case 15:
                respuesta = "" + adicionFormula[14];
                break;
            case 16:
                respuesta = "" + adicionFormula[15];
                break;
            case 17:
                respuesta = "" + adicionFormula[16];
                break;
            case 18:
                respuesta = "" + adicionFormula[17];
                break;
            case 19:
                respuesta = "" + adicionFormula[18];
                break;
            case 20:
                respuesta = "" + adicionFormula[19];
                break;
            default:
                respuesta = "0.0";
                break;
        }
        return respuesta;
    }

    /**
     * <b>Descripcion: </b>Método encargadoo de realizar el calculo de la adicion necesaria de una
     * persona para lograr una correcta vision cercana de acuerdo a la tabla estandar de Donders.
     *
     * @param distancia
     * @param edad
     * @return
     */
    public static String calcularAdicionDonders(int distancia, int edad){
        String respuesta;
        double adicion = 0.0d;
        if (edad >= 15 && edad < 20) {
            adicion = (1 / distancia) - (tablaAcomodacion[0] / 2);
        } else if (edad >= 20 && edad < 25) {
            adicion = (1 / distancia) - (tablaAcomodacion[1] / 2);
        } else if (edad >= 25 && edad < 30) {
            adicion = (1 / distancia) - (tablaAcomodacion[2] / 2);
        } else if (edad >= 30 && edad < 35) {
            adicion = (1 / distancia) - (tablaAcomodacion[3] / 2);
        } else if (edad >= 35 && edad < 40) {
            adicion = (1 / distancia) - (tablaAcomodacion[4] / 2);
        } else if (edad >= 40 && edad < 45) {
            adicion = (1 / distancia) - (tablaAcomodacion[5] / 2);
        } else if (edad >= 45 && edad < 50) {
            adicion = (1 / distancia) - (tablaAcomodacion[6] / 2);
        } else if (edad >= 50 && edad < 55) {
            adicion = (1 / distancia) - (tablaAcomodacion[7] / 2);
        } else if (edad >= 55 && edad < 60) {
            adicion = (1 / distancia) - (tablaAcomodacion[8] / 2);
        } else if (edad >= 60 && edad < 65) {
            adicion = (1 / distancia) - (tablaAcomodacion[9] / 2);
        } else if (edad >= 65 && edad < 70) {
            adicion = (1 / distancia) - (tablaAcomodacion[10] / 2);
        } else if (edad >= 70 && edad < 75) {
            adicion = (1 / distancia) - (tablaAcomodacion[11] / 2);
        } else if (edad > 75) {
            adicion = (1 / distancia) - (tablaAcomodacion[12] / 2);
        }
        respuesta = "" + normalizarDecimal(adicion);
        return respuesta;
    }

    /**
     * <b>Descripcion: </b>Método encargado de normalizar un decimal en terminos de 0.25 Diptrias.
     *
     * @param decimal
     * @return
     */
    public static double normalizarDecimal(double decimal){
        double respuesta = 0.0d;
        int pEnt = (int)decimal;
        double pDec = decimal - pEnt;
        if(pDec > 0.0 && pDec <=0.25){
            respuesta = pEnt + 0.25;
        } else if (pDec > 0.25 && pDec <= 0.5){
            respuesta = pEnt + 0.5;
        } else if (pDec > 0.5 && pDec <= 0.75){
            respuesta = pEnt + 0.75;
        } else if (pDec > 0.75){
            respuesta = pEnt + 1;
        }
        return respuesta;
    }

    /**
     * <b>Descripcion: </b>Método encargado de asignar un tamaño de letra segun formula medica registrada
     * por el usuario.
     *
     * @param formula
     * @return
     */
    public static int asignarTamanioXFormula(double formula){
        Log.d("[Sin_nombre]", "Ingreso de variable formula: " + formula);
        int respuesta = 0;
        formula = normalizarDecimal(formula);
        Log.d("[Sin_nombre]", "Variable Normalizada formula: " + formula);
        if(formula == adicionFormula[3]){
            respuesta = tamanioFuente[0];
        } else if(formula == adicionFormula[5]){
            respuesta = tamanioFuente[1];
        } else if(formula == adicionFormula[7]){
            respuesta = tamanioFuente[2];
        } else if(formula == adicionFormula[9]){
            respuesta = tamanioFuente[3];
        } else if(formula == adicionFormula[11]){
            respuesta = tamanioFuente[4];
        } else if(formula == adicionFormula[13]){
            respuesta = tamanioFuente[5];
        } else if(formula == adicionFormula[15]){
            respuesta = tamanioFuente[6];
        } else if(formula == adicionFormula[17]){
            respuesta = tamanioFuente[7];
        } else if(formula >= adicionFormula[19]){
            respuesta = tamanioFuente[8];
        } else if(formula > adicionFormula[3] && formula <adicionFormula[5]){
            respuesta = 33;
        } else if(formula > adicionFormula[5] && formula < adicionFormula[7]){
            respuesta = 38;
        } else if(formula > adicionFormula[7] && formula < adicionFormula[9]){
            respuesta = 43;
        } else if(formula > adicionFormula[9] && formula < adicionFormula[11]){
            respuesta = 48;
        } else if(formula > adicionFormula[11] && formula < adicionFormula[13]){
            respuesta = 53;
        } else if(formula > adicionFormula[13] && formula < adicionFormula[15]){
            respuesta = 58;
        } else if(formula > adicionFormula[15] && formula < adicionFormula[17]){
            respuesta = 63;
        } else if(formula > adicionFormula[17] && formula < adicionFormula[19]){
            respuesta = 68;
        }
        return respuesta;
    }
}