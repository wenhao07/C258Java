package dvdlibrary.dao;

import dvdlibrary.dto.DVD;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

public interface DVDLibraryDao {
    DVD addDvd(String title, DVD dvd) throws DVDLibraryDaoException;

    DVD removeDVD(String title) throws DVDLibraryDaoException;

    DVD getDVD(String title) throws DVDLibraryDaoException;

    List<DVD> getAllDVD() throws DVDLibraryDaoException;

    List<DVD> getAllDVDWithReleaseYear(int releaseYear) throws DVDLibraryDaoException;

    List<DVD> getAllDVDInPastNYear(int N) throws DVDLibraryDaoException;

    List<DVD> getAllDVDWithRatings(String rating) throws DVDLibraryDaoException;

    List<DVD> getAllDVDWithDirector(String director) throws DVDLibraryDaoException;

    List<DVD> getAllDVDWithStudio(String studio) throws DVDLibraryDaoException;

    OptionalDouble getAverageAge() throws DVDLibraryDaoException;

    DVD getNewestDVD() throws DVDLibraryDaoException;

    DVD getOldestDVD() throws DVDLibraryDaoException;

    List<DVD> getAllDVDContainingPartialTitle(String partTitle) throws DVDLibraryDaoException;

    void setNewestDVD(DVD newestDVD) throws DVDLibraryDaoException;

    void setOldestDVD(DVD oldestDVD) throws DVDLibraryDaoException;

    Map<String, DVD> getDVDs() throws DVDLibraryDaoException;

    DVD editDVD(DVD chosenDVD) throws DVDLibraryDaoException;

    String getDelimiter();
}
