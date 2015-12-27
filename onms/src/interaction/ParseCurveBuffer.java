package interaction;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Julian on 2015/12/20.
 */
public class ParseCurveBuffer {
    double curveXscale;  // t in formula Distance ~ 10^8 * t
    double curveYscale;  // A in formula A*y + B
    double curveYoffset;  // B in formula A*y + B
                      //#6008000
    int sizeInByte;   // ||->size in bytes
                      // |
    int numOfChar;    // Number of characters used to set the size

    public ParseCurveBuffer(double curveXscale, double curveYscale, double curveYoffset) {
        this.curveXscale = curveXscale;
        this.curveYscale = curveYscale;
        this.curveYoffset = curveYoffset;
    }

    public double getCurveXscale() {
        return curveXscale;
    }

    public double getCurveYscale() {
        return curveYscale;
    }

    public double getCurveYoffset() {
        return curveYoffset;
    }

    int parseNumOfChar(String curveBuffer) {
        char c = curveBuffer.charAt(0);
        if (c != '#')
            throw new RuntimeException("curveBuffer not begin with '#'");

        return curveBuffer.charAt(1) - '0';
    }

    int parseSizeInByte(String curveBuffer) {
        return Integer.parseInt(curveBuffer.substring(2, this.numOfChar + 2));
    }

    double[] parseDataPoints(String curveBuffer) {
        int length = curveBuffer.length();
        int i = this.numOfChar + 2;
        String pointSubStr;
        short point;
        double[] points = new double[this.sizeInByte/4];

        for (int pi = 0; i < length; i += 4, pi++) {
            pointSubStr = curveBuffer.substring(i, i+4);
            point = (short)(Integer.parseInt(pointSubStr, 16));

            points[pi] = point * this.curveYscale + this.curveYoffset;
        }

        return points;
    }

    void parseDataPointsFromFile(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Julian\\Documents\\MOP\\ONMS\\lian\\test log\\dataPoints.txt");

            byte[] sizeBytes = new byte[16];
            byte[] fourBytes = new byte[4];

            short y;
            double point;

            char sharp = (char)fis.read();
            this.numOfChar = (byte)(fis.read()) - '0';
            for (int hi = 0; hi < this.numOfChar; hi++) {
                sizeBytes[hi] = (byte)fis.read();
            }
//            this.sizeInByte = Integer.parseInt(new String(sizeBytes));

            while (fis.read(fourBytes) != -1) {
                y = (short) (Integer.parseInt(new String(fourBytes), 16));
                point = y * this.curveYscale + this.curveYoffset;
                fos.write((new Double(point)).toString().getBytes());
                fos.write(' ');
            }

            fis.close();
            fos.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
