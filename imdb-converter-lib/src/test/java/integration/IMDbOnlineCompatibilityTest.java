package integration;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.net.HttpURLConnection;
import java.net.URL;

public class IMDbOnlineCompatibilityTest {

    private static final String BASE_IRI = "https://datasets.imdbws.com/";
    private static final String[] FILE_NAMES = util.IMDBUtils.IMDB_FILE_NAMES;

    @Test
    @Tag("integration")
    public void testDatasetUrlsAreReachable() {
        for (String fileName : FILE_NAMES) {
            String urlString = BASE_IRI + fileName;
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("HEAD");
                connection.connect();

                int responseCode = connection.getResponseCode();
                // 200 OK or 302 Found (if redirected)
                // IMDb datasets are usually direct, but verifying < 400 is good enough
                if (responseCode >= 400) {
                    throw new AssertionError("URL " + urlString + " returned " + responseCode);
                }

                System.out.println("Verified " + urlString + " - " + responseCode);

            } catch (Exception e) {
                throw new AssertionError("Failed to connect to " + urlString, e);
            }
        }
    }
}
