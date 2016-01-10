package interaction;

import java.io.ByteArrayInputStream;
import java.io.IOException;

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
    static Double DoubleYoffset;
    static String Yscale;
    static Double DoubleYscale;
    static byte[] DataPoints; // data points
    static int KeyEventSize; // ken event size
    static String[] KeyEvents; // key event

    void setXoffset(String xoffset) { this.Xoffset = xoffset; }

    void setXscale(String xscale) { this.Xscale = xscale; }

    void setYoffset(String yoffset) {
        this.Yoffset = yoffset;
        this.DoubleYoffset = yoffsetToDouble();
    }

    void setYscale(String yscale) {
        this.Yscale = yscale;
        this.DoubleYscale = yscaleToDouble();
    }

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

    double[] getDoubleDataPoints() {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(this.DataPoints);

            byte[] fourBytes = new byte[4];
            int sizeInByte = 0;
            short y;

            bais.skip(1); // '#'
            int numOfChar = (byte)(bais.read()) - '0';
//            bais.skip(this.numOfChar + 1);
            for (int hi = 0; hi < numOfChar; hi++) {
                sizeInByte  = sizeInByte * 10 + (bais.read() - '0');
            }

            double[] dataPoints = new double[sizeInByte>>2];
            int num = 0;
            while ((bais.read(fourBytes) != -1) && num < sizeInByte>>2) {
                y = (short) (Integer.parseInt(new String(fourBytes), 16));
                dataPoints[num] = y * this.DoubleYscale + this.DoubleYoffset;
                num++;
            }

            bais.close();
            return dataPoints;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
