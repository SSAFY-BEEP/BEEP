package com.example.Beep.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.Beep.api.domain.entity.Message24;
import com.example.Beep.api.domain.entity.User;
import com.example.Beep.api.domain.enums.ErrorCode;
import com.example.Beep.api.exception.CustomException;
import com.example.Beep.api.repository.Message24Repository;
import com.example.Beep.api.repository.UserRepository;
import com.example.Beep.api.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    //24시간 보관함
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    
    //영구 보관함
    @Value("${cloud.aws.s3.bucket2}")
    private String bucket2;

    private final AmazonS3 amazonS3;

    private final UserRepository userRepository;
    private final Message24Repository message24Repository;
    //파일 하나 업로드
    public String uploadFile(MultipartFile multipartFile) {
        String fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }
        System.out.println(fileName+"url 주소인가여?");
        String[]url=fileName.split("url");
        return fileName;
    }

    public String persistFile(MultipartFile multipartFile) {
        String fileName = createFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket2, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }
        return fileName;
    }
    
    //1이면 24시간버킷, 2이면 영구버킷
    public void deleteFile(String fileName, Integer type) {
        if(type==1){
            amazonS3.deleteObject(bucket,fileName);
        } else{
            amazonS3.deleteObject(bucket2,fileName);
        }
    }

    //메세지24 보관/차단 -> 영구메세지로 S3파일 복사
    public void copyFile(String messageId){
        Message24 message24 = message24Repository.findById(messageId).orElseThrow(()-> new CustomException(ErrorCode.BAD_REQUEST));
        if(message24.getAudioUri()!=null){
            amazonS3.copyObject(bucket,message24.getAudioUri(),bucket2,message24.getAudioUri());
        }
    }

    public String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    public String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    public String findUserVoice(){
        String userNum = SecurityUtil.getCurrentUsername().get();

        User user=userRepository.findByPhoneNumber(userNum).orElseThrow(()-> new CustomException(ErrorCode.BAD_REQUEST));
        return user.getIntroduceAudio();
    }

    public String findUserVoiceByPhoneNumber(String phoneNumber){
        User user=userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()-> new CustomException(ErrorCode.BAD_REQUEST));
        return user.getIntroduceAudio();
    }
}
