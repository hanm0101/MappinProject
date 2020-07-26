package mappingData.nodes;

import static org.junit.Assert.assertEquals;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import mappingData.nodes.FloorChangerNode;

/**
 * Test class for FloorChangerNode class. Tests if
 * XMLFloorChangerType field is being set correctly
 * with different inputs.
 *
 * @author Zeineb Bouchamaoui, Sadiyah Khanam and Faith Ann Ong
 * @version 19.02.2020
 */

public class FloorChangerNodeTest {

    /**
     * FloorChanger of type Lift should return one.
     *
     */
    @Test
    public void convertToXMLValuesInputLiftReturnsOne(){
        FloorChangerNode floorChangerNode = new FloorChangerNode(
           "FloorChanger HenRaph_GF_424_369 = new FloorChanger( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , FloorChangerType.Lift ); // LAJ2");
        floorChangerNode.convertToXMLValues();
        String expectedResult = "1";
        assertEquals(expectedResult, floorChangerNode.getFloorChangerTypeEnum());
    }

    /**
     * FloorChanger of type Stairs should return two.
     *
     */
    @Test
    public void convertToXMLValuesInputStairsReturnsTwo()   {
        FloorChangerNode floorChangerNode = new FloorChangerNode(
            "FloorChanger HenRaph_GF_424_369 = new FloorChanger( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , FloorChangerType.Stairs ); // LAJ2");
        floorChangerNode.convertToXMLValues();
        String expectedResult = "2";
        assertEquals(expectedResult, floorChangerNode.getFloorChangerTypeEnum());
    }

    /**
     * FloorChanger of type anything that is not
     * Stairs or Lift should return zero by default.
     *
     * @ 
     */
    @Test
    public void convertToXMLValuesInputRandomReturnsZero(){
        FloorChangerNode floorChangerNode = new FloorChangerNode(
            "FloorChanger HenRaph_GF_424_369 = new FloorChanger( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , FloorChangerType.anything ); // LAJ2");
        floorChangerNode.convertToXMLValues();
        String expectedResult = "0";
        assertEquals(expectedResult, floorChangerNode.getFloorChangerTypeEnum());
    }

    /**
     * FloorChanger of type anything that is not
     * Stairs or Lift with the exact capitalization
     * should return zero by default- boundary case.
     *
     * @ 
     */
    @Test
    public void convertToXMLValuesMixedCapitalisedStairsReturnsZero(){
        FloorChangerNode floorChangerNode = new FloorChangerNode(
            "FloorChanger HenRaph_GF_424_369 = new FloorChanger( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , FloorChangerType.StAiRs ); // LAJ2");
        floorChangerNode.convertToXMLValues();
        String expectedResult = "0";
        assertEquals(expectedResult, floorChangerNode.getFloorChangerTypeEnum());
    }

    /**
     * FloorChanger of type anything that is not Stairs
     * or Lift with the exact same spelling should return
     * zero by default- boundary case.
     *
     */
    @Test
    public void convertToXMLValuesIncorrectSpellingStairsReturnsZero(){
        FloorChangerNode floorChangerNode = new FloorChangerNode(
            "FloorChanger HenRaph_GF_424_369 = new FloorChanger( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , FloorChangerType.Stais ); // LAJ2");
        floorChangerNode.convertToXMLValues();
        String expectedResult = "0";
        assertEquals(expectedResult, floorChangerNode.getFloorChangerTypeEnum());
    }

    /**
     * Test that the node's floor changer type is extracted and interpreted correctly
     */
    @Test
    public void extractDataExtractsFloorChangerTypeEnumCorrectly() {
        FloorChangerNode lift = new FloorChangerNode("FloorChanger HenRaph_GF_424_369 = new FloorChanger( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , FloorChangerType.Lift ); // LAJ2");
        FloorChangerNode stairs = new FloorChangerNode("FloorChanger HenRaph_GF_419_382 = new FloorChanger( 41.976913f , 38.261314f , GuysHeights.HenRaph_GF , FloorChangerType.Stairs );");
        FloorChangerNode other = new FloorChangerNode("FloorChanger HenRaph_GF_419_382 = new FloorChanger( 41.976913f , 38.261314f , GuysHeights.HenRaph_GF , FloorChangerType.SomethingElse );");

        assertEquals("1", lift.getFloorChangerTypeEnum());
        assertEquals("2", stairs.getFloorChangerTypeEnum());
        assertEquals("0", other.getFloorChangerTypeEnum());
    }

    /**
     * Test that getFormattedDataTest() returns the correct string output
     * Test cases: lines with and without comments
     */
    @Test
    public void getFormattedDataGetsCorrectLine() {
        FloorChangerNode liftWithComment = new FloorChangerNode("FloorChanger HenRaph_GF_424_369 = new FloorChanger( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , FloorChangerType.Lift ); // LAJ2");
        FloorChangerNode stairsNoComment = new FloorChangerNode("FloorChanger HenRaph_GF_419_382 = new FloorChanger( 41.976913f , 38.261314f , GuysHeights.HenRaph_GF , FloorChangerType.Stairs );");
        FloorChangerNode otherNoComment = new FloorChangerNode("FloorChanger HenRaph_GF_419_382 = new FloorChanger( 41.976913f , 38.261314f , GuysHeights.HenRaph_GF , FloorChangerType.SomethingElse );");
        stairsNoComment.setUpdatedX(1.111f);
        stairsNoComment.setUpdatedY(-12.102123f);
        liftWithComment.setUpdatedX(1000.24922f);
        liftWithComment.setUpdatedY(-137239.2483f);
        otherNoComment.setUpdatedX(50f);
        otherNoComment.setUpdatedY(-0.29932f);

        assertEquals("FloorChanger HenRaph_GF_424_369 = new FloorChanger( 1000.2492f , -137239.25f , GuysHeights.HenRaph_GF , FloorChangerType.Lift ); // LAJ2", liftWithComment.getFormattedData());
        assertEquals("FloorChanger HenRaph_GF_419_382 = new FloorChanger( 1.111f , -12.102123f , GuysHeights.HenRaph_GF , FloorChangerType.Stairs );", stairsNoComment.getFormattedData());
        assertEquals("FloorChanger HenRaph_GF_419_382 = new FloorChanger( 50.0f , -0.29932f , GuysHeights.HenRaph_GF , FloorChangerType.SomethingElse );", otherNoComment.getFormattedData());
    }

    /**
     * Correct element is being created for XML.
     *
     * @ 
     */
    @Test
    public void generateNodeDataForXML() {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    try{
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        FloorChangerNode testNode = new FloorChangerNode(
            "FloorChanger HenRaph_GF_424_369 = new FloorChanger( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , FloorChangerType.Lift ); // LAJ2");
        testNode.setID(0);
        Element value = testNode.generateFloorChangerNodeData(doc.createElement("FloorChanger"));

        NamedNodeMap attributes = value.getAttributes();
        assertEquals("0", attributes.getNamedItem("id").getNodeValue());
        assertEquals("42.452381f", attributes.getNamedItem("x").getNodeValue());
        assertEquals("36.975510f", attributes.getNamedItem("y").getNodeValue());
        assertEquals("15", attributes.getNamedItem("Floor").getNodeValue());
        assertEquals("1", attributes.getNamedItem("type").getNodeValue());
    }
    catch(Exception e){
        //catch exception
    }
    }
}