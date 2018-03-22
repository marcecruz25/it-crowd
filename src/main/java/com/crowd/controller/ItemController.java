package com.crowd.controller;

import com.crowd.dto.Response;
import com.crowd.model.Word;
import com.crowd.service.ItemService;
import com.crowd.util.Constants;
import com.crowd.util.CustomComparatorWord;
import com.crowd.util.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by marce on 3/21/18.
 */
@RestController
@RequestMapping("/api/v1")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public ResponseEntity<Response> getAllItems() {
        List<Word> listas = new ArrayList<>();
        listas = itemService.findAll();
        Collections.sort(listas);
        Response<Object> words = Response.builder().status(HttpStatus.OK.toString()).message(String.valueOf(Constants.ResponseConstants.SUCCESS)).details(listas).build();
        return new ResponseEntity<Response>(words, HttpStatus.OK);
    }

    /*Devolver los items segun lo indique el FE(Front-End) o el usuario a traves de un curl o post*/

    @RequestMapping(value = "/items/custom", method = RequestMethod.GET)
    public ResponseEntity<Response> getAllItemsBySort(@RequestParam(required = false) String criteriaSort) {
        List<Word> listas = new ArrayList<>();
        listas = itemService.findAll();
        if ("ASC".equals(criteriaSort)) {
            Collections.sort(listas, new CustomComparatorWord(false));
        } else {
            Collections.sort(listas, new CustomComparatorWord(true));
        }
        Response<Object> words = Response.builder().status(HttpStatus.OK.toString()).message(String.valueOf(Constants.ResponseConstants.SUCCESS)).details(listas).build();
        return new ResponseEntity<Response>(words, HttpStatus.OK);
    }

    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    public ResponseEntity<Response> getItem(@PathVariable("id") long id) {
        HttpStatus statusCode = HttpStatus.OK;
        Response<Object> response;
        Word currentWord = itemService.finById(id);
        if (currentWord == null) {
            statusCode = HttpStatus.NOT_FOUND;
            response = Response.builder().status(statusCode.toString()).message(String.valueOf(Constants.ResponseConstants.FAILURE)).details(id).build();
        } else {
            response = Response.builder().status(HttpStatus.OK.toString()).message(String.valueOf(Constants.ResponseConstants.SUCCESS)).details(currentWord).build();
        }

        return new ResponseEntity<Response>(response, statusCode);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/item", method = RequestMethod.POST)
    public ResponseEntity<Response> createItem(@RequestBody Word word) {
        HttpStatus statusCode = HttpStatus.CREATED;
        Response<Object> response;

        if (Validations.isDataValid(word)) {
            boolean isCreated = itemService.create(word);
            if (isCreated) {
                response = Response.builder().status(statusCode.toString()).message(String.valueOf(Constants.ResponseConstants.SUCCESS)).details(word).build();
            } else {
                statusCode = HttpStatus.CONFLICT;
                response = Response.builder().status(statusCode.toString()).message(String.valueOf(Constants.ResponseConstants.EXISTS)).details(word).build();
            }
        } else {
            statusCode = HttpStatus.NOT_ACCEPTABLE;
            response = Response.builder().status(statusCode.toString()).message(String.valueOf(Constants.ResponseConstants.FAILURE)).details(word).build();
        }
        return new ResponseEntity<Response>(response, statusCode);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/item/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Response> updateItem(@PathVariable("id") long id, @RequestBody Word word) {
        Word currentWord = itemService.finById(id);
        if (currentWord == null) {
            Response<Object> response = Response.builder().status(HttpStatus.NOT_FOUND.toString()).message(String.valueOf(Constants.ResponseConstants.FAILURE)).details(word).build();
            return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
        }
        if (Validations.isDataValid(word)) {
            currentWord.setNombre(word.getNombre());
            itemService.update(currentWord);
            Response<Object> response = Response.builder().status(HttpStatus.OK.toString()).message(String.valueOf(Constants.ResponseConstants.SUCCESS)).details(currentWord).build();
            return new ResponseEntity<Response>(response, HttpStatus.OK);
        } else {
            Response<Object> response = Response.builder().status(HttpStatus.NOT_ACCEPTABLE.toString()).message(String.valueOf(Constants.ResponseConstants.FAILURE)).details(word).build();
            return new ResponseEntity<Response>(response, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /*Metodo exclusivo para hacer una busqueda con paginado*/

    @RequestMapping(value = "/items/criteria", method = RequestMethod.GET)
    public ResponseEntity<Response> getAllItemsBySort(@RequestParam(required = false) Integer pagina,
                                                      @RequestParam(required = false) Integer tamanio,
                                                      @RequestParam(required = false) String criteriaSort) {
        Page words = itemService.findAllByCriteria(pagina, tamanio, criteriaSort);
        Response<Object> response = Response.builder().status(HttpStatus.OK.toString()).message(String.valueOf(Constants.ResponseConstants.SUCCESS)).details(words.getContent()).build();
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

}
