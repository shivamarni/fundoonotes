package com.bridgelabz.fundoonotes.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonClientService {
	void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess,String token);

	void deleteFileFromS3Bucket(String fileName,String token);
}
