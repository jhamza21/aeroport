package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AeroportTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aeroport.class);
        Aeroport aeroport1 = new Aeroport();
        aeroport1.setId(1L);
        Aeroport aeroport2 = new Aeroport();
        aeroport2.setId(aeroport1.getId());
        assertThat(aeroport1).isEqualTo(aeroport2);
        aeroport2.setId(2L);
        assertThat(aeroport1).isNotEqualTo(aeroport2);
        aeroport1.setId(null);
        assertThat(aeroport1).isNotEqualTo(aeroport2);
    }
}
