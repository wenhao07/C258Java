package dvdlibrary.dao;

import dvdlibrary.dto.DVD;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


public class DVDLibraryDaoImpl implements DVDLibraryDao{
    private Map<String, DVD> DVDs = new HashMap<>();
    private DVD newestDVD;
    private DVD oldestDVD;
    public static final String DELIMITER = "::";
    public static final String DVD_FILE = "src/dvdlibrary/dvdroster";

    @Override
    public DVD addDvd(String title, DVD dvd) throws DVDLibraryDaoException{
        loadLibrary();
        DVD newDVD = DVDs.put(title, dvd);
        writeLibrary();
        return newDVD;
    }

    @Override
    public DVD removeDVD(String title) throws DVDLibraryDaoException{
        loadLibrary();
        DVD removedDVD = DVDs.remove(title);
        writeLibrary();
        return removedDVD;
    }

    @Override
    public DVD getDVD(String title) throws DVDLibraryDaoException{
        loadLibrary();
        return DVDs.get(title);
    }

    @Override
    public List<DVD> getAllDVD() throws DVDLibraryDaoException{
        loadLibrary();
        return new ArrayList<>(DVDs.values());
    }

    @Override
    public List<DVD> getAllDVDWithReleaseYear(int releaseYear) throws DVDLibraryDaoException{
        loadLibrary();
        return DVDs.values().stream()
                .filter((p) -> p.getReleaseDate().getYear() == releaseYear)
                .collect(Collectors.toList());
    }

    @Override
    public List<DVD> getAllDVDInPastNYear(int N) throws DVDLibraryDaoException{
        loadLibrary();
        LocalDate today = LocalDate.now();
        return DVDs.values().stream()
                .filter((p) -> p.getReleaseDate().isAfter(today.minusYears(N)))
                .collect(Collectors.toList());
    }

    @Override
    public List<DVD> getAllDVDWithRatings(String rating) throws DVDLibraryDaoException{
        loadLibrary();
        return DVDs.values().stream()
                .filter((p) -> p.getRating().equals(rating))
                .collect(Collectors.toList());
    }

    @Override
    public List<DVD> getAllDVDWithDirector(String director) throws DVDLibraryDaoException{
        loadLibrary();
        return DVDs.values().stream()
                .filter((p) -> p.getDirector().equals(director))
                .collect(Collectors.toList());
    }

    @Override
    public List<DVD> getAllDVDWithStudio(String studio) throws DVDLibraryDaoException{
        loadLibrary();
        return DVDs.values().stream()
                .filter((p) -> p.getStudio().equals(studio))
                .collect(Collectors.toList());
    }

    @Override
    public OptionalDouble getAverageAge() throws DVDLibraryDaoException{
        loadLibrary();
        LocalDate today = LocalDate.now();
        return DVDs.values().stream()
                .mapToDouble((p) -> ChronoUnit.YEARS.between(p.getReleaseDate(), today))
                .average();
    }

    @Override
    public DVD getNewestDVD() throws DVDLibraryDaoException{
        return this.newestDVD;
    }

    @Override
    public DVD getOldestDVD() throws DVDLibraryDaoException{
        return this.oldestDVD;
    }

    @Override
    public List<DVD> getAllDVDContainingPartialTitle(String partTitle) throws DVDLibraryDaoException{
        loadLibrary();
        String lowerPartTitle = partTitle.toLowerCase();
        return DVDs.values().stream()
                .filter((p) -> p.getTitle().toLowerCase().contains(lowerPartTitle))
                .collect(Collectors.toList());
    }

    @Override
    public void setNewestDVD(DVD newestDVD) throws DVDLibraryDaoException{
        this.newestDVD = newestDVD;
    }

    @Override
    public void setOldestDVD(DVD oldestDVD) throws DVDLibraryDaoException{
        this.oldestDVD = oldestDVD;
    }

    @Override
    public Map<String, DVD> getDVDs() throws DVDLibraryDaoException{
        return this.DVDs;
    }

    @Override
    public DVD editDVD(DVD chosenDVD) throws DVDLibraryDaoException {
        return null;
    }

    public DVD unmarshallDVD(String dvdAsText){
        String[] DVDTokens = dvdAsText.split(DELIMITER);
        String title = DVDTokens[0];
        String date = DVDTokens[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate releaseDate = LocalDate.parse(date,formatter);

        String rating = DVDTokens[2];
        String director = DVDTokens[3];
        String studio = DVDTokens[4];
        String note = DVDTokens[5];

        DVD DVDFromText = new DVD();
        DVDFromText.setTitle(title);
        DVDFromText.setReleaseDate(releaseDate);
        DVDFromText.setRating(rating);
        DVDFromText.setDirector(director);
        DVDFromText.setStudio(studio);
        DVDFromText.setNote(note);
        return DVDFromText;
    }

    public String marshallDVD(DVD aDVD){
        String DVDAsText = aDVD.getTitle() + DELIMITER;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String releaseDate = aDVD.getReleaseDate().format(formatter);
        DVDAsText += releaseDate + DELIMITER;
        DVDAsText += aDVD.getRating() + DELIMITER;
        DVDAsText += aDVD.getDirector() + DELIMITER;
        DVDAsText += aDVD.getStudio() + DELIMITER;
        if (aDVD.getNote() == null){
            DVDAsText += "No Notes";
        }else {
            DVDAsText += aDVD.getNote();
        }
        return DVDAsText;
    }

    @Override
    public String getDelimiter(){
        return DELIMITER;
    }

    private void loadLibrary() throws DVDLibraryDaoException {
        Scanner scanner;
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(DVD_FILE)));
        }catch(FileNotFoundException e){
            throw new DVDLibraryDaoException("-_- could not load DVD data into memory.", e);
        }
        String currentLine;
        DVD currentDVD;
        while (scanner.hasNext()){
            currentLine = scanner.nextLine();
            currentDVD = unmarshallDVD(currentLine);
            DVDs.put(currentDVD.getTitle(), currentDVD);
        }
        scanner.close();
    }

    private void writeLibrary() throws DVDLibraryDaoException {
        PrintWriter out;
        try{
            out = new PrintWriter(new FileWriter(DVD_FILE));
        }catch(IOException e){
            throw new DVDLibraryDaoException("Could not save dvd data.", e);
        }

        String DVDAsText;
        List<DVD> DVDList = this.getAllDVD();
        for(DVD dvd: DVDList) {
            DVDAsText = marshallDVD(dvd);
            out.println(DVDAsText);
            out.flush();
        }
        out.close();
    }

}