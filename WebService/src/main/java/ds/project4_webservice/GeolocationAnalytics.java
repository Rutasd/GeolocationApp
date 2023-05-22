/**
 * This class provides methods to retrieve and process geolocation analytics data
 *  * @author Ruta Deshpande
 *  * Email id - rutasurd@andrew.cmu.edu
 *  * andrew id - rutasurd
 */

/**
 * Reference 1- https://stackoverflow.com/questions/20727257/query-on-top-n-rows-in-mongodb
 * Reference 2 - https://www.baeldung.com/java-mongodb-aggregations
 */
package ds.project4_webservice;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import org.bson.Document;;
import java.util.ArrayList;
import java.util.List;

public class GeolocationAnalytics {
    /**
     * Retrieves the top countries by the number of requests made to the web service
     * @param limit the maximum number of countries to retrieve
     * @return a list of documents representing the top countries and their request counts
     */
    public static List<Document> getTopCountries(int limit) {
        MongoCollection<Document> collection = MongoConnectionUtil.getCollection();
        List<Document> pipeline = new ArrayList<>();
        Document groupFields = new Document("_id", "$Country")
                .append("count", new Document("$sum", 1));

        pipeline.add(new Document("$group", groupFields));
        pipeline.add(new Document("$sort", new Document("count", -1)));
        pipeline.add(new Document("$limit", limit));
        ArrayList<Document> results = new ArrayList<>();
        collection.aggregate(pipeline).into(results);
        for(Document document:results){
            System.out.println(document.toJson());
        }
        return results;
    }

    /**
     * Retrieves the top organizations by the number of requests made to the web service
     * @param limit the maximum number of organizations to retrieve
     * @return a list of documents representing the top organizations and their request counts
     */
    public static List<Document> getTopOrganizations(int limit) {
        MongoCollection<Document> collection = MongoConnectionUtil.getCollection();
        List<Document> pipeline = new ArrayList<>();
        Document groupFields = new Document("_id", "$Organisation")
                .append("count", new Document("$sum", 1));
        pipeline.add(new Document("$group", groupFields));
        pipeline.add(new Document("$sort", new Document("count", -1)));
        pipeline.add(new Document("$limit", limit));
        ArrayList<Document> results = new ArrayList<>();
        collection.aggregate(pipeline).into(results);
        for(Document document:results){
            System.out.println(document.toJson());
        }
        return results;
    }

    /**
     * Retrieves the top client locales by the number of requests made to the web service
     * @param limit the maximum number of client locales to retrieve
     * @return a list of documents representing the top client locales and their request counts
     */
    public static List<Document> getTopClientLocales(int limit) {
        MongoCollection<Document> collection = MongoConnectionUtil.getCollection();
        List<Document> pipeline = new ArrayList<>();
        Document groupFields = new Document("_id", "$ClientLocale")
                .append("count", new Document("$sum", 1));

        pipeline.add(new Document("$group", groupFields));
        pipeline.add(new Document("$sort", new Document("count", -1)));
        pipeline.add(new Document("$limit", limit));
        ArrayList<Document> results = new ArrayList<>();
        collection.aggregate(pipeline).into(results);
        for(Document document:results){
            System.out.println(document.toJson());
        }
        return results;
    }

    /**
     * Retrieves all logs of requests made to the web service
     * @return a list of documents representing all requests made to the web service
     */
    public static List<Document> getAllLogs() {
        MongoCollection<Document> collection = MongoConnectionUtil.getCollection();
        return collection.find().sort(Sorts.descending("timeOfRequest")).into(new ArrayList<>());
    }

}
