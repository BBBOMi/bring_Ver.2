package com.wonder.bring.s3uploader;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by bomi on 2018-12-29.
 */

public interface S3FileUploadService {
    String upload(MultipartFile uploadFile) throws IOException;
    void uploadOnS3(final String fileName, final File file);
}
