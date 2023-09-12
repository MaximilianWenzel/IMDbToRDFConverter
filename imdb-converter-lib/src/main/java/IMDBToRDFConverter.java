import models.*;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.rdf4j.model.*;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import readers.*;
import util.ConsoleUtils;
import util.IMDBUtils;
import util.LanguageUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

public abstract class IMDBToRDFConverter {

    public static Logger log = ConsoleUtils.getLogger();

    public static Namespace IMDB = Values.namespace("imdb", "https://www.imdb.com/");
    private ValueFactory valueFactory = Values.getValueFactory();
    private File imdbDirectory;

    private Set<String> imdbTitleTypes = new UnifiedSet<>();
    private Set<String> titleTypes = new UnifiedSet<>();
    private Set<String> genres = new UnifiedSet<>();

    private Map<String, IRI> jobCategoryToPredicateIRI = new HashMap<>();
    private Map<String, IRI> jobCategoryToClassIRI = new HashMap<>();

    //private Map<String, Set<String>> jobCategoryToJobTitles = new HashMap<>();
    private IRI primaryName = Values.iri(IMDB, "primaryName");
    private IRI primaryTitle = Values.iri(IMDB, "primaryTitle");
    private IRI originalTitle = Values.iri(IMDB, "originalTitle");
    private IRI birthYear = Values.iri(IMDB, "birthYear");
    private IRI deathYear = Values.iri(IMDB, "deathYear");
    private IRI knownFor = Values.iri(IMDB, "knownFor");
    private IRI primaryProfessionPred = Values.iri(IMDB, "primaryProfession");
    private IRI isAdult = Values.iri(IMDB, "isAdult");
    private IRI startYear = Values.iri(IMDB, "startYear");
    private IRI endYear = Values.iri(IMDB, "endYear");
    private IRI runTimeMinutes = Values.iri(IMDB, "runTimeMinutes");
    private IRI directedBy = Values.iri(IMDB, "directedBy");
    private IRI writtenBy = Values.iri(IMDB, "writtenBy");

    private IRI cast = Values.iri(IMDB, "cast");

    private IRI directorClass = Values.iri(IMDB, "director");
    private IRI writerClass = Values.iri(IMDB, "writer");

    private IRI hasJobCategory = Values.iri(IMDB, "hasJobCategory");
    private IRI jobTitle = Values.iri(IMDB, "jobTitle");
    private IRI playedCharacter = Values.iri(IMDB, "playedCharacter");
    private IRI episodeOf = Values.iri(IMDB, "episodeOf");
    private IRI episodeNumber = Values.iri(IMDB, "episodeNumber");
    private IRI seasonNumber = Values.iri(IMDB, "seasonNumber");
    private IRI averageRating = Values.iri(IMDB, "averageRating");
    private IRI numberOfVotes = Values.iri(IMDB, "numberOfVotes");

    private IRI workClass = Values.iri(IMDB, "work");
    private IRI personClass = Values.iri(IMDB, "person");

    private IRI titlePred = Values.iri(IMDB, "hasTitle");
    private IRI publishedInPred = Values.iri(IMDB, "publishedIn");
    private IRI descriptionTermPred = Values.iri(IMDB, "descriptionTerm");
    private IRI imdbTitleClass = Values.iri(IMDB, "imdbTitle");
    private IRI genreClass = Values.iri(IMDB, "genre");

    public IMDBToRDFConverter(File imdbDirectory) {
        this.imdbDirectory = imdbDirectory;
    }

    public void start() {
        onStart();
        log.info("Converting 'title.akas.tsv'...");
        titleAkasConversion();
        log.info("Converting 'name.basics.tsv'...");
        nameBasicsConversion();
        log.info("Converting 'title.basics.tsv'...");
        titleBasicsConversion();
        log.info("Converting 'title.crew.tsv'...");
        titleCrewConversion();
        log.info("Converting 'title.principal.tsv'...");
        titlePrincipalsConversion();
        log.info("Converting 'title.episode.tsv'...");
        titleEpisodesConversion();
        log.info("Converting 'title.rating.tsv'...");
        titleRatingsConversion();

        initClasses();
        onFinish();
    }

