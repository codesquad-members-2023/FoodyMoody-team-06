package com.foodymoody.be.member.repository;

import com.foodymoody.be.member.domain.TasteMood;
import com.foodymoody.be.member.domain.TasteMoodId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasteMoodRepository extends JpaRepository<TasteMood, TasteMoodId> {

    TasteMood findByName(String name);
}