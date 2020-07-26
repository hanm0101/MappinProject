/*package writers;
import mappingData.nodes.FloorChangerNode;
import mappingData.nodes.Node;
import mappingData.nodes.ToiletNode;
import mappingData.utils.dataWriters.*;
import static org.junit.Assert.*;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
*/
/*
public class BinaryWriterTest{
    private BinaryWriter bw;
    private static final String PARENT_FILE_NAME = "test_folder";
    private File folder = new File(
        getClass().getClassLoader().getResource(PARENT_FILE_NAME).getFile()
    );
    private final String PARENT_PATH = getClass().getClassLoader().getResource(PARENT_FILE_NAME).toExternalForm();
    private final String LOG_PATH = PARENT_PATH + "log/log_"+ PARENT_FILE_NAME;
    private final String OUTPUT_PATH = PARENT_PATH + "BinaryOutput/bin_converted_" + PARENT_FILE_NAME + ".bin";
    private final int VERSION = 1;
    private HashSet<Node> neighbours; 
    private HashSet<Node> emptyNeighbours;
    private File logFile;
    private File outputFile;
    private ArrayList<Node> nodes;
    private DataInputStream dis;
    private Node genericNode;

*/
    /**
     * Initial general set up for all tests 
     * Initialises empty list of nodes and binary writer 
     */
    /*
    @Before
    public void setUp(){
        nodes = new ArrayList<>();
        bw = new BinaryWriter(nodes, folder);
        genericNode = new Node("Node HenRaph_GF_408_393 = new Node( 40.822208f , 39.303856f , GuysHeights.HenRaph_GF );");
        genericNode.setID(1);
        setUpNeighbours();
        setUpEmptyNeighbours();
    }

    private void setUpNeighbours(){
        neighbours = new HashSet<>();
        Node node1 = new Node("Node HenRaph_GF_467_369 = new Node( 46.799503f , 36.975510f , GuysHeights.HenRaph_GF );");
        Node node2 = new Node("Node HenRaph_GF_467_369 = new Node( 46.799503f , 36.975510f , GuysHeights.HenRaph_GF );");
        Node node3 = new Node("Node HenRaph_GF_467_369 = new Node( 46.799503f , 36.975510f , GuysHeights.HenRaph_GF );");
        neighbours.add(node1);
        neighbours.add(node2);
        neighbours.add(node3);
    }

    private void setUpEmptyNeighbours(){
        emptyNeighbours = new HashSet<>();
    }
    
    private void makeDataInputStream(){
        try {
			dis = new DataInputStream(new FileInputStream(OUTPUT_PATH));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    private void readHeader(){
        try {
            dis.readInt();
            dis.readLong();
		} catch (IOException e) {
            e.printStackTrace();
            System.out.println(e + "in readHeader");
		} 
    }

    private void newProcess(){
        bw = new BinaryWriter(nodes, folder);
        bw.createOutputFile();
        makeDataInputStream();   
    }

    @Test 
    public void convertedHeaderTest(){
        nodes.add(genericNode);
        Date now = new Date();
        long timeLong = now.getTime();
        newProcess();
        try{
            int versionInt = dis.readInt();
            long timestampLong = dis.readLong();
            assertEquals(VERSION, versionInt);
            assertTrue(timestampLong - timeLong < 1000);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(e + "in convertedHeaderTest");
        }
        
    }

    @Test 
    public void writeTotalNodeNumberTest(){
        try{
            newProcess();
            readHeader();
            assertEquals(0, dis.readInt());

            nodes.add(genericNode);
            newProcess();
            readHeader();
            assertEquals(1, dis.readInt());

            for(int i = 0; i<9; i++){
                Node node = new Node("Node HenRaph_GF_408_393 = new Node( 40.822208f , 39.303856f , GuysHeights.HenRaph_GF );");
                node.setID(i+2);
                nodes.add(node);
            }
            newProcess();
            readHeader();
            assertEquals(10, dis.readInt());

            //max number an int can store 
            //CAN DELETE IF TAKES TOO MUCH TIME
            for(int i = 0; i<2147483637; i++){
                Node node = new Node("Node HenRaph_GF_408_393 = new Node( 40.822208f , 39.303856f , GuysHeights.HenRaph_GF );");
                node.setID(i+11);
                nodes.add(node);
            }
            newProcess();
            readHeader();
            assertEquals(2147483647, dis.readInt());
        }
        catch(Exception e){
            System.out.println(e + "in writeNodeTotalNumberTest");
        }
    }
*/
    /*@Test 
    public void writeNeighboursTest(){

    }*/
/*
    @Test 
    public void convertOneToiletNodeTest(){
        Node toilet = new ToiletNode("Toilet HenRaph_GF_424_369 = new Toilet( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , ToiletType.female ); // LAJ2");
        toilet.setID(1);
        nodes.add(toilet);
        bw.createOutputFile();
        makeDataInputStream();
        readHeader();
        assertEquals(1, dis.readInt());
    }

    @Test
    public void convertOneFloorChangerTest(){
        nodes.add(new FloorChangerNode("FloorChanger HenRaph_GF_424_369 = new FloorChanger( 42.452381f , 36.975510f , GuysHeights.HenRaph_GF , FloorChangerType.Lift ); // LAJ2"));
    }

    @Test 
    public void convertOneNodeTest(){
        nodes.add(new Node("Node HenRaph_GF_408_393 = new Node( 40.822208f , 39.303856f , GuysHeights.HenRaph_GF );"));
    }

    @Test
    public void convertOneRoomNodeTest(){
        nodes.add(new RoomNode("Room HenRaph_GF_616_374 = new Room( 61.674816f , 37.462032f , GuysHeights.HenRaph_GF , "HR G.2" );"));
    }

    @Test
    public void convertMultipleNodesTest(){
        nodes.add(new Node("Node HenRaph_GF_408_393 = new Node( 40.822208f , 39.303856f , GuysHeights.HenRaph_GF );"));
    }

    @Test
    public void convertNoNodesTest(){
        nodes.add(new Node("Node HenRaph_GF_408_393 = new Node( 40.822208f , 39.303856f , GuysHeights.HenRaph_GF );"));
    }
    
}*/