package com.example.pmswebportal.services;
import java.io.FileNotFoundException;
import java.util.Hashtable;
public interface TranslationService {

    /**
     * from locale input get translation file
     * @param locale
     * @return
     * @throws FileNotFoundException
     */
    Hashtable<String, String> getTranslation(String locale) throws FileNotFoundException, Exception;
}
