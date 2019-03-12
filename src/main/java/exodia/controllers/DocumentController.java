package exodia.controllers;

import exodia.models.binding.DocumentScheduleBindingModel;
import exodia.models.service.DocumentServiceModel;
import exodia.models.view.DocumentDetailsViewModel;
import exodia.services.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class DocumentController {
    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    @Autowired
    public DocumentController(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/schedule")
    public ModelAndView schedule(ModelAndView modelAndView, HttpSession session){
        if (session.getAttribute("username") == null){
            modelAndView.setViewName("redirect:/login");
        }else{
            modelAndView.setViewName("/schedule");
        }
        return modelAndView;
    }

    @PostMapping("/schedule")
    public ModelAndView scheduleConfirm(@ModelAttribute DocumentScheduleBindingModel model,
                                        ModelAndView modelAndView){
        DocumentServiceModel documentServiceModel =
                this.modelMapper.map(model,DocumentServiceModel.class);
        documentServiceModel = this.documentService.scheduleDocument(documentServiceModel);
        if (documentServiceModel == null){
            throw new IllegalArgumentException("Document creation failed!");
        }else{
            modelAndView.setViewName("redirect:/details/" + documentServiceModel.getId());
        }
        return modelAndView;
    }
    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable(name = "id")String id, ModelAndView modelAndView,
     HttpSession session){
        if (session.getAttribute("username") == null){
            modelAndView.setViewName("redirect:/login");
        }else{
            DocumentServiceModel documentServiceModel =
                    this.documentService.findDocumentById(id);
            if (documentServiceModel == null){
                throw new IllegalArgumentException("Document is not found!");
            }
            modelAndView.setViewName("details");
            modelAndView.addObject("model",this.modelMapper
                    .map(documentServiceModel, DocumentDetailsViewModel.class));
        }

        return modelAndView;
    }
    @GetMapping("/print/{id}")
    public ModelAndView print(@PathVariable("id")String id,ModelAndView modelAndView,
                              HttpSession session){
        if (session.getAttribute("username") == null){
            modelAndView.setViewName("redirect:/login");
        }else{
            DocumentServiceModel documentServiceModel =
                    this.documentService.findDocumentById(id);
            if (documentServiceModel == null){
                throw new IllegalArgumentException("Document is not found!");
            }
            modelAndView.setViewName("print");
            modelAndView.addObject("model",this.modelMapper
                    .map(documentServiceModel, DocumentDetailsViewModel.class));
        }
        return modelAndView;

    }
    @PostMapping("/print/{id}")
    public ModelAndView confirmPrint(@PathVariable("id")String id,ModelAndView modelAndView){

        if (!this.documentService.printDocument(id)){
            throw new IllegalArgumentException("Something wrong");
        }
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }
}
