package vn.edu.iuh.qna.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.qna.entity.CategoryModel;
import vn.edu.iuh.qna.repository.CategoryRepository;
import vn.edu.iuh.qna.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public List<CategoryModel> findAll() {
		return categoryRepository.findAll();
	}

}
