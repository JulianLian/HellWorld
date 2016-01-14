package interaction;

import org.junit.Test;

/**
 * Created by Julian on 2016/1/11.
 */
public class OTDRTraceGetterTest {
    static OTDRTraceGetter TestGetter = new OTDRTraceGetter();
//    @Test
//    public void testStartFetchData() throws Exception {
////        TestGetter.startFetchData();
//    }

//    @Test
//    public void testStopFetchData() throws Exception {
//        TestGetter.stopFetchData();
//    }

    // Run testStartFetchData() first.
    @Test
    public void testGetOTDRTrace() throws Exception {
        OTDRTrace trace = TestGetter.getOTDRTrace();
        System.out.println(trace.getDoubleXoffset());
        System.out.println(trace.getDoubleXscale());
        System.out.println(trace.Xunit);
        System.out.println(trace.Yunit);
        System.out.println(trace.getDoubleYscale());
        System.out.println(trace.getDoubleYoffset());

        System.out.println(trace.KeyEventSize);

        for (String s : trace.KeyEvents) {
            System.out.println(s);
        }
        System.out.println(trace.getDoubleDataPoints().length);
    }
}