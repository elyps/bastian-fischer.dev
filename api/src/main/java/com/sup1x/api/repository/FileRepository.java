package com.sup1x.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sup1x.api.model.File;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, String> {

//    List<File> findByUsername(String username);
//    List<File> findByUsernameContaining(String username);
//    List<File> findByNameContaining(String name);
//    List<File> findByTypeContaining(String type);
//    List<File> findByDataContaining(byte[] data);

}
