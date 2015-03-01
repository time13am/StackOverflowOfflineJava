package org.herman.controller;

import java.util.ArrayList;
import java.util.List;
import org.herman.model.Question;
import org.herman.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionController {
    
    @RequestMapping(value = "/question/{id}", method = RequestMethod.GET)
    public ModelAndView display(@PathVariable String id) {
        
        List<Question> answers = new QuestionService().process_page(Integer.parseInt(id));
        String title = answers.get(0).getTitle();
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("question");
        mav.addObject("title", title);
        mav.addObject("answers", answers);
        
        return mav;
    }    
}
