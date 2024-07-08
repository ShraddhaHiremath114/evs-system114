package com.evs.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
String uploadImage(MultipartFile evimage);
String getUrlFromPublicId(String publicId);
}
