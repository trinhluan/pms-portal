import i18n from 'i18next';
import Backend from 'i18next-http-backend';
import LanguageDetector from 'i18next-browser-languagedetector'
import { initReactI18next } from 'react-i18next';


i18n
    .use(Backend)
    .use(initReactI18next)
    .use(LanguageDetector)
    .init({
        backend: {
            loadPath: 'api/translations?locale={{lng}}'
          },
        fallbackLng: 'en',
        lng: "zh_TW",
        detection: {
            order: ['cookie'],
            lookupCookie: 'lang',
            caches: ['cookie']
        }
    });

export default i18n;

