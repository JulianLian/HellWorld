package gr196;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Julian on 16/4/23.
 * Issue 1, September 1995
 */
public class GR196 {

    void saveToFile() {

    }

    class MapBlock {
        // Map
        final String MapBlockID = "Map";
        short MapVersion = 100;
        int MapBytes = 0;

        short NumberOfBlocks = 7;

        // General Parameters
        final String GenParams = "GenParams";
        short GenParamsVer = 0x6500;
        int GenParamsBytes = 0x5A000000;
        // Supplier Parameters
        final String SupParams = "SupParams";
        short SupParamsVer = 0x6500;
        int SupParamsBytes = 0x50000000;
        // Fixed Parameters
        final String FxdParams = "FxdParams";
        short FxdParamsVer = 0x6500;
        int FxdParamsBytes = 0x36000000;
        // Data Points
        final String DataPts = "DataPts";
        short DataPtsVer = 0x6500;
        int DataPtsBytes = 0xDA290000;
        // Key Events
        final String KeyEvents = "KeyEvents";
        short KeyEventsVer = 0x6500;
        int KeyEventsBytes = 0xF0000000;
        // Link Parameters
//    String LnkParams = "LnkParams";
//    short LnkParamsVer = 0x6400;
//    long LnkParamsBytes = 0x0;

        // Special Proprietary
//    String UserNamedPlus = "UserNamedplus";
//    short UserNamedPlusVer = 0x6400;
//    long UserNamedPlusBytes = 0x0;
        // Checksum
        final String Cksum = "Cksum";
        short CksumVer = 100;
        int CksumBytes = 2;



        public void setMapBytes(int mapBytes) {
            MapBytes = mapBytes;
        }

        public void setGenParamsBytes(int genParamsBytes) {
            GenParamsBytes = genParamsBytes;
        }

        public void setSupParamsBytes(int supParamsBytes) {
            SupParamsBytes = supParamsBytes;
        }

        public void setFxdParamsBytes(int fxdParamsBytes) {
            FxdParamsBytes = fxdParamsBytes;
        }

        public void setDataPtsBytes(int dataPtsBytes) {
            DataPtsBytes = dataPtsBytes;
        }

        public void setKeyEventsBytes(int keyEventsBytes) {
            KeyEventsBytes = keyEventsBytes;
        }


        ByteBuffer writeByteBuffer() {
            MapVersion = 100;
            NumberOfBlocks = 7;
            MapBytes = 2
                    + 6*NumberOfBlocks
                    +GenParams.length()+1
                    +SupParams.length()+1
                    +FxdParams.length()+1
                    +DataPts.length()+1
                    +KeyEvents.length()+1
                    +Cksum.length()+1;
            ByteBuffer bbMap = ByteBuffer.allocate(MapBytes);
            bbMap.order(ByteOrder.LITTLE_ENDIAN);

            bbMap.putShort(MapVersion);
            bbMap.putInt(MapBytes);

            bbMap.putShort(NumberOfBlocks);

            bbMap.put(GenParams.getBytes());
            bbMap.put((byte)0);
            bbMap.putShort(GenParamsVer);
            bbMap.putInt(GenParamsBytes);

            bbMap.put(SupParams.getBytes());
            bbMap.put((byte)0);
            bbMap.putShort(SupParamsVer);
            bbMap.putInt(SupParamsBytes);

            bbMap.put(FxdParams.getBytes());
            bbMap.put((byte)0);
            bbMap.putShort(FxdParamsVer);
            bbMap.putInt(FxdParamsBytes);

            bbMap.put(DataPts.getBytes());
            bbMap.put((byte)0);
            bbMap.putShort(DataPtsVer);
            bbMap.putInt(DataPtsBytes);

            bbMap.put(KeyEvents.getBytes());
            bbMap.put((byte)0);
            bbMap.putShort(KeyEventsVer);
            bbMap.putInt(KeyEventsBytes);

            bbMap.put(Cksum.getBytes());
            bbMap.put((byte)0);
            bbMap.putShort(CksumVer);
            bbMap.putInt(CksumBytes);

            bbMap.clear();
            return bbMap;
        }

        MapBlock readFromByteBuff(ByteBuffer bbMap) {
            MapBlock mp = new MapBlock();

            mp.MapVersion = bbMap.getShort();
            mp.MapBytes = bbMap.getInt();
            mp.NumberOfBlocks = bbMap.getShort();

            bbMap.get(GenParams.getBytes());
            bbMap.get();
            mp.GenParamsVer = bbMap.getShort();
            mp.GenParamsBytes = bbMap.getInt();

            bbMap.get(SupParams.getBytes());
            bbMap.get();
            mp.SupParamsVer = bbMap.getShort();
            mp.SupParamsBytes = bbMap.getInt();

            bbMap.get(FxdParams.getBytes());
            bbMap.get();
            FxdParamsVer = bbMap.getShort();
            FxdParamsBytes = bbMap.getInt();

            bbMap.get(DataPts.getBytes());
            bbMap.get();
            DataPtsVer = bbMap.getShort();
            DataPtsBytes = bbMap.getInt();

            bbMap.get(KeyEvents.getBytes());
            bbMap.get();
            KeyEventsVer = bbMap.getShort();
            KeyEventsBytes = bbMap.getInt();

            bbMap.get(Cksum.getBytes());
            bbMap.get();
            CksumVer = bbMap.getShort();
            CksumBytes = bbMap.getInt();

            return mp;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    class GenParams {
        // General Parameters
        char[] LC = {'E', 'N'}; // Language Code (2 bytes)
        String CID = " ";
        String FID = " ";
        short NW = 1650;
        String OL = " ";
        String TL = " ";
        String CCD = " ";
        byte[] CDF = {'C', 'C'};
        long UO = 0;
        String OP = " ";
        String CMT = " ";

    }

    class SupParams {
        // Supplier Parameters
        String SN = " ";
        String MFID = " ";
        String OTDR = " ";
        String OMID = " ";
        String OMSN = " ";
        String SR = " ";
        String OT = " ";

    }

    class FxdParams {
        long DTS = 0;
        byte[] UD = {'k', 'm'};
        short AW = 1650;
        long AO = 0;
        short TPW = 1;
        short PWU = 30;
        long DS = 42;
        long NPPW = 155520;
        long GI = 146800;
        short BC = 800;
        long NAV = 0;
        long AR = 0;
        long FPO = 0;
        short NF = 0;
        short NFSF = 1000;
        short PO = 0;
        short LT = 200;
        short RT = (short) 40000;
        short ET = 3000;

    }

    class KeyEvents {
        short TNKE = 0;

        short EN = 1;
        long EPT = 0;
        short ACI = 0;
        short EL = 0;
        long ER = -45000;
        byte[] EC = {};
        byte[] LMT = {'2', 'P'};
        String CMT = " ";

        long EEL = 0;
        long ELMPstart = 0;
        long ELMPfinish = 0;
        long ORL = 0;
        long RLMPstart = 0;
        long RLMPfinish = 0;

    }

    class DataPts {
        long TNDP = 0;
        short TSF = 1;

        long TPS = 0;
        short SF = 1000;
        short[] DSF; //= {25000, 25000};
    }

    class Cksum {
        short CKSUM = 0;
    }

}