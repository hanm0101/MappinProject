package mappingData.nodes;

import mappingData.utils.enums.ToiletTypeEnum;
import org.w3c.dom.Element;

/**
 * Class representing the toilet node.
 *
 * @author Zeineb Bouchamaoui, U Ri (Uri) Lee, Cherry Lim Siang Sue, David Yin
 * and Sadiyah Khanam
 * @version 18.02.2020
 */

public class ToiletNode extends Node {
    private String toiletType;
    private String toiletTypeEnum;

    public ToiletNode(String line) {
        super(line);
    }

    @Override
    protected void extractData(String line) {
        super.extractData(line);
        toiletType = rawParameters[3];
        String[] typeSection = toiletType.split("\\.", 2);
        String tempToiletType = typeSection[1];
        toiletTypeEnum = findToiletType(tempToiletType);
    }

    @Override
    public String getFormattedData() {
        String formattedString = "Toilet " + nodeName + " = new Toilet( " + updatedX.toString() + "f , "
                + updatedY.toString() + "f , " + nodeFloor + " , " + toiletType + " );" + comments;
        return formattedString;

    }

    /**
     * Sets the particular attributes that only belong to a node
     * of type Toilet.
     *
     * @param currentElement the element that is being created.
     * @return the element after adding the attributes.
     */
    public Element generateToiletNodeData(Element currentElement) {
        currentElement = super.generateNodeData(currentElement);

        if (currentElement == null) {
            return null;
        }

        currentElement.setAttribute("type", getToiletTypeEnum());
        return currentElement;
    }

    /**
     * Convert toilet node's toilet type to an integer value.
     */
    @Override
    public void convertToXMLValues() {
        try {
            super.convertToXMLValues();
        } catch (Exception e) {
            toiletTypeEnum = null;
        }
    }

    /**
     * Find the corresponding integer value for the given toilet type.
     *
     * @param tempToiletType a toilet type
     * @return the integer value of the toilet type converted to a string
     */
    private String findToiletType(String tempToiletType) {

        for (ToiletTypeEnum type : ToiletTypeEnum.values()) {
            if (type.name().equals(tempToiletType.toUpperCase())) {
                ToiletTypeEnum toiletType = ToiletTypeEnum.valueOf(tempToiletType.toUpperCase());
                return Integer.toString(toiletType.getToiletTypeNumber());
            }
        }
        return null;
    }

    public String getToiletTypeEnum() {
        return toiletTypeEnum;
    }
}