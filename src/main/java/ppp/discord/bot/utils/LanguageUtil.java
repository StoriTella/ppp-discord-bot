package ppp.discord.bot.utils;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

public class LanguageUtil {

    public static final String enSigla = "lang-en";
    public static final String ptSigla = "lang-pt";
    public static final String pptSigla = "lang-ppt";
    public static final String enFile = "i18n_en.json";
    public static final String ptFile = "i18n_pt.json";
    public static final String pptFile = "i18n_ppt.json";
    public static final String PATH = "\\src\\main\\resources\\i18n\\";
    public static String defaultLanguage = enFile;
    public static String defaultLanguageSigla = enSigla;

    public static boolean changeLanguage(String language) {
        switch (language) {
            case "en":
                defaultLanguage = enFile;
                defaultLanguageSigla = enSigla;
                break;
            case "pt":
                defaultLanguage = ptFile;
                defaultLanguageSigla = ptSigla;
                break;
            case "ppt":
                defaultLanguage = pptFile;
                defaultLanguageSigla = pptSigla;
                break;
            default:
                return false;
        }
        return true;
    }

    public static String getString(String key) {
        JSONParser jsonParser = new JSONParser();
        String path = new File(defaultLanguage).getAbsolutePath();
        path = path.replace(defaultLanguage,"");
        path = path + PATH + defaultLanguage;
        path = path.replace("\\","\\\\");
        try (FileReader reader = new FileReader(path))
        {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObj = (JSONObject) obj;

            for (String message: key.split("-")) {
                if (key.endsWith(message)) {
                    return (String) jsonObj.get(message);
                } else {
                    jsonObj = (JSONObject) jsonObj.get(message);
                }
            }

            return "notFound";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
}
