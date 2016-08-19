package ar.com.gitmo.androidpruebas.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by gaston.cocce on 18/08/2016.
 */
public class Semana {

    ArrayList<Actividad> actividades=new ArrayList<>();

    Date fechaDesde;
    Date fechaHasta;

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void addActividad(Actividad act){
        actividades.add(act);
    }

    public Iterator<Actividad> getIterator(){
        return actividades.iterator();
    }

}
