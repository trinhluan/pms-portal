package com.example.pmswebportal.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.pmswebportal.constants.AppConstants;

@Service
public class StreamUtil {

    /**
     * Read file in Resources
     * 
     * @param diretory
     * @return
     * @throws FileNotFoundException
     */
    public InputStream readFileInResources(String diretory) throws FileNotFoundException {
        File file = ResourceUtils.getFile(String.format(AppConstants.READ_FILE_IN_RESOURCE_FORMAT, diretory));
        InputStream inputStream = new FileInputStream(file);
        return inputStream;
    }

}
