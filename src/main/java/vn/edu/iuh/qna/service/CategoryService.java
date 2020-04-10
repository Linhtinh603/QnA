package vn.edu.iuh.qna.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.edu.iuh.qna.entity.CategoryModel;

@Service
public interface CategoryService {
	List<CategoryModel> findAll();
}
