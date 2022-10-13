import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.eclipse.rdf4j.model.vocabulary.XSD;
import org.eclipse.rdf4j.rio.turtle.TurtleWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Paths;

public class IMDBToTurtleConverter extends IMDBToRDFConverter {

    private File outputFile;
    private TurtleWriter turtleWriter;
    private long processedStatements = 0;

    public IMDBToTurtleConverter(File imdbDirectory) {
        super(imdbDirectory);
        outputFile = Paths.get(imdbDirectory.toString(), "imdb.ttl").toFile();
    }

    @Override
    protected void onStart() {
        try {
            turtleWriter = new TurtleWriter(new FileOutputStream(outputFile));
            turtleWriter.startRDF();
            turtleWriter.handleNamespace(RDF.PREFIX, RDF.NAMESPACE);
            turtleWriter.handleNamespace(RDFS.PREFIX, RDFS.NAMESPACE);
            turtleWriter.handleNamespace(XSD.PREFIX, XSD.NAMESPACE);
            turtleWriter.handleNamespace(IMDBToRDFConverter.IMDB.getPrefix(), IMDBToRDFConverter.IMDB.getName());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onFinish() {
        turtleWriter.endRDF();
    }

    @Override
    public void process(Statement statement) {
        turtleWriter.handleStatement(statement);
        processedStatements++;
        if (processedStatements % 1_000_000 == 0) {
            log.info("Processed " + processedStatements + " statements.");
        }
    }
}
