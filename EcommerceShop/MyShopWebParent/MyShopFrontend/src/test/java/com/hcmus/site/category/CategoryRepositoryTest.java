package com.hcmus.site.category;

import java.util.List;import org.hibernate.internal.build.AllowSysOut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.hcmus.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository repo;
	@Test
	public void testListAll()
	{
		List<Category> categories = repo.findAllEnabled();
		categories.forEach(System.out::println);
	}
	
	@Test
	public void getByAlias()
	{
	  String alias = "cellphone_cables_adapters";
	  Category cate = repo.findByAliasEnabled(alias);
	  System.out.println(cate);
	}
}
