package com.ucr.reco.service;


import com.ucr.reco.model.Space;
import com.ucr.reco.repository.SpaceJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceService {

    @Autowired
    private SpaceJpaRepository repository;


    public List<Space> findAll() {
        return repository.findAll();
    }

    public Space getById(Integer id) {
        return repository.getById(id);
    }

    public Space add(Space space) {
        Space spaceExits = repository.getByName(space.getName());
        if (spaceExits != null) {
            return null;
        } else {
            if (space.getName() == null || space.getLocation() == null || space.getType() == null || space.getPrice() == null) {
                return null;
            }
        }
        return repository.save(space);
    }

    public Space update(Integer id, Space space) {
        Optional<Space> spaceExists = repository.findById(id);

        if (spaceExists.isPresent()) {
            Space spaceTemp = spaceExists.get();

            if (space.getName() != null) {
                spaceTemp.setName(space.getName());
            }

            if (space.getLocation() != null) {
                spaceTemp.setLocation(space.getLocation());
            }

            if (space.getType() != null) {
                spaceTemp.setType(space.getType());
            }

            if (space.getPrice() != null) {
                spaceTemp.setPrice(space.getPrice());
            }

            return repository.save(spaceTemp);
        }

        return null;
    }

    /*
    public Space delete(Integer id) {
        Space spaceExits = repository.getById(id);
        if (spaceExits != null) {
            repository.deleteById(id);
            return spaceExits;
        }
        return null;
    }
    */

    public Space delete(Integer id) {
        Optional<Space> spaceExits = repository.findById(id);
        if (spaceExits.isPresent()) {
            repository.deleteById(id);
            return spaceExits.get();
        }else {
            return null;
        }
    }

    /*
    //esta version no es muy robusta
    public Space changePrice(Integer id, Double price) {
        Space spaceExits = repository.getById(id);
        if (spaceExits != null) {
            spaceExits.setPrice(price);
            return repository.save(spaceExits);
        }
        return null;
    }
     */

    public Space changePrice(Integer id, Double price) {
        Optional<Space>  spaceExits = repository.findById(id);
        if (spaceExits.isPresent()){
            Space spaceTemp=spaceExits.get();
            spaceTemp.setPrice(price);
            return repository.save(spaceTemp);
        }
        return null;
    }



}
