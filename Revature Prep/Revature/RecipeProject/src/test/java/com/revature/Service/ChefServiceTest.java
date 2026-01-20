package com.revature.Service;

import com.revature.DAO.ChefDAO;
import com.revature.Model.Chef;
import com.revature.Util.Page;
import com.revature.Util.PageOptions;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ChefServiceTest {
    private ChefService chefService;
    private ChefDAO chefDAO;
    List<Chef> MOCKS;

    @BeforeEach
    void setUpMocks() {
        chefDAO = mock(ChefDAO.class);
        chefService = new ChefService(chefDAO);
        MOCKS = Arrays.asList(
                new Chef(1, "JoeCool", "redbarron", "snoopy@null.com", false),
                new Chef(2, "CharlieBrown", "thegreatpumpkin", "goodgrief@peanuts.com", false),
                new Chef(3, "RevaBuddy", "codelikeaboss", "revature@revature.com", false),
                new Chef(4, "ChefTrevin", "trevature", "trevin@revature.com", true));
    }

    @Test
    public void fetchOneChef() {
        when(chefDAO.getChefById(1)).thenReturn(MOCKS.get(0));
        Optional<Chef> chef = chefService.getChefById(1);
        Assertions.assertTrue(chef.isPresent(), () -> "Chef Should be Present");
        Assertions.assertEquals(MOCKS.get(0), chef.get(), () -> "Chef Should Match");
    }

    @Test
    public void failToFetchOneChef() {
        when(chefDAO.getChefById(1)).thenReturn(null);
        Optional<Chef> chef = chefService.getChefById(1);
        Assertions.assertTrue(chef.isEmpty(), () -> "Chef Should not be Present");
    }

    @Test
    public void saveNewChef() {
        Chef newChef = new Chef(0, "new chef", "1234abc", "newchef@chefscape.net", false);
        ArgumentCaptor<Chef> chefCaptor = ArgumentCaptor.forClass(Chef.class);
        when(chefDAO.createChef(any(Chef.class))).thenReturn(42);
        chefService.saveChef(newChef);
        verify(chefDAO).createChef(chefCaptor.capture());
        Chef captureChef = chefCaptor.getValue();
        Assertions.assertEquals(42, captureChef.getId(), () -> "Services Should Set the 'id' of a Newly Created Chef");
    }

    @Test
    public void updateChef() {
        Chef existingChef = new Chef(42, "Existing Chef", "existing.chef@gmail.com", "1234abc", false);
        ArgumentCaptor<Chef> chefCaptor = ArgumentCaptor.forClass(Chef.class);
        chefService.saveChef(existingChef);
        verify(chefDAO).updateChef(chefCaptor.capture());
        Chef captureChef = chefCaptor.getValue();
        Assertions.assertEquals(existingChef, captureChef, () -> "Services Should Not Change the 'id' of an Existing Chef");
    }

    @Test
    public void deleteChef() {
        when(chefDAO.getChefById(1)).thenReturn(MOCKS.get(0));
        doNothing().when(chefDAO).deleteChef(any(Chef.class));
        ArgumentCaptor<Chef> chefCaptor = ArgumentCaptor.forClass(Chef.class);
        chefService.deleteChef(1);
        verify(chefDAO).deleteChef(chefCaptor.capture());
        verify(chefDAO).getChefById(1);
    }

    @Test
    public void searchForListOfAllChefs() {
        when(chefDAO.getAllChefs()).thenReturn(MOCKS);
        List<Chef> chefs = chefService.searchChefs(null);
        Assertions.assertIterableEquals(MOCKS, chefs, () -> "Services Should Return a List of All Chefs");
    }

    @Test
    public void searchForFilteredListOfChefs() {
        when(chefDAO.searchChefsByTerm("a")).thenReturn(Arrays.asList(MOCKS.get(1), MOCKS.get(2)));
        List<Chef> chefs = chefService.searchChefs("a");
        Assertions.assertIterableEquals(Arrays.asList(MOCKS.get(1), MOCKS.get(2)), chefs, () -> "Services Should Return a Filtered List of Chefs");
    }

    @Test
    public void searchReturnsEmptyList() {
        when(chefDAO.searchChefsByTerm("Bal")).thenReturn(Collections.emptyList());
        List<Chef> chefs = chefService.searchChefs("Bal");
        Assertions.assertTrue(chefs.isEmpty(), () -> "Services Should Return an Empty List if No Chefs Match the Search Term");
    }

    @Test
    public void searchForPageOfAllChefs() {
        when(chefDAO.getAllChefs(any(PageOptions.class))).thenReturn(new Page<Chef>(1, 4, 1, 4, MOCKS));
        Page<Chef> chefs = chefService.searchChefs(null, 1, 4, "id", "asc");
        ArgumentCaptor<PageOptions> optionsCaptor = ArgumentCaptor.forClass(PageOptions.class);
        verify(chefDAO).getAllChefs(optionsCaptor.capture());
        Assertions.assertEquals(new Page<Chef>(1, 4, 1, 4, MOCKS), chefs, () -> "Services Shouldn't Change the Page Returned from DAO");
    }

    @Test
    public void searchForFilteredPageOfChef() {
        when(chefDAO.searchChefsByTerm(anyString(), any(PageOptions.class)))
                .thenReturn(new Page<Chef>(1, 2, 1, 2, Arrays.asList(MOCKS.get(1), MOCKS.get(2))));
        Page<Chef> chefs = chefService.searchChefs("a", 1, 2, "id", "asc");
        ArgumentCaptor<PageOptions> optionsCaptor = ArgumentCaptor.forClass(PageOptions.class);
        ArgumentCaptor<String> termCaptor = ArgumentCaptor.forClass(String.class);
        verify(chefDAO).searchChefsByTerm(termCaptor.capture(), optionsCaptor.capture());
        Assertions.assertEquals(new Page<Chef>(1, 2, 1, 2, Arrays.asList(MOCKS.get(1), MOCKS.get(2))), chefs, () -> "Services Shouldn't Change the Page Returned from DAO");
    }

    @Test
    public void searchReturnsEmptyPage() {
        when(chefDAO.searchChefsByTerm(anyString(), any(PageOptions.class)))
                .thenReturn(new Page<Chef>(1, 5, 0, 0, Collections.emptyList()));
        Page<Chef> chefs = chefService.searchChefs("Bal", 1, 5, "id", "asc");
        ArgumentCaptor<PageOptions> optionsCaptor = ArgumentCaptor.forClass(PageOptions.class);
        ArgumentCaptor<String> termCaptor = ArgumentCaptor.forClass(String.class);
        verify(chefDAO).searchChefsByTerm(termCaptor.capture(), optionsCaptor.capture());
        Assertions.assertEquals(new Page<Chef>(1, 5, 0, 0, Collections.emptyList()), chefs, () -> "Services Shouldn't Change the Page Returned from DAO");
    }
}
