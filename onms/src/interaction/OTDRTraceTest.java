package interaction;

import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Julian on 2016/1/4.
 */
public class OTDRTraceTest {

    @Test
    public void testXoffsetToDouble() throws Exception {
        OTDRTrace trace = new OTDRTrace();
        trace.setXoffset("0");
        assertEquals(0.0, trace.xoffsetToDouble());
    }

    @Test
    public void testXscaleToDouble() throws Exception {
        OTDRTrace trace = new OTDRTrace();
        trace.setXscale("6.39488995E-01");
        assertEquals(0.639488995, trace.xscaleToDouble());
    }

    @Test
    public void testYoffsetToDouble() throws Exception {
        OTDRTrace trace = new OTDRTrace();
        trace.setYoffset("-13.700909");
        assertEquals(-13.700909, trace.yoffsetToDouble());
    }

    @Test
    public void testYscaleToDouble() throws Exception {
        OTDRTrace trace = new OTDRTrace();
        trace.setYscale("0.001470");
        assertEquals(0.001470, trace.yscaleToDouble());
    }
}