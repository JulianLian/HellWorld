package interaction;

/**
 * Created by Julian on 16/1/2.
 */
public class OTDRTrace {
    String Module; // Type of plug-in having done the acquisition
    String Date; // Acquisition date
    String Function;
    String SwitchNumber;
    String PortNumber;
    String AutoConfig; // Start automatic measurements and answers OK
    String Pulsewidth; // Pulse used
    String Range; // Range used
    String AcqTime; // Acquisition time
    String Nindex; // Fiber index
    String Kcoeff; // Backscatter coefficient
    String Laser; // Laser used
    String Resolution; // Resolution used

    String Xoffset;
    String Xscale;
    String Yoffset;
    String Yscale;
    byte[] DataPoints; // data points
    int TableSize; // ken event size
    String[] Table; // key event

    double xoffsetToDouble(String xoffset) {
        return Double.parseDouble(xoffset);
    }

    double xscaleToDouble(String xscale) {
        return Double.parseDouble(xscale);
    }

    double yoffsetToDouble(String yoffset) {
        return Double.parseDouble(yoffset);
    }

    double yscaleToDouble(String yscale) {
        return Double.parseDouble(yscale);
    }
}
