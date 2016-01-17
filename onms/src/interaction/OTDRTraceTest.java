package interaction;

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
        assertEquals(0.0, trace.getDoubleXoffset());
    }

    @Test
    public void testXscaleToDouble() throws Exception {
        OTDRTrace trace = new OTDRTrace();
        trace.setXscale("6.39488995E-01");
        assertEquals(0.639488995, trace.getDoubleXscale());
    }

    @Test
    public void testYoffsetToDouble() throws Exception {
        OTDRTrace trace = new OTDRTrace();
        trace.setYoffset("-13.700909");
        assertEquals(-13.700909, trace.getDoubleYoffset());
    }

    @Test
    public void testYscaleToDouble() throws Exception {
        OTDRTrace trace = new OTDRTrace();
        trace.setYscale("0.001470");
        assertEquals(0.001470, trace.getDoubleYscale());
    }

//    @Test
//    public void testFloatToString(){
//        String s = "1.2 m";
//        String[] parts = s.split(" ");
//        String[] partss = parts[0].split(Pattern.quote("."));
//        System.out.println(partss[0]);
//        System.out.println(parts[1] == "cm" ? parts[0] : parts[0].split(Pattern.quote("."))[0]);
//    }
}