package mappingData.nodes;

import static org.junit.Assert.assertEquals;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import mappingData.nodes.ToiletNode;

/**
 * Test class for ToiletNode class to check if the
 * toilet type is set correctly with different types of
 * inputs.
 *
 * @author Zeineb Bouchamaoui Sadiyah Khanam and Hana Mizukami
 * @version 02.04.2020
 */

public class ToiletNodeTest {
    /**
     * Valid toilet type input should correctly get the corresponding integer value.
     *
     * 
     */
    @Test
    public void convertToXMLValuesFemalToiletTypeReturnsTwo()  {
            ToiletNode toiletNode = new ToiletNode(
                    "Toilet HenRaph_GF_424_369 = new Toilet( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , ToiletType.Female ); // LAJ2");
            toiletNode.convertToXMLValues();
            String expectedValue = "2";
            assertEquals(expectedValue, toiletNode.getToiletTypeEnum());
    }

    /**
     * Toilet type that does not exist in the list of possible types should return
     * null.
     *
     * 
     */
    @Test
    public void convertToXMLValuesInvalidToiletTypeReturnsNull()  {
            ToiletNode toiletNode = new ToiletNode(
                    "Toilet HenRaph_GF_424_369 = new Toilet( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , ToiletType.anything ); // LAJ2");
            toiletNode.convertToXMLValues();
            String expectedValue = null;
            assertEquals(expectedValue, toiletNode.getToiletTypeEnum());
    }

    /**
     * Toilet type that is almost correct but not written with the exact spelling
     * should return null.
     *
     * 
     */
    @Test
    public void convertToXMLValuesIncorrectToiletTypeSpellingReturnsNull()  {
            ToiletNode toiletNode = new ToiletNode(
                    "Toilet HenRaph_GF_424_369 = new Toilet( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , ToiletType.Femal ); // LAJ2");
            toiletNode.convertToXMLValues();
            String expectedValue = null;
            assertEquals(expectedValue, toiletNode.getToiletTypeEnum());
    }

    /**
     * Toilet type that is almost correct but not written with the exact
     * capitalization should still return the correct value - boundary case.
     *
     * 
     */
    @Test
    public void convertToXMLValuesIncorrectToiletTypeCapitalizationReturnsTwo()  {
            ToiletNode toiletNode = new ToiletNode(
                    "Toilet HenRaph_GF_424_369 = new Toilet( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , ToiletType.female ); // LAJ2");
            toiletNode.convertToXMLValues();
            String expectedValue = "2";
            assertEquals(expectedValue, toiletNode.getToiletTypeEnum());
    }

    /**
     * Correct element is being created for XML.
     *
     * 
     */
    @Test
    public void generateNodeDataForXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            ToiletNode testNode = new ToiletNode(
                    "Toilet HenRaph_GF_424_369 = new Toilet( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , ToiletType.Female ); // LAJ2");
            testNode.setID(0);
            Element value = testNode.generateToiletNodeData(doc.createElement("Toilet"));

            NamedNodeMap attributes = value.getAttributes();
            assertEquals("0", attributes.getNamedItem("id").getNodeValue());
            assertEquals("42.452381f", attributes.getNamedItem("x").getNodeValue());
            assertEquals("36.975510f", attributes.getNamedItem("y").getNodeValue());
            assertEquals("15", attributes.getNamedItem("Floor").getNodeValue());
            assertEquals("2", attributes.getNamedItem("type").getNodeValue());
        }
        catch (Exception e){
            //
        }
    }

    /**
     * Test getFormattedDataTest()
     * Test cases: lines with and without comments
     */
    @Test
    public void getFormattedData() {
        try{
        ToiletNode nodeNoComments = new ToiletNode("Toilet toilet = new Toilet( 45.8375f , 548.3958f , ToiletFloor , ToiletType.Female );");
        ToiletNode nodeWithComments = new ToiletNode("Toilet toilet = new Toilet( 45.8375f , 548.3958f , ToiletFloor , ToiletType.Female ); // comment");
        String noComment = "Toilet toilet = new Toilet( 45.8375f , 548.3958f , ToiletFloor , ToiletType.Female );";
        String withComments = "Toilet toilet = new Toilet( 45.8375f , 548.3958f , ToiletFloor , ToiletType.Female ); // comment should be ignored";
        assertEquals(noComment, nodeNoComments.getFormattedData());
        assertEquals(withComments, nodeWithComments.getFormattedData());
        }
        catch (Exception e){
                System.out.println(e + "getFormattedData test");
        }
    }
}