    private void initClasses() {
        // process(valueFactory.createStatement(directedBy, RDFS.DOMAIN, movie));
        process(valueFactory.createStatement(directedBy, RDFS.RANGE, directorClass));
        process(valueFactory.createStatement(writtenBy, RDFS.RANGE, writerClass));

        for (String titleType : titleTypes) {
            process(valueFactory.createStatement(
                    Values.iri(IMDB, titleType),
                    RDFS.SUBCLASSOF,
                    imdbTitleClass
            ));
        }

        for (String imdbTitleTypes : imdbTitleTypes) {
            process(valueFactory.createStatement(
                    Values.iri(IMDB, imdbTitleTypes),
                    RDFS.SUBCLASSOF,
                    imdbTitleClass
            ));
        }

        for (IRI jobCategoryIRI : jobCategoryToClassIRI.values()) {
            process(valueFactory.createStatement(
                    jobCategoryIRI,
                    RDFS.SUBCLASSOF,
                    personClass
            ));
        }

        for (String genre : genres) {
            process(valueFactory.createStatement(
                    Values.iri(IMDB, genre),
                    RDF.TYPE,
                    genreClass
            ));
            process(valueFactory.createStatement(
                    Values.iri(IMDB, genre),
                    RDFS.SUBCLASSOF,
                    workClass
            ));
        }
    }

    private void titleAkasConversion() {
        IMDBTitleAkasReader reader = new IMDBTitleAkasReader(IMDBUtils.getTitleAkasFile(imdbDirectory));
        while (reader.hasNext()) {
            IMDBTitleAkasRow row = reader.next();

            Resource titleID = Values.iri(IMDB, row.getTitleId());

            Statement statement;
            if (row.getRegion() != null) {
                String languageTag;
                if (row.getLanguage() != null) {
                    languageTag = row.getLanguage();
                } else {
                    languageTag = LanguageUtils.forRegion(row.getRegion());
                }
                statement = valueFactory.createStatement(
                        titleID,
                        titlePred,
                        languageTag == null ? Values.literal(row.getTitle()) : Values.literal(row.getTitle(), languageTag)
                );
                process(statement);
            }

            if (row.getImdbTypes() != null) {
                for (String imdbType : row.getImdbTypes()) {
                    imdbTitleTypes.add(imdbType);
                    statement = valueFactory.createStatement(
                            titleID,
                            RDF.TYPE,
                            Values.iri(IMDB, imdbType)
                    );
                    process(statement);
                }
            }

            if (row.getAttributes() != null) {
                for (String attribute : row.getAttributes()) {
                    statement = valueFactory.createStatement(
                            titleID,
                            descriptionTermPred,
                            Values.literal(attribute)
                    );
                    process(statement);
                }
            }
        }
    }

    private void nameBasicsConversion() {
        IMDBNameBasicsReader reader = new IMDBNameBasicsReader(IMDBUtils.getNameBasicsFile(imdbDirectory));
        while (reader.hasNext()) {
            IMDBNameBasicsRow row = reader.next();


            Resource nameId = Values.iri(IMDB, row.getNameId());

            Statement statement;
            if (row.getPrimaryName() != null) {
                statement = valueFactory.createStatement(
                        nameId,
                        primaryName,
                        Values.literal(row.getPrimaryName())
                );
                process(statement);
            }

            if (row.getBirthYear() != null) {
                statement = valueFactory.createStatement(
                        nameId,
                        birthYear,
                        Values.literal(row.getBirthYear())
                );
                process(statement);
            }

            if (row.getDeathYear() != null) {
                statement = valueFactory.createStatement(
                        nameId,
                        deathYear,
                        Values.literal(row.getDeathYear())
                );
                process(statement);
            }

            if (row.getPrimaryProfession() != null) {
                for (String primaryProfession : row.getPrimaryProfession()) {
                    IRI job = getJobCategoryClass(primaryProfession);
                    statement = valueFactory.createStatement(
                            nameId,
                            primaryProfessionPred,
                            job
                    );
                    process(statement);

                    statement = valueFactory.createStatement(
                            nameId,
                            RDF.TYPE,
                            job
                    );
                    process(statement);
                }
            }

            if (row.getKnownForTitles() != null) {
                for (String titleId : row.getKnownForTitles()) {
                    statement = valueFactory.createStatement(
                            nameId,
                            knownFor,
                            Values.iri(IMDB, titleId)
                    );
                    process(statement);
                }
            }
        }
    }

