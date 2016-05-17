package gr196;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

/**
 * Created by Julian on 16/4/23.
 * Issue 1, September 1995
 */
public class GR196 {

    static boolean isEven(int n) {
        return (n&1) == 0;
    }

    void saveToFile(String file) throws IOException {
        RandomAccessFile aFile     = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = aFile.getChannel();

        MapBlock mapBlock = new MapBlock();
        ByteBuffer mapBB = mapBlock.writeToByteBuff();
        while (mapBB.hasRemaining()) {
            fileChannel.write(mapBB);
        }

        GenParams genBlock = new GenParams();
        ByteBuffer genBB = genBlock.writeToByteBuff();
        while (genBB.hasRemaining()) {
            fileChannel.write(genBB);
        }

        SupParams supBlock = new SupParams();
        ByteBuffer supBB = supBlock.writeToByteBuff();
        while (supBB.hasRemaining()) {
            fileChannel.write(supBB);
        }

        FxdParams fxdBlock = new FxdParams();
        ByteBuffer fxdBB = fxdBlock.writeToByteBuff();
        while (fxdBB.hasRemaining()) {
            fileChannel.write(fxdBB);
        }

        fileChannel.close();
    }

    class MapBlock {
        // Map
        final String MapBlockID = "Map";
        short MapVersion = 100;
        int MapBytes = 0;

        short NumberOfBlocks = 7;

        // General Parameters
        final String GenParams = "GenParams";
        short GenParamsVer = 0x65;
        int GenParamsBytes = 0x5A;
        // Supplier Parameters
        final String SupParams = "SupParams";
        short SupParamsVer = 0x65;
        int SupParamsBytes = 0x50;
        // Fixed Parameters
        final String FxdParams = "FxdParams";
        short FxdParamsVer = 0x65;
        int FxdParamsBytes = 0x36;
        // Data Points
        final String DataPts = "DataPts";
        short DataPtsVer = 0x65;
        int DataPtsBytes = 0xDA29;
        // Key Events
        final String KeyEvents = "KeyEvents";
        short KeyEventsVer = 0x65;
        int KeyEventsBytes = 0xF0;
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


        ByteBuffer writeToByteBuff() {
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
        final byte[] LanguageCode = {'E', 'N'}; // Language Code (2 bytes)
        String CableID = " ";
        String FiberID = " ";
        short NominalWavelength = 1650;
        String OriginatingLocation = " ";
        String TerminatingLocation = " ";
        String CableCode = " ";
        final byte[] CurrentDataFlag = {'C', 'C'};
        int UserOffset = 0;
        String Operator = " ";
        String Comment = " ";

        int BlockBytes = 0;


        ByteBuffer writeToByteBuff() {
            BlockBytes = 2 +
                    CableID.length()+(isEven(CableID.length()) ? 2 : 1) +
                    FiberID.length()+(isEven(FiberID.length()) ? 2 : 1) +
                    2 +
                    OriginatingLocation.length()+(isEven(OriginatingLocation.length()) ? 2 : 1) +
                    TerminatingLocation.length()+(isEven(TerminatingLocation.length()) ? 2 : 1) +
                    CableCode.length()+(isEven(CableCode.length()) ? 2 : 1) +
                    2 +
                    4 +
                    Operator.length()+(isEven(Operator.length()) ? 2 : 1) +
                    Comment.length()+(isEven(Comment.length()) ? 2 : 1);

            ByteBuffer byteBuffer = ByteBuffer.allocate(BlockBytes);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

            byteBuffer.put(LanguageCode);

            byteBuffer.put(CableID.getBytes());
            if (isEven(CableID.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte) 0);

            byteBuffer.put(FiberID.getBytes());
            if (isEven(FiberID.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte) 0);

            byteBuffer.putShort(NominalWavelength);

            byteBuffer.put(OriginatingLocation.getBytes());
            if (isEven(OriginatingLocation.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte)0);

            byteBuffer.put(TerminatingLocation.getBytes());
            if (isEven(TerminatingLocation.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte)0);

            byteBuffer.put(CableCode.getBytes());
            if (isEven(CableCode.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte)0);

            byteBuffer.put(CurrentDataFlag);

            byteBuffer.putInt(UserOffset);

            byteBuffer.put(Operator.getBytes());
            if (isEven(Operator.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte)0);

            byteBuffer.put(Comment.getBytes());
            if (isEven(Comment.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte)0);

            byteBuffer.clear();
            return byteBuffer;
        }

    }

