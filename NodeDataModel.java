package mappingData.models.nodeDataModels;

import mappingData.models.outputModel.UIOutputDataModel;
import mappingData.models.outputModel.UIOutputMessageModel;
import mappingData.utils.Log;
import mappingData.utils.dataExtractors.DataExtractor;
import mappingData.utils.dataProcessors.DataProcessor;
import mappingData.utils.dataWriters.DataWriter;

public abstract class NodeDataModel<T> {
    protected DataExtractor extractor;
    protected DataProcessor processor;
    protected DataWriter writer;
    protected T nodes; // original nodes from input file
    protected T processedData; // result of processing
    protected UIOutputDataModel output;
    protected Log log = Log.getInstance();

    public NodeDataModel() {
    }

    public UIOutputDataModel handleInput() {
        resetLog();
        extractor = createExtractor();
        nodes = (T) extractor.extractData();
        processor = createProcessor();
        processedData = (T) processor.processData();
        if (processedData == null) {
            makeFailedOutput();
        } else {
            writer = createWriter();
            writer.createOutputFile();
            output = writer.createOutputObject();
            setBackController();
        }
        return output;
    }

    abstract DataExtractor createExtractor();

    abstract DataProcessor createProcessor();

    abstract DataWriter createWriter();

    abstract void resetLog();

    abstract void setBackController();

    private UIOutputDataModel makeFailedOutput() {
        output = new UIOutputMessageModel(false);
        ((UIOutputMessageModel) output).addNewOutputLine("Reference Node not found. Please check your inputs for mistakes.");
        setBackController();
        log.writeActivityLine("Process cancelled.", Log.LineType.ERROR);
        return output;
    }
}
