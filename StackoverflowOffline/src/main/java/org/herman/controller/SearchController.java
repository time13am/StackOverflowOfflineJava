package org.herman.controller;

import java.util.List;
import org.herman.model.Question;
import org.herman.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {
    
    @RequestMapping(value = "/search/{keyword}")
    public @ResponseBody List<Question> search(@PathVariable String keyword) { 
        
        return new SearchService().Search(keyword);
    }
    
}
