package gr196;

/**
 * Created by Julian on 16/4/23.
 */
public class GR196 {
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
    String LnkParams = "LnkParams";
    short LnkParamsVer = 0x6400;
    long LnkParamsBytes = 0x0;

    // Special Proprietary
    String UserNamedPlus = "UserNamedplus";
    short UserNamedPlusVer = 0x6400;
    long UserNamedPlusBytes = 0x0;
    // Checksum
    String Cksum = "Cksum";
    short CksumVer = 0x6400;
    long CksumBytes = 0x02000000;

    void saveToFile() {

    }
}
