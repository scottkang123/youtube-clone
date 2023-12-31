package com.programming.techie.youtubeclone.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;

//import lombok.RequiredArgsConstructor;

import org.springframework.util.StringUtils;

//import com.amazonaws.util.StringUtils;

@Service
// @RequiredArgsConstructor
public class S3Service implements FileService {

    private final AmazonS3 awsS3Client;

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.awsS3Client = amazonS3;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        // upload file to aws s3

        // prepare a key
        var filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        var key = UUID.randomUUID().toString() + "." + filenameExtension;

        var metadata = new ObjectMetadata();

        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            awsS3Client.putObject("youtube-clone1234", key, file.getInputStream(), metadata);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An exception occurred while uploading the files");
        }

        awsS3Client.setObjectAcl("youtube-clone1234", key, CannedAccessControlList.PublicRead);

        // return awsS3Client.getResourceUrl("youtube-clone1234", key);

        String bucketName = "youtube-clone1234"; // replace with your bucket name
        String region = "ap-southeast-2";
        String resourceUrl = String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, region, key);

        return resourceUrl;

    }

}
