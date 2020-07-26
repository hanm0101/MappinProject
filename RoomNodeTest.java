package nodes;

import static org.junit.Assert.assertEquals;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import mappingData.nodes.RoomNode;

/**
 * Tests behaviour specific to the RoomNode class
 *
 * @author Faith Ann Ong
 * @version 17.03.2020
 */
public class RoomNodeTest {
    /**
     * Test that extractData method extracts the node's room name correctly
     */
    @Test
    public void extractDataExtractsRoomNameCorrectly() {
        RoomNode hrg2dot2 = new RoomNode("Room HenRaph_GF_554_385 = new Room( 55.493752f , 38.591454f , GuysHeights.HenRaph_GF , \"HR G.2.2\" );");
        RoomNode henrisDeli = new RoomNode("Room HenRaph_GF_404_402 = new Room( 40.414665f , 40.242146f , GuysHeights.HenRaph_GF , \"Henri's Deli (HR G.1.1)\" );");
        RoomNode blank = new RoomNode("Room HenRaph_GF_404_402 = new Room( 40.414665f , 40.242146f , GuysHeights.HenRaph_GF , \"  \" );");

        assertEquals("HR G.2.2", hrg2dot2.getRoomName());
        assertEquals("Henri's Deli (HR G.1.1)", henrisDeli.getRoomName());
        assertEquals("  ", blank.getRoomName());
    }

    /**
     * Test that getFormattedDataTest() returns the correct string output
     * Test cases: lines with and without comments
     */
    @Test
    public void getFormattedDataGetsCorrectLine() {
        RoomNode noComment = new RoomNode("Room HenRaph_GF_554_385 = new Room( 55.493752f , 38.591454f , GuysHeights.HenRaph_GF , \"HR G.2.2\" );");
        RoomNode withComment = new RoomNode("Room comments = new Room( 40.414665f , 40.242146f , GuysHeights.HenRaph_GF , \"comments room\" ); // COMMENT");
        noComment.setUpdatedX(1.111f);
        noComment.setUpdatedY(-12.102123f);
        withComment.setUpdatedX(1000.24922f);
        withComment.setUpdatedY(-137239.2483f);
        String noCommentUpdated = "Room HenRaph_GF_554_385 = new Room( 1.111f , -12.102123f , GuysHeights.HenRaph_GF , \"HR G.2.2\" );";
        String withCommentUpdated = "Room comments = new Room( 1000.2492f , -137239.25f , GuysHeights.HenRaph_GF , \"comments room\" ); // COMMENT";
        assertEquals(noCommentUpdated, noComment.getFormattedData());
        assertEquals(withCommentUpdated, withComment.getFormattedData());
    }

    /**
     * Correct element is being created for XML.
     *
     */
    @Test
    public void generateNodeDataForXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            RoomNode testNode = new RoomNode(
                    "Room HenRaph_GF_554_385 = new Room( 55.493752f , 38.591454f , GuysHeights.HenRaph_GF , \"HR G.2.2\" );");
            testNode.setID(0);
            Element value = testNode.generateRoomNodeData(doc.createElement("Room"));

            NamedNodeMap attributes = value.getAttributes();
            assertEquals("0", attributes.getNamedItem("id").getNodeValue());
            assertEquals("55.493752f", attributes.getNamedItem("x").getNodeValue());
            assertEquals("38.591454f", attributes.getNamedItem("y").getNodeValue());
            assertEquals("15", attributes.getNamedItem("Floor").getNodeValue());
            assertEquals("\"HR G.2.2\"", attributes.getNamedItem("name").getNodeValue());
        }
        catch (Exception e){
            //
        }
    }
}