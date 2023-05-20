/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.integration.repositories;

import it.csi.conam.conambl.integration.entity.CnmDStatoRiscossione;
import it.csi.conam.conambl.integration.entity.CnmROrdinanzaVerbSog;
import it.csi.conam.conambl.integration.entity.CnmTRiscossione;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CnmTRiscossioneRepository extends CrudRepository<CnmTRiscossione, Integer>, JpaSpecificationExecutor<CnmTRiscossione> {
	// @Query("select r from CnmTRiscossione r join r.cnmROrdinanzaVerbSog ovs
	// where ovs=?1")
	CnmTRiscossione findByCnmROrdinanzaVerbSog(CnmROrdinanzaVerbSog cnmROrdinanzaVerbSog);

	List<CnmTRiscossione> findByCnmROrdinanzaVerbSogIn(List<CnmROrdinanzaVerbSog> cnmROrdinanzaVerbSogs);

	List<CnmTRiscossione> findByCnmDStatoRiscossioneInAndDataOraInsertGreaterThanEqual(List<CnmDStatoRiscossione> cnmDStatoRiscossiones, Timestamp time);

	CnmTRiscossione findByIdRiscossione(Integer idRiscossione);

	List<CnmTRiscossione> findByCnmDStatoRiscossione(CnmDStatoRiscossione cnmDStatoRiscossione);

	@Query(value = "select ris.* from cnm_t_record r join cnm_t_riscossione ris on ris.id_riscossione = r.id_riscossione join cnm_t_record_ritorno rr on rr.id_record = r.id_record "
			+ " where  rr.importo_carico > 0.00 and rr.id_tipo_record = 6 and  rr.cod_entrata in (select dt.cod_tipo_tributo from cnm_d_tipo_tributo dt) and rr.id_stato_record = 1 ", nativeQuery = true)
	List<CnmTRiscossione> findByCnmTRecordCnmTRiscossione();

	@Query(value = "select risc.* " + //
			"from cnm_t_riscossione risc " + //
			"join cnm_t_record rec on rec.id_riscossione = risc.id_riscossione " + //
			"join cnm_t_file f on f.id_file = rec.id_file " + //
			"where f.id_stato_file = ?1 ", nativeQuery = true)
	List<CnmTRiscossione> findByCnmTRiscossioneByStatoFile(long idStatoFile);
}
