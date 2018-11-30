package com.jian.core.model.bo;

import java.util.ArrayList;
import java.util.List;

public class PetFrontPageListBo {

    private List<PetFrontPageBo> petFrontPageBoList = new ArrayList<>();

    public List<PetFrontPageBo> getPetFrontPageBoList() {
        return petFrontPageBoList;
    }

    public void setPetFrontPageBoList(List<PetFrontPageBo> petFrontPageBoList) {
        this.petFrontPageBoList = petFrontPageBoList;
    }
}
