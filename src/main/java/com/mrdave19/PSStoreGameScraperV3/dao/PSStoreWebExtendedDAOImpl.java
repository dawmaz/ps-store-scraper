package com.mrdave19.PSStoreGameScraperV3.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.LikeEscapeStep;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mrdave19.PSStoreGameScraperV3.entity.AuditEmbededId;
import com.mrdave19.PSStoreGameScraperV3.entity.AuditEmbededId_;
import com.mrdave19.PSStoreGameScraperV3.entity.AuditLog;
import com.mrdave19.PSStoreGameScraperV3.entity.AuditLog_;
import com.mrdave19.PSStoreGameScraperV3.entity.PSStoreGame;
import com.mrdave19.PSStoreGameScraperV3.entity.PSStoreGameWebExtended;
import com.mrdave19.PSStoreGameScraperV3.entity.PSStoreGame_;
import com.mrdave19.PSStoreGameScraperV3.entity.Rev;
import com.mrdave19.PSStoreGameScraperV3.entity.Rev_;
import com.mrdave19.PSStoreGameScraperV3.entity.jooq.games.tables.Psstoregamess;
import com.mrdave19.PSStoreGameScraperV3.entity.jooq.games.tables.PsstoregamessAuditLog;
import com.mrdave19.PSStoreGameScraperV3.entity.jooq.games.tables.Revinfo;
import com.mrdave19.PSStoreGameScraperV3.utils.SortingMethod;
import com.mrdave19.PSStoreGameScraperV3.utils.SortingMethodJooq;

@Repository
public class PSStoreWebExtendedDAOImpl implements PSStoreGameWebExtendedDAO{

	@Autowired
	DSLContext create;

	@Autowired
	private EntityManager entityManagerFactory;
	
	@Override
	@Transactional
	public  List<PSStoreGameWebExtended> getGamesFrontPage(int currentPage, int pageSize, String orderby, Optional<String> gamename) {
		Session currentSession = entityManagerFactory.unwrap(Session.class);

		CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
		CriteriaQuery<PSStoreGameWebExtended> createQuery = criteriaBuilder.createQuery(PSStoreGameWebExtended.class);
		Root<PSStoreGame> psstoregameRoot = createQuery.from(PSStoreGame.class);
		Root<AuditLog> auditlogRoot = createQuery.from(AuditLog.class);

		Join<AuditLog, Rev> revJoin = auditlogRoot.join(AuditLog_.rev);

		List<Predicate> predicates = new ArrayList<>();

		Path<AuditEmbededId> auditIdPath = auditlogRoot.get(AuditLog_.auditId);

		Predicate predArtJoin = criteriaBuilder.equal(auditIdPath.get(AuditEmbededId_.id),
				psstoregameRoot.get(PSStoreGame_.id));
		predicates.add(predArtJoin);

		if (gamename.isPresent())
			predicates.add(criteriaBuilder.like(psstoregameRoot.get(PSStoreGame_.name), "%" + gamename.get() + "%"));

		Path<String> id = psstoregameRoot.get(PSStoreGame_.id);
		Path<String> name = psstoregameRoot.get(PSStoreGame_.name);
		Path<String> device = psstoregameRoot.get(PSStoreGame_.device);
		Path<String> type = psstoregameRoot.get(PSStoreGame_.type);
		Path<String> price = psstoregameRoot.get(PSStoreGame_.price);
		Expression<BigInteger> minRevtstmp = criteriaBuilder.min(revJoin.get(Rev_.revtstmp));
		Expression<BigInteger> maxRevtstmp = criteriaBuilder.max(revJoin.get(Rev_.revtstmp));
		Expression<Object> artPrice = criteriaBuilder.selectCase(psstoregameRoot.get(PSStoreGame_.price))
				.when("Free", 0.00).when("No price found", criteriaBuilder.nullLiteral(Double.class))
				.when("Unavailable", criteriaBuilder.nullLiteral(Double.class))
				.otherwise(criteriaBuilder.substring(psstoregameRoot.get(PSStoreGame_.price), 2).as(Double.class));

		createQuery.multiselect(id, name, device, type, price, minRevtstmp, maxRevtstmp, artPrice);
		createQuery.groupBy(id);
		createQuery.where(predicates.toArray(new Predicate[0]));
		createQuery.orderBy(SortingMethod.valueOf(orderby.toUpperCase()).apply(criteriaBuilder, psstoregameRoot, name,
				minRevtstmp, maxRevtstmp, artPrice));

		
		return currentSession.createQuery(createQuery)
				.setFirstResult((currentPage == 1) ? 0 : (currentPage - 1) * pageSize).setMaxResults(pageSize)
				.getResultList();

	}

