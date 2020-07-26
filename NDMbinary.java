package mappingData.models.nodeDataModels;

import mappingData.utils.enums.ControllerEnum;
import mappingData.models.inputModels.InputFolderModel;
import mappingData.models.inputModels.UserInput;
import mappingData.nodes.Node;
import mappingData.utils.dataExtractors.FolderExtractor;
import mappingData.utils.dataProcessors.SetNodeIDProcessor;
import mappingData.utils.dataWriters.BinaryWriter;
import mappingData.utils.dataWriters.TransformedDataWriter;
import mappingData.utils.dataWriters.XMLWriter;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Faith Ong and Hana Mizukami
 */
public class NDMbinary extends NodeDataModel {
    private File inputFolder;

    public NDMbinary(InputFolderModel data) {
        inputFolder = data.getFolder();
    }

    protected FolderExtractor createExtractor() {
        return new FolderExtractor(inputFolder);
    }

    protected SetNodeIDProcessor createProcessor() {
        return new SetNodeIDProcessor((ArrayList<Node>) nodes);
    }

    protected BinaryWriter createWriter() {
        return new BinaryWriter((ArrayList) nodes, inputFolder);
    }

    protected void resetLog() {
        log.resetLog(inputFolder, true);
    }

    protected void setBackController() {
        output.setControllerEnum(ControllerEnum.BINCONVERTER);
    }
}
