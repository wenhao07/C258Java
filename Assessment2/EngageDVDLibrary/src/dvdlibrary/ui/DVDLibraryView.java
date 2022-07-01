package dvdlibrary.ui;

import dvdlibrary.dto.DVD;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.OptionalDouble;


public class DVDLibraryView {
    private UserIO io;

    public DVDLibraryView(UserIO io){this.io = io;}

    public void displayErrorMessage(String message){
        io.print("=== ERROR ===");
        io.print(message);
    }

    public void unknownMessage(){
        io.print("=== Unknown Command !!! ===");
    }

    public void exitMessage(){
        io.print("=== Exiting !!! ===");
    }

    public int printMenuAndGetSelection(){
        io.print("Main Menu:");
        io.print("1) Add DVD");
        io.print("2) Remove DVD");
        io.print("3) Find DVD");
        io.print("4) List All DVDs");
        io.print("5) Update DVD information");
        io.print("6) Show average age of DVDs collection");
        io.print("7) Exit");

        return io.readInt("Please select the operation you wish to perform", 1, 7);
    }

    public int printFindMenuAndGetSelection(){
        io.print("Find DVD Menu:");
        io.print("1) Recent N years");
        io.print("2) MPAA Rating");
        io.print("3) Director");
        io.print("4) Studio");
        io.print("5) Specific year");
        io.print("6) Oldest");
        io.print("7) Newest");
        io.print("8) Specific Title");

        return io.readInt("Please select the method you wish to utilise to search for a DVD", 1, 8);
    }

    public void displayCreateDVDBanner(){
        io.print("=== Creating DVD Entry ===");
    }

    public DVD getNewDVDInfo(){
        String title = io.readString("Please enter DVD title");
        String releaseDate = io.readString("Please enter DVD release date in dd/mm/yyyy format");
        String rating = io.readString("Please enter DVD MPAA rating");
        String director = io.readString("Please enter DVD director");
        String studio = io.readString("Please enter DVD studio");
        String note = io.readString("Please enter personal notes");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate release = LocalDate.parse(releaseDate, formatter);

        DVD newDVD = new DVD();
        newDVD.setTitle(title);
        newDVD.setDirector(director);
        newDVD.setRating(rating);
        newDVD.setStudio(studio);
        newDVD.setReleaseDate(release);
        newDVD.setNote(note);
        return newDVD;
    }

    public void displayCreateSuccessBanner(){
        io.readString("New DVD entry successfuly added. Please hit enter to continue.");
    }

    public void displayRemoveCreateDVDBanner(){
        io.print("=== Removing DVD Entry ===");
    }

    public String getTitleChoice(){
        String titleChoice = io.readString("Please enter specific DVD title");
        return titleChoice;
    }

    public void displayDeleteResult(DVD removedDVD){
        if(removedDVD != null){
            io.print("DVD successfull removed!");
        }else{
            io.print("DVD not found in library.");
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayListDVDBanner(){
        io.print("=== Listing all DVDS ===");
    }

    public void displayAllDVDs(List<DVD> DVDList){
        io.print("Title : Release Year : MPAA Rating : Director : Studio : Notes");
        int i = 1;
        for (DVD dvd: DVDList){
            io.print(i + ") "
                    + dvd.getTitle() + " : "
                    + dvd.getReleaseDate() + " : "
                    + dvd.getRating() + " : "
                    + dvd.getDirector() + " : "
                    + dvd.getStudio() + " : "
                    + dvd.getNote());
            i++;
        }
        io.readString("Please hit enter to continue.");
    }

    public void displayAverageAgeBanner(){
        io.print("=== Showing average age of DVDs in library");
    }

    public void displayAverageAge(OptionalDouble averageAge){
        io.print(String.valueOf(averageAge));
    }

    public String getRatingChoice(){
        String ratingChoice = io.readString("Please enter rating to search by");
        return ratingChoice;
    }

    public String getDirectorChoice(){
        String directorChoice = io.readString("Please enter director to search by");
        return directorChoice;
    }

    public String getStudioChoice(){
        String studioChoice = io.readString("Please enter studio to search by");
        return studioChoice;
    }

    public int getReleaseYearChoice(){
        int releaseYearChoice = io.readInt("Please enter release year to search by");
        return releaseYearChoice;
    }

    public int getNChoice(){
        int NChoice = io.readInt("Please enter number of recent years to search by");
        return NChoice;
    }

    public void displaySearchDVDBanner(){
        io.print("=== Search for DVDs ===");
    }

    public void displayFoundDVD(List<DVD> foundDVD){
        if(!foundDVD.isEmpty()){
            displayAllDVDs(foundDVD);
        }else{
            io.print("No DVDs found.");
        }
    }

    public void displayEditDVDBanner(){io.print("=== Edit DVD information ===");}

    public void displayEditSuccessBanner(){io.print("=== DVD information successfully editted ===");}

    public int printEditMenuAndGetSelection() {
        io.print("Edit DVD Information Menu:");
        io.print("1) Title");
        io.print("2) Release Date");
        io.print("3) MPAA Rating");
        io.print("4) Director");
        io.print("5) Studio");
        io.print("6) Note");

        return io.readInt("Please select the information you wish to edit.", 1, 6);
    }

    public int getDVDChoice(List<DVD> DVDList){
        return io.readInt("Please select your DVD", 1, DVDList.size());
    }

    public String getNewInformation(){
        String newInfo = io.readString("Please enter the new information");
        return newInfo;

    }
}
