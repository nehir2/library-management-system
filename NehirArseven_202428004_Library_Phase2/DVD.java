public class DVD extends Material {

    private String director;

    public DVD(String id, String title, String director, int publicationYear) {
        super(id, title, publicationYear);
        this.director = director;
    }

    public String getDirector() { return director; }

    public void watch() {
        System.out.println("Watching DVD: " + getTitle() + " by " + director);
    }

    @Override
    public String toString() {
        return String.format("DVD  | ID: %s | Title: %s | Director: %s | Year: %d | Status: Available",
                getId(), getTitle(), director, getPublicationYear());
    }

    @Override
    public String toFileFormat() {
        return "DVD," + getId() + "," + getTitle() + "," + director + "," + getPublicationYear();
    }
}