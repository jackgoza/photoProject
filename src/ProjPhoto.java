
import java.io.Serializable;
import javax.swing.*;

/**
 *
 * @author jack
 */
public class ProjPhoto implements Serializable {

    private static final long serialVersionUID = 1516L;

    ProjPhoto(ImageIcon newImage) {
	picture = newImage;
	description = " ";
	date = " ";
    }

    public ImageIcon getIcon() {
	return picture;
    }

    public void setDescription(String newDesc) {
	description = newDesc;
    }

    public void setDate(String newDate) {
	date = newDate;
    }

    public String description;
    public String date;
    public ImageIcon picture;

}


// Does this work in netbeans?
