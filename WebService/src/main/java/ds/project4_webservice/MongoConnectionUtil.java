package ds.project4_webservice;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 A utility class for establishing a connection to the MongoDB server and
 getting the logs collection.
 * @author Ruta Deshpande
 * Email id - rutasurd@andrew.cmu.edu
 * andrew id - rutasurd
 */
public class MongoConnectionUtil {

    // MongoDB connection string with the username, password, cluster, and database name
    //private static final String CONNECTION_URI = "mongodb+srv://rutasdeshpande:Pittsburgh15217@cluster0.pscahqu.mongodb.net/?retryWrites=true&w=majority";
    private static final String CONNECTION_URI ="mongodb://rutasdeshpande:Pittsburgh15217@ac-pqxcgaq-shard-00-00.pscahqu.mongodb.net:27017,ac-pqxcgaq-shard-00-01.pscahqu.mongodb.net:27017,ac-pqxcgaq-shard-00-02.pscahqu.mongodb.net:27017/test?w=majority&retryWrites=true&tls=true&authMechanism=SCRAM-SHA-1";
    // Name of the MongoDB database
    private static final String DB_NAME = "geolocation";

    // Name of the collection that stores geolocation logs
    private static final String COLLECTION_NAME = "logs";

    // MongoClient instance for establishing a connection to the MongoDB server
    private static com.mongodb.client.MongoClient mongoClient;

    // Initialize the MongoClient instance when the class is loaded into memory
    static {
        ConnectionString connectionString = new ConnectionString(CONNECTION_URI);
        mongoClient = MongoClients.create(connectionString);
    }

    /**
     Returns the logs collection from the geolocation database.
     @return MongoCollection<Document> - The logs collection
     */
    public static MongoCollection<Document> getCollection() {
        MongoDatabase database = mongoClient.getDatabase(DB_NAME);
        return database.getCollection(COLLECTION_NAME);
    }
}