package com.example.pmswebportal.services.implement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.pmswebportal.constants.AppConstants;
import com.example.pmswebportal.services.TranslationService;
import com.example.pmswebportal.utilities.StreamUtil;

@Service
public class TranslationServiceImpl implements TranslationService {

    @Autowired
    private StreamUtil streamUtil;

    /**
     * from locale input get translation file
     * 
     * @param locale
     * @return
     * @throws FileNotFoundException, Exception
     */
    @Override
    public Hashtable<String, String> getTranslation(String locale) throws FileNotFoundException, Exception {
        boolean isAnonymous = SecurityContextHolder.getContext()
                .getAuthentication() instanceof AnonymousAuthenticationToken;             
        Hashtable<String, String> translate = new Hashtable<String, String>();
        // if isAnonymous is false, locale is en an zh_TW directory is
        // locales/zh_TW/lang-TC.dic els is locales/zh_CN/lang-SC.dic
        String folder = ((isAnonymous || AppConstants.LOCALE_EN.equals(locale)) ? AppConstants.LOCALE_TC : locale);
        String fileName = ((isAnonymous || AppConstants.LOCALE_EN.equals(locale)
                || AppConstants.LOCALE_TC.equals(locale))
                        ? AppConstants.TC_TRANSLATION
                        : AppConstants.SC_TRANSLATION);
        String dir = String.format(AppConstants.TRANSLATION_DIR_FORMAT, folder, fileName);
        // read file from resource
        InputStream inputStream = streamUtil.readFileInResources(dir);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
        try {
            while (reader.ready()) {
                String line = reader.readLine();
                String[] translation = line.split(Character.toString((char) 9));
                // if language is en or locale is en get key and value is same
                if (translation.length == 2) {
                    translate.put(translation[0],
                    (isAnonymous || AppConstants.LOCALE_EN.equals(locale)) ? translation[0] : translation[1]);
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex);
        } finally {
            reader.close();
        }
        return translate;
    }

}
