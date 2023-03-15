package com.example.Editique.repository;
import com.example.Editique.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template,Long> {
    List<Template> findByCode(String code);



}
