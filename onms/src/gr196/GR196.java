package gr196;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by Julian on 16/4/23.
 * Issue 1, September 1995
 */
public class GR196 {

    public MapBlock mapBlock;
    public GenParams genBlock;
    public SupParams supBlock;
    public FxdParams fxdBlock;
    public DataPts dataBlock;
    public KeyEvents eventBlock;

    public void setMapBlock() {
        this.mapBlock = new MapBlock(genBlock.BlockBytes,supBlock.BlockBytes,fxdBlock.BlockBytes,dataBlock.BlockBytes,eventBlock.BlockBytes);
    }

    public void setGenBlock(String cableId, String fiberId, String operator, String comment) {
        this.genBlock = new GenParams();
        this.genBlock.CableID = cableId;
        this.genBlock.FiberID = fiberId;
        this.genBlock.Operator = operator;
        this.genBlock.Comment = comment;
    }

    public void setSupBlock() {
        this.supBlock = new SupParams();
    }

    public void setFxdBlock() {
        this.fxdBlock = new FxdParams();
    }

    public void setDataBlock(short scaleFactor, short[] data) {
        System.out.println("ScaleFactor: "+scaleFactor);
        this.dataBlock = new DataPts(scaleFactor, data);
    }

    public void setDataBlock(double scaleFactor, double[] data) {
        System.out.println("ScaleFactor: "+scaleFactor);
        this.dataBlock = new DataPts(scaleFactor, data);
    }

    public void setEventBlock() {
        this.eventBlock = new KeyEvents();
        this.eventBlock.BlockBytes = 0;
    }

    public static boolean isEven(int n) {
        return (n&1) == 0;
    }

