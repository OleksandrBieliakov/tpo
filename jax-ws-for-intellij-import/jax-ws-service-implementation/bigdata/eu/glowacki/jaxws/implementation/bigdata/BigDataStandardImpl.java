package eu.glowacki.jaxws.implementation.bigdata;

import eu.glowacki.jaxws.api.bigdata.BigData;
import eu.glowacki.jaxws.api.bigdata.IBigDataStandard;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.logging.Logger;

@WebService( //
		name = "IBigDataStandard", //
		targetNamespace = "http://glowacki.eu/big-data/standard" //
)
public final class BigDataStandardImpl implements IBigDataStandard {

	private static final Logger LOGGER = Logger.getAnonymousLogger();

	public static void main(String... args) {
		Endpoint.publish(IBigDataStandard.URI, new BigDataStandardImpl());
		LOGGER.info("SERVICE STARTED");
	}

	private BigDataStandardImpl() {
	}

	@WebMethod
	public BigData get() {
		BigData data = BigData.generate();
		return data;
	}
}