package com.evs.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.evs.helper.AppConstants;

@Service
public class ImageServiceImpl implements ImageService{

    private AppConstants appConstants;

    @Autowired
    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary){
        this.cloudinary=cloudinary;

    }
    // public ImageServiceImpl (){

    // }
    @Override
    public String uploadImage(MultipartFile evimage) {
        String filename=UUID.randomUUID().toString();
        try{
            byte[] data=new byte[evimage.getInputStream().available()];
            evimage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id",filename));
        }catch(Exception e){
            e.printStackTrace();
        }
        

        return this.getUrlFromPublicId(filename);
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary.url().transformation(
            new Transformation<>()
            .width(appConstants.CONTACT_IMAGE_WIDTH)
            .height(appConstants.CONTACT_IMAGE_HEIGHT)
            .crop(appConstants.CONTACT_IMAGE_CROP)
        ).generate(publicId);
    }

    
}
