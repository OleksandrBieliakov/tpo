package eu.glowacki.jaxws.implementation.bigdata;

import eu.glowacki.jaxws.api.bigdata.BigData;
import eu.glowacki.jaxws.api.bigdata.IBigDataMTOM;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.MTOM;
import java.util.logging.Logger;

@MTOM
@WebService( //
		name = "IBigDataMTOM", //
		targetNamespace = "http://glowacki.eu/big-data/mtom" //
)
public final class BigDataMTOMImpl implements IBigDataMTOM {

	private static final Logger LOGGER = Logger.getAnonymousLogger();
	
	public static void main(String... args) {
		Endpoint.publish(IBigDataMTOM.URI, new BigDataMTOMImpl());
		LOGGER.info("SERVICE STARTED");
	}
	
	private BigDataMTOMImpl() {
	}

	public BigData get() {
		BigData data = BigData.generate();
		return data;
	}
}