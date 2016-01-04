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

    void setXoffset(String xoffset) { this.Xoffset = xoffset; }

    void setXscale(String xscale) { this.Xscale = xscale; }

    void setYoffset(String yoffset) { this.Yoffset = yoffset; }

    void setYscale(String yscale) { this.Yscale = yscale; }

    double xoffsetToDouble( ) {
        return Double.parseDouble(this.Xoffset);
    }

    double xscaleToDouble( ) {
        return Double.parseDouble(this.Xscale);
    }

    double yoffsetToDouble( ) {
        return Double.parseDouble(this.Yoffset);
    }

    double yscaleToDouble( ) {
        return Double.parseDouble(this.Yscale);
    }
}
