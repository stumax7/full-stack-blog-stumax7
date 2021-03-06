package org.wcci.blog.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.wcci.blog.models.Category;
import org.wcci.blog.storage.CategoryStorage;
import org.wcci.blog.storage.PostStorage;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class WebLayerTest {

    @MockBean
    CategoryStorage mockStorage;
    @MockBean
    PostStorage postStorage;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void categoriesShouldBeOKAndReturnTheCategoriesViewwithCategoriesModelAttribute() throws Exception{
        mockMvc.perform(get("/categories"))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(view().name("categoriesView"))
          .andExpect(model().attributeExists("categories"));
    }
    @Test
    public void shouldReceiveOKFromSingleCategoryEndpoint() throws Exception{
        Category testCategory = new Category("Media");
        when(mockStorage.findCategoryByName("Media")).thenReturn(testCategory);
        mockMvc.perform(get("/categories/Media"))
                .andExpect(status().isOk())
                .andExpect(view().name("categoryView"))
                .andExpect(model().attributeExists("category"));
    }
}
