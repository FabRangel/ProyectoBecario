/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

/**
 *
 * @author fgmrr
 */
public class BecarioModel {
    private String curp;
    private String apellidopat;
    private String apellidomat;
    private String nombre;
    private char genero;
    private String contrasenia;
    private Date fecha_nac;
    private String foto;

    public BecarioModel(String curp, String apellidopat, String apellidomat, String nombre, char genero, String contrasenia, Date fecha_nac, String foto) {
        this.curp = curp;
        this.apellidopat = apellidopat;
        this.apellidomat = apellidomat;
        this.nombre = nombre;
        this.genero = genero;
        this.contrasenia = contrasenia;
        this.fecha_nac = fecha_nac;
        this.foto = foto;
    }

    public BecarioModel() {
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getApellidopat() {
        return apellidopat;
    }

    public void setApellidopat(String apellidopat) {
        this.apellidopat = apellidopat;
    }

    public String getApellidomat() {
        return apellidomat;
    }

    public void setApellidomat(String apellidomat) {
        this.apellidomat = apellidomat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    @Override
    public String toString() {
        return "Becario{" + "Curp=" + curp + ",Apellido paterno=" + apellidopat + ",Apellido materno=" + apellidomat + ", nombre=" + nombre + ", genero=" + genero +", contraseña=" + contrasenia + ", fecha de nacimiento=" + fecha_nac + ", url de foto=" + foto + '}';
    } 
}
