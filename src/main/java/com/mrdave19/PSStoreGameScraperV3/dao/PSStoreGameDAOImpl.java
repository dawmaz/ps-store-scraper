package com.mrdave19.PSStoreGameScraperV3.dao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mrdave19.PSStoreGameScraperV3.entity.PSStoreGame;

@Repository
public class PSStoreGameDAOImpl implements PSStoreGameDAO {


	@Autowired
	private  EntityManager entityManagerFactory;

	@Override
	@Transactional
	public void save(PSStoreGame psGame) {
		Session currentSession = entityManagerFactory.unwrap(Session.class);
		currentSession.saveOrUpdate(psGame);

	}

	@Override
	@Transactional
	public void save(List<PSStoreGame> list) {
		Session currentSession = entityManagerFactory.unwrap(Session.class);

		for (PSStoreGame psGame : list) {
			currentSession.saveOrUpdate(psGame);

		}
	}

	}
