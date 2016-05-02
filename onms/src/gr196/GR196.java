package gr196;

/**
 * Created by Julian on 16/4/23.
 * Issue 1, September 1995
 */
public class GR196 {

    void saveToFile() {

    }

    class Map {
        // Map
        String Map = "Map";
        short MapVersion = 0x6400;
        long MapBytes = 0xA5000000;

        short NumberOfBlocks = 0x0B00;

        // General Parameters
        String GenParams = "GenParams";
        short GenParamsVer = 0x6500;
        long GenParamsBytes = 0x5A000000;
        // Supplier Parameters
        String SupParams = "SupParams";
        short SupParamsVer = 0x6500;
        long SupParamsBytes = 0x50000000;
        // Fixed Parameters
        String FxdParams = "FxdParams";
        short FxdParamsVer = 0x6500;
        long FxdParamsBytes = 0x36000000;
        // Data Points
        String DataPts = "DataPts";
        short DataPtsVer = 0x6500;
        long DataPtsBytes = 0xDA290000;
        // Key Events
        String KeyEvents = "KeyEvents";
        short KeyEventsVer = 0x6500;
        long KeyEventsBytes = 0xF0000000;
        // Link Parameters
//    String LnkParams = "LnkParams";
//    short LnkParamsVer = 0x6400;
//    long LnkParamsBytes = 0x0;

        // Special Proprietary
//    String UserNamedPlus = "UserNamedplus";
//    short UserNamedPlusVer = 0x6400;
//    long UserNamedPlusBytes = 0x0;
        // Checksum
        String Cksum = "Cksum";
        short CksumVer = 0x6400;
        long CksumBytes = 0x02000000;

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