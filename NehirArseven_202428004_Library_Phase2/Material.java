public abstract class Material {
    private String id;
    private String title;
    private int publicationYear;

    public Material(String id, String title, int publicationYear) {
        this.id = id;
        this.title = title;
        this.publicationYear = publicationYear;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getPublicationYear() { return publicationYear; }

    public abstract String toFileFormat();

    @Override
    public abstract String toString();
}
