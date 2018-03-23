package com.crowd.controller;

import com.crowd.dto.Response;
import com.crowd.model.Word;
import com.crowd.service.ItemService;
import com.crowd.util.Constants;
import com.crowd.util.CustomComparatorWord;
import com.crowd.util.Validations;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation(value = "Retorna todos los item y siempre esta ordenado en forma ASCENDENTE")
    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public ResponseEntity<Response> getAllItems() {
        List<Word> listas = new ArrayList<>();
        listas = itemService.findAll();
        Collections.sort(listas);
        Response<Object> words = Response.builder().status(HttpStatus.OK.toString()).message(String.valueOf(Constants.ResponseConstants.SUCCESS)).details(listas).build();
        return new ResponseEntity<Response>(words, HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna todos los Items y se le pasa un parametro para ordenar de manera ASC o DESC, si no se especifica nada lo ordena por ASC")
    @RequestMapping(value = "/items/custom", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "criteriaSort", value = "Puede ser ASC o DESC, por default es ASC", required = false, dataType = "string", paramType = "query")})
    public ResponseEntity<Response> getAllItemsBySort(@RequestParam(required = false) String criteriaSort) {
        List<Word> listas = new ArrayList<>();
        listas = itemService.findAll();
        if ("DESC".equalsIgnoreCase(criteriaSort)) {
            Collections.sort(listas, new CustomComparatorWord(false));
        } else {
            Collections.sort(listas, new CustomComparatorWord(true));
        }
        Response<Object> words = Response.builder().status(HttpStatus.OK.toString()).message(String.valueOf(Constants.ResponseConstants.SUCCESS)).details(listas).build();
        return new ResponseEntity<Response>(words, HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna item por ID")
    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id de la palabra", required = true, dataType = "integer", paramType = "query")})
    public ResponseEntity<Response> getItem(@PathVariable("id") long id) {
        HttpStatus statusCode = HttpStatus.OK;
        Response<Object> response;
        Word currentWord = itemService.finById(id);
        if (currentWord == null) {
            statusCode = HttpStatus.NOT_FOUND;
            response = Response.builder().status(statusCode.toString()).message(String.valueOf(Constants.ResponseConstants.FAILURE)).details(id).build();
        } else {
            response = Response.builder().status(statusCode.toString()).message(String.valueOf(Constants.ResponseConstants.SUCCESS)).details(currentWord).build();
        }

        return new ResponseEntity<Response>(response, statusCode);
    }

    @ApiOperation(value = "Crea un Item")
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

    @ApiOperation(value = "Modifica un item, si es que existe")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/item/{id}", method = RequestMethod.PUT)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id de la palabra", required = true, dataType = "integer", paramType = "query")})
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

    @ApiOperation(value = "Elimina un item, si es que existe")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/item/{id}", method = RequestMethod.DELETE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Id de la palabra", required = true, dataType = "integer", paramType = "query")})
    public ResponseEntity<Response> deleteItem(@PathVariable("id") long id) {
        Word currentWord = itemService.finById(id);
        if (currentWord == null) {
            Response<Object> response = Response.builder().status(HttpStatus.NOT_FOUND.toString()).message(String.valueOf(Constants.ResponseConstants.FAILURE)).details(id).build();
            return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
        }
        itemService.delete(id);
        Response<Object> response = Response.builder().status(HttpStatus.OK.toString()).message(String.valueOf(Constants.ResponseConstants.SUCCESS)).details(currentWord).build();
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Metodo exclusivo para hacer una busqueda con paginado usando spring data")
    @RequestMapping(value = "/items/criteria", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pagina", value = "Indica el numero de Pagina", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "tamanio", value = "Indica la cantidad que queremos de esa pagina", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "criteriaSort", value = "Puede ser ASC o DESC, por default es ASC", required = false, dataType = "string", paramType = "query")})
    public ResponseEntity<Response> getAllItemsBySort(@RequestParam(required = false) Integer pagina,
                                                      @RequestParam(required = false) Integer tamanio,
                                                      @RequestParam(required = false) String criteriaSort) {
        Page words = itemService.findAllByCriteria(pagina, tamanio, criteriaSort);
        Response<Object> response = Response.builder().status(HttpStatus.OK.toString()).message(String.valueOf(Constants.ResponseConstants.SUCCESS)).details(words.getContent()).build();
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

}
