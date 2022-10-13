package util;


import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LanguageUtils {

    private static final Map<String, String> regionToLanguageTag;

    static {
        Map<String, String> temp = new HashMap<>(Locale.getAvailableLocales().length);
        /*
        File languageTags = new File(LanguageUtils.class.getClassLoader().getResource("languageTags.txt").getFile());
        try {
            List<String> lines = Files.readAllLines(languageTags.toPath());
            for (String line : lines) {
                String[] languageTag = line.split("\t")[0].split("-");

                temp.put(languageTag[1], languageTag[0]);
                System.out.println("temp.put(\"" + languageTag[1] + "\", \"" + languageTag[0] +"\");");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

        temp.put("BA", "sr");
        temp.put("ZA", "af");
        temp.put("AE", "ar");
        temp.put("BH", "ar");
        temp.put("DZ", "ar");
        temp.put("EG", "ar");
        temp.put("IQ", "ar");
        temp.put("JO", "ar");
        temp.put("KW", "ar");
        temp.put("LB", "ar");
        temp.put("LY", "ar");
        temp.put("MA", "ar");
        temp.put("OM", "ar");
        temp.put("QA", "ar");
        temp.put("SA", "ar");
        temp.put("SY", "ar");
        temp.put("TN", "ar");
        temp.put("YE", "ar");
        temp.put("AZ", "az");
        temp.put("BY", "be");
        temp.put("BG", "bg");
        temp.put("CZ", "cs");
        temp.put("DK", "da");
        temp.put("AT", "de");
        temp.put("CH", "de");
        temp.put("DE", "de");
        temp.put("LI", "de");
        temp.put("MV", "dv");
        temp.put("GR", "el");
        temp.put("AU", "en");
        temp.put("BZ", "en");
        temp.put("CB", "en");
        temp.put("GB", "en");
        temp.put("IE", "en");
        temp.put("JM", "en");
        temp.put("NZ", "en");
        temp.put("PH", "en");
        temp.put("TT", "en");
        temp.put("US", "en");
        temp.put("ZW", "en");
        temp.put("CL", "es");
        temp.put("CO", "es");
        temp.put("CR", "es");
        temp.put("DO", "es");
        temp.put("EC", "es");
        temp.put("GT", "es");
        temp.put("HN", "es");
        temp.put("MX", "es");
        temp.put("NI", "es");
        temp.put("PA", "es");
        temp.put("PE", "es");
        temp.put("PR", "es");
        temp.put("PY", "es");
        temp.put("SV", "es");
        temp.put("UY", "es");
        temp.put("VE", "es");
        temp.put("EE", "et");
        temp.put("IR", "fa");
        temp.put("FI", "fi");
        temp.put("FO", "fo");
        temp.put("BE", "fr");
        temp.put("FR", "fr");
        temp.put("MC", "fr");
        temp.put("ES", "gl");
        temp.put("IL", "he");
        temp.put("HR", "hr");
        temp.put("HU", "hu");
        temp.put("AM", "hy");
        temp.put("ID", "id");
        temp.put("IS", "is");
        temp.put("IT", "it");
        temp.put("JP", "ja");
        temp.put("GE", "ka");
        temp.put("KZ", "kk");
        temp.put("KR", "ko");
        temp.put("KG", "ky");
        temp.put("LT", "lt");
        temp.put("LV", "lv");
        temp.put("MK", "mk");
        temp.put("MN", "mn");
        temp.put("BN", "ms");
        temp.put("MY", "ms");
        temp.put("MT", "mt");
        temp.put("NL", "nl");
        temp.put("PL", "pl");
        temp.put("AR", "ps");
        temp.put("BR", "pt");
        temp.put("PT", "pt");
        temp.put("BO", "qu");
        temp.put("RO", "ro");
        temp.put("RU", "ru");
        temp.put("SK", "sk");
        temp.put("SI", "sl");
        temp.put("AL", "sq");
        temp.put("SE", "sv");
        temp.put("KE", "sw");
        temp.put("TH", "th");
        temp.put("TR", "tr");
        temp.put("UA", "uk");
        temp.put("PK", "ur");
        temp.put("UZ", "uz");
        temp.put("VN", "vi");
        temp.put("CN", "zh");
        temp.put("HK", "zh");
        temp.put("MO", "zh");
        temp.put("SG", "zh");
        temp.put("TW", "zh");
        regionToLanguageTag = Collections.unmodifiableMap(temp);
    }

    public static String forRegion(String region) {
        return regionToLanguageTag.get(region);
    }


}
