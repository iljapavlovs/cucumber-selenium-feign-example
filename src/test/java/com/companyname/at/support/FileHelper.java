package com.companyname.at.support;


import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.io.File;


@NoArgsConstructor
@Slf4j
public class FileHelper {

    public String createWindowsPathFromResourceFileNameAndFolder(String folder, String fileName) {
        return FilenameUtils.getPath(getClass().getResource(String.format("/files/%1$s%2$s", folder, fileName)).getPath()).replace("/", "\\") + fileName;
    }

    private void createFolder(String dirPath) {

        File file = new File(dirPath);

        if (!file.exists()) {
            if (file.mkdirs()) {
                log.info("Directory is created at " + dirPath);
            } else {
                log.error("Failed to create directory at " + dirPath);
            }
        }
    }

    public void createFileDownloadFolder() {
        createFolder(getFileDownloadDirPath());
    }

    /**
     * @return relative "target/downloaded-files" path for current maven module
     */
    public String getFileDownloadDirPath() {
        String moduleTargetPath = getClass().getResource(".").getPath().split("/test-classes")[0]
                .replaceFirst("/", "").replace("/", "\\");
        return moduleTargetPath + "\\downloaded-files";
    }

}