	@Override
	public Long getTotalNumberOfGames(Optional<String> game) {
		Session currentSession = entityManagerFactory.unwrap(Session.class);
		CriteriaBuilder builder = currentSession.getCriteriaBuilder();
		CriteriaQuery<Long> cq = builder.createQuery(Long.class);
		Root<PSStoreGame> root = cq.from(PSStoreGame.class);
		cq.select(builder.count(root));
		if (game.isPresent())
			cq.where(builder.like(root.get(PSStoreGame_.name), "%" + game.get() + "%"));
		
		  return currentSession.createQuery(cq).getSingleResult();
		 

	}

	@Override
	@Transactional
	public Long getTotalNumberOfNewGames(Long days, Optional<String> gameName) {
		Session currentSession = entityManagerFactory.unwrap(Session.class);

		CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
		CriteriaQuery<Long> createQuery = criteriaBuilder.createQuery(Long.class);
		Root<AuditLog> auditRoot = createQuery.from(AuditLog.class);
		Join<AuditLog, Rev> revRoot = auditRoot.join(AuditLog_.rev);

		List<Predicate> predicates = new ArrayList<>();
		Predicate revtstmp = criteriaBuilder.greaterThanOrEqualTo(revRoot.get(Rev_.revtstmp).as(Long.class),
				System.currentTimeMillis() - days * 1000 * 60 * 60 * 24);
		Predicate auditRev = criteriaBuilder.equal(auditRoot.get(AuditLog_.revtype), 0);

		predicates.add(revtstmp);
		predicates.add(auditRev);
		if (gameName.isPresent())
			predicates.add(criteriaBuilder.like(auditRoot.get(AuditLog_.name), "%" + gameName.get() + "%"));

		createQuery.select(criteriaBuilder.count(auditRoot));
		createQuery.where(predicates.toArray(new Predicate[0]));
		

		return currentSession.createQuery(createQuery).getSingleResult();

	}

	@Override
	@Transactional
	public List<PSStoreGameWebExtended> getNewGames(int currentPage, int pageSize, String orderby, long limit,
			Optional<String> gameName) {
		Session currentSession = entityManagerFactory.unwrap(Session.class);

		CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
		CriteriaQuery<PSStoreGameWebExtended> createQuery = criteriaBuilder.createQuery(PSStoreGameWebExtended.class);
		Root<PSStoreGame> psstoregameRoot = createQuery.from(PSStoreGame.class);
		Root<AuditLog> auditlogRoot = createQuery.from(AuditLog.class);

		Join<AuditLog, Rev> revJoin = auditlogRoot.join(AuditLog_.rev);

		List<Predicate> predicates = new ArrayList<>();

		Path<AuditEmbededId> auditIdPath = auditlogRoot.get(AuditLog_.auditId);

		Predicate predArtJoin = criteriaBuilder.equal(auditIdPath.get(AuditEmbededId_.id),
				psstoregameRoot.get(PSStoreGame_.id));
		predicates.add(predArtJoin);

		if (gameName.isPresent())
			predicates.add(criteriaBuilder.like(psstoregameRoot.get(PSStoreGame_.name), "%" + gameName.get() + "%"));

		Path<String> id = psstoregameRoot.get(PSStoreGame_.id);
		Path<String> name = psstoregameRoot.get(PSStoreGame_.name);
		Path<String> device = psstoregameRoot.get(PSStoreGame_.device);
		Path<String> type = psstoregameRoot.get(PSStoreGame_.type);
		Path<String> price = psstoregameRoot.get(PSStoreGame_.price);
		Expression<BigInteger> minRevtstmp = criteriaBuilder.min(revJoin.get(Rev_.revtstmp));
		Expression<BigInteger> maxRevtstmp = criteriaBuilder.max(revJoin.get(Rev_.revtstmp));
		Expression<Object> artPrice = criteriaBuilder.selectCase(psstoregameRoot.get(PSStoreGame_.price))
				.when("Free", 0.00).when("No price found", criteriaBuilder.nullLiteral(Double.class))
				.when("Unavailable", criteriaBuilder.nullLiteral(Double.class))
				.otherwise(criteriaBuilder.substring(psstoregameRoot.get(PSStoreGame_.price), 2).as(Double.class));

		createQuery.multiselect(id, name, device, type, price, minRevtstmp, maxRevtstmp, artPrice);
		createQuery.groupBy(id);
		createQuery.where(predicates.toArray(new Predicate[0]));
		createQuery.having(criteriaBuilder.greaterThanOrEqualTo(minRevtstmp.as(Long.class),
				System.currentTimeMillis() - limit * 1000 * 60 * 60 * 24));
		createQuery.orderBy(SortingMethod.valueOf(orderby.toUpperCase()).apply(criteriaBuilder, psstoregameRoot, name,
				minRevtstmp, maxRevtstmp, artPrice));

		return currentSession.createQuery(createQuery)
				.setFirstResult((currentPage == 1) ? 0 : (currentPage - 1) * pageSize).setMaxResults(pageSize)
				.getResultList();

	}
	