    private void titleBasicsConversion() {
        IMDBTitleBasicsReader reader = new IMDBTitleBasicsReader(IMDBUtils.getTitleBasicsFile(imdbDirectory));

        while (reader.hasNext()) {
            IMDBTitleBasicsRow row = reader.next();

            Statement statement;
            Resource titleId = Values.iri(IMDB, row.getTitleId());

            if (row.getTitleType() != null) {
                titleTypes.add(row.getTitleType());
                statement = valueFactory.createStatement(
                        titleId,
                        RDF.TYPE,
                        Values.iri(IMDB, row.getTitleType())
                );
                process(statement);
            }

            if (row.getPrimaryTitle() != null) {
                statement = valueFactory.createStatement(
                        titleId,
                        primaryTitle,
                        Values.literal(row.getPrimaryTitle())
                );
                process(statement);
            }

            if (row.getOriginalTitle() != null) {
                statement = valueFactory.createStatement(
                        titleId,
                        originalTitle,
                        Values.literal(row.getOriginalTitle())
                );
                process(statement);
            }

            if (row.getAdult() != null) {
                statement = valueFactory.createStatement(
                        titleId,
                        isAdult,
                        Values.literal(row.getAdult().booleanValue())
                );
                process(statement);
            }

            if (row.getStartYear() != null) {
                statement = valueFactory.createStatement(
                        titleId,
                        startYear,
                        Values.literal(row.getStartYear())
                );
                process(statement);
            }

            if (row.getEndYear() != null) {
                statement = valueFactory.createStatement(
                        titleId,
                        endYear,
                        Values.literal(row.getEndYear())
                );
                process(statement);
            }

            if (row.getRuntimeMinutes() != null) {
                statement = valueFactory.createStatement(
                        titleId,
                        runTimeMinutes,
                        Values.literal(row.getRuntimeMinutes())
                );
                process(statement);
            }

            if (row.getGenres() != null) {
                for (String genre : row.getGenres()) {
                    genres.add(genre);
                    statement = valueFactory.createStatement(
                            titleId,
                            RDF.TYPE,
                            Values.iri(IMDB, genre)
                    );
                    process(statement);
                }
            }
        }
    }

    private void titleCrewConversion() {
        IMDBTitleCrewReader reader = new IMDBTitleCrewReader(IMDBUtils.getTitleCrewFile(imdbDirectory));
        while (reader.hasNext()) {
            IMDBTitleCrewRow row = reader.next();
            Statement statement;
            Resource titleId = Values.iri(IMDB, row.getTitleId());

            if (row.getDirectors() != null) {
                for (String director : row.getDirectors()) {
                    Resource directorResource = Values.iri(IMDB, director);
                    statement = valueFactory.createStatement(
                            titleId,
                            directedBy,
                            directorResource
                    );
                    process(statement);
                }
            }

            if (row.getWriters() != null) {
                for (String writer : row.getWriters()) {
                    Resource writerResource = Values.iri(IMDB, writer);

                    statement = valueFactory.createStatement(
                            titleId,
                            writtenBy,
                            writerResource
                    );
                    process(statement);

                }
            }
        }
    }

    private IRI getJobCategoryPredicate(String jobCategory) {
        return jobCategoryToPredicateIRI.computeIfAbsent(jobCategory, (val) ->
        {
            IRI pred = Values.iri(IMDB, "cast-" + val);
            process(valueFactory.createStatement(pred, RDFS.DOMAIN, this.workClass));
            process(valueFactory.createStatement(pred, RDFS.RANGE, getJobCategoryClass(val)));
            process(valueFactory.createStatement(pred, RDFS.SUBPROPERTYOF, cast));
            return pred;
        });
    }

    private IRI getJobCategoryClass(String jobCategory) {
        return jobCategoryToClassIRI.computeIfAbsent(jobCategory, (val) -> Values.iri(IMDB, val));
    }

