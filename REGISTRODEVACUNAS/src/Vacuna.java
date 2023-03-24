public class Vacuna {
    private String fecha;
    private String tipo;
    private String dosis;

    public Vacuna(String fecha, String tipo, String dosis) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.dosis = dosis;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCentro() {
        return dosis;
    }
}
