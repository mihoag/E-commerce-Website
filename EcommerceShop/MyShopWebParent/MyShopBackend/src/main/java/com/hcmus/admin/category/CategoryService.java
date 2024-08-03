package com.hcmus.admin.category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.hcmus.admin.category.controller.CategoryPageInfo;
import com.hcmus.common.entity.Category;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CategoryService {
	
   private static final Integer PAGE_SIZE = 6; 	
   public static final int ROOT_CATEGORIES_PER_PAGE = 1;
   
   @Autowired
   private CategoryRepository repo;
   
   public List<Category> listCategoryByPage(CategoryPageInfo pageInfo, Integer pageNum, String sortField,String sortDir, String keyword)
   {
    Sort sort = Sort.by(sortField);
    if(sortDir.equals("asc"))
    {
    	sort = sort.ascending();
    }
    else if(sortDir.equals("desc"))
    {
    	sort = sort.descending();
    }
    
	Pageable pageable = PageRequest.of(pageNum - 1, ROOT_CATEGORIES_PER_PAGE, sort);
	
	Page<Category> pageCategories = null;
	
	if (keyword != null && !keyword.isEmpty()) {
		pageCategories = repo.search(keyword, pageable);	
	} else {
		pageCategories = repo.findRootCategories(pageable);
	}
	
	List<Category> rootCategories = pageCategories.getContent();
	
	pageInfo.setTotalElement(pageCategories.getTotalElements());
	pageInfo.setTotalPage(pageCategories.getTotalPages());
	
	if (keyword != null && !keyword.isEmpty()) {
		List<Category> searchResult = pageCategories.getContent();
		for (Category category : searchResult) {
			category.setHasChildren(category.getChildren().size() > 0);
		}
		
		return searchResult;
		
	} else {
		
		return listHierarchicalCategories(rootCategories, sortDir);
	}
   }
   
   
	private List<Category> listHierarchicalCategories(List<Category> rootCategories, String sortDir) {
		List<Category> hierarchicalCategories = new ArrayList<>();
		
		for (Category rootCategory : rootCategories) {
			hierarchicalCategories.add(Category.copyFull(rootCategory));
			
			Set<Category> children = sortCategory(sortDir,rootCategory.getChildren());
			
			for (Category subCategory : children) {
				String name = "--" + subCategory.getName();
				hierarchicalCategories.add(Category.copyFull(subCategory, name));
				
				listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1, sortDir);
			}
		}
		
		return hierarchicalCategories;
	}
	
	private void listSubHierarchicalCategories(List<Category> hierarchicalCategories,
			Category parent, int subLevel, String sortDir) {
		
		Set<Category> children = sortCategory(sortDir, parent.getChildren());
		int newSubLevel = subLevel + 1;
		
		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {				
				name += "--";
			}
			name += subCategory.getName();
		
			hierarchicalCategories.add(Category.copyFull(subCategory, name));
			
			listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel, sortDir);
		}
		
	}
	
   
   public Category getCateById(int id) throws CategoryNotFoundException 
   {
   	Category category;
   	 try {
   		category = repo.findById(id).get();
		} catch (Exception e) {
			// TODO: handle exception
			throw new CategoryNotFoundException("Category not found with id " + id);
		}
        return category;
   }
   
   public void deleteById(int id) throws CategoryNotFoundException 
   {
   	    try {
   		   repo.deleteById(id);
		} catch (Exception e) {
		   throw new CategoryNotFoundException("Category not found with id " + id);
		}
   }
   
   public void updateCategoryEnable(Integer id, boolean enable)
   {	
   	 try {
	    repo.updateEnabledStatus(id, enable);
	 } catch (Exception e) {
	    e.printStackTrace();
     }
   }
   
   public boolean checkUniqueName(Integer id, String name)
   {
   	 Category cate = repo.findByName(name);
   	 if(cate == null)
   	 {
   		return true;
     }
   	
   	 boolean isCreatingMode = (id == null);
   	
   	 if(isCreatingMode)
   	 {
   		return false;
   	 }
   	 else
   	 {
   		if(cate.getId() != id)
   		{
   			return false;
   		}
   	 }
   	 return true;
   }
   
   public boolean checkUniqueAlias(Integer id, String alias)
   {
   	 Category cate = repo.findByAlias(alias);
   	 if(cate == null)
   	 {
   		return true;
     }
   	
   	 boolean isCreatingMode = (id == null);
   	
   	 if(isCreatingMode)
   	 {
   		return false;
   	 }
   	 else
   	 {
   		if(cate.getId() != id)
   		{
   			return false;
   		}
   	 }
   	 return true;
   } 
   
   public List<Category> listCategoriesUsedInForm()
   {
	   List<Category> categoryUsedInForm = new ArrayList<>();
	   
	   List<Category> categoryInDb = repo.findRootCategories(Sort.by("name").ascending());
	   
	   for (Category category : categoryInDb) {
		   categoryUsedInForm.add(Category.copyFull(category));
			
			Set<Category> children = sortCategory(category.getChildren());
			
			for (Category subCategory : children) {
				String name = "--" + subCategory.getName();
				Category cloneCate = Category.copyFull(category);
				cloneCate.setName(name);
				categoryUsedInForm.add(cloneCate);
				listSubCategoriesUsedInForm(categoryUsedInForm, subCategory, 1);
			}
		}	
	   return categoryUsedInForm;
   }
   
   private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, 
			Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = sortCategory(parent.getChildren());
		
		for (Category subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {				
				name += "--";
			}
			name += subCategory.getName();
			Category cloneCate = Category.copyFull(subCategory);
			cloneCate.setName(name);
			categoriesUsedInForm.add(cloneCate);
			
			listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
		}		
   }
   
   public SortedSet<Category> sortCategory(Set<Category> categories)
   {
	    return sortCategory("asc", categories);
   }
   
   public SortedSet<Category> sortCategory(String sort,Set<Category> categories)
   {
	   SortedSet<Category> sortedChildren = new TreeSet<>(new Comparator<Category>() {

	   @Override
	   public int compare(Category o1, Category o2) {
			// TODO Auto-generated method stub
			if(sort.equals("asc"))
			{
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
			else
			{
				return o2.getName().compareToIgnoreCase(o1.getName());
			}
		}
	   });
	   sortedChildren.addAll(categories);
	   return sortedChildren;
   }
   
   public Category save(Category cate)
   {
	   return repo.save(cate);
   }
}
