package mappingData.utils.dataWriters;

import javafx.util.Pair;
import mappingData.models.outputModel.UIOutputMessageModel;
import mappingData.nodes.FloorChangerNode;
import mappingData.nodes.Node;
import mappingData.nodes.RoomNode;
import mappingData.nodes.ToiletNode;
import mappingData.utils.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class XMLWriter implements DataWriter {
    private static Document doc;
    private Element mappinDataFileElement;
    private String versionAttribute = "1.0";
    private String copyrightAttribute;
    private String dateAttribute;
    private ArrayList<Node> nodes;
    private File folder;

    public XMLWriter(ArrayList<Node> nodes, File folder) {
        this.nodes = nodes;
        this.folder = folder;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        copyrightAttribute = "Mappin Technologies LTD " + currentYear;
        dateAttribute = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    }

    public void createOutputFile() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            // create a new document
            doc = builder.newDocument();
            createRootElement();
            createElementsFromNodes();
            writeXMLToFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public UIOutputMessageModel createOutputObject() {
        UIOutputMessageModel output = new UIOutputMessageModel(true);
        output.addNewOutputLine(nodes.size() + " nodes added to XML file.");
        output.addNewOutputLine(log.getInvalidFileCount() + " invalid file(s) found.");
        output.addNewOutputLine(log.getErrorCount() + " error(s) found.");
        output.addNewOutputLine(log.getFatalErrorCount() + " fatal error(s) found.");
        output.addNewOutputLine("More information can be found at " + log.getDestinationFile());
        log.writeFinalLine();
        return output;
    }

    public void createRootElement() {
        mappinDataFileElement = doc.createElement("MappinData");
        mappinDataFileElement.setAttribute("version", versionAttribute);
        mappinDataFileElement.setAttribute("created", dateAttribute);
        mappinDataFileElement.setAttribute("copyright", copyrightAttribute);
        doc.appendChild(mappinDataFileElement);
    }

    /**
     * Create elements from the Nodes of all the files
     * combined for the single XML file. Each node is an element
     * and has its own attributes.
     */
    private void createElementsFromNodes() {
        for (Node currentNode : nodes) {
            currentNode.convertToXMLValues();
            Element element = generateNodeType(currentNode);
            if (element != null) {
                mappinDataFileElement.appendChild(element);
                generateNodeNeighbours(currentNode, element);
            } else {
                log.writeActivityLine(currentNode.getName() + ": Could not be created.", Log.LineType.ERROR);
            }
        }
    }

    /**
     * Save the XML elements to a XML file which is stored
     * in a new directory.
     */
    private void writeXMLToFile() {
        try {
            String newPath = createNewFolder();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transf = transformerFactory.newTransformer();

            transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transf.setOutputProperty(OutputKeys.INDENT, "yes");
            transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);

            File myFile = new File(newPath + "/XML_Converted_" + folder.getName() + ".XML");

            log.writeActivityLine(nodes.size() + " nodes added. Destination file: " + myFile.getAbsolutePath(), Log.LineType.ACTIVITY);


            StreamResult file = new StreamResult(myFile);

            transf.transform(source, file);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Create the new directory to store the new XML files if it doesn't already
     * exist.
     *
     * @return the folder path of the new directory.
     */
    private String createNewFolder() {
        String newPath = folder.getParent() + "/XMLOutput";
        File resources = new File(newPath);
        boolean resourcesCreated = resources.mkdirs();
        return newPath;
    }

    private Element generateNodeType(Node currentNode) {
        if (currentNode instanceof RoomNode) {
            return ((RoomNode) currentNode).generateRoomNodeData(doc.createElement("Room"));
        } else if (currentNode instanceof ToiletNode) {
            return ((ToiletNode) currentNode).generateToiletNodeData(doc.createElement("Toilet"));
        } else if (currentNode instanceof FloorChangerNode) {
            return ((FloorChangerNode) currentNode).generateFloorChangerNodeData(doc.createElement("FloorChanger"));
        } else {
            return currentNode.generateNodeData(doc.createElement("Node"));
        }
    }

    private Element generateNodeNeighbours(Node currentNode, Element element) {
        HashSet<Node> nodesNeighbours = currentNode.getNeighbours();
        for (Node currentNeighbour : nodesNeighbours) {
            Element neighbourElement = doc.createElement("Neighbour");
            neighbourElement.setAttribute("id", currentNeighbour.getID());
            element.appendChild(neighbourElement);
        }
        return element;
    }
}
