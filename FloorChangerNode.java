package mappingData.nodes;

import org.w3c.dom.Element;

/**
 * Class representing the FloorChanger node.
 *
 * @author Error 404: Team Name Not Found
 * @version 18.02.2020
 */

public class FloorChangerNode extends Node {
    private String floorChangerType;
    private String floorChangerTypeEnum;

    public FloorChangerNode(String line) {
        super(line);

    }

    @Override
    protected void extractData(String line) {
        super.extractData(line);
        floorChangerType = rawParameters[3];
        String[] typeSection = floorChangerType.split("\\.", 2);
        String tempType = typeSection[1];
        setFloorChangerTypeEnum(tempType);
    }

    @Override
    public String getFormattedData() {
        String formattedString = "FloorChanger " + nodeName + " = new FloorChanger( " + updatedX.toString() + "f , "
                + updatedY.toString() + "f , " + nodeFloor + " , " + floorChangerType + " );" + comments;
        return formattedString;
    }

    /**
     * Sets the particular attributes that only belong to a node
     * of type FloorChanger.
     *
     * @param currentElement the element that is being created.
     * @return the element after adding the attributes.
     */
    public Element generateFloorChangerNodeData(Element currentElement) {
        currentElement = super.generateNodeData(currentElement);

        if (currentElement == null) {
            return null;
        }

        currentElement.setAttribute("type", getFloorChangerTypeEnum());
        return currentElement;
    }

    public String getFloorChangerTypeEnum() {
        return floorChangerTypeEnum;
    }

    /**
     * Convert FloorChanger Node's floor type to an integer.
     */
    @Override
    public void convertToXMLValues() {
        super.convertToXMLValues();

        /*} catch (Exception e) {
            floorChangerTypeEnum = null;
        }*/

    }

    /**
     * Integer value given to XMLFloorChangerType based on the input floor changer
     * type.
     *
     * @param floorChangerType the floor changer type.
     */
    private void setFloorChangerTypeEnum(String floorChangerType) {
        switch (floorChangerType) {
            case "Lift":
                floorChangerTypeEnum = "1";
                break;
            case "Stairs":
                floorChangerTypeEnum = "2";
                break;
            default:
                floorChangerTypeEnum = "0";
        }
    }
}