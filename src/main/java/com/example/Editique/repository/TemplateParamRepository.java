package com.example.Editique.repository;

import com.example.Editique.entity.TemplateParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateParamRepository extends JpaRepository<TemplateParam,Long> {
}
