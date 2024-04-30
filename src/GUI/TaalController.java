package GUI;

public class TaalController
    {
    private static TaalController instance;
    private String language = "nl";

    public static TaalController getInstance()
        {
            if(instance == null)
            {
                instance = new TaalController();
            }

            return instance;
        }
    public void setLanguage(String language)
        {
            this.language = language;
        }

    public String getLanguage()
        {
            return language;
        }
    }
