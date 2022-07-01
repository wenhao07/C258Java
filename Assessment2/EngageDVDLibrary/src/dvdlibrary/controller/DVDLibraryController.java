package dvdlibrary.controller;

import dvdlibrary.dao.DVDLibraryDao;
import dvdlibrary.dao.DVDLibraryDaoException;
import dvdlibrary.dto.DVD;
import dvdlibrary.ui.DVDLibraryView;
import dvdlibrary.ui.UserIO;
import dvdlibrary.ui.UserIOConsoleImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class DVDLibraryController {
    private UserIO io = new UserIOConsoleImpl();
    private DVDLibraryDao dao;
    private DVDLibraryView view;

    public DVDLibraryController(DVDLibraryDao dao, DVDLibraryView view){
        this.dao = dao;
        this.view = view;
    }

    public void run(){
        boolean keepGoing = true;
        int menuSelection = 0;
        try{
            while(keepGoing){
                menuSelection = getMenuSelection();

                switch (menuSelection){
                    case 1:
                        createDVD();
                        break;
                    case 2:
                        removeDVD();
                        break;
                    case 3:
                        findDVD();
                        break;
                    case 4:
                        listAllDVD();
                        break;
                    case 5:
                        updateDVD();
                        break;
                    case 6:
                        showAverageAge();
                        break;
                    case 7:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        }catch (Exception e){
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection(){
        return view.printMenuAndGetSelection();
    }

    private void createDVD() throws DVDLibraryDaoException {
        view.displayCreateDVDBanner();
        DVD newDVD;
        newDVD = view.getNewDVDInfo();
        dao.addDvd(newDVD.getTitle(), newDVD);
        if(dao.getDVDs().isEmpty()){
            dao.setOldestDVD(newDVD);
        }
        dao.setNewestDVD(newDVD);
        view.displayCreateSuccessBanner();
    }

    private void removeDVD() throws DVDLibraryDaoException {
        view.displayRemoveCreateDVDBanner();
        String title = view.getTitleChoice();
        DVD removedDVD = dao.removeDVD(title);
        view.displayDeleteResult(removedDVD);
    }

    private List<DVD> findDVD(){
        view.displaySearchDVDBanner();
        int menuSelection;
        List<DVD> DVDList = new ArrayList<DVD>();
        try{
            menuSelection = view.printFindMenuAndGetSelection();
            switch(menuSelection){
                case 1:
                    int n = view.getNChoice();
                    DVDList = dao.getAllDVDInPastNYear(n);
                    view.displayFoundDVD(DVDList);
                    break;
                case 2:
                    String rating = view.getRatingChoice();
                    DVDList = dao.getAllDVDWithRatings(rating);
                    view.displayFoundDVD(DVDList);
                    break;
                case 3:
                    String director = view.getDirectorChoice();
                    DVDList = dao.getAllDVDWithDirector(director);
                    view.displayFoundDVD(DVDList);
                    break;
                case 4:
                    String studio = view.getStudioChoice();
                    DVDList = dao.getAllDVDWithStudio(studio);
                    view.displayFoundDVD(DVDList);
                    break;
                case 5:
                    int release = view.getReleaseYearChoice();
                    DVDList = dao.getAllDVDWithReleaseYear(release);
                    view.displayFoundDVD(DVDList);
                    break;
                case 6:
                    DVDList = new ArrayList<>();
                    DVDList.add(dao.getOldestDVD());
                    view.displayFoundDVD(DVDList);
                    break;
                case 7:
                    DVDList = new ArrayList<>();
                    DVDList.add(dao.getNewestDVD());
                    view.displayFoundDVD(DVDList);
                    break;
                case 8:
                    String title = view.getTitleChoice();
                    DVDList = dao.getAllDVDContainingPartialTitle(title);
                    view.displayFoundDVD(DVDList);
                    break;
                default:
                    unknownCommand();
                }
            }catch(Exception e){
            view.displayErrorMessage(e.getMessage());
        }
        return DVDList;
    }

    private DVD updateDVD(String[] DVDDetails, int menuSelection, String newInformation) throws DVDLibraryDaoException{
        DVDDetails[menuSelection] = newInformation;
        DVD updatedDVD = new DVD();
        updatedDVD.setTitle(DVDDetails[0]);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate releaseDate = LocalDate.parse(DVDDetails[1],formatter);
        updatedDVD.setReleaseDate(releaseDate);
        updatedDVD.setRating(DVDDetails[2]);
        updatedDVD.setDirector(DVDDetails[3]);
        updatedDVD.setStudio(DVDDetails[4]);
        updatedDVD.setNote(DVDDetails[5]);
        return updatedDVD;
    }

    private void updateDVD() throws DVDLibraryDaoException {
        view.displayEditDVDBanner();
        int menuSelection;
        int DVDChoice;
        DVD selectedDVD;
        DVD updatedDVD;
        String[] DVDDetails = new String[6];
        try {
            List<DVD> DVDList = findDVD();
            DVDChoice = view.getDVDChoice(DVDList) - 1;
            selectedDVD = DVDList.get(DVDChoice);
            menuSelection = view.printEditMenuAndGetSelection() - 1;

            DVDDetails[0] = selectedDVD.getTitle();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DVDDetails[1] = selectedDVD.getReleaseDate().format(formatter);
            DVDDetails[2] = selectedDVD.getRating();
            DVDDetails[3] = selectedDVD.getDirector();
            DVDDetails[4] = selectedDVD.getStudio();
            DVDDetails[5] = selectedDVD.getNote();

            dao.removeDVD(selectedDVD.getTitle());
            updatedDVD = updateDVD(DVDDetails, menuSelection, view.getNewInformation());
            dao.addDvd(updatedDVD.getTitle(), updatedDVD);
        } catch (Exception e) {
            view.displayErrorMessage(e.getMessage());
        }
        view.displayEditSuccessBanner();
    }

    private void listAllDVD() throws DVDLibraryDaoException {
        view.displayListDVDBanner();
        List<DVD> DVDList = dao.getAllDVD();
        view.displayAllDVDs(DVDList);
    }

    private void showAverageAge() throws DVDLibraryDaoException {
        view.displayAverageAgeBanner();
        OptionalDouble average = dao.getAverageAge();
        view.displayAverageAge(average);
    }


    private void unknownCommand(){
        view.unknownMessage();
    }

    private void exitMessage(){
        view.exitMessage();
    }


}
