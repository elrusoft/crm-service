package com.polozhaev.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.polozhaev.business.entities.User;
import com.polozhaev.business.repositories.UserRepository;

@Service
public class UploadService {

	
	@Autowired
	private UserRepository userRepository;
	
	
	 private static String UPLOADED_FOLDER = "upload";

	
	 //only one file
	 public void saveUploadedFile(MultipartFile file,String id) throws IOException {

	    
            if (!file.isEmpty()) {
            
      	     
	            byte[] bytes = file.getBytes();
	
	            File directory = new File(UPLOADED_FOLDER+"/"+id+"/");
	            if (! directory.exists()){
	            	directory.mkdir();
	                // If you require it to make the entire directory path including parents,
	                // use directory.mkdirs(); here instead.
	            }
	            
	            File fileupload = new File(UPLOADED_FOLDER+"/"+id+"/"+file.getOriginalFilename());
		           
	            
	            fileupload.createNewFile();
	            FileOutputStream fout = new FileOutputStream(fileupload);
	            fout.write(bytes);
	            
	            fout.close();
            }

    }
	 
	 //if multiple file
	 
	 public void saveUploadedFile(List<MultipartFile> files,String id) throws IOException {

	    	
	    	
    	 for (MultipartFile file : files) {

            if (file.isEmpty()) {
            	System.out.println("file is empty ");
   	   	     
                continue; 
            }
        	System.out.println("file name: "+file.getOriginalFilename());
   	     
            byte[] bytes = file.getBytes();

            File directory = new File(UPLOADED_FOLDER+"/"+id+"/");
            if (! directory.exists()){
            	directory.mkdir();
                // If you require it to make the entire directory path including parents,
                // use directory.mkdirs(); here instead.
            }
            
            File fileupload = new File(UPLOADED_FOLDER+"/"+id+"/"+file.getOriginalFilename());
	           
            
            fileupload.createNewFile();
            FileOutputStream fout = new FileOutputStream(fileupload);
            fout.write(bytes);
            
            fout.close();
           

        }

    }
	
	
}
