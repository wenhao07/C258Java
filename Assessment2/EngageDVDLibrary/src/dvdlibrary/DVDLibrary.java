package dvdlibrary;

import dvdlibrary.controller.DVDLibraryController;
import dvdlibrary.dao.DVDLibraryDao;
import dvdlibrary.dao.DVDLibraryDaoException;
import dvdlibrary.dao.DVDLibraryDaoImpl;
import dvdlibrary.ui.DVDLibraryView;
import dvdlibrary.ui.UserIO;
import dvdlibrary.ui.UserIOConsoleImpl;

public class DVDLibrary {
    public static void main(String[] args) throws DVDLibraryDaoException {
        UserIO myIo = new UserIOConsoleImpl();
        DVDLibraryView myView = new DVDLibraryView(myIo);
        DVDLibraryDao myDao = new DVDLibraryDaoImpl();
        DVDLibraryController myController = new DVDLibraryController(myDao, myView);
        myController.run();
    }

}
