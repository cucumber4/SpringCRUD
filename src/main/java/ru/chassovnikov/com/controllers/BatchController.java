package ru.chassovnikov.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.chassovnikov.com.dao.PersonDAO;

@Controller
@RequestMapping("/test")
public class BatchController {

    private final PersonDAO personDAO;

    @Autowired
    public BatchController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String string(){
        return "Batch/index";
    }

    @GetMapping("/without")
    public String withoutBatch(){


        return "redirect:/people";
    }

    @GetMapping("/with")
    public String withBatch(){


        return "redirect:/people";
    }
}
