package interaction;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian on 16/1/2.
 */
public class OTDRTrace {
    String Module; // Type of plug-in having done the acquisition
    String Date; // Acquisition date
    String Time; // Acquisition time
    String Function;
    String SwitchNumber;
    String PortNumber;
    String AutoConfig; // Start automatic measurements and answers OK
    String Pulsewidth; // Pulse used
    String Range; // Range used
    String AcqTime; // Acquisition time
    String Nindex; // Fiber index
    String Kcoeff; // Backscatter coefficient
    String Laser; // Laser(wave length) used
    String Resolution; // Resolution used

    String Xoffset;
    String Xscale;
    String Xunit;
    String Yoffset;
    Double DoubleYoffset;
    String Yscale;
    Double DoubleYscale;
    String Yunit;
    byte[] DataPoints; // data points
    int KeyEventSize; // ken event size
    List<String> KeyEvents; // key event

    public OTDRTrace() {
        this.KeyEvents = new ArrayList<>();
    }

    public void setModule(String module) { this.Module = module; }
    public String getModule() { return this.Module; }

    public void setFunction(String function) { this.Function = function; }
    public String getFunction() { return this.Function; }

    public String getDate() { return Date;}
    public void setDate(String date) { Date = date; }

    public String getTime() { return Time;}
    public void setTime(String time) { Time = time; }

    public String getMeasTime() { return Date.replace(',','-')+" "+Time.replace(',',':'); }

    public String getSwitchNumber() { return SwitchNumber; }
    public void setSwitchNumber(String switchNumber) { SwitchNumber = switchNumber; }

    public String getPortNumber() { return PortNumber; }
    public void setPortNumber(String portNumber) { PortNumber = portNumber; }

    public String getAutoConfig() { return AutoConfig; }
    public void setAutoConfig(String autoConfig) { AutoConfig = autoConfig; }

    public String getPulsewidth() { return Pulsewidth; }
    public void setPulsewidth(String pulsewidth) { Pulsewidth = pulsewidth; }

    public String getRange() { return Range; }
    public void setRange(String range) { Range = range; }

    public String getAcqTime() { return AcqTime; }
    public void setAcqTime(String acqTime) { AcqTime = acqTime; }

    public String getNindex() { return Nindex; }
    public void setNindex(String nindex) { Nindex = nindex; }

    public void setKcoeff(String kcoeff) { this.Kcoeff = kcoeff; }
    public String getKcoeff() { return this.Kcoeff; }

    public void setLaser(String laser) { this.Laser = laser; }
    public String getLaser() { return this.Laser; }

    public void setResolution(String resolution) { this.Resolution = resolution; }
    public String getResolution() { return this.Resolution; }

    public void setXoffset(String xoffset) { this.Xoffset = xoffset; }

    public void setXscale(String xscale) { this.Xscale = xscale; }

    public String getXunit() { return this.Xunit; }
    public void setXunit(String s) { this.Xunit = s; }

    public void setYoffset(String yoffset) {
        this.Yoffset = yoffset;
        this.DoubleYoffset = getDoubleYoffset();
    }

    public void setYscale(String yscale) {
        this.Yscale = yscale;
        this.DoubleYscale = getDoubleYscale();
    }

    public String getYunit() { return this.Yunit; }
    public void setYunit(String s) { this.Yunit = s; }

    public double getDoubleXoffset() {
        return Double.parseDouble(this.Xoffset);
    }

    public double getDoubleXscale() {
        return Double.parseDouble(this.Xscale);
    }

    public double getDoubleYoffset() {
        return Double.parseDouble(this.Yoffset);
    }

    public double getDoubleYscale() { return Double.parseDouble(this.Yscale); }


    public List<String> getKeyEvents() { return this.KeyEvents; }

    public short[] getShortDataPoints() {
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

            short[] dataPoints = new short[sizeInByte>>2];
            int num = 0;
            while ((bais.read(fourBytes) != -1) && num < sizeInByte>>2) {
                y = (short) (Integer.parseInt(new String(fourBytes), 16));
                double doubleData = y * this.DoubleYscale + this.DoubleYoffset;
                dataPoints[num] = (short)(doubleData / this.DoubleYscale);
                num++;
            }

            bais.close();
            return dataPoints;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public double[] getDoubleDataPoints() {
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

    public List<Double> getDataPoints() {
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

            List<Double> dataPoints = new ArrayList<>();
            int num = 0;
            while ((bais.read(fourBytes) != -1) && num < sizeInByte>>2) {
                y = (short) (Integer.parseInt(new String(fourBytes), 16));
                dataPoints.add( y * this.DoubleYscale + this.DoubleYoffset);
                num++;
            }

            bais.close();
            return dataPoints;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    void writeDataPointsToFile() {
        FileOutputStream cfos = null;
        FileOutputStream dfos = null;
        FileOutputStream efos = null;
        try {
            cfos = new FileOutputStream("CurveBuff.txt");
            dfos = new FileOutputStream("DataPoints.txt");
            efos = new FileOutputStream("KeyEvent.txt");

            cfos.write(this.DataPoints);
            cfos.close();

            List<Double> dataList = this.getDataPoints();

            for (Double d : dataList){
                dfos.write(d.toString().getBytes());
                dfos.write(' ');
            }


            List<String> eventList = this.getKeyEvents();
            for (String s : eventList) {
                efos.write(s.getBytes());
                efos.write("\n".getBytes());
            }

            efos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
