package interaction;

/**
 * Created by Julian on 16/1/2.
 */
public class OTDRTrace {
    static String Module; // Type of plug-in having done the acquisition
    static String Date; // Acquisition date
    static String Function;
    static String SwitchNumber;
    static String PortNumber;
    static String AutoConfig; // Start automatic measurements and answers OK
    static String Pulsewidth; // Pulse used
    static String Range; // Range used
    static String AcqTime; // Acquisition time
    static String Nindex; // Fiber index
    static String Kcoeff; // Backscatter coefficient
    static String Laser; // Laser used
    static String Resolution; // Resolution used

    static String Xoffset;
    static String Xscale;
    static String Yoffset;
    static String Yscale;
    static byte[] DataPoints; // data points
    static int TableSize; // ken event size
    static String[] Table; // key event

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
