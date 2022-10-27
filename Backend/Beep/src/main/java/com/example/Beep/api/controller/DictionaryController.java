package com.example.Beep.api.controller;

import com.example.Beep.api.domain.dto.DictionaryResponseDto;
import com.example.Beep.api.domain.entity.Dictionary;
import com.example.Beep.api.service.DictionaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    DictionaryServiceImpl dictionaryService;

    @GetMapping("/word/{word}")
    public ResponseEntity<?> FindWord(@PathVariable ("word") String word){
        try{
            return new ResponseEntity<List<DictionaryResponseDto>>(dictionaryService.FindWord(word), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/value/{value}")
    public ResponseEntity<?> FindValue(@PathVariable ("value") String value){
        try{
            return new ResponseEntity<List<DictionaryResponseDto>>(dictionaryService.FindValue(value), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/random")
    public ResponseEntity<?>Random(){
        try{
            return new ResponseEntity<List<DictionaryResponseDto>>(dictionaryService.FindRandom(),HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
