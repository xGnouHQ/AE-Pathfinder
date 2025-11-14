package com.pathfinder.service;

import com.pathfinder.model.NachwuchskraftAnhang;
import com.pathfinder.repository.NachwuchskraftAnhangRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NachwuchskraftAnhangServiceTest {

    @Test
    void simpleTest() {
        assertTrue(true);
    }

    private NachwuchskraftAnhangRepository repository;
    private NachwuchskraftAnhangService service;

    @BeforeEach
    void setup() {
        repository = mock(NachwuchskraftAnhangRepository.class);
        service = new NachwuchskraftAnhangService(repository);
    }

    @Test
    void getByNachwuchskraft_shouldReturnList() {
        NachwuchskraftAnhang a1 = new NachwuchskraftAnhang();
        a1.setId(1L);
        NachwuchskraftAnhang a2 = new NachwuchskraftAnhang();
        a2.setId(2L);

        when(repository.findByNachwuchskraftId(5L)).thenReturn(List.of(a1, a2));

        List<NachwuchskraftAnhang> result = service.getByNachwuchskraft(5L);

        assertEquals(2, result.size());
        verify(repository, times(1)).findByNachwuchskraftId(5L);
    }

    @Test
    void save_shouldReturnSavedEntity() {
        NachwuchskraftAnhang anhang = new NachwuchskraftAnhang();
        anhang.setDateipfad("/tmp/lebenslauf.pdf");

        when(repository.save(anhang)).thenReturn(anhang);

        NachwuchskraftAnhang result = service.save(anhang);

        assertNotNull(result);
        assertEquals("/tmp/lebenslauf.pdf", result.getDateipfad());
        verify(repository, times(1)).save(anhang);
    }

    @Test
    void update_shouldModifyExistingEntity() {
        NachwuchskraftAnhang existing = new NachwuchskraftAnhang();
        existing.setId(10L);
        existing.setDateipfad("/alt.pdf");
        existing.setTyp(NachwuchskraftAnhang.DokumentTyp.ZEUGNIS);

        NachwuchskraftAnhang updated = new NachwuchskraftAnhang();
        updated.setDateipfad("/neu.pdf");
        updated.setTyp(NachwuchskraftAnhang.DokumentTyp.MOTIVATIONSSCHREIBEN);

        when(repository.findById(10L)).thenReturn(Optional.of(existing));
        when(repository.save(any(NachwuchskraftAnhang.class))).thenAnswer(i -> i.getArguments()[0]);

        NachwuchskraftAnhang result = service.update(10L, updated);

        assertEquals("/neu.pdf", result.getDateipfad());
        assertEquals(NachwuchskraftAnhang.DokumentTyp.MOTIVATIONSSCHREIBEN, result.getTyp());
        verify(repository, times(1)).save(existing);
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        NachwuchskraftAnhang updated = new NachwuchskraftAnhang();
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(
                java.util.NoSuchElementException.class,
                () -> service.update(999L, updated)
        );
    }

    @Test
    void delete_shouldCallRepository() {
        service.delete(7L);
        verify(repository, times(1)).deleteById(7L);
    }
}
