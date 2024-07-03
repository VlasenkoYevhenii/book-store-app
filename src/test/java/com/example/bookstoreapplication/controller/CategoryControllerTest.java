package com.example.bookstoreapplication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookstoreapplication.dto.category.CategoryDto;
import com.example.bookstoreapplication.dto.category.CategoryRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
    protected static MockMvc mockMvc;
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private static final String API = "/api/categories";
    private static final String GET_ID = "/1";
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;
    private static List<CategoryDto> dtoList;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();

        dtoList = List.of(
                new CategoryDto().setId(1L).setName("FANTASY"),
                new CategoryDto().setId(2L).setName("FICTION"),
                new CategoryDto().setId(1L).setName("DRAMA")
        );
    }

    @Test
    @DisplayName("""
            Verify createCategory() method""")
    @Sql(scripts = "classpath:database/book/cleanup_tables.sql")
    @WithMockUser(username = "admin", roles = ADMIN)
    void createCategory_AllValidConditions_ReturnsCategoryDto() throws Exception {
        CategoryRequestDto requestDto = new CategoryRequestDto()
                .setName("Test category")
                .setDescription("Description");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult mvcResult = mockMvc.perform(
                        post(API)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andReturn();

        CategoryDto expected = new CategoryDto()
                .setName(requestDto.getName())
                .setDescription(requestDto.getDescription());

        CategoryDto actual = objectMapper.readValue(mvcResult
                .getResponse()
                .getContentAsString(), CategoryDto.class);

        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    @DisplayName("""
            Verify getAll() method""")
    @Sql(scripts = {"classpath:database/book/cleanup_tables.sql",
            "classpath:database/category/add_categories_to_table.sql"})
    @WithMockUser(username = "user", roles = USER)
    void getAll_AllValidConditions_ReturnsListOfCategoryDto() throws Exception {
        MvcResult result = mockMvc.perform(
                        get(API)
                ).andExpect(status().isOk())
                .andReturn();

        List<CategoryDto> expected = dtoList;

        List<CategoryDto> actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(FIRST).getName(), actual.get(FIRST).getName());
        assertEquals(expected.get(SECOND).getName(), actual.get(SECOND).getName());
        assertEquals(expected.get(THIRD).getName(), actual.get(THIRD).getName());
    }

    @Test
    @DisplayName("Verify getCategoryById() method")
    @Sql(scripts = {"classpath:database/book/cleanup_tables.sql",
            "classpath:database/category/add_categories_to_table.sql"})
    @WithMockUser(username = "user", roles = USER)
    void getCategoryById_AllValidConditions_ReturnCategoryDto() throws Exception {
        MvcResult result = mockMvc.perform(
                        get(API + GET_ID)
                ).andExpect(status().isOk())
                .andReturn();

        CategoryDto expected = dtoList.get(FIRST);
        CategoryDto actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                CategoryDto.class);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Verify deleteById() method""")
    @Sql(scripts = {"classpath:database/book/cleanup_tables.sql",
            "classpath:database/category/add_categories_to_table.sql"})
    @WithMockUser(username = "admin", roles = ADMIN)
    void updateCategory_AllValidConditions_ReturnCategoryDto() throws Exception {
        CategoryRequestDto updateDto = new CategoryRequestDto()
                .setName("New name")
                .setDescription("New description");
        String jsonRequest = objectMapper.writeValueAsString(updateDto);
        MvcResult result = mockMvc.perform(
                        put(API + GET_ID)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();

        CategoryDto resultDto = objectMapper.readValue(result.getResponse().getContentAsString(),
                CategoryDto.class);

        assertEquals(updateDto.getName(), resultDto.getName());
        assertEquals(updateDto.getDescription(), resultDto.getDescription());

    }
}