	@Override
	@Transactional
	public List<PSStoreGameWebExtended> getTopDiscountedGames(int currentPage, int pageSize, String orderby, long limit,Optional<String> gameName) {
		
		
		Psstoregamess games = Psstoregamess.PSSTOREGAMESS;
		PsstoregamessAuditLog audit = PsstoregamessAuditLog.PSSTOREGAMESS_AUDIT_LOG.as("primary");
		Revinfo review = Revinfo.REVINFO;
		
		PsstoregamessAuditLog subAudit = PsstoregamessAuditLog.PSSTOREGAMESS_AUDIT_LOG.as("secondary");
		
		SelectConditionStep<Record1<String>> subqueryMaxRev = create.select(subAudit.PRICE)
		.from(subAudit)
		.where(audit.ID.eq(subAudit.ID)).and(subAudit.REV.eq(DSL.max(audit.REV)));
		
		SelectConditionStep<Record1<String>> subqueryMinRev = create.select(subAudit.PRICE)
		.from(subAudit)
		.where(audit.ID.eq(subAudit.ID)).and(subAudit.REV.eq(DSL.min(audit.REV)));
		
		Field<BigDecimal> priceMax = subqueryMaxRev.asField().substring(2).cast(SQLDataType.DECIMAL(10, 2));
		Field<BigDecimal> priceMin= subqueryMinRev.asField().substring(2).cast(SQLDataType.DECIMAL(10, 2));
		
		List<LikeEscapeStep> predicates = new ArrayList<>();
		LikeEscapeStep predicate = audit.PRICE.notLike(DSL.all("Unavailable","No price found"));
		if(gameName.isPresent()) predicates.add(games.NAME.like("%"+ gameName.get()+"%"));
		
		predicates.add(predicate);
		
		
		
		List<PSStoreGameWebExtended> items = create.select(games.ID,games.NAME,games.DEVICE,games.TYPE,games.PRICE, 
				DSL.max(review.REVTSTMP).as("maxDate"),games.PRICE.substring(2).cast(SQLDataType.DECIMAL(10,2)).as("artPrice"),
				priceMax.sub(priceMin).as("priceChange"))
		.from(games).join(audit).on(games.ID.eq(audit.ID)).join(review).onKey()
		.where(predicates)
		.groupBy(audit.ID)
		.having(DSL.count(games.ID).gt(1)).and(priceMax.ne(priceMin).and(DSL.max(review.REVTSTMP).gt(!(limit==0)?System.currentTimeMillis() - limit * 1000 * 60 * 60 * 24:0)))
		.orderBy(SortingMethodJooq.valueOf(orderby.toUpperCase()).apply(games))
		.limit(pageSize).offset((currentPage == 1) ? 0 : (currentPage - 1) * pageSize)
		.fetch().into(PSStoreGameWebExtended.class);

		return items;
	}

