package grup.domain;

public class Pacient {

    private String cnp;
    private int varsta;
    private boolean prematur;
    private String diagnosticPrincipal;
    private int gravitate;

    public Pacient(String cnp, int varsta, boolean prematur, String diagnosticPrincipal, int gravitate) {
        this.cnp = cnp;
        this.varsta = varsta;
        this.prematur = prematur;
        this.diagnosticPrincipal = diagnosticPrincipal;
        this.gravitate = gravitate;
    }

    public String getCnp() {
        return cnp;
    }

    public int getVarsta() {
        return varsta;
    }

    public boolean isPrematur() {
        return prematur;
    }

    public String getDiagnosticPrincipal() {
        return diagnosticPrincipal;
    }

    public int getGravitate() {
        return gravitate;
    }

    public void setGravitate(int gravitate) {
        this.gravitate = gravitate;
    }

    public void setDiagnosticPrincipal(String diagnosticPrincipal) {
        this.diagnosticPrincipal = diagnosticPrincipal;
    }

    public void setPrematur(boolean prematur) {
        this.prematur = prematur;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "cnp='" + cnp + '\'' +
                ", varsta=" + varsta +
                ", prematur=" + prematur +
                ", diagnosticPrincipal='" + diagnosticPrincipal + '\'' +
                ", gravitate=" + gravitate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pacient pacient = (Pacient) o;

        return cnp != null ? cnp.equals(pacient.cnp) : pacient.cnp == null;
    }

    @Override
    public int hashCode() {
        return cnp != null ? cnp.hashCode() : 0;
    }

}
