package com.example.listadecompras.controller;

import com.example.listadecompras.model.produto;
import com.example.listadecompras.repository.repository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/produtos")
public class produtoController {

    @Autowired
    public repository repo;

    @GetMapping("")
    public ModelAndView inicio(){



        List<produto> bag = repo.findAll();
        ModelAndView mv = new ModelAndView("produtos/produtos");
        mv.addObject("produtos", bag);

        return mv;
    }

    @GetMapping(path = "/new")
    public String novo(){
        return "produtos/form";
    }



    @PostMapping("")
    public ModelAndView create(@Valid produto products, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println("******** Error ********");

            ModelAndView mv = new ModelAndView("redirect:/produtos/new");
            return mv;
        }else{
            this.repo.save(products);
            return new ModelAndView("redirect:/produtos");
        }

    }

    @GetMapping("/{id}")
    public ModelAndView show (@PathVariable Long id){
        Optional<produto> optional = this.repo.findById(id);
        if(optional.isPresent()){
            produto product = optional.get();
            ModelAndView mv = new ModelAndView("produtos/show");
            mv.addObject("produto", product);
            return mv;
        }else{
            System.out.println("Didn't find any information with the ID" + id);
            return new ModelAndView("redirect:/produtos");

        }

    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id){
        Optional<produto> optional = this.repo.findById(id);

        if(optional.isPresent()){
            produto product = optional.get();
            ModelAndView mv = new ModelAndView("produtos/edit");
            mv.addObject("produto", product);
            mv.addObject("produtoId", product.getId());
            return mv;
        }else{
            System.out.println("Didn't find any information with the ID" + id);
            return new ModelAndView("redirect:/produtos");
        }
    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid produto products, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
           System.out.println("******** Error ********");

            ModelAndView mv = new ModelAndView("redirect:/produtos");
            return mv;
        }else{
            Optional<produto> optional = this.repo.findById(id);
            if(optional.isPresent()){
                produto product = optional.get();
                product.setNome(products.getNome());
                product.setPreco(products.getPreco());
                product.setQuantidade(products.getQuantidade());
                this.repo.save(product);
                return new ModelAndView("redirect:/produtos/" + product.getId());
            }else{
                System.out.println("Didn't find any information with the ID" + id);
                return new ModelAndView("redirect:/produtos");
            }

        }

    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        try{
            this.repo.deleteById(id);
            return "redirect:/produtos";
        }catch (EmptyResultDataAccessException e){
            System.out.println(e);
            return "redirect:/produtos";
        }

    }



}