	@Override
	@Transactional
	public Integer getTotalNumberOfDiscuntedGames(long limit, Optional<String> gameName) {
		Session currentSession = entityManagerFactory.unwrap(Session.class);

		CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
		CriteriaQuery<String> counter = criteriaBuilder.createQuery(String.class);
		Root<AuditLog> auditlogRoot = counter.from(AuditLog.class);
		Join<AuditLog, Rev> revJoin = auditlogRoot.join(AuditLog_.rev);
		Root<PSStoreGame> psstoregameRoot = counter.from(PSStoreGame.class);

		Path<AuditEmbededId> auditIdPath = auditlogRoot.get(AuditLog_.auditId);
		Path<String> idPath = psstoregameRoot.get(PSStoreGame_.id);
		
		List<Predicate> predicates = new ArrayList<>();
		Predicate crit1 = criteriaBuilder.notEqual(auditlogRoot.get(AuditLog_.price), "Unavailable");
		Predicate crit2 = criteriaBuilder.notEqual(auditlogRoot.get(AuditLog_.price), "No price found");
		Predicate crit3 = criteriaBuilder.equal(auditIdPath.get(AuditEmbededId_.id),
				psstoregameRoot.get(PSStoreGame_.id));
		predicates.add(crit1);
		predicates.add(crit2);
		predicates.add(crit3);
		if(gameName.isPresent()) predicates.add(criteriaBuilder.like(auditlogRoot.get(AuditLog_.NAME), "%" + gameName.get()+"%"));
		
		

		Expression<Long> count = criteriaBuilder.count(idPath);
		Expression<Integer> min = criteriaBuilder.min(auditIdPath.get(AuditEmbededId_.rev));
		Expression<Integer> max = criteriaBuilder.max(auditIdPath.get(AuditEmbededId_.rev));
		Expression<BigInteger> maxtstmp =
		criteriaBuilder.max(revJoin.get(Rev_.revtstmp));
		Predicate greaterThan = criteriaBuilder.greaterThan(count, 1L);

		Subquery<String> price = counter.subquery(String.class);
		Root<AuditLog> fromSub = price.from(AuditLog.class);
		Path<AuditEmbededId> auditIdSub = fromSub.get(AuditLog_.auditId);
		
		Subquery<String> price2 = counter.subquery(String.class);
		Root<AuditLog> fromSub2 = price2.from(AuditLog.class);
		Path<AuditEmbededId> auditIdSub2 = fromSub2.get(AuditLog_.auditId);
		

		Predicate idInd = criteriaBuilder.equal(auditIdPath.get(AuditEmbededId_.id),
				auditIdSub.get(AuditEmbededId_.id));
		Predicate minRev = criteriaBuilder.equal(fromSub.get(AuditLog_.rev), min);
		Subquery<String> whereId = price.select(fromSub.get(AuditLog_.price)).where(idInd, minRev);

		Predicate idInd2 = criteriaBuilder.equal(auditIdPath.get(AuditEmbededId_.id),
				auditIdSub2.get(AuditEmbededId_.id));
		Predicate maxRev2 = criteriaBuilder.equal(fromSub2.get(AuditLog_.rev), max);
		Subquery<String> whereId2 = price2.select(fromSub2.get(AuditLog_.price)).where(idInd2, maxRev2);

		Predicate limited =
		criteriaBuilder.greaterThanOrEqualTo(maxtstmp.as(Long.class),
		!(limit==0)? System.currentTimeMillis()-limit*1000*60*60*24:0);

		Predicate equal = criteriaBuilder.notEqual(whereId, whereId2);


		counter.select(idPath)
				.where(predicates.toArray(new Predicate[0]))
				.groupBy(auditIdPath.get(AuditEmbededId_.id))
				.having(limited,greaterThan,equal);

		return currentSession.createQuery(counter).getResultList().size();
	
	}

	@Override
	@Transactional
	public List<PSStoreGameWebExtended> getGameHistory(String id) {
		Session currentSession = entityManagerFactory.unwrap(Session.class);
		
		CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
		CriteriaQuery<PSStoreGameWebExtended> createQuery = criteriaBuilder.createQuery(PSStoreGameWebExtended.class);
		Root<AuditLog> root = createQuery.from(AuditLog.class);
		
		Join<AuditLog, Rev> join = root.join(AuditLog_.rev);
		Path<AuditEmbededId> path = root.get(AuditLog_.auditId);
		
		//select
		Path<String> pathId = path.get(AuditEmbededId_.id);
		Path<String> name = root.get(AuditLog_.name);
		Path<String> device = root.get(AuditLog_.device);
		Path<String> type = root.get(AuditLog_.type);
		Path<String> price = root.get(AuditLog_.price);
		Path<BigInteger> revtstmp = join.get(Rev_.revtstmp);
		//predicates
		Predicate predicate = criteriaBuilder.equal(pathId, id);
		
		createQuery.multiselect(pathId,name,device,type,price,revtstmp)
					.where(predicate)
					.orderBy(criteriaBuilder.asc(revtstmp));
		
		return currentSession.createQuery(createQuery).getResultList();
		
	}
	
}