    class SupParams {
        // Supplier Parameters
        String SN = "JDSU ";
        String MFID = " ";
        String OTDR = " ";
        String OMID = " ";
        String OMSN = " ";
        String SR = " ";
        String OT = " ";

        int BlockBytes = 0;

        ByteBuffer writeToByteBuff() {
            BlockBytes = SN.length()+(isEven(SN.length()) ? 2 : 1) +
                    MFID.length()+(isEven(MFID.length()) ? 2 : 1) +
                    OTDR.length()+(isEven(OTDR.length()) ? 2 : 1) +
                    OMID.length()+(isEven(OMID.length()) ? 2 : 1) +
                    OMSN.length()+(isEven(OMSN.length()) ? 2 : 1) +
                    SR.length()+(isEven(SR.length()) ? 2 : 1) +
                    OT.length()+(isEven(OT.length()) ? 2 : 1);

            ByteBuffer byteBuffer = ByteBuffer.allocate(BlockBytes);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

            byteBuffer.put(SN.getBytes());
            if (isEven(SN.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte) 0);

            byteBuffer.put(MFID.getBytes());
            if (isEven(MFID.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte) 0);

            byteBuffer.put(OTDR.getBytes());
            if (isEven(OTDR.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte)0);

            byteBuffer.put(OMID.getBytes());
            if (isEven(OMID.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte)0);

            byteBuffer.put(OMSN.getBytes());
            if (isEven(OMSN.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte)0);

            byteBuffer.put(SR.getBytes());
            if (isEven(SR.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte)0);

            byteBuffer.put(OT.getBytes());
            if (isEven(OT.length()))
                byteBuffer.put((byte)' ');
            byteBuffer.put((byte)0);

            byteBuffer.clear();
            return byteBuffer;
        }

    }

    class FxdParams {
        int DateTimeStamp = 0;
        byte[] UnitsofDistance = {'k', 'm'};
        short ActualWavelength = 1650;
        int AcquisitionOffset = 0;
        short TotalPulseWidth = 1;
        short PulseWidthUsed = 30;
        int DataSpacing = 42;
        int NumberPointsPulseWidth = 155520;
        int GroupIndex = 146800;
        short BackscatterCoefficient = 800;
        int NumberAVerage = 0;
        int AcquisitionRange = 0;
        int FrontPanelOffset = 0;
        short NoiseFloorOffset = 0;
        short NoiseFloorScaleFactor = 1000;
        short PowerOffset = 0;
        short LossThreshold = 200;
        short ReflectanceThreshold = (short) 40000;
        short EndoffiberThreshold = 3000;

        int BlockBytes = 0;

        ByteBuffer writeToByteBuff() {
            BlockBytes = 4 + 2 + 2 + 4 + 2 + 2 +4 + 4 + 4 + 2 + 4 + 4 + 4 + 2 + 2 + 2 + 2 + 2 + 2;

            ByteBuffer byteBuffer = ByteBuffer.allocate(BlockBytes);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

            byteBuffer.putInt(DateTimeStamp);
            byteBuffer.put(UnitsofDistance);
            byteBuffer.putShort(ActualWavelength);
            byteBuffer.putInt(AcquisitionOffset);
            byteBuffer.putShort(TotalPulseWidth);
            byteBuffer.putShort(PulseWidthUsed);
            byteBuffer.putInt(DataSpacing);
            byteBuffer.putInt(NumberPointsPulseWidth);
            byteBuffer.putInt(GroupIndex);
            byteBuffer.putShort(BackscatterCoefficient);
            byteBuffer.putInt(NumberAVerage);
            byteBuffer.putInt(AcquisitionRange);
            byteBuffer.putInt(FrontPanelOffset);
            byteBuffer.putShort(NoiseFloorOffset);
            byteBuffer.putShort(NoiseFloorScaleFactor);
            byteBuffer.putShort(PowerOffset);
            byteBuffer.putShort(LossThreshold);
            byteBuffer.putShort(ReflectanceThreshold);
            byteBuffer.putShort(EndoffiberThreshold);

            byteBuffer.clear();
            return byteBuffer;
        }

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