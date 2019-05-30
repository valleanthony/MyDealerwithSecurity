package com.example.springboot_401;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class CarHomeController {
    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    CarsRepo carsRepo;

    @Autowired
    UserService userService;

    @Autowired
    CloudnairyConfig cloudc;


    @RequestMapping("/") //This page will display all the cars uploaded by clients
    public String home(Model model) {
        model.addAttribute("cars", carsRepo.findAll());
        model.addAttribute("category",categoryRepo.findAll());
        return "index";
    }


    // This section is for all cars being made.
    @GetMapping("/AddCar")
    public String addACar(Model model) {
        model.addAttribute("car", new Cars());
        model.addAttribute("category", categoryRepo.findAll());

        return "carForm";
    }

    @PostMapping("/processCar") // <-- not sure if i need this part
    public String processCarForm(@ModelAttribute Cars cars, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));

            cars.setPicture(uploadResult.get("url").toString());
            cars.setCreatorID(userService.getCurrentUser().getId());
            carsRepo.save(cars);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/";
        }
        return "redirect:/";
    }

    @RequestMapping("/detail/car/{id}")
    public String showCar(@PathVariable("id") long id, Model model){
        model.addAttribute("car",carsRepo.findById(id).get());
        model.addAttribute("user_id",userService.getCurrentUser().getId());
        return "mycar";
    }

    @RequestMapping("/update/car/{id}")
    public String updateCar(@PathVariable("id") long id, Model model){
        model.addAttribute("car",carsRepo.findById(id).get());
        model.addAttribute("category", categoryRepo.findAll());
        return "carForm";
    }

    @RequestMapping("/delete/car/{id}")
    public String delCar(@PathVariable("id") long id){
        carsRepo.deleteById(id);
        return "redirect:/";
    }

    // End of the car section.

    //Start of the category

    @GetMapping("/addCategory")
    public String makeACategory(Model model){
        model.addAttribute("category",new Category());
        return "addCategory";
    }

    @PostMapping("/process")
    public String processForm(@Valid Category category, BindingResult result){
        if (result.hasErrors()){
            return "addCategory";
        }
        categoryRepo.save(category);
        return "redirect:/";
    }

    @RequestMapping("/mypost")
    public String showMyCars(Model model){
       long creatorID= userService.getCurrentUser().getId();
        ArrayList<Cars> results =(ArrayList<Cars>)
                carsRepo.findByCreatorID(creatorID);

        //if(userService.getCurrentUser().getId()== creatorID){
            model.addAttribute("results",results);
            System.out.println("results found");
//            return "mypost";
//        }
        return "mypost";
    }



}