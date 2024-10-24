package com.hcmus.site.setting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmus.common.entity.setting.Setting;
import com.hcmus.common.entity.setting.SettingCategory;

import java.util.List;


@Repository
public interface SettingRepository extends JpaRepository<Setting, String> {

	List<Setting> findByCategory(SettingCategory category);

	Setting findByKey(String key);
}
