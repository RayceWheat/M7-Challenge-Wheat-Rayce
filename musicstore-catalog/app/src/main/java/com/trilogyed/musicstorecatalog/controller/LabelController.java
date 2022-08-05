package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/label")
public class LabelController {

    @Autowired
    LabelRepository repo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Label> getAllLabels(){
        List<Label> allLabels = repo.findAll();
        if (allLabels == null || allLabels.isEmpty()){
            throw new IllegalArgumentException("No labels were found");
        } else {
            return allLabels;
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Label createLabel(@RequestBody @Valid Label label){
        repo.save(label);
        return label;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@RequestBody @Valid Label label){
        if (label == null || label.getId() < 1){
            throw new IllegalArgumentException("Label does not exists with this Id");
        } else if (label.getId() > 0){
            repo.save(label);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Label getLabelById(@PathVariable("id") Integer labelId){
        Optional<Label> label = repo.findById(labelId);
        if (label == null){
            throw new IllegalArgumentException("Label not found with this id");
        } else {
            return (label.get());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelById(@PathVariable("id") Integer labelId){
        repo.deleteById(labelId);
    }


}
