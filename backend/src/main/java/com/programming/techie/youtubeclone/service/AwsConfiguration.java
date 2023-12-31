package com.programming.techie.youtubeclone.service;

//import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

@Configuration
public class AwsConfiguration {

    @Bean
    public AmazonS3 amazonS3() {
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withRegion("ap-southeast-2")
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
        System.out.println("AmazonS3 Bean Created: " + amazonS3); // Log statement
        return amazonS3;
    }
}
