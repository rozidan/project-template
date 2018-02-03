package com.company.template.server.web.controllers;

import com.company.template.client.web.dtos.ProductDto;
import com.company.template.client.web.dtos.catalogue.AvgPriceResponse;
import com.company.template.client.web.dtos.catalogue.CatalogueResponse;
import com.company.template.client.web.dtos.errors.ErrorDto;
import com.company.template.server.services.ProductService;
import com.github.rozidan.springboot.logger.Loggable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Idan Rozenfeld
 */
@Loggable(ignore = Exception.class)
@Api(tags = "Catalogue")
@RestController
@RequestMapping(path = "/catalogue")
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CatalogueController {

    private final ProductService productService;

    @ApiOperation(value = "Catalogue new product or update existing one")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Successfully catalogue product"),
            @ApiResponse(code = HttpServletResponse.SC_CONFLICT, message = "Invalid product info", response = ErrorDto.class)})
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public CatalogueResponse catalogue(@ApiParam(required = true) @Validated @RequestBody ProductDto productDto) {
        return CatalogueResponse.of(productService.catalogue(productDto));
    }

    @ApiOperation(value = "Catalogue new product or update existing one")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Successfully replace product"),
            @ApiResponse(code = HttpServletResponse.SC_CONFLICT, message = "Invalid product info", response = ErrorDto.class)})
    @ResponseStatus(code = HttpStatus.OK)
    @PutMapping("/{id:\\d+}")
    public void replace(@ApiParam(required = true) @Validated @RequestBody ProductDto productDto, @PathVariable("id") long id) {
        productService.replace(productDto, id);
    }

    @ApiOperation(value = "Fetch catalogue with paging")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query",
                    value = "Number of records per page")})
    @ApiResponse(code = HttpServletResponse.SC_OK, message = "Successfully lists Products")
    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping
    public Page<ProductDto> fetch(@ApiIgnore @PageableDefault final Pageable pageable) {
        return productService.fetch(pageable);
    }

    @ApiOperation("Remove product from catalog")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Product has been removed from catalog"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Product not found")})
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id:\\d+}")
    public void remove(@PathVariable("id") long id) {
        productService.remove(id);
    }

    @ApiOperation("Gets the catalogue price average")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Product has been fetched"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Product not found")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/avgPrice")
    public AvgPriceResponse avgPrice() {
        return AvgPriceResponse.of(productService.getProductPriceAvg());
    }

    @ApiOperation("Fetch single product by id")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Product has beenfetched"),
            @ApiResponse(code = HttpServletResponse.SC_NOT_FOUND, message = "Product not found")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id:\\d+}")
    public ProductDto fetchSingle(@PathVariable("id") long id) {
        return productService.get(id);
    }
}