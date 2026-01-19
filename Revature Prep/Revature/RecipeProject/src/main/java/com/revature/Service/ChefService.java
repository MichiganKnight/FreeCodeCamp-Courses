package com.revature.Service;

import com.revature.DAO.ChefDAO;
import com.revature.Model.Chef;
import com.revature.Util.Page;
import com.revature.Util.PageOptions;

import java.util.List;
import java.util.Optional;

public class ChefService {
    private ChefDAO chefDAO;

    public ChefService(ChefDAO chefDAO) {
        this.chefDAO = chefDAO;
    }

    public Optional<Chef> getChefById(int id) {
        return Optional.ofNullable(chefDAO.getChefById(id));
    }

    public void saveChef(Chef chef) {
        if (chef.getId() == 0) {
            int id = chefDAO.createChef(chef);
            chef.setId(id);
        } else {
            chefDAO.updateChef(chef);
        }
    }

    public List<Chef> searchChefs(String term) {
        if (term == null) {
            return chefDAO.getAllChefs();
        } else {
            return chefDAO.searchChefsByTerm(term);
        }
    }

    public void deleteChef(int id) {
        Chef chef = chefDAO.getChefById(id);
        if (chef != null) {
            chefDAO.deleteChef(chef);
        }
    }

    public Page<Chef> searchChefs(String term, int page, int pageSize, String sortBy, String sortDirection) {
        PageOptions pageOptions = new PageOptions(page, pageSize, sortBy, sortDirection);
        if (term == null) {
            return chefDAO.getAllChefs(pageOptions);
        } else {
            return chefDAO.searchChefsByTerm(term, pageOptions);
        }
    }
}
