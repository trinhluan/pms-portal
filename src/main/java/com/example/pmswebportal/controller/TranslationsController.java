package com.example.pmswebportal.controller;

import java.util.Hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pmswebportal.services.TranslationService;

@RestController
public class TranslationsController extends BaseController {

    @Autowired
    private TranslationService translationService;

    @GetMapping("translations")
    ResponseEntity<?> translations(@RequestParam String locale) {
        Hashtable<String, String> translate = new Hashtable<String, String>();
        try {
            translate = translationService.getTranslation(locale);
        } catch (Exception e) {
            // Luan: TODO
            e.printStackTrace();
        }
        return new ResponseEntity<>(translate, HttpStatus.OK);
    }
}
