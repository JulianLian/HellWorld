package gr196;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by Julian on 2016/5/2.
 */
public class GR196Test {
    @Test
    public void testSaveToFile() throws Exception {
        GR196 gr196Test = new GR196();
        gr196Test.saveToFile();
    }

    @Test
    public void testMapWriteByteBuffer() throws Exception {
        GR196 gr196Test = new GR196();
        GR196.MapBlock mapBlock = gr196Test.new MapBlock();
        ByteBuffer bbTest = mapBlock.writeToByteBuff();
//        assertEquals(bbTest.position(), bbTest.capacity());
//        bbTest.position(0);
        while (bbTest.hasRemaining())
            System.out.print(Integer.toHexString(bbTest.get()) + "\t");
        System.out.println("==End==");
    }

    @Test
    public void testMapReadFromByteBuff() throws Exception {
        GR196 gr196Test = new GR196();
        GR196.MapBlock mapBlock = gr196Test.new MapBlock();
        ByteBuffer bbTest = mapBlock.writeToByteBuff();

        mapBlock = mapBlock.readFromByteBuff(bbTest);

        assertEquals("GenParams", mapBlock.GenParams);
        assertEquals(7, mapBlock.NumberOfBlocks);
        assertEquals(100, mapBlock.CksumVer);
        assertEquals(2, mapBlock.CksumBytes);

        System.out.println(mapBlock.FxdParams);
        System.out.println(mapBlock.FxdParamsVer);
        System.out.println(mapBlock.SupParamsBytes);
    }

    @Test
    public void testGenWriteByteBuffer() throws Exception {
        GR196 gr196Test = new GR196();
        GR196.GenParams genBlock = gr196Test.new GenParams();
        ByteBuffer bbTest = genBlock.writeToByteBuff();
//        assertEquals(bbTest.position(), bbTest.capacity());
//        bbTest.position(0);
        while (bbTest.hasRemaining())
            System.out.print(Integer.toHexString(bbTest.get()) + "\t");
        System.out.println("==End==");
    }

    @Test
    public void testByteBuffer() throws Exception {
        byte[] x=new byte[100];
        ByteBuffer buf=ByteBuffer.allocate(100);
        buf.putInt(0x01020304);
        buf.putChar((char)0x0506);
        buf.putLong(0x0102030405060708L);
        buf.putDouble(3.3e15);
        buf.position(0);
        buf.get(x);
        System.out.println(x[0]+","+x[1]+","+x[2]+","+x[3]);
        buf.position(0);
        System.out.println(Integer.toHexString(buf.getInt()));
        System.out.println(Integer.toHexString(buf.getChar()));
        System.out.println(Long.toHexString(buf.getLong()));
        System.out.println(buf.getDouble());
        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.position(0);
        buf.get(x);
        System.out.println(x[0]+","+x[1]+","+x[2]+","+x[3]);
        buf.position(0);
        System.out.println(Integer.toHexString(buf.getInt()));
        System.out.println(Integer.toHexString(buf.getChar()));
        System.out.println(Long.toHexString(buf.getLong()));
        System.out.println(buf.getDouble());
//        buf.clear();
        buf.position(0);
        buf.put((byte) 0xFE);
        buf.put((byte) 0xDC);
        buf.put((byte) 0xBA);
        buf.put((byte) 0x98);
        buf.put((byte) 0x76);
        buf.put((byte) 0x54);
        buf.put((byte) 0x32);
        buf.put((byte) 0x10);
        buf.position(0);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        ShortBuffer shortBuffer = buf.asShortBuffer();
        System.out.println("buffer order: "+shortBuffer.order());
        System.out.print(Integer.toHexString(shortBuffer.get())+"\t");
        System.out.print(Integer.toHexString(shortBuffer.get())+"\t");
        System.out.print(Integer.toHexString(shortBuffer.get())+"\t");
        System.out.println(Integer.toHexString(shortBuffer.get()));
        buf.position(0);
        buf.order(ByteOrder.BIG_ENDIAN);
        shortBuffer = buf.asShortBuffer();
        System.out.println("buffer order: "+shortBuffer.order());
        System.out.print(shortBuffer.get()+"\t");
        System.out.print(shortBuffer.get()+"\t");
        System.out.print(shortBuffer.get() + "\t");
        System.out.println(shortBuffer.get());
    }

    @Test
    public void testUnsignedByte() throws Exception {
        // 0xca is a negative number considered as a signed byte.
        byte b = ( byte ) 0xca;
        // false, b sign extends, 0xca does not
        System.out.println(b == 0xca);
        // true, b sign extends, 0xca sign extends
        System.out.println( b == ( byte ) 0xca );
        // true, b chopped, 0xca does not sign extend
        System.out.println( ( b & 0xff ) == 0xca );
        // false, b chopped, 0xca sign extends.
        System.out.println( ( b & 0xff ) == ( byte ) 0xca );
    }

    @Test
    public void testUnsignedShort() throws Exception {
        // take 16-bit short apart into two 8-bit bytes.
        short x = (short)0xabcd;

        byte high = (byte)(x >>> 8);

        byte low = (byte)x;/* cast implies & 0xff */

        System.out.println("x=" + x + " high=" + high + " low=" + low);
    }

    @Test
    public void testReversBytes() throws Exception {
        int test = 0xabcdef82;  // test value
        System.out.println(Integer.toHexString(test));  // abcdef82
        System.out.println(Integer.toHexString(Integer.reverseBytes(test)));  // 82efcdab
        test = 0x0000abcd;  // test value
        System.out.println(Integer.toHexString(Integer.reverseBytes(test))); // cdab0000 does not work for 16 bits
        System.out.println(Integer.toHexString(Short.reverseBytes((short) test))); // ffffcdab, sign extends!
        System.out.println(Integer.toHexString(Character.reverseBytes((char) test))); // cdab unsigned.
    }
}