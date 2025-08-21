package pe.gob.mpfn.cfms.mesadeturno.common.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.impl.AlgorithmSupportMessage;
import com.nimbusds.jose.crypto.impl.BaseJWSProvider;
import com.nimbusds.jose.crypto.impl.CriticalHeaderParamsDeferral;
import com.nimbusds.jose.crypto.impl.HMAC;
import com.nimbusds.jose.crypto.utils.ConstantTimeUtils;
import com.nimbusds.jose.util.Base64URL;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class DESVerifier extends BaseJWSProvider implements JWSVerifier {
    public static final Set<JWSAlgorithm> SUPPORTED_ALGORITHMS;

    static {
        Set<JWSAlgorithm> algs = new LinkedHashSet<>();
        algs.add(JWSAlgorithm.HS512);
        SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(algs);
    }

    private final SecretKey secret;

    private final CriticalHeaderParamsDeferral critPolicy = new CriticalHeaderParamsDeferral();

    public DESVerifier(final SecretKey secretKey) {
        super(SUPPORTED_ALGORITHMS);
        this.secret = secretKey;
    }

    public SecretKey getSecretKey() {
        return new SecretKeySpec(secret.getEncoded(), secret.getAlgorithm());
    }
    public byte[] getSecret() {
        return secret.getEncoded();
    }

    protected static String getJCAAlgorithmName(final JWSAlgorithm alg) throws JOSEException {
        if (alg.equals(JWSAlgorithm.HS512)) {
            return "HMACSHA512";
        } else {
            throw new JOSEException(AlgorithmSupportMessage.unsupportedJWSAlgorithm(alg, SUPPORTED_ALGORITHMS));
        }
    }

    @Override
    public boolean verify(JWSHeader header, byte[] signedContent, Base64URL signature) throws JOSEException {
        if (!critPolicy.headerPasses(header)) {
            return false;
        }

        String jcaAlg = getJCAAlgorithmName(header.getAlgorithm());
        byte[] expectedHMAC = HMAC.compute(jcaAlg, getSecret(), signedContent, getJCAContext().getProvider());
        return ConstantTimeUtils.areEqual(expectedHMAC, signature.decode());
    }
}