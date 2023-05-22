package ds.project4_webservice;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
/**
 The Logger class is responsible for logging the search requests made to the API.
 * @author Ruta Deshpande
 * Email id - rutasurd@andrew.cmu.edu
 * andrew id - rutasurd
 */
public class Logger {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * This method logs the search requests by creating a document with the given search request data,
     * and inserts it into the logs collection in the geolocation database.
     *
     * @param logData a map containing the search request data to be logged
     */
    public static void log(Map<String, String> logData) {
        String timestamp = DATE_FORMAT.format(new Date());
        Document log = new Document("timestamp",timestamp);
        for (Map.Entry<String, String> entry : logData.entrySet()) {
            log.append(entry.getKey(), entry.getValue());
        }
        MongoCollection<Document> collection = MongoConnectionUtil.getCollection();
        collection.insertOne(log);
    }
}
