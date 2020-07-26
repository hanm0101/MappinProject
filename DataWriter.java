package mappingData.utils.dataWriters;

import mappingData.models.outputModel.UIOutputDataModel;
import mappingData.utils.Log;

public interface DataWriter {
    Log log = Log.getInstance();

    void createOutputFile();

    UIOutputDataModel createOutputObject();
}
