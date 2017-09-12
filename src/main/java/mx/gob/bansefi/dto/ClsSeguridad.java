package mx.gob.bansefi.dto;

public class ClsSeguridad {

	   private int Estatus; 
	   private String error;
	   private String respuesta;
	   
	   public ClsSeguridad()
	   {
	       Estatus=0;
	       error="";
	       respuesta="";
	   }
	          
	    /**
	     * Regresar el valor de la variable
	     * @return the Estatus
	     */
	    public int getEstatus() {
	        return Estatus;
	    }

	    /**
	     * Almacena el valor en la variable
	     * @param Estatus the Estatus to set
	     */
	    public void setEstatus(int Estatus) {
	        this.Estatus = Estatus;
	    }

	    /**
	     * Regresar el valor de la variable
	     * @return the error
	     */
	    public String getError() {
	        return error;
	    }

	    /**
	     * Almacena el valor en la variable
	     * @param error the error to set
	     */
	    public void setError(String error) {
	        this.error = error;
	    }

	    /**
	     * Regresar el valor de la variable
	     * @return the respuesta
	     */
	    public String getRespuesta() {
	        return respuesta;
	    }

	    /**
	     * Almacena el valor en la variable
	     * @param respuesta the respuesta to set
	     */
	    public void setRespuesta(String respuesta) {
	        this.respuesta = respuesta;
	    }
	   
}
