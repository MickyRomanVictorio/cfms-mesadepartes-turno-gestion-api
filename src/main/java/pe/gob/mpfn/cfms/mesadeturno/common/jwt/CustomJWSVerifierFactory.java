package pe.gob.mpfn.cfms.mesadeturno.common.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyTypeException;
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;

import javax.crypto.SecretKey;
import java.security.Key;

public class CustomJWSVerifierFactory extends DefaultJWSVerifierFactory {
    @Override
    public JWSVerifier createJWSVerifier(JWSHeader header, Key key) throws JOSEException {
        JWSVerifier verifier;

        if (DESVerifier.SUPPORTED_ALGORITHMS.contains(header.getAlgorithm())) {
            if (!(key instanceof SecretKey)) {
                throw new KeyTypeException(SecretKey.class);
            }

            SecretKey macKey = (SecretKey)key;

            verifier = new DESVerifier(macKey);

            return verifier;
        }

        return super.createJWSVerifier(header, key);
    }
}