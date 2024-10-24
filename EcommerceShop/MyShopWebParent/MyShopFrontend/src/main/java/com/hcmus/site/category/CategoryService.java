package com.hcmus.site.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcmus.common.entity.Category;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;

	public List<Category> listAll() {
		return repo.findAllEnabled();
	}

	public Category getByAlias(String alias) {
		return repo.findByAliasEnabled(alias);
	}

	public List<Category> seachByKeyWord(String keyword) {
		List<Category> categories = repo.findByKeyWord(keyword);
		return categories;
	}

	public List<Category> getCategoryParents(Category child) {
		List<Category> listParents = new ArrayList<>();

		Category parent = child.getParent();

		while (parent != null) {
			listParents.add(0, parent);
			parent = parent.getParent();
		}

		listParents.add(child);

		return listParents;
	}
}
