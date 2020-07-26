/*package mappingData.utils.dataReaders;

import mappingData.models.outputModel.UIOutputDataModel;
import mappingData.models.outputModel.UIOutputMessageModel;
import mappingData.nodes.FloorChangerNode;
import mappingData.nodes.Node;
import mappingData.nodes.RoomNode;
import mappingData.nodes.ToiletNode;
import mappingData.xmlconversion.NodeTypeEnum;
import mappingData.utils.Log;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
* Reads the list of nodes with their attributes in binary and outputs it as a .txt file
* in its own directory 
 * 
 * @author Hana Mizukami
 * @version 21.03.2020
*/
/*
public class BinaryReader implements DataReader{
    private String copyrightAttribute;
    private ArrayList<Node> nodes;
    private File folder;
    private File newFile;
    private DataOutputStream dos;
    private static int VERSION = 1;

    public BinaryReader(ArrayList<Node> nodes, File folder) {
        this.nodes = nodes;
        this.folder = folder;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        copyrightAttribute = "Mappin Technologies LTD " + currentYear;
    }

    public void readBinaryFile() throws IOException {
        try {
            String newPath = createNewFolder();
            newFile = new File(newPath + "/bin_to_String" + folder.getName() + ".txt");
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream("BinaryOutput/bin_converted_binInput.bin"));
            readFileFormat(dataInputStream);
            readTimeStamp(dataInputStream);
            readNumberOfNodes(dataInputStream);
            readNodeType(dataInputStream);
            readID(dataInputStream);
            readX(dataInputStream);
            readY(dataInputStream);
            readEnding(dataInputStream);
            dataInputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e + " in readBinaryFile()");
        }
    }

    private int readFileFormat(DataInputStream dataInSt){
        int fileFormat = dataInSt.readInt();
    }

    private long readTimeStamp(DataInputStream dataInSt){
        long timeStamp = dataInSt.readLong();
    }

    private int readNumberOfNodes(DataInputStream dataInSt){
        int numberOfNodes = dataInSt.readInt();
    }

    private int readNodeType(DataInputStream dataInSt){
        int nodeType = dataInSt.readInt();
    }

    private int readID(DataInputStream dataInSt){
        int id = dataInSt.readInt();
    }

    private int readX(DataInputStream dataInSt){
        int xValue = dataInSt.readInt();
    }
    
    private int readY(DataInputStream dataInSt){
        int yValue = dataInSt.readInt();
    }

    private int readEnding(DataInputStream dataInSt){
        int ending = dataInSt.readInt();
    }

    /**
     * Create the new directory to store the new XML files if it doesn't already
     * exist.
     *
     * @return the folder path of the new directory.
     */
    /*
    private String createNewFolder() {
        String newPath = folder.getParent() + "/TextFileOutput" ;
        File resources = new File(newPath);
        boolean resourcesCreated = resources.mkdirs();
        return newPath;
    }
    
    
}
*/