package mappingData.nodes;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mappingData.utils.enums.*;
import org.w3c.dom.Element;

/**
 * Class representing a node.
 *
 * @author Error 404: Team Name Not Found
 * @version 18.02.2020
 */

public class Node {
    // raw Node parameters (between brackets in text file)
    protected String[] rawParameters;

    // input Data
    protected String nodeName;
    protected Float XCoord;
    protected Float YCoord;
    protected String nodeFloor;
    protected String comments;

    // newData
    protected Float updatedX;
    protected Float updatedY;
    protected int nodeID;
    protected String XMLFloorNumber;
    protected HashSet<Node> neighbours;

    public Node(String line) {
        extractData(line);
        neighbours = new HashSet<>();
    }

    protected void extractData(String line) {
        String[] lineSections = line.split(";", 2);
        String nodeStatement = lineSections[0];
        String unformattedParameters = nodeStatement.split("\\(", 2)[1];
        // set name and comments
        nodeName = nodeStatement.split("=")[0].split(" ")[1];
        comments = lineSections[1];
        // set node parameters
        rawParameters = formatParameters(unformattedParameters);
        XCoord = getFloat(rawParameters[0]);
        YCoord = getFloat(rawParameters[1]);
        nodeFloor = rawParameters[2];
    }

    private String[] formatParameters(String unformattedParameters) {
        String unformattedInput = unformattedParameters.trim(); // in case of trailing whitespaces
        String formattedParameters = unformattedInput.substring(0, unformattedInput.length() - 1); // removes closing
        // bracket
        String[] parametersList = formattedParameters.split(",");
        // trim away spaces
        for (int i = 0; i < parametersList.length; i++) {
            parametersList[i] = parametersList[i].trim();
        }
        return parametersList;
    }

    public Float getXCoord() {
        return XCoord;
    }

    public Float getYCoord() {
        return YCoord;
    }

    public Float getUpdatedX() {
        return updatedX;
    }

    public Float getUpdatedY() {
        return updatedY;
    }

    public String getFloor() {
        return nodeFloor;
    }

    public String getName() {
        return nodeName;
    }

    public String getComments() {
        return comments;
    }

    public String getID() {
        return Integer.toString(nodeID);
    }

    public String getXMLFloorNumber() {
        return XMLFloorNumber;
    }

    public void setUpdatedX(Float newX) {
        updatedX = newX;
    }

    public void setUpdatedY(Float newY) {
        updatedY = newY;
    }

    public void setID(int ID) {
        nodeID = ID;
    }

    public Float getFloat(String value) {
        Pattern p = Pattern.compile("(\\d+(?:\\.\\d+))");
        Matcher m = p.matcher(value);
        while (m.find()) {
            float floatValue = Float.parseFloat(m.group(1));
            return floatValue;
        }
        return -1.0f;
    }

    public String getFormattedData() {
        String formattedString = "Node " + nodeName + " = new Node( " + updatedX.toString() + "f , " + updatedY.toString()
                + "f , " + nodeFloor + " );" + comments;
        return formattedString;
    }

    /**
     * Sets the attribute that all nodes, regardless
     * of type, would have.
     *
     * @param currentElement the element that is being created.
     * @return the element after adding the attributes.
     */
    public Element generateNodeData(Element currentElement) {
        currentElement.setAttribute("id", getID());
        currentElement.setAttribute("x", Float.toString(getXCoord()));
        currentElement.setAttribute("y", Float.toString(getYCoord()));

        if (getXMLFloorNumber() == null) {
            return null;
        }
        currentElement.setAttribute("Floor", getXMLFloorNumber());

        return currentElement;
    }

    /**
     * Convert the Node's floor to an integer
     */
    public void convertToXMLValues() {
        try {
            String[] floorSection = nodeFloor.split("\\.", 2);
            String tempFloor = floorSection[1];
            XMLFloorNumber = findXMLFloorNumber(tempFloor);
        } catch (Exception e) {
            XMLFloorNumber = null;
        }
    }

    /**
     * Get the corresponding integer value for the Enum value passed in.
     *
     * @param tempFloor the string value of the floor
     * @return the string value of the integer floor
     */
    private String findXMLFloorNumber(String tempFloor) {

        for (FloorLevelEnum lvl : FloorLevelEnum.values()) {
            if (lvl.name().equals(tempFloor.toUpperCase())) {
                FloorLevelEnum floor = FloorLevelEnum.valueOf(tempFloor.toUpperCase());
                return Integer.toString(floor.getLevelNumber());
            }
        }
        return null;
    }

    public void setNeighbours(HashSet<Node> neighbours) {
        this.neighbours = neighbours;
    }

    public HashSet<Node> getNeighbours() {
        return neighbours;
    }

    public String getFormattedNeighbours() {
        String tempString = nodeName + ".addAllNeighbours( new List<Node>{ ";
        for (Node neighbour : neighbours) {
            tempString += neighbour.getName() + " , ";
        }
        String formattedString = tempString.substring(0, tempString.length() - 3) + " } );";
        return formattedString;
    }

}
