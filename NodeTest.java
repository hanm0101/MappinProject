package nodes;

import static org.junit.Assert.assertEquals;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import mappingData.nodes.Node;

/**
 * Test class for the Node class to test floor number field is set correctly
 * with different types of inputs.
 *
 * @author Zeineb Bouchamaoui Sadiyah Khanam and Hana Mizukami
 * @version 02.04.2020
 */

public class NodeTest {

    /**
     * Valid floor number input should get the corresponding integer value.
     *
     */
    @Test
    public void convertToXMLValuesValidFloorNoReturnsFifteen(){
        Node testNode = new Node(
            "node HenRaph_GF_467_369 = new node( 46.799503f , 36.975510f , GuysHeights.HenRaph_GF ); ");
        testNode.convertToXMLValues();
        String expectedValue = "15";
        assertEquals(expectedValue, testNode.getXMLFloorNumber());
    }

    /**
     * Floor value that does not exist in the list of possible floors should
     * return null.
     */
    @Test
    public void convertToXMLValuesInvalidFloorNoReturnsNull(){
        Node testNode = new Node(
            "node HenRaph_GF_467_369 = new node( 46.799503f , 36.975510f , GuysHeights.HenRaph_agsbj ); ");
        testNode.convertToXMLValues();
        String expectedValue = null;
        assertEquals(expectedValue, testNode.getXMLFloorNumber());

    }

    /**
     * Floor that is almost correct but not written with the exact
     * capitalization should return the correct value - boundary case.
     */
    @Test
    public void convertToXMLValuesInvalidCapitalisationFloorNoReturnsNull()  {
        Node testNode = new Node(
            "node HenRaph_GF_467_369 = new node( 46.799503f , 36.975510f , GuysHeights.HenRapH_GF ); ");
        testNode.convertToXMLValues();
        String expectedValue = "15";
        assertEquals(expectedValue, testNode.getXMLFloorNumber());
    }

    /**
     * Floor that is almost correct but not written with the exact spelling
     * should return null.
     *
     */
    @Test
    public void convertToXMLValuesInvalidSpellingFloorNoReturnsNull()  {
        Node testNode = new Node(
            "node HenRaph_GF_467_369 = new node( 46.799503f , 36.975510f , GuysHeights.HenRph_02 ); ");
        testNode.convertToXMLValues();
        String expectedValue = null;
        assertEquals(expectedValue, testNode.getXMLFloorNumber());
    }

    /**
     * Correct element is being created for XML.
     *
     */
    @Test
    public void generateNodeDataForXML()  {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Node testNode = new Node(
                        "node HenRaph_GF_467_369 = new node( 46.799503f , 36.975510f , GuysHeights.HenRaph_GF ); ");
            testNode.setID(0);
            Element value = testNode.generateNodeData(doc.createElement("Node"));

            NamedNodeMap attributes = value.getAttributes();
            assertEquals("0", attributes.getNamedItem("id").getNodeValue());
            assertEquals("46.799503f", attributes.getNamedItem("x").getNodeValue());
            assertEquals("36.975510f", attributes.getNamedItem("y").getNodeValue());
            assertEquals("15", attributes.getNamedItem("Floor").getNodeValue());
        }
        catch (Exception e){
            //
        }
    }

    /**
     * Test the correct format of the node data
     */
    @Test
    public void getFormattedData()  {
        try{
        Node testNode = new Node("Node HenRaph_GF_467_369 = new Node( 46.799503f , 36.975510f , GuysHeights.HenRph_02 ); ");
        String expectedValue = "Node " + testNode.getName() + " = new Node( " + testNode.getUpdatedX().toString() + "f , " + testNode.getUpdatedY().toString()
        + "f , " + testNode.getFloor() + " );" + testNode.getComments();
        assertEquals(expectedValue, testNode.getFormattedData());
        }
        catch (Exception e){
            System.out.println(e + "in getFormattedData");
        }
    }

    /**
     * Test the correct format of the node data
     */
    @Test
    public void getFormattedNeighbours()  {
        Node testNode = new Node("Node HenRaph_GF_467_369 = new Node( 46.799503f , 36.975510f , GuysHeights.HenRph_02 ); ");
        String subString = testNode.getName() + ".addAllNeighbours( new List<Node>{ ";
        for (Node neighbour : testNode.getNeighbours()) {
            subString += neighbour.getName() + " , ";
        }
        String expectedValue = subString.substring(0, subString.length() - 3) + " } );";
        assertEquals(expectedValue, testNode.getFormattedNeighbours());
    }
    
}