import models.*;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.XSD;
import org.eclipse.rdf4j.rio.turtle.TurtleWriter;
import org.junit.jupiter.api.Test;
import readers.*;

import java.io.File;

import java.io.FileOutputStream;
import java.nio.file.Paths;

public class IMDBIterationTests {

    public static File getRootDir() {
        return Paths.get(System.getProperty("user.dir"), "src", "test").toFile();
    }

    public static File getResourcesDir() {
        return Paths.get(getRootDir().toString(), "resources").toFile();
    }

    public static File getResource(String resource) {
        return Paths.get(getResourcesDir().toString(), resource).toFile();
    }

    @Test
    public void testIMDBToRDFConversion() throws java.io.IOException {
        File tempFile = File.createTempFile("imdb", ".ttl");
        tempFile.deleteOnExit();
        TurtleWriter turtleWriter = new TurtleWriter(new FileOutputStream(tempFile));
        turtleWriter.startRDF();
        turtleWriter.handleNamespace(RDF.PREFIX, RDF.NAMESPACE);
        turtleWriter.handleNamespace(RDFS.PREFIX, RDFS.NAMESPACE);
        turtleWriter.handleNamespace(XSD.PREFIX, XSD.NAMESPACE);

        turtleWriter.handleNamespace(IMDBToRDFConverter.IMDB.getPrefix(), IMDBToRDFConverter.IMDB.getName());

        IMDBToRDFConverter converter = new IMDBToRDFConverter(getResourcesDir()) {
            @Override
            protected void onStart() {
            }

            @Override
            protected void onFinish() {
            }

            @Override
            public void process(Statement statement) {
                turtleWriter.handleStatement(statement);
            }
        };
        converter.start();
        turtleWriter.endRDF();
    }

    @Test
    public void testTitleAkasReader() {
        IMDBTitleAkasReader reader = new IMDBTitleAkasReader(getResource("title.akas.tsv"));
        for (int i = 0; reader.hasNext() && i < 10; i++) {
            IMDBTitleAkasRow row = reader.next();
            System.out.println(row);
        }
    }

    @Test
    public void testTitleBasicsReader() {
        IMDBTitleBasicsReader reader = new IMDBTitleBasicsReader(getResource("title.basics.tsv"));
        for (int i = 0; reader.hasNext() && i < 10; i++) {
            IMDBTitleBasicsRow row = reader.next();
            System.out.println(row);
        }
    }

    @Test
    public void testTitlePrincipalsReader() {
        IMDBTitlePrincipalsReader reader = new IMDBTitlePrincipalsReader(getResource("title.principals.tsv"));
        for (int i = 0; reader.hasNext() && i < 10; i++) {
            IMDBTitlePrincipalsRow row = reader.next();
            System.out.println(row);
        }
    }

    @Test
    public void testTitleCrewReader() {
        IMDBTitleCrewReader reader = new IMDBTitleCrewReader(getResource("title.crew.tsv"));
        for (int i = 0; reader.hasNext() && i < 10; i++) {
            IMDBTitleCrewRow row = reader.next();
            System.out.println(row);
        }
    }

    @Test
    public void testRatingsReader() {
        IMDBRatingsReader reader = new IMDBRatingsReader(getResource("title.ratings.tsv"));
        for (int i = 0; reader.hasNext() && i < 10; i++) {
            IMDBRatingsRow row = reader.next();
            System.out.println(row);
        }
    }

    @Test
    public void testNameBasicsReader() {
        IMDBNameBasicsReader reader = new IMDBNameBasicsReader(getResource("name.basics.tsv"));
        for (int i = 0; reader.hasNext() && i < 10; i++) {
            IMDBNameBasicsRow row = reader.next();
            System.out.println(row);
        }
    }

    @Test
    public void testEpisodeReader() {
        IMDBEpisodeReader reader = new IMDBEpisodeReader(getResource("title.episode.tsv"));
        for (int i = 0; reader.hasNext() && i < 10; i++) {
            IMDBEpisodeRow row = reader.next();
            System.out.println(row);
        }
    }
}