    private void titlePrincipalsConversion() {
        /*
        tconst	ordering	nconst	category	job	characters
        tt0000001	1	nm1588970	self	\N	["Self"]
        tt0000001	2	nm0005690	director	\N	\N
         */
        IMDBTitlePrincipalsReader reader = new IMDBTitlePrincipalsReader(IMDBUtils.getTitlePrincipalsFile(imdbDirectory));
        while (reader.hasNext()) {
            IMDBTitlePrincipalsRow row = reader.next();
            Statement statement;
            Resource titleId = Values.iri(IMDB, row.getTitleId());
            Resource nameId = Values.iri(IMDB, row.getNameId());

            Resource statementID = Values.bnode();

            IRI predicate;
            if (row.getJobCategory() != null) {
                predicate = getJobCategoryPredicate(row.getJobCategory());
            } else {
                predicate = cast;
            }

            statement = valueFactory.createStatement(
                    titleId,
                    predicate,
                    nameId
            );
            process(statement);

            if (row.getJobTitle() != null || row.getCharacters() != null) {
                createReifiedStatementTriples(
                        this::process,
                        titleId,
                        predicate,
                        nameId,
                        statementID
                );
                if (row.getJobTitle() != null) {
                    statement = valueFactory.createStatement(
                            statementID,
                            jobTitle,
                            Values.literal(row.getJobTitle())
                    );
                    process(statement);
                }

                if (row.getCharacters() != null) {
                    for (String character : row.getCharacters()) {
                        statement = valueFactory.createStatement(
                                statementID,
                                playedCharacter,
                                Values.literal(character)
                        );
                        process(statement);
                    }
                }
            }
        }
    }

    private void createReifiedStatementTriples(Consumer<Statement> tripleConsumer,
                                               Resource titleId, IRI cast, Resource nameId, Resource statementID) {
        tripleConsumer.accept(
                valueFactory.createStatement(statementID, RDF.SUBJECT, titleId)
        );
        tripleConsumer.accept(
                valueFactory.createStatement(statementID, RDF.PREDICATE, cast)
        );
        tripleConsumer.accept(
                valueFactory.createStatement(statementID, RDF.OBJECT, nameId)
        );
        tripleConsumer.accept(
                valueFactory.createStatement(statementID, RDF.TYPE, RDF.STATEMENT)
        );
    }

    private void titleEpisodesConversion() {
        IMDBEpisodeReader reader = new IMDBEpisodeReader(IMDBUtils.getTitleEpisodeFile(imdbDirectory));
        while (reader.hasNext()) {
            IMDBEpisodeRow row = reader.next();
            Statement statement;
            Resource titleId = Values.iri(IMDB, row.getTitleId());

            statement = valueFactory.createStatement(
                    titleId,
                    episodeOf,
                    Values.iri(IMDB, row.getParentTitleId())
            );
            process(statement);

            if (row.getSeasonNumber() != null) {
                statement = valueFactory.createStatement(
                        titleId,
                        seasonNumber,
                        Values.literal(row.getSeasonNumber())
                );
                process(statement);
            }

            if (row.getEpisodeNumber() != null) {
                statement = valueFactory.createStatement(
                        titleId,
                        episodeNumber,
                        Values.literal(row.getEpisodeNumber())
                );
                process(statement);
            }
        }
    }

    private void titleRatingsConversion() {
        IMDBRatingsReader reader = new IMDBRatingsReader(IMDBUtils.getTitleRatingsFile(imdbDirectory));
        while (reader.hasNext()) {
            IMDBRatingsRow row = reader.next();
            Statement statement;
            Resource titleId = Values.iri(IMDB, row.getTitleId());

            if (row.getAverageRating() != null) {
                statement = valueFactory.createStatement(
                        titleId,
                        averageRating,
                        Values.literal(row.getAverageRating())
                );
                process(statement);
            }

            if (row.getNumVotes() != null) {
                statement = valueFactory.createStatement(
                        titleId,
                        numberOfVotes,
                        Values.literal(row.getNumVotes())
                );
                process(statement);
            }
        }
    }

    protected abstract void onStart();

    protected abstract void onFinish();

    protected abstract void process(Statement statement);
}
