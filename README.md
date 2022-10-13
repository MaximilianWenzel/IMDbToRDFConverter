# IMDB to RDF Converter 
This project provides a Java application that can be used to convert the IMDb .tsv files from the official IMDb website (https://www.imdb.com/interfaces/) into a single RDF dump file. An overview of the resulting individual assertions and the schema of the dataset is provided in the 'dataset-summary' directory. In order to run the application, the git repository most be first cloned and the maven command `mvn install` has to be executed afterwards in the directory of the Java library where the pom.xml file is located. Subsequently, the corresponding target folder contains a JAR file with all required dependencies. 

## Running the IMDb to RDF Converter 
To automatically fetch all files from the official website and start the conversion procedure, execute the following command (with adjusted JAR and direcory): 
```
java IMDBConverter-1.0-jar-with-dependencies.jar IMDBToTurtleCLApp -d ./some/directory/ -f
``` 
If the files have been already downloaded and unzipped to the specified directory, the `-f` parameter can be left out in order to directly start the conversion process. The whole conversion process takes about 1h and has no special main memory requirements. The unzipped .tsv files consume about 5.5 GB whereas the resulting RDF dump file (not RDFS-entailed) in Turtle format requires approximately 11 GB of disk space. 
