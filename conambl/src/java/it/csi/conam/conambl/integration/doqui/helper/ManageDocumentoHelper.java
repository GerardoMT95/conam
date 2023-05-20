/**
 * 
 */
package it.csi.conam.conambl.integration.doqui.helper;

import it.csi.conam.conambl.integration.beans.*;
import it.csi.conam.conambl.integration.doqui.exception.CambiaStatoRichiestaException;
import it.csi.conam.conambl.integration.doqui.exception.EliminaDocumentoException;
import it.csi.conam.conambl.integration.doqui.exception.RecuperaRiferimentoDocumentoFisicoException;
import it.csi.conam.conambl.integration.doqui.exception.RicercaAllegatoException;
import it.csi.conam.conambl.integration.doqui.exception.SalvaDocumentoException;


/**
 * @author giuganino
 *
 */
public interface ManageDocumentoHelper extends CommonManageDocumentoHelper {
	
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws SalvaDocumentoException
	 */
	public ResponseSalvaDocumento salvaDocumento (RequestSalvaDocumento request) throws SalvaDocumentoException;
	
	
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws SalvaDocumentoException
	 */
	public ResponseSalvaDocumentoLogico salvaDocumentoLogico (RequestSalvaDocumentoLogico request) throws SalvaDocumentoException;
	
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws SalvaDocumentoException
	 * @throws EliminaDocumentoException 
	 */
	public ResponseEliminaDocumento eliminaDocumento (RequestEliminaDocumento request) throws EliminaDocumentoException;
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws SalvaDocumentoException
	 */
	public ResponseCambiaStatoRichiesta cambiaStatoRichiesta (RequestCambiaStatoRichiesta request) throws CambiaStatoRichiestaException;
	
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws SalvaDocumentoException
	 */
	public ResponseRecuperaRiferimentoDocumentoFisico recuperaRiferimentoDocumentoFisico (RequestRecuperaRiferimentoDocumentoFisico request) throws RecuperaRiferimentoDocumentoFisicoException;
	
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws RicercaAllegatoException
	 */
	public ResponseRicercaAllegato ricercaAllegato(RequestRicercaAllegato request) throws RicercaAllegatoException;
	

	/**
	 * 
	 * @param request
	 * @return
	 * @throws RicercaAllegatoException
	 */
	public ResponseRicercaAllegato ricercaAllegatoActa(RequestRicercaAllegato request) throws RicercaAllegatoException;
	

	
}
