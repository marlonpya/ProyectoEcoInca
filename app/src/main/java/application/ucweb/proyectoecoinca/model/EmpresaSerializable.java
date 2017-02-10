package application.ucweb.proyectoecoinca.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ucweb03 on 09/02/2017.
 */

public class EmpresaSerializable implements Serializable{

    private int id_server;
    private String nombre;
    private int tipo_negocio;
    private String imagen;
    private int tipo_empresa;
    private String ciudad;
    private String pais;
    private String anio_f;
    private String descripcion;
    private int tipo_match;
    private int id_match;
    private int posicion;
    private String web;
    private String telefono1;
    private String telefono2;
    private String correo1;
    private String correo2;

    public EmpresaSerializable(int id_server, String nombre, int tipo_negocio, String imagen) {
        this.id_server = id_server;
        this.nombre = nombre;
        this.tipo_negocio = tipo_negocio;
        this.imagen = imagen;
    }

    public EmpresaSerializable(int id_server, String nombre, String imagen, String ciudad, String pais, String anio_f, String descripcion, String web, String telefono1, String telefono2, String correo1, String correo2, int tipo_empresa) {
        this.id_server = id_server;
        this.nombre = nombre;
        this.imagen = imagen;
        this.ciudad = ciudad;
        this.pais = pais;
        this.anio_f = anio_f;
        this.descripcion = descripcion;
        this.web = web;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.correo1 = correo1;
        this.correo2 = correo2;
        this.tipo_empresa = tipo_empresa;
    }

    public int getId_server() {
        return id_server;
    }

    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipo_negocio() {
        return tipo_negocio;
    }

    public void setTipo_negocio(int tipo_negocio) {
        this.tipo_negocio = tipo_negocio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getTipo_empresa() {
        return tipo_empresa;
    }

    public void setTipo_empresa(int tipo_empresa) {
        this.tipo_empresa = tipo_empresa;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getAnio_f() {
        return anio_f;
    }

    public void setAnio_f(String anio_f) {
        this.anio_f = anio_f;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTipo_match() {
        return tipo_match;
    }

    public void setTipo_match(int tipo_match) {
        this.tipo_match = tipo_match;
    }

    public int getId_match() {
        return id_match;
    }

    public void setId_match(int id_match) {
        this.id_match = id_match;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono) {
        this.telefono1 = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getCorreo1() {
        return correo1;
    }

    public void setCorreo1(String correo1) {
        this.correo1 = correo1;
    }

    public String getCorreo2() {
        return correo2;
    }

    public void setCorreo2(String correo2) {
        this.correo2 = correo2;
    }
}
