package com.trilogyed.MusicStoreRecommendations.controller;

import com.trilogyed.MusicStoreRecommendations.model.LabelRecommendation;
import com.trilogyed.MusicStoreRecommendations.repository.LabelRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/labelRec")
public class LabelRecommendationController {

    @Autowired
    LabelRecommendationRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRecommendation> getAllLabelRecommendations(){
        List<LabelRecommendation> allLabelRecommendations = repo.findAll();
        if (allLabelRecommendations == null || allLabelRecommendations.isEmpty()){
            throw new IllegalArgumentException("No labelRecs were found");
        } else {
            return allLabelRecommendations;
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation createLabelRecommendation(@RequestBody @Valid LabelRecommendation labelRec){
        repo.save(labelRec);
        return labelRec;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelRecommendation(@RequestBody @Valid LabelRecommendation labelRec){
        if (labelRec == null || labelRec.getId() < 1){
            throw new IllegalArgumentException("LabelRecommendation does not exists with this Id");
        } else if (labelRec.getId() > 0){
            repo.save(labelRec);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelRecommendation getLabelRecommendationById(@PathVariable("id") Integer labelRecId){
        Optional<LabelRecommendation> labelRec = repo.findById(labelRecId);
        if (labelRec == null){
            throw new IllegalArgumentException("LabelRecommendation not found with this id");
        } else {
            return (labelRec.get());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendationById(@PathVariable("id") Integer labelRecId){
        repo.deleteById(labelRecId);
    }


}
