package clases;

/**
 *
 * @author Diego González
 */
public class Usuario {

    public String correo;
    

    public Usuario() {
    }

    public Usuario(String correo) {
        this.correo = correo;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
