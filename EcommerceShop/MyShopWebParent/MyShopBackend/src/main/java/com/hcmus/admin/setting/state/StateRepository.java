package com.hcmus.admin.setting.state;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmus.common.entity.Country;
import com.hcmus.common.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
	List<State> findByCountryOrderByName(Country country);
}
