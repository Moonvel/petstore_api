package petstore.api.tools;

public final class PetEndPoints {
    // ToDo заглавные через _ имя с именем метода совпадают
    public static final String addPet = "/pet";
    public static final String findPet = "/pet/{petId}";
    public static final String deletePet = "/pet/{petId}";
    public static final String updatingPet = "/pet";
    public static final String uploadAnImage = "/pet/{petId}/uploadImage";
    public static final String findByStatus = "/pet/findByStatus";
    public static final String updatesPetWithFormData = "/pet/{petId}";
}
