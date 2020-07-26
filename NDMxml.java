package mappingData.models.nodeDataModels;

import mappingData.utils.enums.ControllerEnum;
import mappingData.models.inputModels.InputFolderModel;
import mappingData.nodes.Node;
import mappingData.utils.dataExtractors.FolderExtractor;
import mappingData.utils.dataProcessors.SetNodeIDProcessor;
import mappingData.utils.dataWriters.XMLWriter;

import java.io.File;
import java.util.ArrayList;

public class NDMxml extends NodeDataModel {
    private File inputFolder;

    public NDMxml(InputFolderModel data) {
        inputFolder = data.getFolder();

    }

    protected FolderExtractor createExtractor() {
        return new FolderExtractor(inputFolder);
    }

    protected SetNodeIDProcessor createProcessor() {
        return new SetNodeIDProcessor((ArrayList<Node>) nodes);
    }

    protected XMLWriter createWriter() {
        return new XMLWriter((ArrayList) processedData, inputFolder);
    }

    protected void resetLog() {
        log.resetLog(inputFolder, true);
    }

    protected void setBackController() {
        output.setControllerEnum(ControllerEnum.XMLCONVERTER);
    }
}
