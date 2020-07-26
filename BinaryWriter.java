package mappingData.utils.dataWriters;

import mappingData.models.outputModel.UIOutputDataModel;
import mappingData.models.outputModel.UIOutputMessageModel;
import mappingData.nodes.FloorChangerNode;
import mappingData.nodes.Node;
import mappingData.nodes.RoomNode;
import mappingData.nodes.ToiletNode;
import mappingData.utils.enums.NodeTypeEnum;
import mappingData.utils.Log;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
* Writes the list of nodes with their attributes in binary and outputs it as a .txt binary file
* in its own directory
 *
 * @author Faith Ong and Hana Mizukami
 * @version 16.03.2020
*/
public class BinaryWriter implements DataWriter{
    private String copyrightAttribute;
    private ArrayList<Node> nodes;
    private File folder;
    private File newFile;
    private DataOutputStream dos;
    private static int VERSION = 1;

    public BinaryWriter(ArrayList<Node> nodes, File folder) {
        this.nodes = nodes;
        this.folder = folder;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        copyrightAttribute = "Mappin Technologies LTD " + currentYear;
    }

    public void createOutputFile() {

        try {
            // create a new document
            String newPath = createNewFolder();
            newFile = new File(newPath + "/bin_converted_" + folder.getName() + ".bin");
            FileOutputStream fos = new FileOutputStream(newFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            dos = new DataOutputStream(bos);
            createHeader();
            writeAllNodes();
            logActivity();
            dos.flush();
            dos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex + " in createOutputFile()");
        }
    }

    private void logActivity() {
        log.writeActivityLine("Binary conversion started.", Log.LineType.ACTIVITY);
        log.writeActivityLine(nodes.size() + " nodes added. Destination file: " + newFile.getAbsolutePath(), Log.LineType.ACTIVITY);
    }

    /**
     * writes the header of the file in binary
     */
    private void createHeader() {
        try {
            dos.writeInt(VERSION);
            Date date = new Date();
            long dateValue = date.getTime();
            dos.writeLong(dateValue);
        } catch (Exception e) {
            System.out.println(e + " in createHeader()");
        }
    }

    /**
     * writes a list of all the nodes and their attributes in binary
     */
    private void writeAllNodes() {
        try {
            int numberNodes = nodes.size();
            dos.writeInt(numberNodes);
            for (Node node : nodes) {
                writeNode(node);
            }
        } catch (Exception e) {
            System.out.println(e + " in writeAllNodes()");
        }
    }

    /**
     * writes an individual node's information in binary
     *
     * @param node to be documented
     */
    private void writeNode(Node node) {
        try {
            dos.writeInt(generateNodeTypeInt(node));
            //System.out.println("done generate node type int");
            dos.writeInt(Integer.parseInt(node.getID()));
            //System.out.println("done id");
            dos.writeFloat(node.getXCoord());
            //System.out.println("done x coord");
            dos.writeFloat(node.getYCoord());
            //System.out.println("done y coord");
            writeEndDeclarations(node);
            //System.out.println("done write end declarations");
        } catch (Exception e) {
            System.out.println(e + " in writeNode()");
        }
        writeNeighbours(node);
    }

    private void writeEndDeclarations(Node currentNode) {
        try {
            if (currentNode instanceof RoomNode) {
                RoomNode roomNode = (RoomNode) currentNode;
                dos.writeUTF(roomNode.getRoomName());
            } else if (currentNode instanceof ToiletNode) {
                ToiletNode toiletNode = (ToiletNode) currentNode;
                dos.writeInt(Integer.parseInt(toiletNode.getToiletTypeEnum()));
            } else if (currentNode instanceof FloorChangerNode) {
                FloorChangerNode floorChangerNode = (FloorChangerNode) currentNode;
                dos.writeInt(Integer.parseInt(floorChangerNode.getFloorChangerTypeEnum()));
            } else {

            }
        } catch (Exception e) {
            System.out.println(e + " in writeEndDeclaration()");
        }
    }

    /**
     * generate the node type as an integer
     *
     * @param currentNode to type check
     * @return integer representing the type of node
     */
    private int generateNodeTypeInt(Node currentNode) {
        if (currentNode instanceof RoomNode) {
            return NodeTypeEnum.ROOMNODE.getTypeNumber();
        } else if (currentNode instanceof ToiletNode) {
            return NodeTypeEnum.TOILETNODE.getTypeNumber();
        } else if (currentNode instanceof FloorChangerNode) {
            return NodeTypeEnum.FLOORCHANGERNODE.getTypeNumber();
        } else {
            return NodeTypeEnum.NODE.getTypeNumber();
        }
    }

    /**
     * write references to the node's neighbours in binary
     *
     * @param node for which we are writing the neighbours
     */
    private void writeNeighbours(Node node) {
        try {
            if (node.getNeighbours() != null) {
                dos.writeInt(node.getNeighbours().size());
                for (Node nb : node.getNeighbours()) {
                    dos.writeInt(Integer.parseInt(nb.getID()));
                }
            } else {
                dos.write(0);
            }
        } catch (Exception e) {
            System.out.println(e + " in writeNeighbours()");
        }

    }

    /**
     * Create the new directory to store the new XML files if it doesn't already
     * exist.
     *
     * @return the folder path of the new directory.
     */
    private String createNewFolder() {
        String newPath = folder.getParent() + "/BinaryOutput";
        File resources = new File(newPath);
        boolean resourcesCreated = resources.mkdirs();
        return newPath;
    }

    /**
     * prepare and pass output to display in the UI
     */
    @Override
    public UIOutputDataModel createOutputObject() {
        UIOutputMessageModel output = new UIOutputMessageModel(true);
        output.addNewOutputLine(nodes.size() + " nodes added to the binary file.");
        output.addNewOutputLine(log.getInvalidFileCount() + " invalid file(s) found.");
        output.addNewOutputLine(log.getErrorCount() + " error(s) found.");
        output.addNewOutputLine(log.getFatalErrorCount() + " fatal error(s) found.");
        output.addNewOutputLine("More information can be found at " + log.getDestinationFile());
        log.writeFinalLine();
        return output;
    }


}
