package com.fev.docxtohtml.docxtohtmlservice.repository;

import com.fev.docxtohtml.docxtohtmlservice.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface FileRepository extends JpaRepository<FileDetails, String> {}
