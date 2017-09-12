package mx.gob.bansefi.dto;

/**
 * Created by AppWIns on 17/04/2017.
 */
public class ReqEncryptDTO {

    private String text;

    public ReqEncryptDTO() {
    }

    public ReqEncryptDTO(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
