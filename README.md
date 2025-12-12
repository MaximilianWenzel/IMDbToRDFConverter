# IMDB to RDF Converter 
This project provides a Java application that can be used to convert the IMDb .tsv files from the official IMDb website (https://www.imdb.com/interfaces/) into a single RDF dump file. An overview of the resulting individual assertions and the schema of the dataset is provided in the 'dataset-summary' directory. In order to run the application, the git repository most be first cloned and the maven command `mvn install` has to be executed afterwards in the directory of the Java library where the pom.xml file is located. Subsequently, the corresponding target folder contains a JAR file with all required dependencies. 

## Running the IMDb to RDF Converter 
To automatically fetch all files from the official website and start the conversion procedure, execute the following command (with adjusted JAR and direcory): 
```
java IMDBConverter-1.0-jar-with-dependencies.jar IMDBToTurtleCLApp -d ./some/directory/ -f
``` 
If the files have been already downloaded and unzipped to the specified directory, the `-f` parameter can be left out in order to directly start the conversion process. The whole conversion process takes about 1h and has no special main memory requirements. The unzipped .tsv files consume about 5.5 GB whereas the resulting RDF dump file (not RDFS-entailed) in Turtle format requires approximately 11 GB of disk space. 

## Running with Docker
You can also build and run the application using Docker:

1. **Build the image**:
   ```bash
   docker build . -t imdb-converter
   ```

2. **Run the container**:
   Mount a local volume to `/data` in the container. The application writes output files to this directory.
   ```bash
   docker run -v /absolute/path/to/data:/data imdb-converter -d /data -f
   ```
   * `-d /data`: Tells the app to use the mounted directory.
   * `-f`: Fetches files if they are not present.

## Licensing and Data Usage

**Disclaimer: This software is not affiliated with, endorsed by, or connected to IMDb (Internet Movie Database).**

This tool allows you to download and process data provided by IMDb. This data is subject to IMDb's terms of use.

If you use the data retrieved by this tool, you must comply with the [Non-Commercial Licensing](https://help.imdb.com/article/imdb/general-information/can-i-use-imdb-data-in-my-software/G5JTRESSHJBBHTGX) terms, which typically require you to display the following notice:

> **Information courtesy of IMDb (https://www.imdb.com). Used with permission.**
