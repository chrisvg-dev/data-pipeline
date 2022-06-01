package com.arkon.pipeline.v1.repository;

import com.arkon.pipeline.v1.model.Alcaldia;
import com.arkon.pipeline.v1.model.Informacion;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlcaldiaRepositoryTest {
    @Autowired
    private AlcaldiaRepository alcaldiaRepository;


    @Test
    public void contextLoads() {
        assertThat(alcaldiaRepository).isNotNull();
    }
    private Alcaldia alcaldia, alcaldia2, alcaldia3;

    @BeforeEach
    public void setup() {
        this.alcaldia = new Alcaldia(1, "AZCAPOTZALCO");
        this.alcaldia2 = new Alcaldia(null, "CUAUHTEMOC");
        this.alcaldia3 = new Alcaldia(null, "BENITO JUAREZ");
        this.alcaldiaRepository.save(alcaldia);
        this.alcaldiaRepository.save(alcaldia2);
        this.alcaldiaRepository.save(alcaldia3);
    }
    @Test
    void givenList_whenGetAllResults_thenGiveAList(){
        assertFalse( this.alcaldiaRepository.findAll().isEmpty() );
    }

    @Test
    void givenItem_whenGetItem_thenGiveAnItem(){
        this.alcaldia = new Alcaldia(1, "AZCAPOTZALCO");
        this.alcaldiaRepository.save(alcaldia);
        //assertEquals(alcaldia, this.alcaldiaRepository.findByName("AZCAPOTZALCO") );
    }

}