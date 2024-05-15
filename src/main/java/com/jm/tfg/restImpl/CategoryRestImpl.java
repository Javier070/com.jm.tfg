package com.jm.tfg.restImpl;

import com.jm.tfg.Entidades.Category;
import com.jm.tfg.constantes.TfgConstants;
import com.jm.tfg.utils.TfgUtils;
import com.jm.tfg.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;



@Slf4j
@RestController
@RequestMapping(path = "/category")
public class CategoryRestImpl {

    @Autowired
    CategoryService categoryService;


    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> getAllCategoria(@RequestParam(required = false) String filterValue){
        try{
            return  categoryService.getAllCategoria(filterValue);
        }catch (Exception ex){
            log.error("Error al obtener todas las categorias", ex);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
    }

    @PostMapping("/agregar")
    public ResponseEntity<String>agregarNuevaCategoria(@RequestBody Map<String, String>requestMap){
        try {
                return categoryService.agregarNuevaCategoria(requestMap);
        }catch (Exception ex){
           log.error("Error al agregar categoria", ex);
        }
     return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }




    @PostMapping(path = "/update")
    ResponseEntity<String>updateCategory(@RequestBody Map<String,String> requestMap){
        try{
                return  categoryService.updateCategory(requestMap);
        }catch (Exception ex){
            log.error("Error al actualizar categoria en el controlador", ex);

        }

        return TfgUtils.getResponseEntity(TfgConstants.ALGO_SALE_MAL+"en el controlador de update", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}