package mappingData.nodes;

import org.w3c.dom.Element;

/**
 * Class representing the Room node.
 *
 * @author Error 404: Team Name Not Found
 * @version 18.02.2020
 */

public class RoomNode extends Node {
    private String roomName;

    public RoomNode(String line) {
        super(line);
    }

    @Override
    protected void extractData(String line) {
        super.extractData(line);
        roomName = rawParameters[3].replace("\"", "");
    }

    @Override
    public String getFormattedData() {
        String formattedString = "Room " + nodeName + " = new Room( "
                + updatedX.toString() + "f , "
                + updatedY.toString() + "f , "
                + nodeFloor + " , "
                + "\""+roomName + "\" );"
                + comments;
        return formattedString;
    }

    /**
     * Sets the particular attributes that only belong to a node
     * of type Room.
     *
     * @param currentElement the element that is being created.
     * @return the element after adding the attributes.
     */
    public Element generateRoomNodeData(Element currentElement) {
        currentElement = super.generateNodeData(currentElement);

        if (currentElement == null) {
            return null;
        }

        currentElement.setAttribute("name", getRoomName());
        return currentElement;
    }

    public String getRoomName() {
        return roomName;
    }
}