    public static int CRC_CCITT(byte[] bytes){
        int crc = 0x00;          // initial value
        int polynomial = 0x1021;
        for (int index = 0 ; index< bytes.length; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b   >> (7-i) & 1) == 1);
                boolean c15 = ((crc >> 15    & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        return crc;
    }

    public void saveToFile(String file) throws IOException {

        ByteBuffer genBB = this.genBlock.writeToByteBuff();

        ByteBuffer supBB = this.supBlock.writeToByteBuff();

        ByteBuffer fxdBB = this.fxdBlock.writeToByteBuff();

//        ByteBuffer dataBB = this.dataBlock.writeToByteBuff();
//        ByteBuffer dataBB = this.dataBlock.writeToByteBuff("GR196");
        ByteBuffer dataBB = this.dataBlock.writeToByteBuff(196);

        this.mapBlock = new MapBlock(genBlock.BlockBytes,supBlock.BlockBytes,fxdBlock.BlockBytes,dataBlock.BlockBytes,eventBlock.BlockBytes);
        ByteBuffer mapBB = this.mapBlock.writeToByteBuff();

        ByteBuffer allInOne = ByteBuffer.allocate(
                mapBB.capacity()+genBB.capacity()+supBB.capacity()+fxdBB.capacity()+dataBlock.BlockBytes+2);
        allInOne.put(mapBB).put(genBB).put(supBB).put(fxdBB).put(dataBB).putShort((short) 0);

        short cksum = (short)(CRC_CCITT(allInOne.array()) & 0xFFFF);
        allInOne.position(allInOne.position()-2);
        allInOne.putShort(cksum);

        allInOne.clear();

        RandomAccessFile aFile     = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = aFile.getChannel();

        fileChannel.write(allInOne);

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
        // Checksum
        final String Cksum = "Cksum";
        short CksumVer = 100;
        int CksumBytes = 2;


        public MapBlock(int genParamsBytes, int supParamsBytes, int fxdParamsBytes,int dataPtsBytes, int keyEventsBytes) {
            this.GenParamsBytes = genParamsBytes;
            this.SupParamsBytes = supParamsBytes;
            this.FxdParamsBytes = fxdParamsBytes;
            this.DataPtsBytes = dataPtsBytes;
            this.KeyEventsBytes = keyEventsBytes;
        }

        public MapBlock() { }

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
            NumberOfBlocks = (((DataPtsBytes == 0) || (KeyEventsBytes == 0)) ? (short)6 : (short)7);
            MapBytes = 2
                    + 6*NumberOfBlocks
                    +GenParams.length()+1
                    +SupParams.length()+1
                    +FxdParams.length()+1
                    +((DataPtsBytes != 0) ? (DataPts.length()+1) : 0)
                    +((KeyEventsBytes != 0) ? (KeyEvents.length()+1) : 0)
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

            if ( DataPtsBytes != 0) {
                bbMap.put(DataPts.getBytes());
                bbMap.put((byte) 0);
                bbMap.putShort(DataPtsVer);
                bbMap.putInt(DataPtsBytes);
            }

            if ( KeyEventsBytes != 0) {
                bbMap.put(KeyEvents.getBytes());
                bbMap.put((byte) 0);
                bbMap.putShort(KeyEventsVer);
                bbMap.putInt(KeyEventsBytes);
            }

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

    public class GenParams {
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
        short ActualWavelength = 16500;
        int AcquisitionOffset = 0;
        short TotalPulseWidth = 1;
        short PulseWidthUsed = 30;
        int DataSpacing = 42;
        int NumberPointsPulseWidth = 155520;
        int GroupIndex = 146500;
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
        short TheNumberKeyEvents = 0;

        class EventItem {
            short EventNumber = 1;
            int EventPropagationTime = 0;
            short AttenuationCoefficientLeadIn = 0;
            short EventLoss = 0;
            int EventReflectance = -45000;
            byte[] EventCode = {}; //string (6 bytes)
            byte[] LossMeasurementTecknique = {'2', 'P'}; // string (2 bytes)
            String Comment = " ";
        }

        List<EventItem> EventList;

        int EndToEndLoss = 0;
        int EndToEndMarkerPositionStart = 0;
        int EndToEndMarkerPositionFinish = 0;
        int OpticalReturnLoss = 0;
        int RLMPstart = 0;
        int RLMPfinish = 0;


        int BlockBytes = 0;

        ByteBuffer writeToByteBuff() {
            BlockBytes = 2 + TheNumberKeyEvents * (2+4+2+2+4+6+2+2) + 4 + 4 + 4 + 4 + 4 + 4;

            ByteBuffer byteBuffer = ByteBuffer.allocate(BlockBytes);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);



            byteBuffer.clear();
            return byteBuffer;
        }


    }

    class DataPts {
        int TNDP = 0;
        short TSF = 1;

        int TPS = 0;
        short SF = 1000;
        short[] DSF; //= {25000, 25000};

        double SR4731SF;
        double[] Doubles;

        int BlockBytes = 0;

        public DataPts(short scaleFactor, short[] data) {
            SF = scaleFactor;
            DSF = data;
            TNDP = data.length;
            TPS = TNDP;
        }

        public DataPts(double scaleFactor, double[] doubles) {
            Doubles = doubles;
            TNDP = doubles.length;
            SR4731SF = scaleFactor;
            TPS = 0;
            TSF = 0;
        }

        ByteBuffer writeToByteBuff(String _s) {

            BlockBytes = 4 + 2 + (4 + 2)*100 + TNDP * 2;

            ByteBuffer byteBuffer = ByteBuffer.allocate(BlockBytes);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

            byteBuffer.putInt(TNDP);

            int positionTSF = byteBuffer.position();
            byteBuffer.putShort(TSF);

            int positionTPS = byteBuffer.position(); // 6
            byteBuffer.putInt(TPS);
            byteBuffer.putShort(SF);

            double scaleFactor = SR4731SF;
            short y;
            int position;
            for ( double ds : Doubles) {
                if (((ds > 0) && (scaleFactor > 0)) || ((ds < 0) && (scaleFactor < 0)) ) {

                    position = byteBuffer.position();
                    if (position != 12) {  // == means no data put
                        byteBuffer.position(positionTPS);
                        byteBuffer.putInt(TPS);
                        byteBuffer.putShort((short) (scaleFactor * 1000000));
                        byteBuffer.position(position);

                        positionTPS = position;
                        TPS = 0;
                        byteBuffer.putInt(TPS);
                        byteBuffer.putShort(SF);

                        TSF += 1;
                    }
                    scaleFactor = (0 - scaleFactor);
                }

                y = (short) (((int)(ds / (0-scaleFactor)))&0xFFFF);
                byteBuffer.putShort(y);
                TPS += 1;
            }

            BlockBytes = byteBuffer.position();
            System.out.println("datapts.BlockBytes: "+BlockBytes);

            byteBuffer.position(positionTPS);
            byteBuffer.putInt(TPS);
            byteBuffer.putShort((short) (scaleFactor*1000000));

            TSF += 1;
            byteBuffer.position(positionTSF);
            byteBuffer.putShort(TSF);

            byteBuffer.position(BlockBytes);

            byteBuffer.flip();
            return byteBuffer;
        }

        ByteBuffer writeToByteBuff(int _version) {
            BlockBytes = 4 + 2 + 4 + 2 + TPS * 2;

            ByteBuffer byteBuffer = ByteBuffer.allocate(BlockBytes);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

            byteBuffer.putInt(TNDP);
            byteBuffer.putShort(TSF);
            byteBuffer.putInt(TPS);
            byteBuffer.putShort(SF);
            for ( short sd : DSF) {
                if (sd > 0)
                    byteBuffer.putShort((short)0);
                else
                    byteBuffer.putShort((short) (0-sd));
            }

            byteBuffer.clear();
            return byteBuffer;
        }

        ByteBuffer writeToByteBuff() {
            BlockBytes = 4 + 2 + 4 + 2 + TPS * 2;

            ByteBuffer byteBuffer = ByteBuffer.allocate(BlockBytes);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

            byteBuffer.putInt(TNDP);
            byteBuffer.putShort(TSF);
            byteBuffer.putInt(TPS);
            byteBuffer.putShort(SF);
            for ( short sd : DSF)
                byteBuffer.putShort(sd);

            byteBuffer.clear();
            return byteBuffer;
        }
    }

}