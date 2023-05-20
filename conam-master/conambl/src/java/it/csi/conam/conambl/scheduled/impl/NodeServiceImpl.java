/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.scheduled.impl;

import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.common.config.Config;
import it.csi.conam.conambl.integration.entity.CnmCSchedulatore;
import it.csi.conam.conambl.integration.repositories.CnmCSchedulatoreRepository;
import it.csi.conam.conambl.scheduled.NodeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author riccardo.bova
 * @date 20 mar 2019
 */
@Service
@Transactional
public class NodeServiceImpl implements NodeService, ApplicationListener {

	private String ip;

	@Autowired
	private CnmCSchedulatoreRepository cnmCSchedulatoreRepository;
	@Autowired
	private UtilsDate utilsDate;

	public static final long TIMEOUT_IN_MINUTI = 11;

	protected static Logger logger = Logger.getLogger(NodeServiceImpl.class);

	@Autowired
	private Config config;

	@Override
	public void onApplicationEvent(final ApplicationEvent applicationEvent) {
		if (!config.getSendAllegatiToActa())
			return;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			logger.error("Non riesco a reperire l'ip del nodo");
		}
		if (applicationEvent instanceof ContextRefreshedEvent) {
			pingNode();
		}
	}

	@Override
	public void pingNode() {
		CnmCSchedulatore cnmCSchedulatore = cnmCSchedulatoreRepository.findByIp(ip);
		if (cnmCSchedulatore == null) {
			createNode();
		} else {
			updateNode(cnmCSchedulatore);
		}
	}

	@Override
	public void checkLeaderShip() {
		final List<CnmCSchedulatore> allList = (List<CnmCSchedulatore>) cnmCSchedulatoreRepository.findAll();
		final List<CnmCSchedulatore> aliveList = filterAliveNodes(allList);

		CnmCSchedulatore leader = findLeader(allList);
		if (leader != null && aliveList.contains(leader)) {
			setLeaderFlag(allList, Boolean.FALSE);
			leader.setLeader(Boolean.TRUE);
			cnmCSchedulatoreRepository.save(allList);
		} else {
			logger.info("leader non trovato in lista");
			final CnmCSchedulatore node = findMinNode(aliveList);
			setLeaderFlag(allList, Boolean.FALSE);
			node.setLeader(Boolean.TRUE);
			cnmCSchedulatoreRepository.save(allList);
		}
	}

	private CnmCSchedulatore findLeader(final List<CnmCSchedulatore> list) {
		for (CnmCSchedulatore systemNode : list) {
			if (systemNode.getLeader() != null && systemNode.getLeader()) {
				return systemNode;
			}
		}
		return null;
	}

	@Override
	public boolean isLeader() {
		final CnmCSchedulatore node = cnmCSchedulatoreRepository.findByIp(ip);
		return node != null && node.getLeader() != null && node.getLeader();
	}

	private void createNode() {
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		final CnmCSchedulatore node = new CnmCSchedulatore();
		node.setIp(ip);
		node.setDataOraInsert(now);
		node.setUltimoPing(now);
		List<CnmCSchedulatore> cnmCSchedulatores = (List<CnmCSchedulatore>) cnmCSchedulatoreRepository.findAll();
		node.setLeader(cnmCSchedulatores.isEmpty());
		cnmCSchedulatoreRepository.save(node);
	}

	private void updateNode(final CnmCSchedulatore node) {
		Timestamp now = utilsDate.asTimeStamp(LocalDateTime.now());
		node.setUltimoPing(now);
		cnmCSchedulatoreRepository.save(node);
	}

	private List<CnmCSchedulatore> filterAliveNodes(final List<CnmCSchedulatore> list) {
		final List<CnmCSchedulatore> finalList = new LinkedList<>();
		for (CnmCSchedulatore systemNode : list) {
			if (!hasExpired(systemNode.getUltimoPing())) {
				finalList.add(systemNode);
			}
		}
		if (CollectionUtils.isEmpty(finalList)) {
			logger.error("Nessun nodo attivo");
		}
		return finalList;
	}

	private boolean hasExpired(Timestamp time) {
		long timeout = TIMEOUT_IN_MINUTI * 60 * 1000;
		long actualTime = System.currentTimeMillis() - timeout;
		return time.getTime() < actualTime;
	}

	private CnmCSchedulatore findMinNode(final List<CnmCSchedulatore> list) {
		CnmCSchedulatore min = list.get(0);
		for (CnmCSchedulatore systemNode : list) {
			if (systemNode.getDataOraInsert().compareTo(min.getDataOraInsert()) < -1) {
				min = systemNode;
			}
		}
		return min;
	}

	private void setLeaderFlag(final List<CnmCSchedulatore> list, final Boolean value) {
		for (CnmCSchedulatore systemNode : list) {
			systemNode.setLeader(value);
		}
	}
}
